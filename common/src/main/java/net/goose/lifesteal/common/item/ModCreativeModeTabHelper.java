package net.goose.lifesteal.common.item;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabHelper {
    public static final ResourceLocation TAB = new ResourceLocation(LifeSteal.MOD_ID, "creativemodetab");

    public static ItemStack makeIcon() {
        ItemStack stack = new ItemStack(ModItems.HEART_CRYSTAL.get());
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsCreativeTab", true);
        stack.setTag(tag);
        return stack;
    }
}
