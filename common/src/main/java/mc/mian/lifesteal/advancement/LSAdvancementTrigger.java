package mc.mian.lifesteal.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

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
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(TriggerInstance::player)).apply(instance, TriggerInstance::new));

        public static Criterion<LSAdvancementTrigger.TriggerInstance> GET_10_MAX_HEARTS() {
            return LSCriteria.GET_10_MAX_HEARTS.createCriterion(new LSAdvancementTrigger.TriggerInstance(Optional.empty()));
        }
        public static Criterion<LSAdvancementTrigger.TriggerInstance> USE_TOTEM_WHILE_20_MAX_HEARTS() {
            return LSCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS.createCriterion(new LSAdvancementTrigger.TriggerInstance(Optional.empty()));
        }
        public static Criterion<LSAdvancementTrigger.TriggerInstance> BACK_FROM_THE_DEAD() {
            return LSCriteria.BACK_FROM_THE_DEAD.createCriterion(new LSAdvancementTrigger.TriggerInstance(Optional.empty()));
        }
        public static Criterion<LSAdvancementTrigger.TriggerInstance> REVIVED() {
            return LSCriteria.REVIVED.createCriterion(new LSAdvancementTrigger.TriggerInstance(Optional.empty()));
        }
    }
}
