package net.goose.lifesteal.item.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.item.ModCreativeModeTabHelper;
import net.goose.lifesteal.item.ModItems;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import static net.goose.lifesteal.item.ModCreativeModeTabHelper.TAB;
public class ModCreativeModeTab {
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModCreativeModeTab for " + LifeSteal.MOD_ID);
        FabricItemGroup.builder(TAB)
                .icon(ModCreativeModeTabHelper::makeIcon)
                .title(Component.translatable("itemGroup.lifesteal"))
                .displayItems((flags, output, isOp) -> {
                    for (RegistrySupplier<Item> item : ModItems.ITEMS.getEntries()) {
                        output.accept(item.get());
                    }
                }).build();
    }
}
