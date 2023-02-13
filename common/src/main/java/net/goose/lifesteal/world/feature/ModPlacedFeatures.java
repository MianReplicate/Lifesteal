package net.goose.lifesteal.world.feature;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegistry<PlacedFeature> PLACED_FEATURES = DeferredRegistry.create(LifeSteal.MOD_ID, Registry.PLACED_FEATURE_REGISTRY);

    public static final RegistrySupplier<PlacedFeature> HEART_ORE_PLACED = PLACED_FEATURES.register("heart_ore_placed", () -> new PlacedFeature(
            Holder.direct(ModConfiguredFeatures.HEART_ORE.get()),
            List.copyOf(
                    commonOrePlacement(6, //VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(70))))));

    public static final RegistrySupplier<PlacedFeature> NETHER_HEART_ORE_PLACED = PLACED_FEATURES.register("nether_heart_ore_placed", () -> new PlacedFeature(
            Holder.direct(ModConfiguredFeatures.NETHER_HEART_ORE.get()),
            List.copyOf(
                    commonOrePlacement(6, //VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(20), VerticalAnchor.absolute(100))))));

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static final RegistrySupplier<PlacedFeature> DEEPSLATE_HEART_GEODE_PLACED = PLACED_FEATURES.register("deepslate_heart_geode_placed", () -> new PlacedFeature(
            Holder.direct(ModConfiguredFeatures.DEEPSLATE_HEART_GEODE.get()),
            List.of(
                    RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(-45), VerticalAnchor.absolute(0)),
                    BiomeFilter.biome())));
    public static final RegistrySupplier<PlacedFeature> NETHER_HEART_GEODE_PLACED = PLACED_FEATURES.register("nether_heart_geode_placed", () -> new PlacedFeature(
            Holder.direct(ModConfiguredFeatures.NETHER_HEART_GEODE.get()),
            List.of(
                    RarityFilter.onAverageOnceEvery(30), InSquarePlacement.spread(),
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(30)),
                    BiomeFilter.biome())));
    public static void register(){
        LifeSteal.LOGGER.debug("Registering ModPlacedFeatures for "+LifeSteal.MOD_ID);
        PLACED_FEATURES.register();
    }
}
