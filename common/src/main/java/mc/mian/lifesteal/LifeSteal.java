package mc.mian.lifesteal;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.blockentity.LSBlockEntityTypes;
import mc.mian.lifesteal.common.component.LSDataComponents;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.network.LSNetwork;
import mc.mian.lifesteal.common.tab.LSTabs;
import mc.mian.lifesteal.configuration.LSConfig;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSPlatform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LifeSteal {
    public static LSConfig config;

    public static void init() {
        LSConstants.LOGGER.info("Lifestealers are on the loose!");
        LSDataComponents.register();
        LSItems.register();
        LSBlocks.register();
        LSBlockEntityTypes.register();
        LSTabs.register();
        LSNetwork.register();
    }
}