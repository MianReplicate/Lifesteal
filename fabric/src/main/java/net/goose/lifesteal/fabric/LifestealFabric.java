package net.goose.lifesteal.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.configuration.ConfigHolder;
import net.goose.lifesteal.event.ModCommands;
import net.goose.lifesteal.event.ModEvents;
import net.goose.lifesteal.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class LifestealFabric implements ModInitializer {
    private static final CreativeModeTab LIFESTEAL_TAB = FabricItemGroup.builder(new ResourceLocation(LifeSteal.MOD_ID, "creative_tab")).icon(() -> new ItemStack(ModItems.HEART_CRYSTAL.get()))
            .title(Component.translatable("itemGroup.lifesteal"))
            .displayItems((enabledFeatures, entries, operatorEnabled) -> {

                BuiltInRegistries.ITEM.iterator().forEachRemaining(item -> {
                    if (BuiltInRegistries.ITEM.getKey(item).getNamespace().matches(LifeSteal.MOD_ID)) {
                        entries.accept(item);
                    }
                });
            }).build();

    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(LifeSteal.MOD_ID, net.minecraftforge.fml.config.ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();
        ModEvents.register();
        ModCommands.register();
    }
}