package net.goose.lifesteal.common.item.fabric;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModCreativeModeTab;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static net.goose.lifesteal.common.item.ModCreativeModeTab.TAB;

public class ModCreativeModeTabImpl {
    public static CreativeModeTab getCreativeModeTab() {
        return FabricItemGroupBuilder.build(TAB, ModCreativeModeTab::makeIcon);
    }
}
