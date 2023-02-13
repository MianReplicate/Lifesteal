package net.goose.lifesteal.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.goose.lifesteal.client.render.ReviveHeadBER;
import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;

public class LifestealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(ModBlockEntityTypes.REVIVE_HEAD.get(), ReviveHeadBER::new);
    }
}