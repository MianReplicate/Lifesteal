package mc.mian.lifesteal.data.neoforge;

import mc.mian.lifesteal.api.ILSData;
import mc.mian.lifesteal.util.LSConstants;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class LSCapabilities {
    public static final EntityCapability<ILSData, Void> LIFESTEAL_DATA =
            EntityCapability.createVoid(
                    LSConstants.LIFESTEAL_DATA,
                    ILSData.class
            );
}