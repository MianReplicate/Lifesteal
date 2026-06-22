package mc.mian.lifesteal.mixin;

import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BEMixin {
    @Inject(method = "isValid", at = @At(value = "HEAD"), cancellable = true)
    public void isValidBlockState(BlockState state, CallbackInfoReturnable<Boolean> cir){
        if(state.getBlock() instanceof SkullBlock
                && ((BlockEntityType)(Object) this) == BlockEntityTypes.SKULL)
            cir.setReturnValue(true);
    }
}