package net.goose.lifesteal.datagen;

import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.util.ModResources;
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

public class ModDataGenerators {
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
            gen.addProvider(ev.includeServer(), new ModWorldGenProvider(packOutput, provider)); // ConfiguredFeatures&PlacedFeatures with BiomeModifiers && Structures
            gen.addProvider(ev.includeServer(), new ModRecipesProvider(packOutput, provider)); // Recipes
            gen.addProvider(ev.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), // LootTables
                    List.of(new LootTableProvider.SubProviderEntry(ModLootProvider.ModBlockLoot::new, LootContextParamSets.BLOCK),
                            new LootTableProvider.SubProviderEntry(ModLootProvider.ModChestLoot::new, LootContextParamSets.CHEST)),
                    provider));
            gen.addProvider(ev.includeServer(), new ModBiomeTagsProvider(packOutput, provider, efh)); // BiomeTags
            gen.addProvider(ev.includeServer(), new ModAdvancementsProvider(packOutput, provider, efh, // Advancements
                    List.of(new ModAdvancementsProvider.AdvancementsGenerator())));
            TagsProvider tagsProvider = gen.addProvider(ev.includeServer(), new ModBlockTagsProvider(packOutput, provider, efh)); // BlockTags
            gen.addProvider(ev.includeServer(), new ModItemTagsProvider(packOutput, provider, tagsProvider.contentsGetter(), efh));
            gen.addProvider(ev.includeServer(), new ModLangProvider(packOutput));
            gen.addProvider(ev.includeServer(), new ModStateAndModelProvider(packOutput, efh));
            gen.addProvider(ev.includeServer(), new ModItemModelProvider(packOutput, efh));
        }
    }

    private static void addVirtualPackContents(ExistingFileHelper existingFileHelper) {
        existingFileHelper.trackGenerated(
                ModResources.modLoc(ModBlocks.CRYSTAL_BLOCK.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
        );
        existingFileHelper.trackGenerated(
                ModResources.modLoc(ModBlocks.DEEPSLATE_CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
        );
        existingFileHelper.trackGenerated(
                ModResources.modLoc(ModBlocks.CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
        );
        existingFileHelper.trackGenerated(
                ModResources.modLoc(ModBlocks.NETHERRACK_CRYSTAL_ORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_BLOCK_PREFIX
        );
        existingFileHelper.trackGenerated(
                ModResources.modLoc(ModItems.CRYSTAL_CORE.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
        );
        existingFileHelper.trackGenerated(
                ModResources.modLoc(ModItems.HEART_CRYSTAL.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
        );
        existingFileHelper.trackGenerated(
                ModResources.modLoc(ModItems.CRYSTAL_FRAGMENT.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
        );
        existingFileHelper.trackGenerated(
                ModResources.modLoc(ModItems.REVIVE_CRYSTAL.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
        );
    }
}
