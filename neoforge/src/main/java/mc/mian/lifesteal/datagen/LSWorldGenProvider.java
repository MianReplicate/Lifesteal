package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.datagen.worldgen.level.LSBiomeModifiersProvider;
import mc.mian.lifesteal.datagen.worldgen.level.LSConfiguredFeaturesProvider;
import mc.mian.lifesteal.datagen.worldgen.level.LSPlacedFeaturesProvider;
import mc.mian.lifesteal.datagen.worldgen.structure.LSStructurePoolProvider;
import mc.mian.lifesteal.datagen.worldgen.structure.LSStructureProvider;
import mc.mian.lifesteal.datagen.worldgen.structure.LSStructureSetProvider;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LSWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TEMPLATE_POOL, LSStructurePoolProvider::bootstrap)
            .add(Registries.STRUCTURE_SET, LSStructureSetProvider::bootstrap)
            .add(Registries.STRUCTURE, LSStructureProvider::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, LSConfiguredFeaturesProvider::bootstrap)
            .add(Registries.PLACED_FEATURE, LSPlacedFeaturesProvider::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, LSBiomeModifiersProvider::bootstrap);

    public LSWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Set.of(LSConstants.MOD_ID));
    }

}
