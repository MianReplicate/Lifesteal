package mc.mian.lifesteal.data.neoforge;

import mc.mian.lifesteal.api.ILifestealData;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class LSCapabilities {
    public static final EntityCapability<ILifestealData, Void> LIFESTEAL_DATA =
            EntityCapability.createVoid(
                    LSConstants.LIFESTEAL_DATA,
                    ILifestealData.class
            );
}