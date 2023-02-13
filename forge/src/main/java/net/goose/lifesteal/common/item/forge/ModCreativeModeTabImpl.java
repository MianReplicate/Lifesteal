package net.goose.lifesteal.common.item.forge;

import net.goose.lifesteal.common.item.ModCreativeModeTab;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabImpl {
    public static CreativeModeTab getCreativeModeTab() {
        return new CreativeModeTab("lifesteal") {
            @Override
            public ItemStack makeIcon() {
                return ModCreativeModeTab.makeIcon();
            }
        };
    }
}