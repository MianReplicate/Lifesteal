package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.datagen.loottable.LSBlockLoot;
import mc.mian.lifesteal.datagen.loottable.LSChestLoot;
import mc.mian.lifesteal.datagen.worldgen.level.LSBiomeModifiersProvider;
import mc.mian.lifesteal.datagen.worldgen.level.LSFeaturesProvider;
import mc.mian.lifesteal.datagen.worldgen.level.LSPlacedFeaturesProvider;
import mc.mian.lifesteal.datagen.worldgen.structure.LSStructurePoolProvider;
import mc.mian.lifesteal.datagen.worldgen.structure.LSStructureProvider;
import mc.mian.lifesteal.datagen.worldgen.structure.LSStructureSetProvider;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LSWorldGenProvider {
    public static RegistrySetBuilder builder() {
        return new RegistrySetBuilder()
                .add(Registries.TEMPLATE_POOL, LSStructurePoolProvider::bootstrap)
                .add(Registries.STRUCTURE_SET, LSStructureSetProvider::bootstrap)
                .add(Registries.STRUCTURE, LSStructureProvider::bootstrap)
                .add(Registries.FEATURE, LSFeaturesProvider::bootstrap)
                .add(Registries.PLACED_FEATURE, LSPlacedFeaturesProvider::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, LSBiomeModifiersProvider::bootstrap);
    }
}
