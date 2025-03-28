package mc.mian.lifesteal.data;

import mc.mian.lifesteal.api.ILSData;
import mc.mian.lifesteal.util.LSConstants;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class NeoForgeLSCapabilities {
    public static final EntityCapability<ILSData, Void> LIFESTEAL_DATA =
            EntityCapability.createVoid(
                    LSConstants.LIFESTEAL_DATA,
                    ILSData.class
            );
}