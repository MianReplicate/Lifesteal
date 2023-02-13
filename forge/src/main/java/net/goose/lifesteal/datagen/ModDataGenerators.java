package net.goose.lifesteal.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModDataGenerators {
    @SubscribeEvent
    public static void generateData(GatherDataEvent ev) {
        final DataGenerator gen = ev.getGenerator();
        final ExistingFileHelper efh = ev.getExistingFileHelper();

        if (ev.includeServer()) {
            gen.addProvider(new RecipesProvider(gen)); // Recipes
            gen.addProvider(new LootProvider(gen));
            gen.addProvider(new BiomeTagsProvider(gen, efh)); // BiomeTags
            gen.addProvider(new BlockTagsProvider(gen, efh)); // BlockTags
            gen.addProvider(new AdvancementsProvider(gen, efh)); // Advancements
        }
    }
}
