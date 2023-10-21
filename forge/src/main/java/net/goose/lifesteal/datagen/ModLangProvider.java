package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

// TODO: Finish this up sometime lol
public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, LifeSteal.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

    }
}
