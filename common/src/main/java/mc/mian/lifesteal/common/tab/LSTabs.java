package mc.mian.lifesteal.common.tab;

import dev.architectury.injectables.annotations.ExpectPlatform;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class LSTabs {
    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(LSConstants.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> LIFESTEAL = TABS.register("lifesteal", LSTabs::createTab);
    public static ItemStack makeIcon() {
        return new ItemStack(LSItems.HEART_CRYSTAL.get());
    }
    @ExpectPlatform
    public static CreativeModeTab createTab(){
        throw new RuntimeException("fuck off");
    }
    public static void register() {
        LSConstants.LOGGER.debug("Registering ModTabs for " + LSConstants.MOD_ID);
        TABS.register();
    }
}
