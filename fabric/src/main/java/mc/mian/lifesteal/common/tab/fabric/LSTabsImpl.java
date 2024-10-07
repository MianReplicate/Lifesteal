package mc.mian.lifesteal.common.tab.fabric;

import mc.mian.lifesteal.util.LSConstants;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.tab.LSTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class LSTabsImpl {
    public static CreativeModeTab createTab(){
        return FabricItemGroup.builder()
                .icon(LSTabs::makeIcon)
                .title(Component.translatable("itemGroup."+ LSConstants.MOD_ID))
                .displayItems((itemDisplayParameters, output) -> LSItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                .build();
    }
}
