package net.goose.lifesteal.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModTags;
import net.goose.lifesteal.world.gen.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public record WorldBiomeMods(DataGenerator dataGenerator) implements DataProvider {
    public static void generate(RegistryOps<JsonElement> ops, BiomeModifier modifier, Path outputFolder, String saveName, CachedOutput cache) {
        final String directory = PackType.SERVER_DATA.getDirectory();
        final ResourceLocation biomeModifiersRegistryID = ForgeRegistries.Keys.BIOME_MODIFIERS.location();

        final String biomeModifierPathString = String.join("/", directory, LifeSteal.MOD_ID, biomeModifiersRegistryID.getNamespace(), biomeModifiersRegistryID.getPath(), saveName + ".json");

        BiomeModifier.DIRECT_CODEC.encodeStart(ops, modifier).resultOrPartial(msg -> LifeSteal.LOGGER.error("Failed to encode {}: {}", biomeModifierPathString, msg)).ifPresent(json ->
        {
            try {
                final Path biomeModifierPath = outputFolder.resolve(biomeModifierPathString);
                DataProvider.saveStable(cache, json, biomeModifierPath);
            } catch (
                    IOException e) {
                LifeSteal.LOGGER.error("Failed to save " + biomeModifierPathString, e);
            }
        });
    }
    @Override
    public void run(@NotNull CachedOutput cachedOutput) {
        RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());
        final Path outputFolder = this.dataGenerator.getOutputFolder();

        // Biome Modifiers
        ForgeBiomeModifiers.AddFeaturesBiomeModifier overworldModifier = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), ModTags.OVERWORLD_ORE), HolderSet.direct(Holder.direct(ModPlacedFeatures.HEART_ORE_PLACED.get()), Holder.direct(ModPlacedFeatures.DEEPSLATE_HEART_GEODE_PLACED.get())), GenerationStep.Decoration.UNDERGROUND_ORES);
        ForgeBiomeModifiers.AddFeaturesBiomeModifier netherModifier = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), ModTags.NETHER_ORE), HolderSet.direct(Holder.direct(ModPlacedFeatures.NETHER_HEART_ORE_PLACED.get()), Holder.direct(ModPlacedFeatures.NETHER_HEART_GEODE_PLACED.get())), GenerationStep.Decoration.UNDERGROUND_ORES);

        generate(ops, overworldModifier, outputFolder, "add_overworld_features", cachedOutput);
        generate(ops, netherModifier, outputFolder, "add_nether_features", cachedOutput);
    }

    @Override
    public @NotNull String getName() {
        return LifeSteal.MOD_ID + " Biome Modifiers";
    }
}
