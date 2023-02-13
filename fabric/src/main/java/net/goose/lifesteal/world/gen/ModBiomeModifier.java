package net.goose.lifesteal.world.gen;

import net.fabricmc.fabric.api.biome.v1.*;
import net.goose.lifesteal.LifeSteal;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.BiConsumer;

public class ModBiomeModifier {
    public static final ResourceKey<PlacedFeature> HEART_ORE_PLACED = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(LifeSteal.MOD_ID, "heart_ore_placed"));
    public static final ResourceKey<PlacedFeature> NETHER_HEART_ORE_PLACED = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(LifeSteal.MOD_ID, "nether_heart_ore_placed"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_HEART_GEODE_PLACED = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(LifeSteal.MOD_ID, "deepslate_heart_geode_placed"));
    public static final ResourceKey<PlacedFeature> NETHER_HEART_GEODE_PLACED = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(LifeSteal.MOD_ID, "nether_heart_geode_placed"));

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> HEART_ORE_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) ->
                // here we can potentially narrow our biomes down
                // but here we won't
                biomeModificationContext.getGenerationSettings().addFeature(
                        // ores to ores
                        GenerationStep.Decoration.UNDERGROUND_ORES,
                        // this is the key of the placed feature
                        HEART_ORE_PLACED);

    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> DEEPSLATE_HEART_GEODE_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) ->
                // here we can potentially narrow our biomes down
                // but here we won't
                biomeModificationContext.getGenerationSettings().addFeature(
                        // ores to ores
                        GenerationStep.Decoration.UNDERGROUND_ORES,
                        // this is the key of the placed feature
                        DEEPSLATE_HEART_GEODE_PLACED);

    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> NETHER_HEART_GEODE_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) ->
                // here we can potentially narrow our biomes down
                // but here we won't
                biomeModificationContext.getGenerationSettings().addFeature(
                        // ores to ores
                        GenerationStep.Decoration.UNDERGROUND_ORES,
                        // this is the key of the placed feature
                        NETHER_HEART_GEODE_PLACED);

    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> NETHER_HEART_ORE_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) ->
                // here we can potentially narrow our biomes down
                // but here we won't
                biomeModificationContext.getGenerationSettings().addFeature(
                        // ores to ores
                        GenerationStep.Decoration.UNDERGROUND_ORES,
                        // this is the key of the placed feature
                        NETHER_HEART_ORE_PLACED);

    }


    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModBiomeModifier for " + LifeSteal.MOD_ID);
        BiomeModifications.create(new ResourceLocation(LifeSteal.MOD_ID, "features"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), HEART_ORE_MODIFIER());
        BiomeModifications.create(new ResourceLocation(LifeSteal.MOD_ID, "features"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), DEEPSLATE_HEART_GEODE_MODIFIER());
        BiomeModifications.create(new ResourceLocation(LifeSteal.MOD_ID, "features"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInTheNether(), NETHER_HEART_ORE_MODIFIER());
        BiomeModifications.create(new ResourceLocation(LifeSteal.MOD_ID, "features"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInTheNether(), NETHER_HEART_GEODE_MODIFIER());
    }
}