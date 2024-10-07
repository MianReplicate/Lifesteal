package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.item.LSItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class LSItemModelProvider extends ItemModelProvider {
    public LSItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LifeSteal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(LSItems.CRYSTAL_FRAGMENT.get());
        basicItem(LSItems.CRYSTAL_CORE.get());
        basicItem(LSItems.HEART_CRYSTAL.get());
        basicItem(LSItems.REVIVE_CRYSTAL.get());
        getBuilder(LSItems.REVIVE_HEAD_ITEM.get().toString()).parent(new ModelFile.UncheckedModelFile("item/template_skull"));
    }
}
