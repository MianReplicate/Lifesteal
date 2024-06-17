package net.goose.lifesteal.datagen.level;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeaturesProvider {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ModResources.DEEPSLATE_HEART_GEODE_PLACED, configuredFeatures.getOrThrow(ModResources.DEEPSLATE_HEART_GEODE),
                List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-45), VerticalAnchor.absolute(0)),
                        BiomeFilter.biome()));
        register(context, ModResources.HEART_ORE_PLACED, configuredFeatures.getOrThrow(ModResources.HEART_ORE),
                List.copyOf(
                        commonOrePlacement(6,
                                HeightRangePlacement.triangle(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(70)))));
        register(context, ModResources.NETHER_HEART_GEODE_PLACED, configuredFeatures.getOrThrow(ModResources.NETHER_HEART_GEODE),
                List.of(RarityFilter.onAverageOnceEvery(30), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(30)),
                        BiomeFilter.biome()));
        register(context, ModResources.NETHER_HEART_ORE_PLACED, configuredFeatures.getOrThrow(ModResources.NETHER_HEART_ORE),
                List.copyOf(
                        commonOrePlacement(6,
                                HeightRangePlacement.triangle(VerticalAnchor.absolute(20), VerticalAnchor.absolute(100)))));

    }

    private static List<PlacementModifier> orePlacement(PlacementModifier plMod, PlacementModifier plMod2) {
        return List.of(plMod, InSquarePlacement.spread(), plMod2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int amt, PlacementModifier plMod) {
        return orePlacement(CountPlacement.of(amt), plMod);
    }

    public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
