package mc.mian.lifesteal.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// All shared resources should be created here
public class LSConstants {
    public static final Logger LOGGER = LogManager.getLogger(LSConstants.MOD_ID);

    public static final String MOD_ID = "lifesteal";
    public static final String MOD_DISPLAY_NAME = "Lifesteal";

    public static final String RIPPED = "ripped";
    public static final String UNFRESH = "unfresh";

    // Structure Template Pools
    public static final ResourceKey<StructureTemplatePool> ABANDONED_TRADING_CART_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "abandoned_trading_cart");
    public static final ResourceKey<StructureTemplatePool> COLLAPSED_MINESHAFT_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "collapsed_mineshaft");
    public static final ResourceKey<StructureTemplatePool> MINERS_BROKEN_PORTAL_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_broken_portal");
    public static final ResourceKey<StructureTemplatePool> MINERS_BROKEN_PORTAL_POOL_1 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_broken_portal_1");
    public static final ResourceKey<StructureTemplatePool> MINERS_BROKEN_PORTAL_POOL_2 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_broken_portal_2");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_home");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_POOL_1 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_home_1");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_POOL_2 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_home_2");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_TAIGA_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_home_taiga");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_TAIGA_POOL_1 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_home_taiga_1");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_TAIGA_POOL_2 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_home_taiga_2");
    public static final ResourceKey<StructureTemplatePool> MINERS_SHACK_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_shack");
    public static final ResourceKey<StructureTemplatePool> MINERS_RUINED_SHACK_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "miners_ruined_shack");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "ore_cart");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_1 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "ore_cart_1");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_2 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "ore_cart_2");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_3 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "ore_cart_3");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_4 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "ore_cart_4");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_5 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "ore_cart_5");
    public static final ResourceKey<StructureTemplatePool> ROBBED_CART_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "robbed_cart");
    public static final ResourceKey<StructureTemplatePool> ROBBED_CART_POOL_1 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "robbed_cart_1");
    public static final ResourceKey<StructureTemplatePool> ROBBED_CART_POOL_2 = LSUtil.createTemplatePool(LSConstants.MOD_ID, "robbed_cart_2");
    public static final ResourceKey<StructureTemplatePool> RUINED_LIBRARY_START_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "ruined_library/start_pool");
    public static final ResourceKey<StructureTemplatePool> RUINED_LIBRARY_SIDE_POOL = LSUtil.createTemplatePool(LSConstants.MOD_ID, "ruined_library/side_pool");

    // Structure Sets
    public static final ResourceKey<StructureSet> BIG_STRUCTURES = LSUtil.createStructureSet(LSConstants.MOD_ID, "big_structures");
    public static final ResourceKey<StructureSet> CARTS = LSUtil.createStructureSet(LSConstants.MOD_ID, "carts");
    public static final ResourceKey<StructureSet> MINERS_LORE = LSUtil.createStructureSet(LSConstants.MOD_ID, "miners_lore");

    // Structures
    public static final ResourceKey<Structure> ABANDONED_TRADING_CART = LSUtil.createStructure(LSConstants.MOD_ID, "abandoned_trading_cart");
    public static final ResourceKey<Structure> COLLAPSED_MINESHAFT = LSUtil.createStructure(LSConstants.MOD_ID, "collapsed_mineshaft");
    public static final ResourceKey<Structure> MINERS_BROKEN_PORTAL = LSUtil.createStructure(LSConstants.MOD_ID, "miners_broken_portal");
    public static final ResourceKey<Structure> MINERS_BROKEN_PORTAL_1 = LSUtil.createStructure(LSConstants.MOD_ID, "miners_broken_portal_1");
    public static final ResourceKey<Structure> MINERS_BROKEN_PORTAL_2 = LSUtil.createStructure(LSConstants.MOD_ID, "miners_broken_portal_2");
    public static final ResourceKey<Structure> MINERS_HOME = LSUtil.createStructure(LSConstants.MOD_ID, "miners_home");
    public static final ResourceKey<Structure> MINERS_HOME_1 = LSUtil.createStructure(LSConstants.MOD_ID, "miners_home_1");
    public static final ResourceKey<Structure> MINERS_HOME_2 = LSUtil.createStructure(LSConstants.MOD_ID, "miners_home_2");
    public static final ResourceKey<Structure> MINERS_HOME_TAIGA = LSUtil.createStructure(LSConstants.MOD_ID, "miners_home_taiga");
    public static final ResourceKey<Structure> MINERS_HOME_TAIGA_1 = LSUtil.createStructure(LSConstants.MOD_ID, "miners_home_taiga_1");
    public static final ResourceKey<Structure> MINERS_HOME_TAIGA_2 = LSUtil.createStructure(LSConstants.MOD_ID, "miners_home_taiga_2");
    public static final ResourceKey<Structure> MINERS_RUINED_SHACK = LSUtil.createStructure(LSConstants.MOD_ID, "miners_ruined_shack");
    public static final ResourceKey<Structure> MINERS_SHACK = LSUtil.createStructure(LSConstants.MOD_ID, "miners_shack");
    public static final ResourceKey<Structure> ORE_CART = LSUtil.createStructure(LSConstants.MOD_ID, "ore_cart");
    public static final ResourceKey<Structure> ORE_CART_1 = LSUtil.createStructure(LSConstants.MOD_ID, "ore_cart_1");
    public static final ResourceKey<Structure> ORE_CART_2 = LSUtil.createStructure(LSConstants.MOD_ID, "ore_cart_2");
    public static final ResourceKey<Structure> ORE_CART_3 = LSUtil.createStructure(LSConstants.MOD_ID, "ore_cart_3");
    public static final ResourceKey<Structure> ORE_CART_4 = LSUtil.createStructure(LSConstants.MOD_ID, "ore_cart_4");
    public static final ResourceKey<Structure> ORE_CART_5 = LSUtil.createStructure(LSConstants.MOD_ID, "ore_cart_5");
    public static final ResourceKey<Structure> ROBBED_CART = LSUtil.createStructure(LSConstants.MOD_ID, "robbed_cart");
    public static final ResourceKey<Structure> ROBBED_CART_1 = LSUtil.createStructure(LSConstants.MOD_ID, "robbed_cart_1");
    public static final ResourceKey<Structure> ROBBED_CART_2 = LSUtil.createStructure(LSConstants.MOD_ID, "robbed_cart_2");
    public static final ResourceKey<Structure> RUINED_LIBRARY = LSUtil.createStructure(LSConstants.MOD_ID, "ruined_library");

    // Lifesteal Data
    public static final ResourceLocation LIFESTEAL_DATA = LSUtil.modLoc("lifesteal_data");
    public static final ResourceLocation HEALTH_DIFFERENCE = LSUtil.modLoc("health_difference");
    public static final ResourceLocation TIME_KILLED = LSUtil.modLoc("time_killed");
    public static final ResourceLocation HEALTH_MODIFIER = LSUtil.modLoc("health_modifier");
    public static final ResourceLocation STORED_MODIFIER_UUID = LSUtil.modLoc("stored_modifier");

    // Loot Tables
    public static final ResourceLocation BARREL_1_TABLE =
            new ResourceLocation("chests/barrel_1");
    public static final ResourceLocation MINERS_HOME_TABLE =
            new ResourceLocation("chests/miners_home");
    public static final ResourceLocation MINERS_RUINED_SHACK_TABLE =
            new ResourceLocation("chests/miners_ruined_shack");
    public static final ResourceLocation RICH_CART_TABLE =
            new ResourceLocation("chests/rich_cart");
    public static final ResourceLocation RUINED_LIBRARY_TABLE =
            new ResourceLocation("chests/ruined_library");

    // Advancement Triggers (and also used for Advancements themselves lol)
    public static final ResourceLocation GET_10_MAX_HEARTS = LSUtil.modLoc("get_10_max_hearts");
    public static final ResourceLocation USE_TOTEM_WHILE_20_MAX_HEARTS = LSUtil.modLoc("use_totem_while_20_max_hearts");
    public static final ResourceLocation REVIVED = LSUtil.modLoc("revived");
    public static final ResourceLocation BACK_FROM_THE_DEAD = LSUtil.modLoc("back_from_the_dead");

    // Advancements
    public static final ResourceLocation GET_10_MAX_HEARTS_WITH_NETHERITE_ARMOR = LSUtil.modLoc("get_10_max_hearts_with_netherite_armor");
    public static final ResourceLocation GET_REVIVE_CRYSTAL = LSUtil.modLoc("get_revive_crystal");
    public static final ResourceLocation GET_HEART_CRYSTAL = LSUtil.modLoc("get_heart_crystal");
    public static final ResourceLocation GET_CRYSTAL_CORE = LSUtil.modLoc("get_crystal_core");
    public static final ResourceLocation ROOT = LSUtil.modLoc("root");

    // Configured Features
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_HEART_GEODE_CONFIGURED = LSUtil.createConfiguredFeature(LSConstants.MOD_ID,"deepslate_heart_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEART_ORE_CONFIGURED = LSUtil.createConfiguredFeature(LSConstants.MOD_ID,"heart_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_HEART_GEODE_CONFIGURED = LSUtil.createConfiguredFeature(LSConstants.MOD_ID,"nether_heart_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_HEART_ORE_CONFIGURED = LSUtil.createConfiguredFeature(LSConstants.MOD_ID,"nether_heart_ore");

    // Placed Features
    public static final ResourceKey<PlacedFeature> HEART_ORE_PLACED = LSUtil.createPlacedFeature(LSConstants.MOD_ID,"heart_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_HEART_ORE_PLACED = LSUtil.createPlacedFeature(LSConstants.MOD_ID,"nether_heart_ore_placed");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_HEART_GEODE_PLACED = LSUtil.createPlacedFeature(LSConstants.MOD_ID,"deepslate_heart_geode_placed");
    public static final ResourceKey<PlacedFeature> NETHER_HEART_GEODE_PLACED = LSUtil.createPlacedFeature(LSConstants.MOD_ID,"nether_heart_geode_placed");

    // Biome Modifiers
    public static final ResourceLocation ADD_OVERWORLD_FEATURES = LSUtil.modLoc("add_overworld_features");
    public static final ResourceLocation ADD_NETHER_FEATURES = LSUtil.modLoc("add_nether_features");
}