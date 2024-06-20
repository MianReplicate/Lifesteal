package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModStateAndModelProvider extends BlockStateProvider {
    public ModStateAndModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LifeSteal.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile crystal_block_model = cubeAll(ModBlocks.CRYSTAL_BLOCK.get());
        simpleBlockWithItem(ModBlocks.CRYSTAL_BLOCK.get(), crystal_block_model);

        ModelFile deepslate_heart_ore_model = cubeAll(ModBlocks.DEEPSLATE_CRYSTAL_ORE.get());
        simpleBlockWithItem(ModBlocks.DEEPSLATE_CRYSTAL_ORE.get(), deepslate_heart_ore_model);

        ModelFile heart_ore_model = cubeAll(ModBlocks.CRYSTAL_ORE.get());
        simpleBlockWithItem(ModBlocks.CRYSTAL_ORE.get(), heart_ore_model);

        ModelFile netherrack_heart_ore_model = cubeAll(ModBlocks.NETHERRACK_CRYSTAL_ORE.get());
        simpleBlockWithItem(ModBlocks.NETHERRACK_CRYSTAL_ORE.get(), netherrack_heart_ore_model);

        ModelFile skull = models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull"));
        simpleBlock(ModBlocks.REVIVE_HEAD.get(), skull);
        simpleBlock(ModBlocks.REVIVE_WALL_HEAD.get(), skull);
    }
}
