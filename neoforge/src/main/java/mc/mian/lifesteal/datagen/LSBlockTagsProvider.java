package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class LSBlockTagsProvider extends BlockTagsProvider {

    public LSBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, LSConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(LSBlocks.CRYSTAL_ORE.get(), LSBlocks.DEEPSLATE_CRYSTAL_ORE.get(), LSBlocks.NETHERRACK_CRYSTAL_ORE.get(), LSBlocks.CRYSTAL_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(LSBlocks.CRYSTAL_ORE.get(), LSBlocks.DEEPSLATE_CRYSTAL_ORE.get(), LSBlocks.CRYSTAL_BLOCK.get());
    }
}
