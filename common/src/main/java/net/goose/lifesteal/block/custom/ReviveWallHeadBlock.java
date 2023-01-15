package net.goose.lifesteal.block.custom;

import net.goose.lifesteal.block.ModBlocks;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ReviveWallHeadBlock extends WallSkullBlock implements EntityBlock {
    public ReviveWallHeadBlock(Properties properties) {
        super(SkullBlock.Types.PLAYER, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ReviveSkullBlockEntity(blockPos, blockState);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        ModBlocks.REVIVE_HEAD.get().setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
    }
}
