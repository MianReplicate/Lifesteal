package net.goose.lifesteal.common.block;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.custom.ReviveHeadBlock;
import net.goose.lifesteal.common.block.custom.ReviveWallHeadBlock;
import net.goose.lifesteal.common.item.ModItems;
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
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(LifeSteal.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> HEART_CORE_BLOCK = registerBlock("heart_core_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()), true, null);

    public static final RegistrySupplier<Block> HEART_ORE = registerBlock("heart_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)), true, null);

    public static final RegistrySupplier<Block> DEEPSLATE_HEART_ORE = registerBlock("deepslate_heart_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)), true, null);

    public static final RegistrySupplier<Block> NETHERRACK_HEART_ORE = registerBlock("netherrack_heart_ore", () ->
            new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops().explosionResistance(999f), UniformInt.of(5, 9)), true, null);
    public static final RegistrySupplier<Block> REVIVE_HEAD = registerBlock("revive_head", () ->
            new ReviveHeadBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0F).explosionResistance(999f)), false, null);
    public static final RegistrySupplier<Block> REVIVE_WALL_HEAD = registerBlock("revive_wall_head", () ->
            new ReviveWallHeadBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0F).explosionResistance(999f).dropsLike(REVIVE_HEAD.get())), false, null);

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, boolean registerItem, @Nullable Item.Properties properties) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        if (registerItem) {
            if (properties == null) {
                registerBlockItem(name, toReturn);
            } else {
                registerBlockItem(name, toReturn, properties);
            }
        }
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block, Item.Properties properties) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModBlocks for " + LifeSteal.MOD_ID);
        BLOCKS.register();
    }
}
