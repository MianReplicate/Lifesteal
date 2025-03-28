package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.tab.LSTabs;
import mc.mian.lifesteal.platform.services.ITabCreator;
import mc.mian.lifesteal.util.LSConstants;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class FabricTabCreator implements ITabCreator {
    public CreativeModeTab createTab(){
        return FabricItemGroup.builder()
                .icon(LSTabs::makeIcon)
                .title(Component.translatable("itemGroup."+ LSConstants.MOD_ID))
                .displayItems((itemDisplayParameters, output) -> LSItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                .build();
    }
}
