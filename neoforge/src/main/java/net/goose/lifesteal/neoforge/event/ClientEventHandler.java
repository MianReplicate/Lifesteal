package net.goose.lifesteal.neoforge.event;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.client.render.ReviveHeadBER;
import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = LifeSteal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void OnRenderersRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityTypes.REVIVE_HEAD.get(), ReviveHeadBER::new);
    }
}
