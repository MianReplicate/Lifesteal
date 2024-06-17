package net.goose.lifesteal.common.item.custom;

import net.goose.lifesteal.common.block.ModBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.Block;

public class ReviveHeadItem extends PlayerHeadItem {

    public ReviveHeadItem(Block block, Block block2, Properties properties) {
        super(block, block2, properties);
    }
}
