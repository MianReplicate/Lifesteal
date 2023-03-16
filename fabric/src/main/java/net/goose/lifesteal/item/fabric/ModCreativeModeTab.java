package net.goose.lifesteal.item.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModCreativeModeTabHelper;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.network.chat.Component;

import static net.goose.lifesteal.common.item.ModCreativeModeTabHelper.TAB;

public class ModCreativeModeTab {
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModCreativeModeTab for " + LifeSteal.MOD_ID);
        FabricItemGroup.builder(TAB)
                .icon(ModCreativeModeTabHelper::makeIcon)
                .title(Component.translatable("itemGroup.lifesteal"))
                .displayItems((itemDisplayParameters, output) -> ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                .build();
    }
}
