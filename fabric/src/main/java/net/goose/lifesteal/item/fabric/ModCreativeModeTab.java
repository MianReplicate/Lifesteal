package net.goose.lifesteal.item.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModCreativeModeTabHelper;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import static net.goose.lifesteal.common.item.ModCreativeModeTabHelper.TAB;
public class ModCreativeModeTab {
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModCreativeModeTab for " + LifeSteal.MOD_ID);
        FabricItemGroup.builder(TAB)
                .icon(ModCreativeModeTabHelper::makeIcon)
                .title(Component.translatable("itemGroup.lifesteal"))
                .displayItems((flags, output, isOp) -> ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                .build();
    }
}
