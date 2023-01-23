package net.goose.lifesteal.command.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

public class LifestealCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("ls")
                        .then(Commands.literal("withdraw")
                                .requires((commandSource) -> commandSource.hasPermission(Commands.LEVEL_ALL))
                                .executes((command) -> withdraw(command.getSource(), 1))
                                .then(Commands.argument("Amount", IntegerArgumentType.integer())
                                        .executes((command) -> withdraw(command.getSource(), IntegerArgumentType.getInteger(command, "Amount")))))
                        .then(Commands.literal("get-hitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                .executes((command) -> getHitPoint(command.getSource()))
                                .then(Commands.argument("Player", EntityArgument.entity())
                                        .executes((command) -> getHitPoint(command.getSource(), EntityArgument.getEntity(command, "Player"))))
                        )
                        .then(Commands.literal("set-hitpoints")
                                .requires((commandSource) -> commandSource.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                .then(Commands.argument("Amount", IntegerArgumentType.integer())
                                        .executes((command) -> setHitPoint(command.getSource(), IntegerArgumentType.getInteger(command, "Amount"))))
                                .then(Commands.argument("Player", EntityArgument.entity())
                                        .then(Commands.argument("Amount", IntegerArgumentType.integer())
                                                .executes((command) -> setHitPoint(command.getSource(), EntityArgument.getEntity(command, "Player"), IntegerArgumentType.getInteger(command, "Amount")))))));
    }

    private static int withdraw(CommandSourceStack source, int amount) throws CommandSyntaxException {
        if (source.isPlayer()) {
            LivingEntity livingEntity = source.getPlayer();
            ServerPlayer serverPlayer = (ServerPlayer) livingEntity;

            if (!LifeSteal.config.disableWithdrawing.get()) {
                final int maximumheartsLoseable = LifeSteal.config.maximumamountofhitpointsLoseable.get();
                final int startingHitPointDifference = LifeSteal.config.startingHeartDifference.get();
                String advancementUsed = (String) LifeSteal.config.advancementUsedForWithdrawing.get();

                if (serverPlayer.getAdvancements().getOrStartProgress(Advancement.Builder.advancement().build(new ResourceLocation(advancementUsed))).isDone() || advancementUsed.isEmpty() || serverPlayer.isCreative()) {
                    AtomicInteger heartDifference = new AtomicInteger();
                    HealthData.get(livingEntity).ifPresent(IHeartCap ->
                            heartDifference.set(IHeartCap.getHeartDifference() - (LifeSteal.config.heartCrystalAmountGain.get() * amount)));

                    if (maximumheartsLoseable >= 0) {
                        if (heartDifference.get() < startingHitPointDifference - maximumheartsLoseable) {
                            serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.can't_withdraw_less_than_minimum"), true);
                            return Command.SINGLE_SUCCESS;
                        }
                    }
                    HealthData.get(livingEntity).ifPresent(IHeartCap ->
                    {
                        IHeartCap.setHeartDifference(heartDifference.get());
                        IHeartCap.refreshHearts(false);
                    });

                    ItemStack heartCrystal = new ItemStack(ModItems.HEART_CRYSTAL.get(), amount);
                    CompoundTag compoundTag = heartCrystal.getOrCreateTagElement("lifesteal");
                    compoundTag.putBoolean("Unfresh", true);
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
            } else {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.withdrawing_disabled"), true);
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source) throws CommandSyntaxException {
        if (source.isPlayer()) {
            LivingEntity playerthatsentcommand = source.getPlayer();
            HealthData.get(playerthatsentcommand).ifPresent(iHeartData -> playerthatsentcommand.sendSystemMessage(Component.translatable("chat.message.lifesteal.get_hit_point_for_self", iHeartData.getHeartDifference())));
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source, Entity chosenentity) throws CommandSyntaxException {

        LivingEntity playerthatsentcommand = source.getPlayer();

        HealthData.get(chosenentity).ifPresent(iHeartData -> {
            if (!source.isPlayer()) {
                LifeSteal.LOGGER.info(Component.translatable("chat.message.lifesteal.get_hit_point_for_player", chosenentity.getName().getString(), iHeartData.getHeartDifference()).toString());
            } else {
                playerthatsentcommand.sendSystemMessage(Component.translatable("chat.message.lifesteal.get_hit_point_for_player", chosenentity.getName().getString(), iHeartData.getHeartDifference()));
            }
        });
        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, int amount) throws CommandSyntaxException {
        if (source.isPlayer()) {
            LivingEntity playerthatsentcommand = source.getPlayer();
            HealthData.get(playerthatsentcommand).ifPresent(IHeartCap -> {
                IHeartCap.setHeartDifference(amount);
                IHeartCap.refreshHearts(false);
            });

            playerthatsentcommand.sendSystemMessage(Component.translatable("chat.message.lifesteal.set_hit_point_for_self", amount));
        }
        return Command.SINGLE_SUCCESS;
    }
    private static int setHitPoint(CommandSourceStack source, Entity chosenentity, int amount) throws CommandSyntaxException {
        HealthData.get(chosenentity).ifPresent(IHeartCap -> {
            IHeartCap.setHeartDifference(amount);
            IHeartCap.refreshHearts(false);
        });

        if (source.isPlayer()) {
            LivingEntity playerthatsentcommand = source.getPlayer();

            playerthatsentcommand.sendSystemMessage(Component.translatable("chat.message.lifesteal.set_hit_point_for_player", chosenentity.getName().getString(), amount));
        } else if (!source.isPlayer()) {
            LifeSteal.LOGGER.info("chat.message.lifesteal.set_hit_point_for_player", chosenentity.getName().getString(), amount);
        }

        if (LifeSteal.config.tellPlayersIfHitPointChanged.get()) {
            chosenentity.sendSystemMessage(Component.translatable("chat.message.lifesteal.set_hit_point_for_self", amount));
        }

        return Command.SINGLE_SUCCESS;
    }
}
