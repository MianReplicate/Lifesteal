package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LifeSteal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.HEART_FRAGMENT.get());
        basicItem(ModItems.CRYSTAL_CORE.get());
        basicItem(ModItems.HEART_CRYSTAL.get());
        basicItem(ModItems.REVIVE_CRYSTAL.get());
        getBuilder(ModItems.REVIVE_HEAD_ITEM.get().toString()).parent(new ModelFile.UncheckedModelFile("item/template_skull"));
    }
}
