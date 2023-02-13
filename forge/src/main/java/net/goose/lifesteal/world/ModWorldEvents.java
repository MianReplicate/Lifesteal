package net.goose.lifesteal.world;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.world.gen.ModGeodeGeneration;
import net.goose.lifesteal.world.gen.ModOreGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LifeSteal.MOD_ID)
public class ModWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        ModOreGeneration.generateOres(event);
        ModGeodeGeneration.generateGeodes(event);
    }
}