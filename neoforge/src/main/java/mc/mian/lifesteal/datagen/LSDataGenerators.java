package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.datagen.loottable.LSLootTableProvider;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;
import net.neoforged.neoforge.common.data.internal.NeoForgeItemTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LSDataGenerators {

    @SubscribeEvent
    public static void generateData(GatherDataEvent.Client ev) {
        final CompletableFuture<HolderLookup.Provider> wProvider = ev.getWorldLookupProvider();

        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();

        ev.createReloadableRegistryObjects(
                new RegistrySetBuilder()
                        .add(Registries.ADVANCEMENT, new LSAdvancementsProvider(List.of(LSAdvancementsProvider.LSAdvancementsGenerator::new)))
                        .add(RecipeProvider.asBootstrap(LSRecipesProvider::new))
                        .add(Registries.LOOT_TABLE, LSLootTableProvider.create()),
                Set.of(LSConstants.MOD_ID));
        ev.createWorldRegistryObjects(LSWorldGenProvider.builder(), Set.of(LSConstants.MOD_ID));

        ev.createBlockAndItemTags(LSBlockTagsProvider::new, LSItemTagsProvider::new);

        gen.addProvider(true, new LSBiomeTagsProvider(packOutput, wProvider)); // BiomeTags
        gen.addProvider(true, new LSLangProvider(packOutput));
        gen.addProvider(true, new LSModelProvider(packOutput));
    }

}
