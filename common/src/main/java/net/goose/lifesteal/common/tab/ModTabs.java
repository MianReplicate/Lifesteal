package net.goose.lifesteal.common.tab;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(LifeSteal.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> LIFESTEAL = TABS.register("lifesteal", ModTabs::createTab);
    public static ItemStack makeIcon() {
        return new ItemStack(ModItems.HEART_CRYSTAL.get());
    }
    @ExpectPlatform
    public static CreativeModeTab createTab(){
        throw new RuntimeException("fuck off");
    }
    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModTabs for " + LifeSteal.MOD_ID);
        TABS.register();
    }
}
