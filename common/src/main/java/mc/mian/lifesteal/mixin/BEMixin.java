package mc.mian.lifesteal.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mc.mian.lifesteal.common.blockentity.LSBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BEMixin {
    @Mutable
    @Shadow
    @Final
    private BlockEntityType<?> type;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void getType(BlockEntityType type, BlockPos pos, BlockState blockState, CallbackInfo ci) {
        if ((BlockEntity) (Object) this instanceof SkullBlockEntity) {
            this.type = LSBlockEntityTypes.EXPANDED_SKULL.get();
        }
    }
}