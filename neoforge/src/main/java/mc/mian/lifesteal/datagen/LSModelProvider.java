package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;

public class LSModelProvider extends ModelProvider {
    public LSModelProvider(PackOutput output) {
        super(output, LSConstants.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(LSBlocks.CRYSTAL_BLOCK.get());
        blockModels.createTrivialCube(LSBlocks.DEEPSLATE_CRYSTAL_ORE.get());
        blockModels.createTrivialCube(LSBlocks.CRYSTAL_ORE.get());
        blockModels.createTrivialCube(LSBlocks.NETHERRACK_CRYSTAL_ORE.get());

        itemModels.generateFlatItem(LSItems.CRYSTAL_FRAGMENT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LSItems.CRYSTAL_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LSItems.HEART_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LSItems.REVIVE_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);

        ResourceLocation resourceLocation = ModelLocationUtils.decorateItemModelLocation("template_skull");
        blockModels.createHead(LSBlocks.REVIVE_HEAD.get(), LSBlocks.REVIVE_WALL_HEAD.get(), SkullBlock.Types.PLAYER, resourceLocation);
    }
}
