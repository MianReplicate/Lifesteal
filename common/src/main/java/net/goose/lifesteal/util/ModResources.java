package net.goose.lifesteal.util;

import net.goose.lifesteal.LifeSteal;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModResources {
    public static final ResourceKey<LootTable> BARREL_1 =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("chests/barrel_1"));
    public static final ResourceKey<LootTable> MINERS_HOME_TABLE =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("chests/miners_home"));
    public static final ResourceKey<LootTable> MINERS_RUINED_SHACK_TABLE =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("chests/miners_ruined_shack"));
    public static final ResourceKey<LootTable> RICH_CART_TABLE =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("chests/rich_cart"));
    public static final ResourceKey<LootTable> RUINED_LIBRARY_TABLE =
            ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("chests/ruined_library"));

    public static final ResourceLocation GET_10_MAX_HEARTS = modLoc("get_10_max_hearts");
    public static final ResourceLocation USE_TOTEM_WHILE_20_MAX_HEARTS = modLoc("use_totem_while_20_max_hearts");
    public static final ResourceLocation REVIVED = modLoc("revived");

    public static final ResourceKey<PlacedFeature> HEART_ORE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, modLoc("heart_ore_placed"));
    public static final ResourceKey<PlacedFeature> NETHER_HEART_ORE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, modLoc("nether_heart_ore_placed"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_HEART_GEODE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, modLoc("deepslate_heart_geode_placed"));
    public static final ResourceKey<PlacedFeature> NETHER_HEART_GEODE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, modLoc("nether_heart_geode_placed"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_HEART_GEODE = ResourceKey.create(Registries.CONFIGURED_FEATURE, modLoc("deepslate_heart_geode"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEART_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, modLoc("heart_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_HEART_GEODE = ResourceKey.create(Registries.CONFIGURED_FEATURE, modLoc("nether_heart_geode"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_HEART_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, modLoc("nether_heart_ore"));
    public static final ResourceLocation ADD_OVERWORLD_FEATURES = modLoc("add_overworld_features");
    public static final ResourceLocation ADD_NETHER_FEATURES = modLoc("add_nether_features");
    
    public static ResourceLocation modLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(LifeSteal.MOD_ID, name);
    }

    public static String modLocString(String name) {
        return modLoc(name).toString();
    }
}
