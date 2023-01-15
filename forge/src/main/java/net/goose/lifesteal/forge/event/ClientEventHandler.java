package net.goose.lifesteal.forge.event;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.client.render.ReviveHeadBER;
import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LifeSteal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void OnRenderersRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.REVIVE_HEAD.get(), ReviveHeadBER::new);
    }
}
