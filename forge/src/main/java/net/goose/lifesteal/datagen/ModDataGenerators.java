package net.goose.lifesteal.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModDataGenerators {
    @SubscribeEvent
    public static void generateData(GatherDataEvent ev) {
        final CompletableFuture<HolderLookup.Provider> provider = ev.getLookupProvider();
        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();
        final ExistingFileHelper efh = ev.getExistingFileHelper();

        if (ev.includeServer()) {
            gen.addProvider(ev.includeServer(), new ModWorldGenProvider(packOutput, provider)); // ConfiguredFeatures&PlacedFeatures with BiomeModifiers
            gen.addProvider(ev.includeServer(), new ModRecipesProvider(packOutput)); // Recipes
            gen.addProvider(ev.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), // LootTables
                    List.of(new LootTableProvider.SubProviderEntry(ModLootProvider.ModBlockLoot::new, LootContextParamSets.BLOCK),
                            new LootTableProvider.SubProviderEntry(ModLootProvider.ModChestLoot::new, LootContextParamSets.CHEST))));
            gen.addProvider(ev.includeServer(), new ModBiomeTagsProvider(packOutput, provider, efh)); // BiomeTags
            gen.addProvider(ev.includeServer(), new ModBlockTagsProvider(packOutput, provider, efh)); // BlockTags
            gen.addProvider(ev.includeServer(), new ModAdvancementsProvider(packOutput, provider, efh, // Advancements
                    List.of(new ModAdvancementsProvider.AdvancementsGenerator())));
        }
    }
}
