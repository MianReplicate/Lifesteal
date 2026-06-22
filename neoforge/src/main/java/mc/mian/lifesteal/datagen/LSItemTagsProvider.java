package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class LSItemTagsProvider extends TagsProvider<Item> {

    public LSItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ITEM, lookupProvider, LSConstants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(LSTags.ORIGINS_IGNORE_DIET).add(LSItems.CRYSTAL_CORE.getKey(), LSItems.HEART_CRYSTAL.getKey());
    }
}
