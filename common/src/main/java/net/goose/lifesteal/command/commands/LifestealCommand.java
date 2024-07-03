package net.goose.lifesteal.command.commands;

import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.LifestealData;
import net.goose.lifesteal.util.ModResources;
import net.goose.lifesteal.util.ModUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class LifestealCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("ls")
                        .then(Commands.literal("reviveplayer")
                                .requires((commandSource -> commandSource.hasPermission(LifeSteal.config.permissionLevelForRevival.get())))
                                .then(Commands.argument("players", GameProfileArgument.gameProfile())
                                        .suggests((commandContext, suggestionsBuilder) -> {
                                            ArrayList<String> suggestList = new ArrayList<>();
                                            ImmutableMap<GameProfile, ModUtil.KilledType> bannedProfiles = ModUtil.getDeadPlayers(commandContext.getSource().getServer());
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
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                        .executes((command) -> withdraw(command.getSource(), IntegerArgumentType.getInteger(command, "amount")))))
                        .then(Commands.literal("gethitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(LifeSteal.config.permissionLevelForGettingHitPoints.get()))
                                .executes((command) -> getHitPoint(command.getSource(), List.of(Objects.requireNonNull(command.getSource().getPlayer()).getGameProfile())))
                                .then(Commands.argument("players", GameProfileArgument.gameProfile())
                                        .suggests(((context, builder) -> {
                                            ArrayList<String> suggestList = new ArrayList<>();
                                            List<GameProfile> gameProfiles = ModUtil.getGameProfiles(context.getSource().getServer(), true);
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
                                            List<GameProfile> gameProfiles = ModUtil.getGameProfiles(context.getSource().getServer(), true);
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
                    boolean success = ModUtil.revivePlayer(
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
            LifestealData lifestealData = LifestealData.get(serverPlayer).get();

            int heartDifference = (int) lifestealData.getValue(ModResources.HEALTH_DIFFERENCE) - (LifeSteal.config.heartCrystalAmountGain.get() * amount);

            if (maximumheartsLoseable >= 0) {
                if (heartDifference < startingHitPointDifference - maximumheartsLoseable) {
                    serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.cant_withdraw_less_than_maximum"), true);
                    return Command.SINGLE_SUCCESS;
                }
            }else if(heartDifference <= lifestealData.getHPDifferenceRequiredForBan()) {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.cant_withdraw_less_than_amount_have"), true);
                return Command.SINGLE_SUCCESS;
            }

            lifestealData.setValue(ModResources.HEALTH_DIFFERENCE,heartDifference);
            lifestealData.refreshHealth(false);

            ItemStack heartCrystal = new ItemStack(ModItems.HEART_CRYSTAL.get(), amount);
            CompoundTag compoundTag = heartCrystal.getOrCreateTagElement(LifeSteal.MOD_ID);
            compoundTag.putBoolean(ModResources.UNFRESH, true);
            heartCrystal.setHoverName(Component.translatable("item.lifesteal.heart_crystal.unnatural"));
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

    private static int getHitPoint(CommandSourceStack source, Collection<GameProfile> gameProfiles) throws CommandSyntaxException {
        gameProfiles.forEach(gameProfile -> {
            ServerPlayer chosenPlayer = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if(chosenPlayer != null){
                LifestealData.get(chosenPlayer).ifPresent(iLifestealData ->
                        source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.get_hit_point_for_player", gameProfile.getName(), iLifestealData.getValue(ModResources.HEALTH_DIFFERENCE)), false)
                );
            } else {
                source.sendSuccess(() ->
                        Component.translatable(
                                "chat.message.lifesteal.get_hit_point_for_player",
                                gameProfile.getName(),
                                ModUtil.getLifestealDataFromTag(ModUtil.getPlayerData(source.getServer(), gameProfile), ModResources.HEALTH_DIFFERENCE.getPath(), CompoundTag::getInt)),
                        false);
            }
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, Collection<GameProfile> gameProfiles, int amount) throws CommandSyntaxException {
        gameProfiles.forEach(gameProfile -> {
            ServerPlayer chosenPlayer = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            if(chosenPlayer != null){
                LifestealData.get(chosenPlayer).ifPresent(iLifestealData ->
                {
                    iLifestealData.setValue(ModResources.HEALTH_DIFFERENCE,amount);
                    iLifestealData.refreshHealth(false);
                });
                source.sendSuccess(() -> Component.translatable("chat.message.lifesteal.set_hit_point_for_player", chosenPlayer.getName().getString(), amount), true);

                if (LifeSteal.config.tellPlayersIfHitPointChanged.get() && chosenPlayer != source.getPlayer()) {
                    chosenPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.set_hit_point_for_self", amount));
                }
            } else {
                CompoundTag playerTag = ModUtil.getPlayerData(source.getServer(), gameProfile);
                playerTag = ModUtil.setLifestealDataFromTag(playerTag, ModResources.HEALTH_DIFFERENCE.getPath(), (attachmentsTag, key) ->
                {
                    attachmentsTag.putInt(key, amount);
                    return attachmentsTag;
                });
                ModUtil.savePlayerData(source.getServer(), gameProfile, playerTag);
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
