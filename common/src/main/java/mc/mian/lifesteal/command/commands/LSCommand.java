package mc.mian.lifesteal.command.commands;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.component.LSDataComponents;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class LSCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("ls")
                        .then(Commands.literal("reviveplayer")
                                .requires((commandSource -> commandSource.hasPermission(LifeSteal.config.permissionLevelForRevival.get())))
                                .then(Commands.argument("players", GameProfileArgument.gameProfile())
                                        .suggests((commandContext, suggestionsBuilder) -> {
                                            ArrayList<String> suggestList = new ArrayList<>();
                                            ImmutableMap<GameProfile, LSUtil.KilledType> bannedProfiles = LSUtil.getDeadPlayers(commandContext.getSource().getServer());
                                            bannedProfiles.forEach((profile, killedType) -> suggestList.add(profile.getName()));

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
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1, 99))
                                        .executes((command) -> withdraw(command.getSource(), IntegerArgumentType.getInteger(command, "amount")))))
                        .then(Commands.literal("gethitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForGettingHitPoints.get()))
                                .executes((command) -> getHitPoint(command.getSource(), List.of(Objects.requireNonNull(command.getSource().getPlayer()).getGameProfile())))
                                .then(Commands.argument("players", GameProfileArgument.gameProfile())
                                        .suggests(((context, builder) -> {
                                            ArrayList<String> suggestList = new ArrayList<>();
                                            List<GameProfile> gameProfiles = LSUtil.getGameProfiles(context.getSource().getServer(), true);
                                            gameProfiles.forEach(gameProfile -> suggestList.add(gameProfile.getName()));
                                            return SharedSuggestionProvider.suggest(suggestList, builder);
                                        }))
                                        .executes((command) -> getHitPoint(command.getSource(), GameProfileArgument.getGameProfiles(command, "players"))))
                        )
                        .then(Commands.literal("sethitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForSettingHitPoints.get()))
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes((command) -> setHitPoint(command.getSource(), List.of(Objects.requireNonNull(command.getSource().getPlayer()).getGameProfile()), IntegerArgumentType.getInteger(command, "amount"))))
                                .then(Commands.argument("players", GameProfileArgument.gameProfile())
                                        .suggests(((context, builder) -> {
                                            ArrayList<String> suggestList = new ArrayList<>();
                                            List<GameProfile> gameProfiles = LSUtil.getGameProfiles(context.getSource().getServer(), true);
                                            gameProfiles.forEach(gameProfile -> suggestList.add(gameProfile.getName()));
                                            return SharedSuggestionProvider.suggest(suggestList, builder);
                                        }))
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes((command) -> setHitPoint(command.getSource(), GameProfileArgument.getGameProfiles(command, "players"), IntegerArgumentType.getInteger(command, "amount")))))));
    }

    private static int revivePlayer(CommandSourceStack source, Collection<GameProfile> gameProfiles, @Nullable Vec3 position, boolean enableLightningEffect, boolean silentRevive){
        if(position == null && !source.isPlayer()){
            source.sendFailure(Component.translatable("chat.message.lifesteal.revived_player_failed"));
        } else{
            position = position == null ? source.getPlayer().position() : position;
            Vec3 finalPosition = position;
            gameProfiles.forEach(gameProfile ->
            {
                    boolean success = LSUtil.revivePlayer(
                            source.getLevel(),
                            new BlockPos((int) finalPosition.x, (int) finalPosition.y, (int) finalPosition.z),
                            gameProfile,
                            enableLightningEffect,
                            silentRevive,
                            null);
                    if(success)
                        source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.revived_player_success", gameProfile.getName()), true);
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
            LSData lifestealData = LSData.get(serverPlayer).get();

            int newHealthDifference = (int) lifestealData.getValue(LSConstants.HEALTH_DIFFERENCE) - (LifeSteal.config.heartCrystalAmountGain.get() * amount);

            if (maximumheartsLoseable >= 0) {
                if (newHealthDifference < startingHitPointDifference - maximumheartsLoseable) {
                    serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.cant_withdraw_less_than_maximum"), true);
                    return Command.SINGLE_SUCCESS;
                }
            }else if(newHealthDifference <= lifestealData.getHPDifferenceRequiredForBan()) {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.cant_withdraw_less_than_amount_have"), true);
                return Command.SINGLE_SUCCESS;
            }

            lifestealData.setValue(LSConstants.HEALTH_DIFFERENCE,newHealthDifference);
            lifestealData.refreshHealth(false);

            ItemStack heartCrystal = new ItemStack(LSItems.HEART_CRYSTAL.get(), amount);
            heartCrystal.set(LSDataComponents.UNFRESH.get(), true);
            heartCrystal.set(DataComponents.CUSTOM_NAME, Component.translatable("item.lifesteal.heart_crystal.unnatural"));
            boolean given = serverPlayer.getInventory().add(heartCrystal);
            if (!given) {
                serverPlayer.drop(heartCrystal, false);
            }
        } else {
            String text = (String) LifeSteal.config.textUsedForRequirementOnWithdrawing.get();
            if (!text.isEmpty()) {
                serverPlayer.displayClientMessage(Component.literal((String) LifeSteal.config.textUsedForRequirementOnWithdrawing.get()), true);
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source, Collection<GameProfile> gameProfiles) throws CommandSyntaxException {
        gameProfiles.forEach(gameProfile -> {
            ServerPlayer chosenPlayer = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if(chosenPlayer != null){
                LSData.get(chosenPlayer).ifPresent(iLifestealData ->
                        source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.get_hit_point_for_player", gameProfile.getName(), iLifestealData.getValue(LSConstants.HEALTH_DIFFERENCE)), false)
                );
            } else {
                source.sendSuccess(() ->
                        Component.translatable(
                                "chat.message.lifesteal.get_hit_point_for_player",
                                gameProfile.getName(),
                                LSUtil.getLifestealDataFromTag(LSUtil.getPlayerData(source.getServer(), gameProfile), LSConstants.HEALTH_DIFFERENCE.getPath(), CompoundTag::getInt)),
                        false);
            }
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, Collection<GameProfile> gameProfiles, int amount) throws CommandSyntaxException {
        gameProfiles.forEach(gameProfile -> {
            ServerPlayer chosenPlayer = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if(chosenPlayer != null){
                LSData.get(chosenPlayer).ifPresent(iLifestealData ->
                {
                    iLifestealData.setValue(LSConstants.HEALTH_DIFFERENCE,amount);
                    iLifestealData.refreshHealth(false);
                });
                source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.set_hit_point_for_player", chosenPlayer.getName().getString(), amount), true);

                if (LifeSteal.config.tellPlayersIfHitPointChanged.get() && chosenPlayer != source.getPlayer()) {
                    chosenPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.set_hit_point_for_self", amount));
                }
            } else {
                CompoundTag playerTag = LSUtil.getPlayerData(source.getServer(), gameProfile);
                playerTag = LSUtil.setLifestealDataFromTag(playerTag, LSConstants.HEALTH_DIFFERENCE.getPath(), (attachmentsTag, key) ->
                {
                    attachmentsTag.putInt(key, amount);
                    return attachmentsTag;
                });
                LSUtil.savePlayerData(source.getServer(), gameProfile, playerTag);
                source.sendSuccess(() ->
                                Component.translatable(
                                        "chat.message.lifesteal.set_hit_point_for_player",
                                        gameProfile.getName(),
                                        amount),
                        false);
            }
        });

        return Command.SINGLE_SUCCESS;
    }
}
