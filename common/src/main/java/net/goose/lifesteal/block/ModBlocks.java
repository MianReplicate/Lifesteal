package net.goose.lifesteal.block;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.item.ModItems;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(LifeSteal.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> HEART_CORE_BLOCK = registerBlock("heart_core_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> HEART_ORE = registerBlock("heart_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistrySupplier<Block> DEEPSLATE_HEART_ORE = registerBlock("deepslate_heart_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));

    public static final RegistrySupplier<Block> NETHERRACK_HEART_ORE = registerBlock("netherrack_heart_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops().explosionResistance(999f), UniformInt.of(5, 9)));

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register() {
        BLOCKS.register();
    }
}
