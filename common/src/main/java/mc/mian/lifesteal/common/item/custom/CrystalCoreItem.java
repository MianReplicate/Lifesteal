package mc.mian.lifesteal.common.item.custom;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.item.LSItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CrystalCoreItem extends Item {

    public CrystalCoreItem(Properties pProperties) {
        super(pProperties);
    }

    public boolean useCrystalCore(LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (LifeSteal.config.disableCores.get()) {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.crystal_core_disabled"), true);
                return false;
            }
            if (Math.round(entity.getHealth()) >= entity.getMaxHealth() && LifeSteal.config.preventFromUsingCoreIfMax.get()) {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.crystal_core_at_max_health"), true);
                return false;
            }

            float maxHealth = entity.getMaxHealth();
            float amountThatWillBeHealed = (float) (maxHealth * LifeSteal.config.coreHeal.get());
            float differenceInHealth = entity.getMaxHealth() - entity.getHealth();
            if (differenceInHealth <= amountThatWillBeHealed) {
                amountThatWillBeHealed = differenceInHealth;
            }

            int oldDuration = 0;
            if (entity.hasEffect(MobEffects.REGENERATION)) {
                MobEffectInstance mobEffect = entity.getEffect(MobEffects.REGENERATION);

                oldDuration = mobEffect.getDuration();
            }

            int tickTime = (int) ((amountThatWillBeHealed * 50) / 3) + oldDuration;
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, tickTime, 2));
            return true;
        }
        return false;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {
        boolean success = false;
        if(!level.isClientSide){
            if (!LifeSteal.config.coreInstantUse.get()) {
                if (!level.isClientSide) {
                    success = useCrystalCore(entity);
                }
            } else {
                item.set(DataComponents.FOOD, null);
            }
        }

        return success && !LifeSteal.config.coreInstantUse.get() ? super.finishUsingItem(item, level, entity) : item;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide){
            ItemStack item = player.getItemInHand(interactionHand);
            if (LifeSteal.config.coreInstantUse.get()) {
                item.set(DataComponents.FOOD, null);
                boolean success = useCrystalCore(player);

                if (success) {
                    item.shrink(1);
                    player.containerMenu.broadcastChanges();
                }
            } else {
                item.set(DataComponents.FOOD, LSItems.alwaysEdible);
            }
        }

        return super.use(level, player, interactionHand);
    }
}
