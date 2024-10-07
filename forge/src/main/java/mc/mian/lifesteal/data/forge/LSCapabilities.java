package mc.mian.lifesteal.data.forge;

import mc.mian.lifesteal.api.ILSDataForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class LSCapabilities {
    public static final Capability<ILSDataForge> LIFESTEAL_DATA = CapabilityManager.get(
            new CapabilityToken<>(){}
    );
}