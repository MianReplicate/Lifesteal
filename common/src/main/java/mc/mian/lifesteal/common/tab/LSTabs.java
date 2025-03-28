package mc.mian.lifesteal.common.tab;

import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.platform.Services;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class LSTabs {
    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(LSConstants.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> LIFESTEAL = TABS.register("lifesteal", Services.TAB::createTab);
    public static ItemStack makeIcon() {
        return new ItemStack(LSItems.HEART_CRYSTAL.get());
    }

    public static void register() {
        LSConstants.LOGGER.debug("Registering ModTabs for " + LSConstants.MOD_ID);
        TABS.register();
    }
}
