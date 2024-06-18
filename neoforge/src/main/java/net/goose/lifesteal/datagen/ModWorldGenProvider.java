package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.datagen.level.ModBiomeModifiersProvider;
import net.goose.lifesteal.datagen.level.ModConfiguredFeaturesProvider;
import net.goose.lifesteal.datagen.level.ModPlacedFeaturesProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeaturesProvider::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeaturesProvider::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiersProvider::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Set.of(LifeSteal.MOD_ID));
    }

}
