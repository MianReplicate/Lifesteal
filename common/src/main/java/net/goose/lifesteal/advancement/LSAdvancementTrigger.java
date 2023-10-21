package net.goose.lifesteal.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class LSAdvancementTrigger extends SimpleCriterionTrigger<LSAdvancementTrigger.Instance> {
    public LSAdvancementTrigger() {
    }

    @Override
    protected Instance createInstance(JsonObject jsonObject, Optional<ContextAwarePredicate> optional, DeserializationContext arg) {
        return new Instance(optional);
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, (testTrigger) -> true);
    }


    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(Optional<ContextAwarePredicate> optional){
            super(optional);
        }

        public static Criterion<LSAdvancementTrigger.Instance> GET_10_MAX_HEARTS() {
            return ModCriteria.GET_10_MAX_HEARTS.createCriterion(new LSAdvancementTrigger.Instance(Optional.empty()));
        }
        public static Criterion<LSAdvancementTrigger.Instance> USE_TOTEM_WHILE_20_MAX_HEARTS() {
            return ModCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS.createCriterion(new LSAdvancementTrigger.Instance(Optional.empty()));
        }
        public static Criterion<LSAdvancementTrigger.Instance> REVIVED() {
            return ModCriteria.REVIVED.createCriterion(new LSAdvancementTrigger.Instance(Optional.empty()));
        }
    }
}
