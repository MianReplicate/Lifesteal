package net.goose.lifesteal.command.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.HealthData;
import net.goose.lifesteal.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
            final int maximumheartsLoseable = LifeSteal.config.maximumamountofheartsLoseable.get();
            final int startingHitPointDifference = LifeSteal.config.startingHeartDifference.get();

            LivingEntity playerthatsentcommand = source.getPlayer();
            if (playerthatsentcommand instanceof Player player) {
                String advancementUsed = (String) LifeSteal.config.advancementUsedForWithdrawing.get();

                if (source.getPlayer().getAdvancements().getOrStartProgress(Advancement.Builder.advancement().build(new ResourceLocation(advancementUsed))).isDone() || advancementUsed.isEmpty()) {
                    AtomicInteger heartDifference = new AtomicInteger();
                    HealthData.get(playerthatsentcommand).ifPresent(IHeartCap ->
                            heartDifference.set(IHeartCap.getHeartDifference() - (LifeSteal.config.heartCrystalAmountGain.get() * amount)));

                    if (maximumheartsLoseable >= 0) {
                        if (heartDifference.get() < startingHitPointDifference - maximumheartsLoseable) {
                            player.displayClientMessage(Component.translatable("gui.lifesteal.can't_withdraw_less_than_minimum"), true);
                            return Command.SINGLE_SUCCESS;
                        }
                    }
                    HealthData.get(playerthatsentcommand).ifPresent(IHeartCap ->
                    {
                        IHeartCap.setHeartDifference(heartDifference.get());
                        IHeartCap.refreshHearts(false);
                    });

                    ItemStack heartCrystal = new ItemStack(ModItems.HEART_CRYSTAL.get(), amount);
                    CompoundTag compoundTag = heartCrystal.getOrCreateTagElement("lifesteal");
                    compoundTag.putBoolean("Fresh", false);
                    heartCrystal.setHoverName(Component.translatable("item.lifesteal.heart_crystal.unnatural"));
                    if (player.getInventory().getFreeSlot() == -1) {
                        player.drop(heartCrystal, true);
                    } else {
                        player.getInventory().add(heartCrystal);
                    }
                } else {
                    String text = (String) LifeSteal.config.textUsedForRequirementOnWithdrawing.get();
                    if (!text.isEmpty()) {
                        player.displayClientMessage(Component.literal((String) LifeSteal.config.textUsedForRequirementOnWithdrawing.get()), true);
                    }
                }
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source) throws CommandSyntaxException {
        if (source.isPlayer()) {
            LivingEntity playerthatsentcommand = source.getPlayer();
            HealthData.get(playerthatsentcommand).ifPresent(HeartCap -> playerthatsentcommand.sendSystemMessage(Component.translatable("Your HitPoint difference is " + HeartCap.getHeartDifference() + ".")));
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int getHitPoint(CommandSourceStack source, Entity chosenentity) throws CommandSyntaxException {

        LivingEntity playerthatsentcommand = source.getPlayer();

        if (!source.isPlayer()) {
            HealthData.get(chosenentity).ifPresent(HeartCap -> LifeSteal.LOGGER.info(chosenentity.getName().getString() + "'s HitPoint difference is " + HeartCap.getHeartDifference() + "."));
        } else {
            HealthData.get(chosenentity).ifPresent(HeartCap -> playerthatsentcommand.sendSystemMessage(Component.translatable(chosenentity.getName().getString() + "'s HitPoint difference is " + HeartCap.getHeartDifference() + ".")));
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, int amount) throws CommandSyntaxException {
        if (source.isPlayer()) {
            LivingEntity playerthatsentcommand = source.getPlayer();
            HealthData.get(playerthatsentcommand).ifPresent(IHeartCap -> {
                IHeartCap.setHeartDifference(amount);
                IHeartCap.refreshHearts(false);
            });

            playerthatsentcommand.sendSystemMessage(Component.translatable("Your HitPoint difference has been set to " + amount));
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int setHitPoint(CommandSourceStack source, Entity chosenentity, int amount) throws CommandSyntaxException {

        LivingEntity playerthatsentcommand = source.getPlayer();

        HealthData.get(chosenentity).ifPresent(IHeartCap -> {
            IHeartCap.setHeartDifference(amount);
            IHeartCap.refreshHearts(false);
        });

        if (chosenentity != playerthatsentcommand && source.isPlayer()) {
            playerthatsentcommand.sendSystemMessage(Component.translatable("Set " + chosenentity.getName().getString() + "'s HitPoint difference to " + amount));
        } else if (!source.isPlayer()) {
            LifeSteal.LOGGER.info("Set " + chosenentity.getName().getString() + "'s HitPoint difference to " + amount);
        }

        if (LifeSteal.config.tellPlayersIfHitPointChanged.get()) {
            chosenentity.sendSystemMessage(Component.translatable("Your HitPoint difference has been set to " + amount));
        }

        return Command.SINGLE_SUCCESS;
    }
}
