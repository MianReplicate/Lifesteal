package mc.mian.lifesteal.common.tab.neoforge;

import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.tab.LSTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class LSTabsImpl {
    public static CreativeModeTab createTab(){
        return CreativeModeTab.builder().title(Component.translatable("itemGroup.lifesteal"))
                        .icon(LSTabs::makeIcon).displayItems((itemDisplayParameters, output) ->
                                LSItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()))).build();
    }
}