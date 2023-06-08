package net.goose.lifesteal.item.forge;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModCreativeModeTabHelper;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeModeTab {
    public static final DeferredRegistry<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegistry.create(LifeSteal.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> LIFESTEAL = CREATIVE_MODE_TABS.register("lifesteal", () ->
            CreativeModeTab.builder().title(Component.translatable("itemGroup.lifesteal"))
                    .icon(ModCreativeModeTabHelper::makeIcon).displayItems((itemDisplayParameters, output) ->
                            ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()))).build());
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModCreativeModeTabs for " + LifeSteal.MOD_ID);
        CREATIVE_MODE_TABS.register();
    }
}