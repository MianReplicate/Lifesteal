package net.goose.lifesteal.common.blockentity.custom;

import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReviveSkullBlockEntity extends SkullBlockEntity {
    public ReviveSkullBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntityTypes.REVIVE_HEAD.get();
    }
}
