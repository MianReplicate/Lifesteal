package net.goose.lifesteal.world.gen;

import net.goose.lifesteal.world.feature.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Set;

public class ModGeodeGeneration {
    public static void generateGeodes(BiomeLoadingEvent event) {
        List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        if(types.contains(BiomeDictionary.Type.OVERWORLD)){
            base.add(ModPlacedFeatures.DEEPSLATE_HEART_GEODE_PLACED);
        }
        if(types.contains(BiomeDictionary.Type.NETHER)){
            base.add(ModPlacedFeatures.NETHER_HEART_GEODE_PLACED);
        }
    }
}