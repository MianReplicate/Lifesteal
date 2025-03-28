package mc.mian.lifesteal.data;

import mc.mian.lifesteal.api.AutoCapLSData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ForgeLSCapabilities {
    public static final Capability<AutoCapLSData> LIFESTEAL_DATA = CapabilityManager.get(
            new CapabilityToken<>(){}
    );
}