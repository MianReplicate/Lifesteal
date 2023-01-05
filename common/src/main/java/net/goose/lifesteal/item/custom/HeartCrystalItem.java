package net.goose.lifesteal.item.custom;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicInteger;

public class HeartCrystalItem extends Item {

    public static final FoodProperties HeartCrystal = (new FoodProperties.Builder()).alwaysEat().build();

    public HeartCrystalItem(Properties pProperties) {
        super(pProperties);
    }

    public void applyCrystalEffect(LivingEntity entity) {
        // Formula, for every hit point, increase duration of the regeneration by 50 ticks: TickDuration = MaxHealth * 50
        int tickTime = (int) (entity.getMaxHealth() * 50) / 4;
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, tickTime, 3));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {

        if (!level.isClientSide() && entity instanceof ServerPlayer serverPlayer) {

            if (!LifeSteal.config.disableHeartCrystals.get()) {
                AtomicInteger currentheartDifference = new AtomicInteger();
                HealthData.get(entity).ifPresent(IHeartCap -> currentheartDifference.set(IHeartCap.getHeartDifference()));

                if (LifeSteal.config.maximumamountofheartsGainable.get() > -1 && LifeSteal.config.preventFromUsingCrystalIfMax.get()) {
                    int maximumheartDifference = LifeSteal.config.startingHeartDifference.get() + LifeSteal.config.maximumamountofheartsGainable.get();
                    if (currentheartDifference.get() == maximumheartDifference) {
                        serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.heart_crystal_reaching_max"), true);
                        item.setCount(item.getCount() + 1);
                        serverPlayer.containerMenu.broadcastChanges();
                        return super.finishUsingItem(item, level, entity);
                    }
                }

                int newheartDifference = currentheartDifference.get() + LifeSteal.config.heartCrystalAmountGain.get();

                HealthData.get(entity).ifPresent(IHeartCap -> {
                    IHeartCap.setHeartDifference(newheartDifference);
                    IHeartCap.refreshHearts(false);
                });

                // Formula, for every hit point, increase duration of the regeneration by 50 ticks: TickDuration = MaxHealth * 50
                CompoundTag compoundTag = item.getTagElement("lifesteal");
                if (compoundTag == null) {
                    applyCrystalEffect(entity);
                } else if (compoundTag.getBoolean("Fresh")) {
                    applyCrystalEffect(entity);
                }

            } else {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.heart_crystal_disabled"), true);
                item.grow(1);
                serverPlayer.containerMenu.broadcastChanges();

            }
        }
        return super.finishUsingItem(item, level, entity);
    }
}
