package net.goose.lifesteal.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.goose.lifesteal.client.render.ReviveHeadBER;
import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class LifestealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(ModBlockEntityTypes.REVIVE_HEAD.get(), ReviveHeadBER::new);
    }
}