package net.goose.lifesteal.mixin;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.data.LifestealData;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingMixin {

    @Nullable
    public abstract LivingEntity getAttacker();

    @Inject(method = "checkTotemDeathProtection", at = @At("TAIL"))
    private void totemUsed(final DamageSource source, final CallbackInfoReturnable<Boolean> cir) {
        if (!cir.isCancelled() && cir.getReturnValue()) { // The return value is if there's a totem or not.
            LivingEntity livingEntity = ((LivingEntity) (Object) this);
            if (livingEntity instanceof ServerPlayer serverPlayer) {
                LifestealData.get(livingEntity).ifPresent(lifestealData ->
                {
                    if ((int)lifestealData.getValue(ModResources.HEALTH_DIFFERENCE) >= 20) {
                        ModCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS.trigger(serverPlayer);
                    }
                });
            }
        }
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void onDeath(final CallbackInfo ci) {
        if (LifeSteal.config.shouldAllMobsGiveHearts.get()) {
            LivingEntity entity = this.getAttacker();
            if (entity instanceof ServerPlayer serverPlayer) {
                LifestealData.get(serverPlayer).ifPresent(lifestealData ->
                {
                    lifestealData.setValue(ModResources.HEALTH_DIFFERENCE,(int)lifestealData.getValue(ModResources.HEALTH_DIFFERENCE) + LifeSteal.config.amountOfHealthLostUponLoss.get());
                    lifestealData.refreshHealth(false);
                });
            }
        }
    }
}
