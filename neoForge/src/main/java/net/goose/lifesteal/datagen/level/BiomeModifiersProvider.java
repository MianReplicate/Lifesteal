package net.goose.lifesteal.datagen.level;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BiomeModifiersProvider {
    private static final ResourceKey<BiomeModifier> ADD_OVERWORLD_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(LifeSteal.MOD_ID, "add_overworld_features"));
    private static final ResourceKey<BiomeModifier> ADD_NETHER_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(LifeSteal.MOD_ID, "add_nether_features"));

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var overworldTags = context.lookup(Registries.BIOME).getOrThrow(ModTags.OVERWORLD_ORE);
        var netherTags = context.lookup(Registries.BIOME).getOrThrow(ModTags.NETHER_ORE);

        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        BiomeModifiers.AddFeaturesBiomeModifier overworldModifier = new BiomeModifiers.AddFeaturesBiomeModifier(overworldTags, HolderSet.direct(placed.getOrThrow(PlacedFeaturesProvider.HEART_ORE_PLACED), placed.getOrThrow(PlacedFeaturesProvider.DEEPSLATE_HEART_GEODE_PLACED)), GenerationStep.Decoration.UNDERGROUND_ORES);
        BiomeModifiers.AddFeaturesBiomeModifier netherModifier = new BiomeModifiers.AddFeaturesBiomeModifier(netherTags, HolderSet.direct(placed.getOrThrow(PlacedFeaturesProvider.NETHER_HEART_ORE_PLACED), placed.getOrThrow(PlacedFeaturesProvider.NETHER_HEART_GEODE_PLACED)), GenerationStep.Decoration.UNDERGROUND_ORES);

        context.register(ADD_OVERWORLD_FEATURES, overworldModifier);
        context.register(ADD_NETHER_FEATURES, netherModifier);
    }
}
