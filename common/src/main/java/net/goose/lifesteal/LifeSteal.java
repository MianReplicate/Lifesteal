package net.goose.lifesteal;

import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.goose.lifesteal.common.item.ModCreativeModeTab;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.configuration.ModConfig;
import net.goose.lifesteal.world.feature.ModConfiguredFeatures;
import net.goose.lifesteal.world.feature.ModPlacedFeatures;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifeSteal {
    public static final String MOD_ID = "lifesteal";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ModConfig config;

    public static ResourceLocation BARREL_1 = new ResourceLocation("minecraft", "chests/barrel_1");
    public static ResourceLocation MINERS_HOME_TABLE = new ResourceLocation("minecraft", "chests/miners_home");
    public static ResourceLocation MINERS_RUINED_SHACK_TABLE = new ResourceLocation("minecraft", "chests/miners_ruined_shack");
    public static ResourceLocation RICH_CART_TABLE = new ResourceLocation("minecraft", "chests/rich_cart");
    public static ResourceLocation RUINED_LIBRARY_TABLE = new ResourceLocation("minecraft", "chests/ruined_library");

    public static ResourceLocation modLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static void init() {
        LOGGER.info("Lifestealers are on the loose!");
        ModCreativeModeTab.register();
        ModItems.register();
        ModBlocks.register();
        ModBlockEntityTypes.register();
        ModConfiguredFeatures.register();
        ModPlacedFeatures.register();
        ModCriteria.init();
    }
}