package net.goose.lifesteal;

import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.block.ModBlocks;
import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.goose.lifesteal.configuration.ModConfig;
import net.goose.lifesteal.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifeSteal {
    public static final String MOD_ID = "lifesteal";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ModConfig config;

    public static void init() {
        LOGGER.info("Lifestealers are on the loose!");
        ModItems.register();
        ModBlocks.register();
        ModBlockEntityTypes.register();
        ModCriteria.init();
    }
}