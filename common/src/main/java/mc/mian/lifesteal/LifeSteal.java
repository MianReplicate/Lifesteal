package mc.mian.lifesteal;

import mc.mian.lifesteal.advancement.LSCriteria;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.blockentity.LSBlockEntityTypes;
import mc.mian.lifesteal.common.network.LSNetwork;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.tab.LSTabs;
import mc.mian.lifesteal.configuration.LSConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LifeSteal {
    public static final String MOD_ID = "lifesteal";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static LSConfig config;

    public static void init() {
        LOGGER.info("Lifestealers are on the loose!");
        LSItems.register();
        LSBlocks.register();
        LSBlockEntityTypes.register();
        LSTabs.register();
        LSCriteria.init();
        LSNetwork.register();
    }
}