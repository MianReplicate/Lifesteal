package mc.mian.lifesteal.common.block;

import mc.mian.lifesteal.common.block.custom.ReviveHeadBlock;
import mc.mian.lifesteal.common.block.custom.ReviveWallHeadBlock;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.Optional;
import java.util.function.Function;

public class LSBlocks {
    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(LSConstants.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> CRYSTAL_BLOCK = registerBlockWithItem("crystal_block", (properties) ->
            new Block(properties.mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> CRYSTAL_ORE = registerBlockWithItem("crystal_ore", (properties) ->
            new DropExperienceBlock(UniformInt.of(3, 7), properties.mapColor(MapColor.STONE).strength(4f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> DEEPSLATE_CRYSTAL_ORE = registerBlockWithItem("deepslate_crystal_ore", (properties) ->
            new DropExperienceBlock(UniformInt.of(3, 7), properties.mapColor(MapColor.STONE).strength(5f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> NETHERRACK_CRYSTAL_ORE = registerBlockWithItem("netherrack_crystal_ore", (properties) ->
            new DropExperienceBlock(UniformInt.of(5, 9), properties.mapColor(MapColor.STONE).strength(2f).requiresCorrectToolForDrops().explosionResistance(999f)));
    public static final RegistrySupplier<Block> REVIVE_HEAD = registerBlock("revive_head", (properties) ->
            new ReviveHeadBlock(properties.instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0F).explosionResistance(999f)));
    public static final RegistrySupplier<Block> REVIVE_WALL_HEAD = registerBlock("revive_wall_head", (properties) ->
            new ReviveWallHeadBlock(properties.instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0F).explosionResistance(999f).overrideLootTable(Optional.of(ResourceKey.create(Registries.LOOT_TABLE,  LSUtil.modLoc("blocks/"+REVIVE_HEAD.getId().getPath()))))));

    public static RegistrySupplier<Block> registerBlock(String name, Function<BlockBehaviour.Properties, Block> blockFunction) {
        return BLOCKS.register(name, () -> blockFunction.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, LSUtil.modLoc(name)))));
    }

    public static RegistrySupplier<Block> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, Block> blockFunction) {
        RegistrySupplier<Block> block = registerBlock(name, blockFunction);
        LSItems.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
        return block;
    }

    public static void register() {
        LSConstants.LOGGER.debug("Registering ModBlocks for " + LSConstants.MOD_ID);
        BLOCKS.register();
    }
}
