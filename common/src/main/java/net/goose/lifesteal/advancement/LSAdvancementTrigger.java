package net.goose.lifesteal.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class LSAdvancementTrigger extends SimpleCriterionTrigger<LSAdvancementTrigger.Instance> {
    public final ResourceLocation resourceLocation;

    public LSAdvancementTrigger(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    protected Instance createInstance(JsonObject jsonObject, Optional<ContextAwarePredicate> optional, DeserializationContext deserializationContext) {
        return new Instance(optional);
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, (testTrigger) -> {
            return true;
        });
    }


    public static class Instance extends AbstractCriterionTriggerInstance {

        public Instance(Optional<ContextAwarePredicate> contextAwarePredicate) {
            super(contextAwarePredicate);
        }

        public static Criterion<ConstructBeaconTrigger.TriggerInstance> constructedBeacon() {
            return CriteriaTriggers.CONSTRUCT_BEACON.createCriterion(new ConstructBeaconTrigger.TriggerInstance(Optional.empty(), MinMaxBounds.Ints.ANY));
        }

        public JsonObject serializeToJson() {
            JsonObject jsonObject = super.serializeToJson();
            return jsonObject;
        }
    }
}
