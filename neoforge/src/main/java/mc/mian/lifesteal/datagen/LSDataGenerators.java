package mc.mian.lifesteal.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LSDataGenerators {

    @SubscribeEvent
    public static void generateData(GatherDataEvent.Client ev) {
        final CompletableFuture<HolderLookup.Provider> provider = ev.getLookupProvider();
        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();

        gen.addProvider(true, new LSWorldGenProvider(packOutput, provider)); // ConfiguredFeatures&PlacedFeatures with BiomeModifiers && Structures
        gen.addProvider(true, new LSRecipesProvider.Runner(packOutput, provider)); // Recipes
        gen.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(), // LootTables
                List.of(new LootTableProvider.SubProviderEntry(LSLootProvider.ModBlockLoot::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(LSLootProvider.ModChestLoot::new, LootContextParamSets.CHEST)),
                provider));
        gen.addProvider(true, new LSBiomeTagsProvider(packOutput, provider)); // BiomeTags
        gen.addProvider(true, new LSAdvancementsProvider(packOutput, provider, // Advancements
                List.of(new LSAdvancementsProvider.AdvancementsGenerator())));
        gen.addProvider(true, new LSBlockTagsProvider(packOutput, provider)); // BlockTags
        gen.addProvider(true, new LSItemTagsProvider(packOutput, provider));
        gen.addProvider(true, new LSLangProvider(packOutput));
        gen.addProvider(true, new LSModelProvider(packOutput));
    }

}
