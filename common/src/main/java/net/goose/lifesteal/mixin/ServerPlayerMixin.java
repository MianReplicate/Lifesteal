package net.goose.lifesteal.mixin;

import net.goose.lifesteal.data.HealthData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends LivingEntity {
    protected ServerPlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "initInventoryMenu", at = @At("HEAD"))
    private void onSpawn(final CallbackInfo info) {
        HealthData.get(this).ifPresent(healthData -> {
            healthData.refreshHearts(true);
            healthData.revivedTeleport();
        });
    }
}
