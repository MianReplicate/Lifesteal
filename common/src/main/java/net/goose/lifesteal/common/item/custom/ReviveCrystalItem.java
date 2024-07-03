package net.goose.lifesteal.common.item.custom;

import com.mojang.authlib.GameProfile;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ReviveCrystalItem extends Item {
    public ReviveCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        if (!useOnContext.getLevel().isClientSide) {
            Level level = useOnContext.getLevel();
            Player player = useOnContext.getPlayer();

            if (level.getServer().isSingleplayer()) {
                player.displayClientMessage(Component.translatable("gui.lifesteal.multiplayer_only"), true);
                return super.useOn(useOnContext);
            }

            if (LifeSteal.config.disableReviveCrystals.get()) {
                player.displayClientMessage(Component.translatable("gui.lifesteal.revive_crystal_disabled"), true);
                return super.useOn(useOnContext);
            }

            ItemStack itemStack = useOnContext.getItemInHand();
            BlockPos blockPos = useOnContext.getClickedPos();
            if(level.getBlockEntity(blockPos) instanceof ReviveSkullBlockEntity blockEntity){
                GameProfile gameprofile = blockEntity.getOwnerProfile();
                if (gameprofile != null) {
                    boolean successful = ModUtil.revivePlayer(
                            (ServerLevel) level,
                            blockPos,
                            gameprofile,
                            !LifeSteal.config.disableLightningEffect.get(),
                            LifeSteal.config.silentlyRevivePlayer.get(),
                            player);

                    if (successful) {
                        itemStack.shrink(1);
                        ModCriteria.REVIVED.trigger((ServerPlayer) player);
                    } else {
                        player.displayClientMessage(Component.translatable("gui.lifesteal.error_revive_block"), true);
                    }
                } else {
                    player.displayClientMessage(Component.translatable("gui.lifesteal.null_revive_block"), true);
                }
            } else {
                player.displayClientMessage(Component.translatable("gui.lifesteal.invaild_revive_block"), true);
            }
        }
        return super.useOn(useOnContext);
    }
}
