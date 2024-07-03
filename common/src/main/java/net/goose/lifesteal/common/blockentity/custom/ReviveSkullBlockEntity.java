package net.goose.lifesteal.common.blockentity.custom;

import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReviveSkullBlockEntity extends SkullBlockEntity {
    private boolean forceDestroy = false;

    public ReviveSkullBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    public void setDestroyed(boolean destroy){
        this.forceDestroy = destroy;
        this.setChanged();
    }

    public boolean getDestroyed(){
        return this.forceDestroy;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("ForceDestroy", this.forceDestroy);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("ForceDestroy", 10)) {
            this.setDestroyed(tag.getBoolean("ForceDestroy"));
        }
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntityTypes.REVIVE_HEAD.get();
    }
}
