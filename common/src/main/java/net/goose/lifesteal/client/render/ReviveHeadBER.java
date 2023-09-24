package net.goose.lifesteal.client.render;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ReviveHeadBER implements BlockEntityRenderer<ReviveSkullBlockEntity> {
    private final Map<SkullBlock.Type, SkullModelBase> modelByType;
    private static final Map<SkullBlock.Type, ResourceLocation> SKIN_BY_TYPE = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(SkullBlock.Types.PLAYER, DefaultPlayerSkin.getDefaultTexture());
    });

    public static Map<SkullBlock.Type, SkullModelBase> createSkullRenderers(EntityModelSet entityModelSet) {
        ImmutableMap.Builder<SkullBlock.Type, SkullModelBase> builder = ImmutableMap.builder();
        builder.put(SkullBlock.Types.PLAYER, new SkullModel(entityModelSet.bakeLayer(ModelLayers.PLAYER_HEAD)));
        return builder.build();
    }

    public ReviveHeadBER(BlockEntityRendererProvider.Context context) {
        this.modelByType = createSkullRenderers(context.getModelSet());
    }

    @Override
    public void render(ReviveSkullBlockEntity skullBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        float animation = skullBlockEntity.getAnimation(pPartialTick);
        BlockState blockState = skullBlockEntity.getBlockState();
        boolean bl = blockState.getBlock() instanceof WallSkullBlock;
        Direction direction = bl ? blockState.getValue(WallSkullBlock.FACING) : null;
        int k = bl ? RotationSegment.convertToSegment(direction) : blockState.getValue(SkullBlock.ROTATION);
        float degrees = RotationSegment.convertToDegrees(k);
        SkullBlock.Type type = ((AbstractSkullBlock) blockState.getBlock()).getType();
        SkullModelBase skullModelBase = this.modelByType.get(type);
        RenderType renderType = getRenderType(type, skullBlockEntity.getOwnerProfile());

        renderSkull(direction, degrees, animation, poseStack, multiBufferSource, pPackedLight, skullModelBase, renderType);
    }

    public static void renderSkull(@Nullable Direction direction, float degrees, float animation, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, SkullModelBase skullModelBase, RenderType renderType) {
        poseStack.pushPose();
        if (direction == null) {
            poseStack.translate(0.5F, 0.0F, 0.5F);
        } else {
            float h = 0.25F;
            poseStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25F, 0.5F - (float) direction.getStepZ() * 0.25F);
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
        skullModelBase.setupAnim(animation, degrees, 0.0F);
        skullModelBase.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }


    public static RenderType getRenderType(SkullBlock.Type type, @Nullable GameProfile gameProfile) {
        ResourceLocation resourceLocation = SKIN_BY_TYPE.get(type);
        if (type == SkullBlock.Types.PLAYER && gameProfile != null) {
            SkinManager skinManager = Minecraft.getInstance().getSkinManager();
            return RenderType.entityTranslucent(skinManager.getInsecureSkin(gameProfile).texture());
        } else {
            return RenderType.entityCutoutNoCullZOffset(resourceLocation);
        }
    }
}
