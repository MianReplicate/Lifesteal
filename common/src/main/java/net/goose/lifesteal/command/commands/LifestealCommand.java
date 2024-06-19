package net.goose.lifesteal.command.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.component.ModDataComponents;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.HealthData;
import net.goose.lifesteal.util.ModUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class LifestealCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("ls")
                        .then(Commands.literal("reviveplayer")
                                .requires((commandSource -> commandSource.hasPermission(LifeSteal.config.permissionLevelForRevival.get())))
                                .then(Commands.argument("players", GameProfileArgument.gameProfile())
                                        .suggests((commandContext, suggestionsBuilder) -> {
                                            UserBanList userBanList = commandContext.getSource().getServer().getPlayerList().getBans();
                                            ArrayList<String> suggestList = new ArrayList<>();
                                            userBanList.getEntries().forEach(entry -> {
                                                if(entry.getSource().matches(LifeSteal.MOD_ID)){
                                                    suggestList.add(entry.getUser().getName());
                                                }
                                            });
                                            return SharedSuggestionProvider.suggest(suggestList, suggestionsBuilder);
                                        })
                                        .executes((command) -> revivePlayer(command.getSource(), GameProfileArgument.getGameProfiles(command, "players"), null, true, false))
                                        .then(Commands.argument("location", Vec3Argument.vec3())
                                                .executes((command) -> revivePlayer(command.getSource(), GameProfileArgument.getGameProfiles(command, "players"),Vec3Argument.getVec3(command, "location"), true, false))
                                                .then(Commands.argument("enableLightning", BoolArgumentType.bool())
                                                        .executes((command) -> revivePlayer(command.getSource(), GameProfileArgument.getGameProfiles(command, "players"),Vec3Argument.getVec3(command, "location"), BoolArgumentType.getBool(command, "enableLightning"), false))
                                                        .then(Commands.argument("silentlyRevive", BoolArgumentType.bool())
                                                                .executes((command) -> revivePlayer(command.getSource(), GameProfileArgument.getGameProfiles(command, "players"),Vec3Argument.getVec3(command, "location"), BoolArgumentType.getBool(command, "enableLightning"), BoolArgumentType.getBool(command, "silentlyRevive"))))))))
                        .then(Commands.literal("withdraw")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForWithdraw.get()))
                                .executes((command) -> withdraw(command.getSource(), 1))
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                        .executes((command) -> withdraw(command.getSource(), IntegerArgumentType.getInteger(command, "amount")))))
                        .then(Commands.literal("gethitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForGettingHitPoints.get()))
                                .executes((command) -> getHitPoint(command.getSource()))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes((command) -> getHitPoint(command.getSource(), EntityArgument.getPlayer(command, "player"))))
                        )
                        .then(Commands.literal("sethitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForSettingHitPoints.get()))
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes((command) -> setHitPoint(command.getSource(), IntegerArgumentType.getInteger(command, "amount"))))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes((command) -> setHitPoint(command.getSource(), EntityArgument.getPlayer(command, "player"), IntegerArgumentType.getInteger(command, "amount")))))));
    }

    private static int revivePlayer(CommandSourceStack source, Collection<GameProfile> gameProfiles, @Nullable Vec3 position, boolean enableLightningEffect, boolean silentRevive){
        if(position == null && !source.isPlayer()){
            source.sendFailure(Component.translatable("chat.message.lifesteal.revived_player_command_failed"));
        } else{
            position = position == null ? source.getPlayer().position() : position;
            Vec3 finalPosition = position;
            gameProfiles.forEach(gameProfile ->
            {
                    boolean success = ModUtil.revivePlayer(
                            source.getLevel(),
                            new BlockPos((int) finalPosition.x, (int) finalPosition.y, (int) finalPosition.z),
                            gameProfile,
                            enableLightningEffect,
                            silentRevive,
                            null,
                            source.getServer().getPlayerList().getBans());
                    if(success)
                        source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.revived_player_command_success", gameProfile.getName()), true);
            });
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int withdraw(CommandSourceStack source, int amount) throws CommandSyntaxException {
        ServerPlayer serverPlayer = source.getPlayerOrException();

        final int maximumheartsLoseable = LifeSteal.config.maximumHealthLoseable.get();
        final int startingHitPointDifference = LifeSteal.config.startingHealthDifference.get();
        String advancementUsed = (String) LifeSteal.config.advancementUsedForWithdrawing.get();

        if (serverPlayer.getAdvancements().getOrStartProgress(Advancement.Builder.advancement().build(ResourceLocation.tryParse(advancementUsed))).isDone() || advancementUsed.isEmpty() || serverPlayer.isCreative()) {
            HealthData IHeartCap = HealthData.get(serverPlayer).get();

            int heartDifference = IHeartCap.getHealthDifference() - (LifeSteal.config.heartCrystalAmountGain.get() * amount);

            if (maximumheartsLoseable >= 0) {
                if (heartDifference < startingHitPointDifference - maximumheartsLoseable) {
                    serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.can't_withdraw_less_than_minimum"), true);
                    return Command.SINGLE_SUCCESS;
                }
            }else if(heartDifference <= IHeartCap.getHPDifferenceRequiredForBan()) {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.can't_withdraw_less_than_amount_have"), true);
                return Command.SINGLE_SUCCESS;
            }

            IHeartCap.setHealthDifference(heartDifference);
            IHeartCap.refreshHealth(false);

            ItemStack heartCrystal = new ItemStack(ModItems.HEART_CRYSTAL.get(), amount);
            heartCrystal.set(ModDataComponents.UNFRESH.get(), true);
            heartCrystal.set(DataComponents.CUSTOM_NAME, Component.translatable("item.lifesteal.heart_crystal.unnatural"));
            if (serverPlayer.getInventory().getFreeSlot() == -1) {
                serverPlayer.drop(heartCrystal, true);
            } else {
                serverPlayer.getInventory().add(heartCrystal);
            }
        } else {
            String text = (String) LifeSteal.config.textUsedForRequirementOnWithdrawing.get();
            if (!text.isEmpty()) {
                serverPlayer.displayClientMessage(Component.literal((String) LifeSteal.config.textUsedForRequirementOnWithdrawing.get()), true);
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source) throws CommandSyntaxException {
        LivingEntity playerthatsentcommand = source.getPlayerOrException();
        HealthData.get(playerthatsentcommand).ifPresent(iHeartData -> source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.get_hit_point_for_self", iHeartData.getHealthDifference()), false));
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source, ServerPlayer chosenPlayer) throws CommandSyntaxException {
        HealthData.get(chosenPlayer).ifPresent(iHeartData ->
                source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.get_hit_point_for_player", chosenPlayer.getName().getString(), iHeartData.getHealthDifference()), false)
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, int amount) throws CommandSyntaxException {
        LivingEntity player = source.getPlayerOrException();
        HealthData.get(player).ifPresent(IHeartCap -> {
            IHeartCap.setHealthDifference(amount);
            IHeartCap.refreshHealth(false);
        });

        source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.set_hit_point_for_self", amount), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, ServerPlayer chosenPlayer, int amount) throws CommandSyntaxException {
        HealthData.get(chosenPlayer).ifPresent(IHeartCap -> {
            IHeartCap.setHealthDifference(amount);
            IHeartCap.refreshHealth(false);
        });

        source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.set_hit_point_for_player", chosenPlayer.getName().getString(), amount), true);

        if (LifeSteal.config.tellPlayersIfHitPointChanged.get()) {
            chosenPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.set_hit_point_for_self", amount));
        }

        return Command.SINGLE_SUCCESS;
    }
}
