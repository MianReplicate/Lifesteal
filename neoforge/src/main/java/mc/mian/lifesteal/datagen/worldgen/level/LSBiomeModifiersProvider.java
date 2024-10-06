package mc.mian.lifesteal.datagen.worldgen.level;

import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LSBiomeModifiersProvider {
    private static final ResourceKey<BiomeModifier> ADD_OVERWORLD_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, LSConstants.ADD_OVERWORLD_FEATURES);
    private static final ResourceKey<BiomeModifier> ADD_NETHER_FEATURES = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, LSConstants.ADD_NETHER_FEATURES);

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var overworldTags = context.lookup(Registries.BIOME).getOrThrow(LSTags.OVERWORLD_ORE);
        var netherTags = context.lookup(Registries.BIOME).getOrThrow(LSTags.NETHER_ORE);

        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        BiomeModifiers.AddFeaturesBiomeModifier overworldModifier = new BiomeModifiers.AddFeaturesBiomeModifier(overworldTags, HolderSet.direct(placed.getOrThrow(LSConstants.HEART_ORE_PLACED), placed.getOrThrow(LSConstants.DEEPSLATE_HEART_GEODE_PLACED)), GenerationStep.Decoration.UNDERGROUND_ORES);
        BiomeModifiers.AddFeaturesBiomeModifier netherModifier = new BiomeModifiers.AddFeaturesBiomeModifier(netherTags, HolderSet.direct(placed.getOrThrow(LSConstants.NETHER_HEART_ORE_PLACED), placed.getOrThrow(LSConstants.NETHER_HEART_GEODE_PLACED)), GenerationStep.Decoration.UNDERGROUND_ORES);

        context.register(ADD_OVERWORLD_FEATURES, overworldModifier);
        context.register(ADD_NETHER_FEATURES, netherModifier);
    }
}
