package net.goose.lifesteal.datagen;

import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CRYSTAL_CORE.get())
                .pattern("fff")
                .pattern("fgf")
                .pattern("fff")
                .define('f', ModItems.CRYSTAL_FRAGMENT.get())
                .define('g', Items.GOLDEN_APPLE)
                .unlockedBy("has_fragment", RecipeProvider.has(ModItems.CRYSTAL_FRAGMENT.get()))
                .save(consumer, ModItems.CRYSTAL_CORE.getId());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CRYSTAL_BLOCK.get().asItem())
                .pattern("cfc")
                .pattern("fdf")
                .pattern("cfc")
                .define('c', ModItems.CRYSTAL_CORE.get())
                .define('f', ModItems.CRYSTAL_FRAGMENT.get())
                .define('d', Items.DIAMOND)
                .unlockedBy("has_crystal_core", RecipeProvider.has(ModItems.CRYSTAL_CORE.get()))
                .save(consumer, ModBlocks.CRYSTAL_BLOCK.getId());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEART_CRYSTAL.get())
                .pattern("bhb")
                .pattern("dcd")
                .pattern("rbg")
                .define('b', ModBlocks.CRYSTAL_BLOCK.get())
                .define('d', Items.DIAMOND)
                .define('c', ModItems.CRYSTAL_CORE.get())
                .define('r', Items.BLAZE_ROD)
                .define('g', Items.GOLDEN_APPLE)
                .define('h', Items.HEART_OF_THE_SEA)
                .unlockedBy("has_heart_of_the_sea", RecipeProvider.has(Items.HEART_OF_THE_SEA))
                .save(consumer, ModItems.HEART_CRYSTAL.getId());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REVIVE_CRYSTAL.get())
                .pattern("gcg")
                .pattern("nhn")
                .pattern("ctc")
                .define('g', Items.GHAST_TEAR)
                .define('n', Items.NETHERITE_INGOT)
                .define('c', ModItems.CRYSTAL_CORE.get())
                .define('t', Items.TOTEM_OF_UNDYING)
                .define('h', ModItems.HEART_CRYSTAL.get())
                .unlockedBy("has_totem", RecipeProvider.has(Items.TOTEM_OF_UNDYING))
                .save(consumer, ModItems.REVIVE_CRYSTAL.getId());
    }
}
