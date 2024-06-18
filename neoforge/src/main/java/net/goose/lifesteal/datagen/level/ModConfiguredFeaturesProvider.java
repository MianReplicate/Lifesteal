package net.goose.lifesteal.datagen.level;

import com.google.common.collect.ImmutableList;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeaturesProvider {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceable = new TagMatchTest(BlockTags.BASE_STONE_NETHER);

        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, ModResources.DEEPSLATE_HEART_GEODE_CONFIGURED, Feature.GEODE,
                new GeodeConfiguration(
                        new GeodeBlockSettings(
                                BlockStateProvider.simple(Blocks.AIR),
                                BlockStateProvider.simple(Blocks.DEEPSLATE),
                                BlockStateProvider.simple(ModBlocks.DEEPSLATE_HEART_ORE.get()),
                                BlockStateProvider.simple(Blocks.CALCITE),
                                BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                                List.of(Blocks.GRAVEL.defaultBlockState()),
                                BlockTags.FEATURES_CANNOT_REPLACE,
                                BlockTags.GEODE_INVALID_BLOCKS
                        ),
                        new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackSettings(0.95, 2.0, 2),
                        0.35, 0.083, true, UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2), 16, -16, 0.05, 1));
        register(context, ModResources.NETHER_HEART_GEODE_CONFIGURED, Feature.GEODE,
                new GeodeConfiguration(
                        new GeodeBlockSettings(
                                BlockStateProvider.simple(Blocks.AIR),
                                BlockStateProvider.simple(Blocks.NETHERRACK),
                                BlockStateProvider.simple(ModBlocks.NETHERRACK_HEART_ORE.get()),
                                BlockStateProvider.simple(Blocks.MAGMA_BLOCK),
                                BlockStateProvider.simple(Blocks.BLACKSTONE),
                                List.of(Blocks.NETHER_GOLD_ORE.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()),
                                BlockTags.FEATURES_CANNOT_REPLACE,
                                BlockTags.GEODE_INVALID_BLOCKS
                        ),
                        new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackSettings(0.95, 2.0, 2),
                        0.35, 0.083, true, UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2), 16, -16, 0.05, 1));
        register(context, ModResources.HEART_ORE_CONFIGURED, Feature.SCATTERED_ORE,
                new OreConfiguration(
                        ImmutableList.of(
                                OreConfiguration.target(stoneReplaceable, ModBlocks.HEART_ORE.get().defaultBlockState()),
                                OreConfiguration.target(deepslateReplaceable, ModBlocks.DEEPSLATE_HEART_ORE.get().defaultBlockState())), 6));
        register(context, ModResources.NETHER_HEART_ORE_CONFIGURED, Feature.SCATTERED_ORE,
                new OreConfiguration(
                        ImmutableList.of(
                                OreConfiguration.target(netherReplaceable, ModBlocks.NETHERRACK_HEART_ORE.get().defaultBlockState())), 7));
    }

    public static void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Feature<NoneFeatureConfiguration> feature) {
        register(context, key, feature, FeatureConfiguration.NONE);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
