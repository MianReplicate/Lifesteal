package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class LSRecipesProvider extends RecipeProvider {
    public LSRecipesProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
        super(lookupProvider, output);
    }

    @Override
    protected void buildRecipes() {
        this.shaped(RecipeCategory.MISC, LSItems.CRYSTAL_CORE.get())
                .pattern("fff")
                .pattern("fgf")
                .pattern("fff")
                .define('f', LSItems.CRYSTAL_FRAGMENT.get())
                .define('g', Items.GOLDEN_APPLE)
                .unlockedBy("has_fragment", this.has(LSItems.CRYSTAL_FRAGMENT.get()))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, LSBlocks.CRYSTAL_BLOCK.get().asItem())
                .pattern("cfc")
                .pattern("fdf")
                .pattern("cfc")
                .define('c', LSItems.CRYSTAL_CORE.get())
                .define('f', LSItems.CRYSTAL_FRAGMENT.get())
                .define('d', Items.DIAMOND)
                .unlockedBy("has_crystal_core", this.has(LSItems.CRYSTAL_CORE.get()))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, LSItems.HEART_CRYSTAL.get())
                .pattern("bhb")
                .pattern("dcd")
                .pattern("rbg")
                .define('b', LSBlocks.CRYSTAL_BLOCK.get())
                .define('d', Items.DIAMOND)
                .define('c', LSItems.CRYSTAL_CORE.get())
                .define('r', Items.BLAZE_ROD)
                .define('g', Items.GOLDEN_APPLE)
                .define('h', Items.HEART_OF_THE_SEA)
                .unlockedBy("has_heart_of_the_sea", this.has(Items.HEART_OF_THE_SEA))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, LSItems.REVIVE_CRYSTAL.get())
                .pattern("gcg")
                .pattern("nhn")
                .pattern("ctc")
                .define('g', Items.GHAST_TEAR)
                .define('n', Items.NETHERITE_INGOT)
                .define('c', LSItems.CRYSTAL_CORE.get())
                .define('t', Items.TOTEM_OF_UNDYING)
                .define('h', LSItems.HEART_CRYSTAL.get())
                .unlockedBy("has_totem", this.has(Items.TOTEM_OF_UNDYING))
                .save(this.output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(packOutput, completableFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new LSRecipesProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return LSConstants.MOD_DISPLAY_NAME + " Recipes";
        }
    }
}
