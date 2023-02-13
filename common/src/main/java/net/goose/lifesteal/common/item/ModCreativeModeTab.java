package net.goose.lifesteal.common.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.goose.lifesteal.LifeSteal;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final ResourceLocation TAB = new ResourceLocation(LifeSteal.MOD_ID, "creativemodetab");
    public static CreativeModeTab creativeModeTab = null;
    @ExpectPlatform
    private static CreativeModeTab getCreativeModeTab(){
        throw new RuntimeException("Uh!");
    }
    public static void register(){
        LifeSteal.LOGGER.debug("Registering ModCreativeModeTab for " + LifeSteal.MOD_ID);
        creativeModeTab = getCreativeModeTab();
    }
    public static ItemStack makeIcon() {
        ItemStack stack = new ItemStack(ModItems.HEART_CRYSTAL.get());
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsCreativeTab", true);
        stack.setTag(tag);
        return stack;
    }
}
