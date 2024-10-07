package mc.mian.lifesteal.datagen.worldgen.level;

import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class LSBiomeModifiersProvider {
    private static final ResourceKey<BiomeModifier> ADD_OVERWORLD_FEATURES = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, LSConstants.ADD_OVERWORLD_FEATURES);
    private static final ResourceKey<BiomeModifier> ADD_NETHER_FEATURES = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, LSConstants.ADD_NETHER_FEATURES);

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var overworldTags = context.lookup(Registries.BIOME).getOrThrow(LSTags.OVERWORLD_ORE);
        var netherTags = context.lookup(Registries.BIOME).getOrThrow(LSTags.NETHER_ORE);

        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        ForgeBiomeModifiers.AddFeaturesBiomeModifier overworldModifier = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldTags, HolderSet.direct(placed.getOrThrow(LSConstants.HEART_ORE_PLACED), placed.getOrThrow(LSConstants.DEEPSLATE_HEART_GEODE_PLACED)), GenerationStep.Decoration.UNDERGROUND_ORES);
        ForgeBiomeModifiers.AddFeaturesBiomeModifier netherModifier = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(netherTags, HolderSet.direct(placed.getOrThrow(LSConstants.NETHER_HEART_ORE_PLACED), placed.getOrThrow(LSConstants.NETHER_HEART_GEODE_PLACED)), GenerationStep.Decoration.UNDERGROUND_ORES);

        context.register(ADD_OVERWORLD_FEATURES, overworldModifier);
        context.register(ADD_NETHER_FEATURES, netherModifier);
    }
}
