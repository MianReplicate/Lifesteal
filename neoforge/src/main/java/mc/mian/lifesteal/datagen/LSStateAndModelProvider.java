package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class LSStateAndModelProvider extends BlockStateProvider {
    public LSStateAndModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LSConstants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile crystal_block_model = cubeAll(LSBlocks.CRYSTAL_BLOCK.get());
        simpleBlockWithItem(LSBlocks.CRYSTAL_BLOCK.get(), crystal_block_model);

        ModelFile deepslate_heart_ore_model = cubeAll(LSBlocks.DEEPSLATE_CRYSTAL_ORE.get());
        simpleBlockWithItem(LSBlocks.DEEPSLATE_CRYSTAL_ORE.get(), deepslate_heart_ore_model);

        ModelFile heart_ore_model = cubeAll(LSBlocks.CRYSTAL_ORE.get());
        simpleBlockWithItem(LSBlocks.CRYSTAL_ORE.get(), heart_ore_model);

        ModelFile netherrack_heart_ore_model = cubeAll(LSBlocks.NETHERRACK_CRYSTAL_ORE.get());
        simpleBlockWithItem(LSBlocks.NETHERRACK_CRYSTAL_ORE.get(), netherrack_heart_ore_model);

        ModelFile skull = models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull"));
        simpleBlock(LSBlocks.REVIVE_HEAD.get(), skull);
        simpleBlock(LSBlocks.REVIVE_WALL_HEAD.get(), skull);
    }
}
