package net.goose.lifesteal.world;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.world.feature.ModPlacedFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LifeSteal.MOD_ID)
public class ModFeatureGeneration {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        BiomeGenerationSettingsBuilder gen = event.getGeneration();
        if (event.getCategory() != Biome.BiomeCategory.NETHER && event.getCategory() != Biome.BiomeCategory.THEEND) {
            gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.HEART_ORE_PLACED).addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.DEEPSLATE_HEART_GEODE_PLACED);
        } else if(event.getCategory() == Biome.BiomeCategory.NETHER) {
            gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.NETHER_HEART_ORE_PLACED).addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.NETHER_HEART_GEODE_PLACED);

        }
    }
}