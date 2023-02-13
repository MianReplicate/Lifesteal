package net.goose.lifesteal.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModDataGenerators {
    @SubscribeEvent
    public static void generateData(GatherDataEvent ev) {
        final DataGenerator gen = ev.getGenerator();
        final ExistingFileHelper efh = ev.getExistingFileHelper();

        if (ev.includeServer()) {
            gen.addProvider(ev.includeServer(), new WorldBiomeMods(gen)); // BiomeModifiers
            gen.addProvider(ev.includeServer(), new RecipesProvider(gen)); // Recipes
            gen.addProvider(ev.includeServer(), new LootProvider(gen));
            gen.addProvider(ev.includeServer(), new BiomeTagsProvider(gen, efh)); // BiomeTags
            gen.addProvider(ev.includeServer(), new BlockTagsProvider(gen, efh)); // BlockTags
            gen.addProvider(ev.includeServer(), new AdvancementsProvider(gen, efh)); // Advancements
        }
    }
}
