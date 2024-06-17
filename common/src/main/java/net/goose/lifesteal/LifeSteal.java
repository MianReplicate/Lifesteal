package net.goose.lifesteal;

import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.common.tab.ModTabs;
import net.goose.lifesteal.configuration.ModConfig;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LifeSteal {
    public static final String MOD_ID = "lifesteal";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static ModConfig config;

    public static ResourceLocation BARREL_1 = ResourceLocation.fromNamespaceAndPath("minecraft", "chests/barrel_1");
    public static ResourceLocation MINERS_HOME_TABLE = ResourceLocation.fromNamespaceAndPath("minecraft", "chests/miners_home");
    public static ResourceLocation MINERS_RUINED_SHACK_TABLE = ResourceLocation.fromNamespaceAndPath("minecraft", "chests/miners_ruined_shack");
    public static ResourceLocation RICH_CART_TABLE = ResourceLocation.fromNamespaceAndPath("minecraft", "chests/rich_cart");
    public static ResourceLocation RUINED_LIBRARY_TABLE = ResourceLocation.fromNamespaceAndPath("minecraft", "chests/ruined_library");

    public static ResourceLocation modLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static String modLocString(String name) {
        return modLoc(name).toString();
    }

    public static void init() {
        LOGGER.info("Lifestealers are on the loose!");
        ModItems.register();
        ModBlocks.register();
        ModBlockEntityTypes.register();
        ModTabs.register();
    }
}