package net.goose.lifesteal.common.item.custom;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.data.LifestealData;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicBoolean;

public class HeartCrystalItem extends Item {

    public HeartCrystalItem(Properties pProperties) {
        super(pProperties);
    }

    public void applyCrystalEffect(LivingEntity entity) {
        // Formula, for every hit point, increase duration of the regeneration by 50 ticks: TickDuration = MaxHealth * 50
        int tickTime = (int) (entity.getMaxHealth() * 50) / 4;
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, tickTime, 3));
    }

    public boolean useHeartCrystal(ItemStack item, Level level, LivingEntity entity) {
        AtomicBoolean success = new AtomicBoolean(false);

        if (!level.isClientSide() && entity instanceof ServerPlayer serverPlayer) {
            CompoundTag compoundTag = item.getTagElement(LifeSteal.MOD_ID);
            boolean rippedHeartCrystal = compoundTag != null && compoundTag.getBoolean(ModResources.RIPPED);
            boolean unnaturalHeartCrystal = compoundTag != null && compoundTag.getBoolean(ModResources.UNFRESH);
            success.set(true);

            if (!rippedHeartCrystal) {
                if (unnaturalHeartCrystal) {
                    if (LifeSteal.config.disableUnnaturalHeartCrystals.get()) {
                        serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.unnatural_heart_crystal_disabled"), true);
                        success.set(false);
                    }
                } else {
                    if (LifeSteal.config.disableHeartCrystals.get()) {
                        serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.heart_crystal_disabled"), true);
                        success.set(false);
                    }
                }
            }


            LifestealData.get(entity).ifPresent(iLifestealData -> {
                if (LifeSteal.config.maximumHealthGainable.get() > -1 && LifeSteal.config.preventFromUsingCrystalIfMax.get()) {
                    int maximumheartDifference = LifeSteal.config.startingHealthDifference.get() + LifeSteal.config.maximumHealthGainable.get();
                    if ((int)iLifestealData.getValue(ModResources.HEALTH_DIFFERENCE) == maximumheartDifference) {
                        serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.heart_crystal_reaching_max"), true);
                        success.set(false);
                    }
                }

                if (success.get()) {
                    int newheartDifference = (int) iLifestealData.getValue(ModResources.HEALTH_DIFFERENCE) + LifeSteal.config.heartCrystalAmountGain.get();

                    iLifestealData.setValue(ModResources.HEALTH_DIFFERENCE,newheartDifference);
                    iLifestealData.refreshHealth(false);

                    // Formula, for every hit point, increase duration of the regeneration by 50 ticks: TickDuration = MaxHealth * 50
                    if (!unnaturalHeartCrystal) {
                        applyCrystalEffect(entity);
                    }
                }
            });
        }
        return success.get();
    }


    @Override
    public ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {
        boolean success = useHeartCrystal(item, level, entity);
        return success ? super.finishUsingItem(item, level, entity) : item;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!this.isEdible() && !level.isClientSide) {
            ItemStack item = player.getItemInHand(interactionHand);
            boolean success = useHeartCrystal(item, level, player);

            if (success) {
                item.shrink(1);
                player.containerMenu.broadcastChanges();
            }
        }

        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isEdible() {
        return !LifeSteal.config.crystalInstantUse.get();
    }

    @Override
    public FoodProperties getFoodProperties() {
        if (LifeSteal.config.crystalInstantUse.get()) {
            return null;
        } else {
            return ModItems.alwaysEdible;
        }
    }
}
