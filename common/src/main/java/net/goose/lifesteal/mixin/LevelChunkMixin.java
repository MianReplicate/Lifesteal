package net.goose.lifesteal.mixin;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin {

    @Shadow
    public abstract Level getLevel();

    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    private void remove(BlockPos pos, BlockState state, boolean isMoving, CallbackInfoReturnable<BlockState> cir) {
        Level level = this.getLevel();
        if (LifeSteal.config.unbreakableReviveHeads.get() && !level.isClientSide) {
            if (state.getBlock() != ModBlocks.REVIVE_HEAD.get() && state.getBlock() != ModBlocks.REVIVE_WALL_HEAD.get()) {

                if(level.getBlockEntity(pos) instanceof ReviveSkullBlockEntity reviveSkullBlockEntity){
                    if (!reviveSkullBlockEntity.getDestroyed()) {
                        cir.cancel();
                        reviveSkullBlockEntity.setChanged();
                    }
                }
            }
        }
    }
}
