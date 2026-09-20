package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class LSRecipesProvider extends RecipeProvider {
    public LSRecipesProvider(final BootstrapContext<Recipe<?>> recipeOutput, final BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
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
                .pattern("rcr")
                .pattern("cac")
                .pattern("rcr")
                .define('c', LSItems.CRYSTAL_CORE.get())
                .define('a', Items.AMETHYST_BLOCK)
                .define('r', Items.REDSTONE_BLOCK)
                .unlockedBy("has_crystal_core", this.has(LSItems.CRYSTAL_CORE.get()))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, LSItems.HEART_CRYSTAL.get())
                .pattern("brb")
                .pattern("dcd")
                .pattern("rbg")
                .define('b', LSBlocks.CRYSTAL_BLOCK.get())
                .define('d', Items.DIAMOND)
                .define('c', LSItems.CRYSTAL_CORE.get())
                .define('r', Items.PRISMARINE_CRYSTALS)
                .define('g', Items.ENCHANTED_GOLDEN_APPLE)
                .unlockedBy("has_apple", this.has(Items.ENCHANTED_GOLDEN_APPLE))
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
}
