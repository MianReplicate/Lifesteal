package mc.mian.lifesteal.common.tab.forge;

import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.tab.LSTabs;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class LSTabsImpl {
    public static CreativeModeTab createTab(){
        return CreativeModeTab.builder().title(Component.translatable("itemGroup."+ LSConstants.MOD_ID))
                        .icon(LSTabs::makeIcon).displayItems((itemDisplayParameters, output) ->
                                LSItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()))).build();
    }
}