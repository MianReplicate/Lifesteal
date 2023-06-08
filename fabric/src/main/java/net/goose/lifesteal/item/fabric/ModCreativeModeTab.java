package net.goose.lifesteal.item.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModCreativeModeTabHelper;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ModCreativeModeTab {
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModCreativeModeTab for " + LifeSteal.MOD_ID);

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(LifeSteal.MOD_ID, "itemGroup.lifesteal")),
                FabricItemGroup.builder()
                        .icon(ModCreativeModeTabHelper::makeIcon)
                        .title(Component.translatable("itemGroup.lifesteal"))
                        .displayItems((itemDisplayParameters, output) -> ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                        .build()
        );
    }
}
