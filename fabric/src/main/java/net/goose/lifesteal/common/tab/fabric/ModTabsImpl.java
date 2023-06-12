package net.goose.lifesteal.common.tab.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.common.tab.ModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ModTabsImpl {
    public static CreativeModeTab createTab(){
        return FabricItemGroup.builder()
                .icon(ModTabs::makeIcon)
                .title(Component.translatable("itemGroup.lifesteal"))
                .displayItems((itemDisplayParameters, output) -> ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                .build();
    }
}
