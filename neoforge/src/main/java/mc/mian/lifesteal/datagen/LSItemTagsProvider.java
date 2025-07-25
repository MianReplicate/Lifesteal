package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class LSItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {

    public LSItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ITEM, lookupProvider, (item) -> item.builtInRegistryHolder().key(), LSConstants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(LSTags.ORIGINS_IGNORE_DIET).add(LSItems.CRYSTAL_CORE.get(), LSItems.HEART_CRYSTAL.get());
    }
}
