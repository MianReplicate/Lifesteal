package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class LSRecipesProvider extends RecipeProvider {
    public LSRecipesProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LSItems.CRYSTAL_CORE.get())
                .pattern("fff")
                .pattern("fgf")
                .pattern("fff")
                .define('f', LSItems.CRYSTAL_FRAGMENT.get())
                .define('g', Items.GOLDEN_APPLE)
                .unlockedBy("has_fragment", RecipeProvider.has(LSItems.CRYSTAL_FRAGMENT.get()))
                .save(consumer, LSItems.CRYSTAL_CORE.getId());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LSBlocks.CRYSTAL_BLOCK.get().asItem())
                .pattern("cfc")
                .pattern("fdf")
                .pattern("cfc")
                .define('c', LSItems.CRYSTAL_CORE.get())
                .define('f', LSItems.CRYSTAL_FRAGMENT.get())
                .define('d', Items.DIAMOND)
                .unlockedBy("has_crystal_core", RecipeProvider.has(LSItems.CRYSTAL_CORE.get()))
                .save(consumer, LSBlocks.CRYSTAL_BLOCK.getId());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LSItems.HEART_CRYSTAL.get())
                .pattern("bhb")
                .pattern("dcd")
                .pattern("rbg")
                .define('b', LSBlocks.CRYSTAL_BLOCK.get())
                .define('d', Items.DIAMOND)
                .define('c', LSItems.CRYSTAL_CORE.get())
                .define('r', Items.BLAZE_ROD)
                .define('g', Items.GOLDEN_APPLE)
                .define('h', Items.HEART_OF_THE_SEA)
                .unlockedBy("has_heart_of_the_sea", RecipeProvider.has(Items.HEART_OF_THE_SEA))
                .save(consumer, LSItems.HEART_CRYSTAL.getId());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LSItems.REVIVE_CRYSTAL.get())
                .pattern("gcg")
                .pattern("nhn")
                .pattern("ctc")
                .define('g', Items.GHAST_TEAR)
                .define('n', Items.NETHERITE_INGOT)
                .define('c', LSItems.CRYSTAL_CORE.get())
                .define('t', Items.TOTEM_OF_UNDYING)
                .define('h', LSItems.HEART_CRYSTAL.get())
                .unlockedBy("has_totem", RecipeProvider.has(Items.TOTEM_OF_UNDYING))
                .save(consumer, LSItems.REVIVE_CRYSTAL.getId());
    }
}
