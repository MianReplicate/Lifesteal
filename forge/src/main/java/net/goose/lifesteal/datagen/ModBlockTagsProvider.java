package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, LifeSteal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.CRYSTAL_ORE.get(), ModBlocks.DEEPSLATE_CRYSTAL_ORE.get(), ModBlocks.NETHERRACK_CRYSTAL_ORE.get(), ModBlocks.CRYSTAL_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.CRYSTAL_ORE.get(), ModBlocks.DEEPSLATE_CRYSTAL_ORE.get(), ModBlocks.CRYSTAL_BLOCK.get());
    }
}