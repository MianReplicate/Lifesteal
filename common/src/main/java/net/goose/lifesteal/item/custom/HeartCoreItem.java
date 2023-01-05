package net.goose.lifesteal.item.custom;

import net.goose.lifesteal.LifeSteal;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeartCoreItem extends Item {

    public static final FoodProperties HeartCore = (new FoodProperties.Builder()).alwaysEat().build();

    public HeartCoreItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof ServerPlayer serverPlayer) {
            if (!LifeSteal.config.disableHeartCores.get()) {
                if (entity.getHealth() < entity.getMaxHealth() || !LifeSteal.config.preventFromUsingCoreIfMax.get()) {
                    float maxHealth = entity.getMaxHealth();
                    float amountThatWillBeHealed = (float) (maxHealth * LifeSteal.config.heartCoreHeal.get());
                    float differenceInHealth = entity.getMaxHealth() - entity.getHealth();
                    if (differenceInHealth <= amountThatWillBeHealed) {
                        amountThatWillBeHealed = differenceInHealth;
                    }

                    int oldDuration = 0;
                    if (entity.hasEffect(MobEffects.REGENERATION)) {
                        MobEffectInstance mobEffect = entity.getEffect(MobEffects.REGENERATION);

                        oldDuration = mobEffect.getDuration();
                    }

                    int tickTime = (int) ((amountThatWillBeHealed * 50) / 2) + oldDuration;
                    entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, tickTime, 1));
                } else {
                    serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.heart_core_at_max_health"), true);
                    item.grow(1);
                    serverPlayer.containerMenu.broadcastChanges();
                }
            } else {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.heart_core_disabled"), true);
                item.grow(1);
                serverPlayer.containerMenu.broadcastChanges();
            }
        }
        return super.finishUsingItem(item, level, entity);
    }
}
