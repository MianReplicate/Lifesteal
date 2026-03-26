package mc.mian.lifesteal.common.item.custom;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.advancement.LSCriteria;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.jetbrains.annotations.NotNull;

public class ReviveCrystalItem extends Item {
    public ReviveCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        if (!useOnContext.getLevel().isClientSide()) {
            Level level = useOnContext.getLevel();
            Player player = useOnContext.getPlayer();

            if (!LSUtil.isMultiplayer(level.getServer(), false)) {
                player.sendOverlayMessage(Component.translatable("gui.lifesteal.multiplayer_only"));
                return super.useOn(useOnContext);
            }

            if (LifeSteal.config.disableReviveCrystals.get()) {
                player.sendOverlayMessage(Component.translatable("gui.lifesteal.revive_crystal_disabled"));
                return super.useOn(useOnContext);
            }

            ItemStack itemStack = useOnContext.getItemInHand();
            BlockPos blockPos = useOnContext.getClickedPos();
            if(level.getBlockEntity(blockPos) instanceof SkullBlockEntity blockEntity){
                ResolvableProfile gameprofile = blockEntity.getOwnerProfile();
                if (gameprofile == null) {
                    player.sendOverlayMessage(Component.translatable("gui.lifesteal.null_revive_block"));
                } else {
                    if (LSUtil.revivePlayer(
                            (ServerLevel) level,
                            blockPos,
                            new NameAndId(gameprofile.partialProfile()),
                            !LifeSteal.config.disableLightningEffect.get(),
                            LifeSteal.config.silentlyRevivePlayer.get(),
                            player)) {
                        itemStack.shrink(1);
                        LSCriteria.REVIVED.trigger((ServerPlayer) player);
                    } else {
                        player.sendOverlayMessage(Component.translatable("gui.lifesteal.error_revive_block"));
                    }
                }

            } else {
                player.sendOverlayMessage(Component.translatable("gui.lifesteal.invaild_revive_block"));
            }
        }
        return super.useOn(useOnContext);
    }
}
