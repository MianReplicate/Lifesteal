package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.datagen.worldgen.level.ModBiomeModifiersProvider;
import net.goose.lifesteal.datagen.worldgen.level.ModConfiguredFeaturesProvider;
import net.goose.lifesteal.datagen.worldgen.level.ModPlacedFeaturesProvider;
import net.goose.lifesteal.datagen.worldgen.structure.ModStructurePoolProvider;
import net.goose.lifesteal.datagen.worldgen.structure.ModStructureProvider;
import net.goose.lifesteal.datagen.worldgen.structure.ModStructureSetProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TEMPLATE_POOL, ModStructurePoolProvider::bootstrap)
            .add(Registries.STRUCTURE_SET, ModStructureSetProvider::bootstrap)
            .add(Registries.STRUCTURE, ModStructureProvider::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeaturesProvider::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeaturesProvider::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiersProvider::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Set.of(LifeSteal.MOD_ID));
    }

}
