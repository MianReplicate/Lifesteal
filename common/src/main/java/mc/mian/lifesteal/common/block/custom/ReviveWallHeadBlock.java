package mc.mian.lifesteal.common.block.custom;

import mc.mian.lifesteal.common.block.LSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.PlayerWallHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ReviveWallHeadBlock extends PlayerWallHeadBlock implements EntityBlock {
    public ReviveWallHeadBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        LSBlocks.REVIVE_HEAD.get().setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
    }
}
