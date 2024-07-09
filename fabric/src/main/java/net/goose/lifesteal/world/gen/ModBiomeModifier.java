package net.goose.lifesteal.world.gen;

import net.fabricmc.fabric.api.biome.v1.*;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.BiConsumer;

public class ModBiomeModifier {
    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> HEART_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    ModResources.HEART_ORE_PLACED);
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    ModResources.DEEPSLATE_HEART_GEODE_PLACED);
        };

    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> NETHER_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    ModResources.NETHER_HEART_ORE_PLACED);
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    ModResources.NETHER_HEART_GEODE_PLACED);
        };

    }

    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModBiomeModifier for " + LifeSteal.MOD_ID);
        BiomeModifications.create(ModResources.ADD_OVERWORLD_FEATURES)
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), HEART_MODIFIER());
        BiomeModifications.create(ModResources.ADD_NETHER_FEATURES)
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInTheNether(), NETHER_MODIFIER());
    }
}