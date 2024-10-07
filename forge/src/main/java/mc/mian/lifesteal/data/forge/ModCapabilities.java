package mc.mian.lifesteal.data.forge;

import mc.mian.lifesteal.api.ILSData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<ILSData> LIFESTEAL_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });
}