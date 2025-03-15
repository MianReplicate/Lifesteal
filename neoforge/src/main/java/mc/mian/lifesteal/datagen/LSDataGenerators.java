package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LSDataGenerators {
//    private static final String PATH_ITEM_PREFIX = "textures/item";
//    private static final String PATH_BLOCK_PREFIX = "textures/block";
//    private static final String PATH_SUFFIX = ".png";

    @SubscribeEvent
    public static void generateData(GatherDataEvent.Client ev) {
        final CompletableFuture<HolderLookup.Provider> provider = ev.getLookupProvider();
        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();

//        addVirtualPackContents(efh);

//        if (ev.includeServer()) {
        gen.addProvider(true, new LSWorldGenProvider(packOutput, provider)); // ConfiguredFeatures&PlacedFeatures with BiomeModifiers && Structures
        gen.addProvider(true, new LSRecipesProvider.Runner(packOutput, provider)); // Recipes
        gen.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(), // LootTables
                List.of(new LootTableProvider.SubProviderEntry(LSLootProvider.ModBlockLoot::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(LSLootProvider.ModChestLoot::new, LootContextParamSets.CHEST)),
                provider));
        gen.addProvider(true, new LSBiomeTagsProvider(packOutput, provider)); // BiomeTags
        gen.addProvider(true, new LSAdvancementsProvider(packOutput, provider, // Advancements
                List.of(new LSAdvancementsProvider.AdvancementsGenerator())));
        TagsProvider tagsProvider = gen.addProvider(true, new LSBlockTagsProvider(packOutput, provider)); // BlockTags
        gen.addProvider(true, new LSItemTagsProvider(packOutput, provider, tagsProvider.contentsGetter()));
        gen.addProvider(true, new LSLangProvider(packOutput));
        gen.addProvider(true, new LSModelProvider(packOutput));
//        gen.addProvider(true, new LSItemModelProvider(packOutput));
//        }
    }

//    private static void addVirtualPackContents(ExistingFileHelper existingFileHelper) {
//        existingFileHelper.trackGenerated(
//                LSConstants.modLoc(LSBlocks.CRYSTAL_BLOCK.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
//        );
//        existingFileHelper.trackGenerated(
//                LSConstants.modLoc(LSBlocks.DEEPSLATE_CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
//        );
//        existingFileHelper.trackGenerated(
//                LSConstants.modLoc(LSBlocks.CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
//        );
//        existingFileHelper.trackGenerated(
//                LSConstants.modLoc(LSBlocks.NETHERRACK_CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
//        );
//        existingFileHelper.trackGenerated(
//                LSConstants.modLoc(LSItems.CRYSTAL_CORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
//        );
//        existingFileHelper.trackGenerated(
//                LSConstants.modLoc(LSItems.HEART_CRYSTAL.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
//        );
//        existingFileHelper.trackGenerated(
//                LSConstants.modLoc(LSItems.CRYSTAL_FRAGMENT.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
//        );
//        existingFileHelper.trackGenerated(
//                LSConstants.modLoc(LSItems.REVIVE_CRYSTAL.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
//        );
//    }
}
