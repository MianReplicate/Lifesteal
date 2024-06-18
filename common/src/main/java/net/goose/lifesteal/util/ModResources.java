package net.goose.lifesteal.util;

import net.goose.lifesteal.LifeSteal;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootTable;

// All shared resources should be created here
public class ModResources {
    // Health Data
    public static final ResourceLocation HEALTH_DATA = modLoc("health_data");
    public static final ResourceLocation HEALTH_MODIFIER = modLoc("health_modifier");

    // Loot Tables
    public static final ResourceKey<LootTable> BARREL_1 =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/barrel_1");
    public static final ResourceKey<LootTable> MINERS_HOME_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/miners_home");
    public static final ResourceKey<LootTable> MINERS_RUINED_SHACK_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/miners_ruined_shack");
    public static final ResourceKey<LootTable> RICH_CART_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/rich_cart");
    public static final ResourceKey<LootTable> RUINED_LIBRARY_TABLE =
            createLootTable(ResourceLocation.DEFAULT_NAMESPACE,"chests/ruined_library");

    // Advancement Triggers
    public static final ResourceLocation GET_10_MAX_HEARTS = modLoc("get_10_max_hearts");
    public static final ResourceLocation USE_TOTEM_WHILE_20_MAX_HEARTS = modLoc("use_totem_while_20_max_hearts");
    public static final ResourceLocation REVIVED = modLoc("revived");

    // Advancements
    public static final ResourceLocation GET_10_MAX_HEARTS_WITH_NETHERITE_ARMOR = modLoc("get_10_max_hearts_with_netherite_armor");
    public static final ResourceLocation GET_HEART_CRYSTAL = modLoc("get_heart_crystal");
    public static final ResourceLocation GET_CRYSTAL_CORE = modLoc("get_crystal_core");
    public static final ResourceLocation ROOT = modLoc("root");

    // Configured Features
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_HEART_GEODE_CONFIGURED = createConfiguredFeature(LifeSteal.MOD_ID,"deepslate_heart_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEART_ORE_CONFIGURED = createConfiguredFeature(LifeSteal.MOD_ID,"heart_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_HEART_GEODE_CONFIGURED = createConfiguredFeature(LifeSteal.MOD_ID,"nether_heart_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_HEART_ORE_CONFIGURED = createConfiguredFeature(LifeSteal.MOD_ID,"nether_heart_ore");

    // Placed Features
    public static final ResourceKey<PlacedFeature> HEART_ORE_PLACED = createPlacedFeature(LifeSteal.MOD_ID,"heart_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_HEART_ORE_PLACED = createPlacedFeature(LifeSteal.MOD_ID,"nether_heart_ore_placed");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_HEART_GEODE_PLACED = createPlacedFeature(LifeSteal.MOD_ID,"deepslate_heart_geode_placed");
    public static final ResourceKey<PlacedFeature> NETHER_HEART_GEODE_PLACED = createPlacedFeature(LifeSteal.MOD_ID,"nether_heart_geode_placed");
    
    // Biome Modifiers
    public static final ResourceLocation ADD_OVERWORLD_FEATURES = modLoc("add_overworld_features");
    public static final ResourceLocation ADD_NETHER_FEATURES = modLoc("add_nether_features");

    public static ResourceLocation modLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(LifeSteal.MOD_ID, name);
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
}
