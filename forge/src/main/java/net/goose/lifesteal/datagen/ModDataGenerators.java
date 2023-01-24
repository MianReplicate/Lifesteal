package net.goose.lifesteal.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
public class ModDataGenerators {
    @SubscribeEvent
    public static void generateData(GatherDataEvent ev) {
        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();
        final ExistingFileHelper efh = ev.getExistingFileHelper();

        if (ev.includeServer()) {
            gen.addProvider(ev.includeServer(), new Recipes(packOutput));
        }
    }
}
