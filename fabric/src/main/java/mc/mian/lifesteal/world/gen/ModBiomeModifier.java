package mc.mian.lifesteal.world.gen;

import mc.mian.lifesteal.util.LSConstants;
import net.fabricmc.fabric.api.biome.v1.*;
import mc.mian.lifesteal.LifeSteal;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.util.function.BiConsumer;

public class ModBiomeModifier {
    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> HEART_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    LSConstants.HEART_ORE_PLACED);
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    LSConstants.DEEPSLATE_HEART_GEODE_PLACED);
        };

    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> NETHER_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    LSConstants.NETHER_HEART_ORE_PLACED);
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    LSConstants.NETHER_HEART_GEODE_PLACED);
        };

    }

    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModBiomeModifier for " + LifeSteal.MOD_ID);
        BiomeModifications.create(LSConstants.ADD_OVERWORLD_FEATURES)
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), HEART_MODIFIER());
        BiomeModifications.create(LSConstants.ADD_NETHER_FEATURES)
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInTheNether(), NETHER_MODIFIER());
    }
}