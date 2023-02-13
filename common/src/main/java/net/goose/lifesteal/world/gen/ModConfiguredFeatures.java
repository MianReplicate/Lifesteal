package net.goose.lifesteal.world.gen;

import com.google.common.collect.ImmutableList;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegistry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegistry.create(LifeSteal.MOD_ID, Registry.CONFIGURED_FEATURE_REGISTRY);
    public static RegistrySupplier<ConfiguredFeature<?, ?>> HEART_ORE = CONFIGURED_FEATURES.register("heart_ore", () -> new ConfiguredFeature<>(
            Feature.SCATTERED_ORE,
            new OreConfiguration(ImmutableList.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.HEART_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_HEART_ORE.get().defaultBlockState())),
                    6)));

    public static RegistrySupplier<ConfiguredFeature<?, ?>> NETHER_HEART_ORE = CONFIGURED_FEATURES.register("nether_heart_ore", () -> new ConfiguredFeature<>(
            Feature.SCATTERED_ORE,
            new OreConfiguration(ImmutableList.of(
                    OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, ModBlocks.NETHERRACK_HEART_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_HEART_ORE.get().defaultBlockState())),
                    7)));
    public static final RegistrySupplier<ConfiguredFeature<?, ?>>  DEEPSLATE_HEART_GEODE = CONFIGURED_FEATURES.register("deepslate_heart_geode", () -> new ConfiguredFeature<>(
            Feature.GEODE,
            new GeodeConfiguration(new GeodeBlockSettings(
                    BlockStateProvider.simple(Blocks.AIR),
                    BlockStateProvider.simple(Blocks.DEEPSLATE),
                    BlockStateProvider.simple(ModBlocks.DEEPSLATE_HEART_ORE.get()),
                    BlockStateProvider.simple(Blocks.DIRT),
                    BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                    List.of(Blocks.CALCITE.defaultBlockState()),
                    BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                    new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                    new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D,
                    true, UniformInt.of(4, 6),
                    UniformInt.of(3, 4), UniformInt.of(1, 2),
                    -16, 16, 0.05D, 1)));
    public static final RegistrySupplier<ConfiguredFeature<?, ?>> NETHER_HEART_GEODE = CONFIGURED_FEATURES.register("nether_heart_geode", () -> new ConfiguredFeature<>(
            Feature.GEODE,
            new GeodeConfiguration(new GeodeBlockSettings(
                    BlockStateProvider.simple(Blocks.AIR),
                    BlockStateProvider.simple(Blocks.NETHERRACK),
                    BlockStateProvider.simple(ModBlocks.NETHERRACK_HEART_ORE.get()),
                    BlockStateProvider.simple(Blocks.MAGMA_BLOCK),
                    BlockStateProvider.simple(Blocks.BLACKSTONE),
                    List.of(Blocks.NETHER_GOLD_ORE.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()),
                    BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                    new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                    new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D,
                    true, UniformInt.of(4, 6),
                    UniformInt.of(3, 4), UniformInt.of(1, 2),
                    -16, 16, 0.05D, 1)));
    public static void register(){
        LifeSteal.LOGGER.debug("Registering ModConfiguredFeatures for "+LifeSteal.MOD_ID);
        CONFIGURED_FEATURES.register();
    }
}
