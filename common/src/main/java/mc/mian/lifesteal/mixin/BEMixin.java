package mc.mian.lifesteal.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mc.mian.lifesteal.common.block.LSBlocks;
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
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntity.class)
public class BEMixin {
//    @Mutable
//    @Shadow
//    @Final
//    private BlockEntityType<?> type;
//
//    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;validateBlockState(Lnet/minecraft/world/level/block/state/BlockState;)V"))
//    public void getType(BlockEntity instance, BlockState arg, Operation<Void> original) {
//        if ((BlockEntity) (Object) this instanceof SkullBlockEntity) {
//            this.type = LSBlockEntityTypes.EXPANDED_SKULL.get();
//        }
//        original.call(instance, arg);
//    }

    @WrapOperation(method = "isValidBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;isValid(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean isValidBlockState(BlockEntityType instance, BlockState state, Operation<Boolean> original){
        if((BlockEntity) (Object) this instanceof SkullBlockEntity && state.getBlock().equals(LSBlocks.REVIVE_BEACON.get()))
            return true;

        return original.call(instance, state);
    }
}