package mc.mian.lifesteal.common.item.custom;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.item.LSItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CrystalCoreItem extends Item {

    public CrystalCoreItem(Properties pProperties) {
        super(pProperties);
    }

    public boolean useCrystalCore(LivingEntity entity) {
        boolean success = true;
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!LifeSteal.config.disableCores.get()) {
                if (Math.round(entity.getHealth()) < entity.getMaxHealth() || !LifeSteal.config.preventFromUsingCoreIfMax.get()) {
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
                } else {
                    serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.crystal_core_at_max_health"), true);
                    success = false;
                }
            } else {
                serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.crystal_core_disabled"), true);
                success = false;
            }
        }
        return success;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {
        boolean success = false;
        if (!level.isClientSide) {
            success = useCrystalCore(entity);
        }

        return success ? super.finishUsingItem(item, level, entity) : item;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!this.isEdible() && !level.isClientSide) {
            ItemStack item = player.getItemInHand(interactionHand);
            boolean success = useCrystalCore(player);

            if (success) {
                item.shrink(1);
                player.containerMenu.broadcastChanges();
            }
        }

        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isEdible() {
        return !LifeSteal.config.coreInstantUse.get();
    }

    @Override
    public FoodProperties getFoodProperties() {
        if (LifeSteal.config.coreInstantUse.get()) {
            return null;
        } else {
            return LSItems.alwaysEdible;
        }
    }
}
