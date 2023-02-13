package net.goose.lifesteal.common.item.custom;

import net.goose.lifesteal.common.block.ModBlocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.level.block.Block;

public class ReviveHeadItem extends PlayerHeadItem {

    public ReviveHeadItem(Block block, Block block2, Properties properties) {
        super(block, block2, properties);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        if (itemStack.is(ModBlocks.REVIVE_HEAD.get().asItem()) && itemStack.hasTag()) {
            String string = null;
            CompoundTag compoundTag = itemStack.getTag();
            if (compoundTag.contains("SkullOwner", 8)) {
                string = compoundTag.getString("SkullOwner");
            } else if (compoundTag.contains("SkullOwner", 10)) {
                CompoundTag compoundTag2 = compoundTag.getCompound("SkullOwner");
                if (compoundTag2.contains("Name", 8)) {
                    string = compoundTag2.getString("Name");
                }
            }

            if (string != null) {
                return new TranslatableComponent("block.lifesteal.revive_head.named", string);
            }
        }

        return super.getName(itemStack);
    }
}
