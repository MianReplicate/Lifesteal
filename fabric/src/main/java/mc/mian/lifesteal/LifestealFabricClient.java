package mc.mian.lifesteal;

import mc.mian.lifesteal.platform.FabricNetworkRegistry;
import mc.mian.lifesteal.platform.Services;
import net.fabricmc.api.ClientModInitializer;

public class LifestealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(){
        ((FabricNetworkRegistry) Services.NETWORK_REGISTRY).registerClient();
    }
}
