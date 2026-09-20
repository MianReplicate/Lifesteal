package mc.mian.lifesteal.datagen.worldgen.level;

import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class LSPlacedFeaturesProvider {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> features = context.lookup(Registries.FEATURE);

        register(context, LSConstants.DEEPSLATE_HEART_GEODE_PLACED, features.getOrThrow(LSConstants.DEEPSLATE_HEART_GEODE_CONFIGURED),
                List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-45), VerticalAnchor.absolute(0)),
                        BiomeFilter.biome()));
        register(context, LSConstants.HEART_ORE_PLACED, features.getOrThrow(LSConstants.HEART_ORE_CONFIGURED),
                List.copyOf(
                        commonOrePlacement(6,
                                HeightRangePlacement.triangle(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(70)))));
        register(context, LSConstants.NETHER_HEART_GEODE_PLACED, features.getOrThrow(LSConstants.NETHER_HEART_GEODE_CONFIGURED),
                List.of(RarityFilter.onAverageOnceEvery(30), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(30)),
                        BiomeFilter.biome()));
        register(context, LSConstants.NETHER_HEART_ORE_PLACED, features.getOrThrow(LSConstants.NETHER_HEART_ORE_CONFIGURED),
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

    public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<Feature> feature, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(feature, List.copyOf(modifiers)));
    }

    public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<Feature> feature, PlacementModifier... modifiers) {
        register(context, key, feature, List.of(modifiers));
    }
}
