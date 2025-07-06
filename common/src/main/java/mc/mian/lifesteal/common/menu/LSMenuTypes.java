package mc.mian.lifesteal.common.menu;

import mc.mian.lifesteal.client.ReviveBeaconMenu;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class LSMenuTypes {
    public static final DeferredRegistry<MenuType<?>> MENU_TYPES = DeferredRegistry.create(LSConstants.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<ReviveBeaconMenu>> REVIVE_BEACON =
            MENU_TYPES.register("revive_beacon", () -> new MenuType<>(ReviveBeaconMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register() {
        LSConstants.LOGGER.debug("Registering MenuTypes for " + LSConstants.MOD_ID);
        MENU_TYPES.register();
    }
}
