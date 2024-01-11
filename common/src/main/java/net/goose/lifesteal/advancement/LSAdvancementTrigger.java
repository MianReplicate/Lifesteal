package net.goose.lifesteal.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public class LSAdvancementTrigger extends SimpleCriterionTrigger<LSAdvancementTrigger.TriggerInstance> {
    public LSAdvancementTrigger() {
    }
    @Override
    public Codec<LSAdvancementTrigger.TriggerInstance> codec() {
        return LSAdvancementTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, (testTrigger) -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player)).apply(instance, TriggerInstance::new));

        public static Criterion<LSAdvancementTrigger.TriggerInstance> GET_10_MAX_HEARTS() {
            return ModCriteria.GET_10_MAX_HEARTS.createCriterion(new LSAdvancementTrigger.TriggerInstance(Optional.empty()));
        }
        public static Criterion<LSAdvancementTrigger.TriggerInstance> USE_TOTEM_WHILE_20_MAX_HEARTS() {
            return ModCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS.createCriterion(new LSAdvancementTrigger.TriggerInstance(Optional.empty()));
        }
        public static Criterion<LSAdvancementTrigger.TriggerInstance> REVIVED() {
            return ModCriteria.REVIVED.createCriterion(new LSAdvancementTrigger.TriggerInstance(Optional.empty()));
        }
    }
}
