package net.goose.lifesteal.common.block.custom;

import com.mojang.authlib.GameProfile;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class ReviveHeadBlock extends SkullBlock implements EntityBlock {
    public ReviveHeadBlock(Properties properties) {
        super(Types.PLAYER, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ReviveSkullBlockEntity(blockPos, blockState);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof ReviveSkullBlockEntity skullBlockEntity) {
            GameProfile gameProfile = null;
            DataComponentMap components = itemStack.get(DataComponents.ITEM);
            if (itemStack.hasTag()) {
                CompoundTag compoundTag = itemStack.getTag();
                if (compoundTag.contains("SkullOwner", 10)) {
                    gameProfile = NbtUtils.readGameProfile(compoundTag.getCompound("SkullOwner"));
                } else if (compoundTag.contains("SkullOwner", 8) && !StringUtils.isBlank(compoundTag.getString("SkullOwner"))) {
                    gameProfile = level.getServer().getProfileCache().get(compoundTag.getString("SkullOwner")).orElse(null);
                }
            }

            skullBlockEntity.setOwner(gameProfile);
        }
    }
}
