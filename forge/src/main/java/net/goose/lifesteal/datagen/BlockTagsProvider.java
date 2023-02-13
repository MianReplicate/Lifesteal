package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BlockTagsProvider extends net.minecraft.data.tags.BlockTagsProvider {

    public BlockTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, LifeSteal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.HEART_ORE.get(), ModBlocks.DEEPSLATE_HEART_ORE.get(), ModBlocks.NETHERRACK_HEART_ORE.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.HEART_ORE.get(), ModBlocks.DEEPSLATE_HEART_ORE.get());
    }
}
