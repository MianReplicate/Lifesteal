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
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LSDataGenerators {
    private static final String PATH_ITEM_PREFIX = "textures/item";
    private static final String PATH_BLOCK_PREFIX = "textures/block";
    private static final String PATH_SUFFIX = ".png";

    @SubscribeEvent
    public static void generateData(GatherDataEvent ev) {
        final CompletableFuture<HolderLookup.Provider> provider = ev.getLookupProvider();
        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();
        final ExistingFileHelper efh = ev.getExistingFileHelper();

        addVirtualPackContents(efh);

        if (ev.includeServer()) {
            gen.addProvider(ev.includeServer(), new LSWorldGenProvider(packOutput, provider)); // ConfiguredFeatures&PlacedFeatures with BiomeModifiers && Structures
            gen.addProvider(ev.includeServer(), new LSRecipesProvider(packOutput, provider)); // Recipes
            gen.addProvider(ev.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), // LootTables
                    List.of(new LootTableProvider.SubProviderEntry(LSLootProvider.ModBlockLoot::new, LootContextParamSets.BLOCK),
                            new LootTableProvider.SubProviderEntry(LSLootProvider.ModChestLoot::new, LootContextParamSets.CHEST)),
                    provider));
            gen.addProvider(ev.includeServer(), new LSBiomeTagsProvider(packOutput, provider, efh)); // BiomeTags
            gen.addProvider(ev.includeServer(), new LSAdvancementsProvider(packOutput, provider, efh, // Advancements
                    List.of(new LSAdvancementsProvider.AdvancementsGenerator())));
            TagsProvider tagsProvider = gen.addProvider(ev.includeServer(), new LSBlockTagsProvider(packOutput, provider, efh)); // BlockTags
            gen.addProvider(ev.includeServer(), new LSItemTagsProvider(packOutput, provider, tagsProvider.contentsGetter(), efh));
            gen.addProvider(ev.includeServer(), new LSLangProvider(packOutput));
            gen.addProvider(ev.includeServer(), new LSStateAndModelProvider(packOutput, efh));
            gen.addProvider(ev.includeServer(), new LSItemModelProvider(packOutput, efh));
        }
    }

    private static void addVirtualPackContents(ExistingFileHelper existingFileHelper) {
        existingFileHelper.trackGenerated(
                LSConstants.modLoc(LSBlocks.CRYSTAL_BLOCK.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
        );
        existingFileHelper.trackGenerated(
                LSConstants.modLoc(LSBlocks.DEEPSLATE_CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
        );
        existingFileHelper.trackGenerated(
                LSConstants.modLoc(LSBlocks.CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
        );
        existingFileHelper.trackGenerated(
                LSConstants.modLoc(LSBlocks.NETHERRACK_CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
        );
        existingFileHelper.trackGenerated(
                LSConstants.modLoc(LSItems.CRYSTAL_CORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
        );
        existingFileHelper.trackGenerated(
                LSConstants.modLoc(LSItems.HEART_CRYSTAL.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
        );
        existingFileHelper.trackGenerated(
                LSConstants.modLoc(LSItems.CRYSTAL_FRAGMENT.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
        );
        existingFileHelper.trackGenerated(
                LSConstants.modLoc(LSItems.REVIVE_CRYSTAL.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
        );
    }
}
