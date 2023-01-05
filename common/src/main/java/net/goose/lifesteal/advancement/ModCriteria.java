package net.goose.lifesteal.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;

public class ModCriteria {

    public static LSAdvancementTrigger GET_10_MAX_HEARTS = new LSAdvancementTrigger(new ResourceLocation("lifesteal:get_10_max_hearts"));
    public static LSAdvancementTrigger USE_TOTEM_WHILE_20_MAX_HEARTS = new LSAdvancementTrigger(new ResourceLocation("lifesteal:use_totem_while_20_max_hearts"));
    public static LSAdvancementTrigger REVIVED = new LSAdvancementTrigger(new ResourceLocation("lifesteal:revived"));

    public static void init() {
        CriteriaTriggers.register(GET_10_MAX_HEARTS);
        CriteriaTriggers.register(USE_TOTEM_WHILE_20_MAX_HEARTS);
        CriteriaTriggers.register(REVIVED);
    }

}
