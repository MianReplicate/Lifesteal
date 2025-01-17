package mc.mian.lifesteal.util;

import mc.mian.lifesteal.LifeSteal;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.storage.loot.LootTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// All shared resources should be created here
public class LSConstants {
    public static final Logger LOGGER = LogManager.getLogger(LSConstants.MOD_ID);

    public static final String MOD_ID = "lifesteal";
    public static final String MOD_DISPLAY_NAME = "Lifesteal";

    // Structure Template Pools
    public static final ResourceKey<StructureTemplatePool> ABANDONED_TRADING_CART_POOL = createTemplatePool(LSConstants.MOD_ID, "abandoned_trading_cart");
    public static final ResourceKey<StructureTemplatePool> COLLAPSED_MINESHAFT_POOL = createTemplatePool(LSConstants.MOD_ID, "collapsed_mineshaft");
    public static final ResourceKey<StructureTemplatePool> MINERS_BROKEN_PORTAL_POOL = createTemplatePool(LSConstants.MOD_ID, "miners_broken_portal");
    public static final ResourceKey<StructureTemplatePool> MINERS_BROKEN_PORTAL_POOL_1 = createTemplatePool(LSConstants.MOD_ID, "miners_broken_portal_1");
    public static final ResourceKey<StructureTemplatePool> MINERS_BROKEN_PORTAL_POOL_2 = createTemplatePool(LSConstants.MOD_ID, "miners_broken_portal_2");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_POOL = createTemplatePool(LSConstants.MOD_ID, "miners_home");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_POOL_1 = createTemplatePool(LSConstants.MOD_ID, "miners_home_1");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_POOL_2 = createTemplatePool(LSConstants.MOD_ID, "miners_home_2");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_TAIGA_POOL = createTemplatePool(LSConstants.MOD_ID, "miners_home_taiga");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_TAIGA_POOL_1 = createTemplatePool(LSConstants.MOD_ID, "miners_home_taiga_1");
    public static final ResourceKey<StructureTemplatePool> MINERS_HOME_TAIGA_POOL_2 = createTemplatePool(LSConstants.MOD_ID, "miners_home_taiga_2");
    public static final ResourceKey<StructureTemplatePool> MINERS_SHACK_POOL = createTemplatePool(LSConstants.MOD_ID, "miners_shack");
    public static final ResourceKey<StructureTemplatePool> MINERS_RUINED_SHACK_POOL = createTemplatePool(LSConstants.MOD_ID, "miners_ruined_shack");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL = createTemplatePool(LSConstants.MOD_ID, "ore_cart");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_1 = createTemplatePool(LSConstants.MOD_ID, "ore_cart_1");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_2 = createTemplatePool(LSConstants.MOD_ID, "ore_cart_2");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_3 = createTemplatePool(LSConstants.MOD_ID, "ore_cart_3");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_4 = createTemplatePool(LSConstants.MOD_ID, "ore_cart_4");
    public static final ResourceKey<StructureTemplatePool> ORE_CART_POOL_5 = createTemplatePool(LSConstants.MOD_ID, "ore_cart_5");
    public static final ResourceKey<StructureTemplatePool> ROBBED_CART_POOL = createTemplatePool(LSConstants.MOD_ID, "robbed_cart");
    public static final ResourceKey<StructureTemplatePool> ROBBED_CART_POOL_1 = createTemplatePool(LSConstants.MOD_ID, "robbed_cart_1");
    public static final ResourceKey<StructureTemplatePool> ROBBED_CART_POOL_2 = createTemplatePool(LSConstants.MOD_ID, "robbed_cart_2");
    public static final ResourceKey<StructureTemplatePool> RUINED_LIBRARY_START_POOL = createTemplatePool(LSConstants.MOD_ID, "ruined_library/start_pool");
    public static final ResourceKey<StructureTemplatePool> RUINED_LIBRARY_SIDE_POOL = createTemplatePool(LSConstants.MOD_ID, "ruined_library/side_pool");

    // Structure Sets
    public static final ResourceKey<StructureSet> BIG_STRUCTURES = createStructureSet(LSConstants.MOD_ID, "big_structures");
    public static final ResourceKey<StructureSet> CARTS = createStructureSet(LSConstants.MOD_ID, "carts");
    public static final ResourceKey<StructureSet> MINERS_LORE = createStructureSet(LSConstants.MOD_ID, "miners_lore");

    // Structures
    public static final ResourceKey<Structure> ABANDONED_TRADING_CART = createStructure(LSConstants.MOD_ID, "abandoned_trading_cart");
    public static final ResourceKey<Structure> COLLAPSED_MINESHAFT = createStructure(LSConstants.MOD_ID, "collapsed_mineshaft");
    public static final ResourceKey<Structure> MINERS_BROKEN_PORTAL = createStructure(LSConstants.MOD_ID, "miners_broken_portal");
    public static final ResourceKey<Structure> MINERS_BROKEN_PORTAL_1 = createStructure(LSConstants.MOD_ID, "miners_broken_portal_1");
    public static final ResourceKey<Structure> MINERS_BROKEN_PORTAL_2 = createStructure(LSConstants.MOD_ID, "miners_broken_portal_2");
    public static final ResourceKey<Structure> MINERS_HOME = createStructure(LSConstants.MOD_ID, "miners_home");
    public static final ResourceKey<Structure> MINERS_HOME_1 = createStructure(LSConstants.MOD_ID, "miners_home_1");
    public static final ResourceKey<Structure> MINERS_HOME_2 = createStructure(LSConstants.MOD_ID, "miners_home_2");
    public static final ResourceKey<Structure> MINERS_HOME_TAIGA = createStructure(LSConstants.MOD_ID, "miners_home_taiga");
    public static final ResourceKey<Structure> MINERS_HOME_TAIGA_1 = createStructure(LSConstants.MOD_ID, "miners_home_taiga_1");
    public static final ResourceKey<Structure> MINERS_HOME_TAIGA_2 = createStructure(LSConstants.MOD_ID, "miners_home_taiga_2");
    public static final ResourceKey<Structure> MINERS_RUINED_SHACK = createStructure(LSConstants.MOD_ID, "miners_ruined_shack");
    public static final ResourceKey<Structure> MINERS_SHACK = createStructure(LSConstants.MOD_ID, "miners_shack");
    public static final ResourceKey<Structure> ORE_CART = createStructure(LSConstants.MOD_ID, "ore_cart");
    public static final ResourceKey<Structure> ORE_CART_1 = createStructure(LSConstants.MOD_ID, "ore_cart_1");
    public static final ResourceKey<Structure> ORE_CART_2 = createStructure(LSConstants.MOD_ID, "ore_cart_2");
    public static final ResourceKey<Structure> ORE_CART_3 = createStructure(LSConstants.MOD_ID, "ore_cart_3");
    public static final ResourceKey<Structure> ORE_CART_4 = createStructure(LSConstants.MOD_ID, "ore_cart_4");
    public static final ResourceKey<Structure> ORE_CART_5 = createStructure(LSConstants.MOD_ID, "ore_cart_5");
    public static final ResourceKey<Structure> ROBBED_CART = createStructure(LSConstants.MOD_ID, "robbed_cart");
    public static final ResourceKey<Structure> ROBBED_CART_1 = createStructure(LSConstants.MOD_ID, "robbed_cart_1");
    public static final ResourceKey<Structure> ROBBED_CART_2 = createStructure(LSConstants.MOD_ID, "robbed_cart_2");
    public static final ResourceKey<Structure> RUINED_LIBRARY = createStructure(LSConstants.MOD_ID, "ruined_library");

    // Lifesteal Data
    public static final ResourceLocation LIFESTEAL_DATA = modLoc("lifesteal_data");
    public static final ResourceLocation HEALTH_DIFFERENCE = modLoc("health_difference");
    public static final ResourceLocation TIME_KILLED = modLoc("time_killed");
    public static final ResourceLocation HEALTH_MODIFIER = modLoc("health_modifier");

    // Loot Tables
    public static final ResourceKey<LootTable> BARREL_1_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/barrel_1");
    public static final ResourceKey<LootTable> MINERS_HOME_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/miners_home");
    public static final ResourceKey<LootTable> MINERS_RUINED_SHACK_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/miners_ruined_shack");
    public static final ResourceKey<LootTable> RICH_CART_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/rich_cart");
    public static final ResourceKey<LootTable> RUINED_LIBRARY_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/ruined_library");

    // Advancement Triggers (and also used for Advancements themselves lol)
    public static final ResourceLocation GET_10_MAX_HEARTS = modLoc("get_10_max_hearts");
    public static final ResourceLocation USE_TOTEM_WHILE_20_MAX_HEARTS = modLoc("use_totem_while_20_max_hearts");
    public static final ResourceLocation REVIVED = modLoc("revived");
    public static final ResourceLocation BACK_FROM_THE_DEAD = modLoc("back_from_the_dead");

    // Advancements
    public static final ResourceLocation GET_10_MAX_HEARTS_WITH_NETHERITE_ARMOR = modLoc("get_10_max_hearts_with_netherite_armor");
    public static final ResourceLocation GET_REVIVE_CRYSTAL = modLoc("get_revive_crystal");
    public static final ResourceLocation GET_HEART_CRYSTAL = modLoc("get_heart_crystal");
    public static final ResourceLocation GET_CRYSTAL_CORE = modLoc("get_crystal_core");
    public static final ResourceLocation ROOT = modLoc("root");

    // Configured Features
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_HEART_GEODE_CONFIGURED = createConfiguredFeature(LSConstants.MOD_ID,"deepslate_heart_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEART_ORE_CONFIGURED = createConfiguredFeature(LSConstants.MOD_ID,"heart_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_HEART_GEODE_CONFIGURED = createConfiguredFeature(LSConstants.MOD_ID,"nether_heart_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_HEART_ORE_CONFIGURED = createConfiguredFeature(LSConstants.MOD_ID,"nether_heart_ore");

    // Placed Features
    public static final ResourceKey<PlacedFeature> HEART_ORE_PLACED = createPlacedFeature(LSConstants.MOD_ID,"heart_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_HEART_ORE_PLACED = createPlacedFeature(LSConstants.MOD_ID,"nether_heart_ore_placed");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_HEART_GEODE_PLACED = createPlacedFeature(LSConstants.MOD_ID,"deepslate_heart_geode_placed");
    public static final ResourceKey<PlacedFeature> NETHER_HEART_GEODE_PLACED = createPlacedFeature(LSConstants.MOD_ID,"nether_heart_geode_placed");
    
    // Biome Modifiers
    public static final ResourceLocation ADD_OVERWORLD_FEATURES = modLoc("add_overworld_features");
    public static final ResourceLocation ADD_NETHER_FEATURES = modLoc("add_nether_features");

    public static ResourceLocation modLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(LSConstants.MOD_ID, name);
    }
    
    private static ResourceKey<PlacedFeature> createPlacedFeature(String domain, String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(domain, name));
    }
    private static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeature(String domain, String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(domain, name));
    }
    
    private static ResourceKey<LootTable> createLootTable(String domain, String name){
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(domain, name));
    }

    public static ResourceKey<StructureTemplatePool> createTemplatePool(String domain, String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(domain, name));
    }

    public static ResourceKey<StructureSet> createStructureSet(String domain, String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(domain, name));
    }

    public static ResourceKey<Structure> createStructure(String domain, String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(domain, name));
    }
}
