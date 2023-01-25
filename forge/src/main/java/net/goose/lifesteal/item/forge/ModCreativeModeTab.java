package net.goose.lifesteal.item.forge;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.item.ModCreativeModeTabHelper;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;

import java.util.Collection;

import static net.goose.lifesteal.common.item.ModCreativeModeTabHelper.TAB;

public class ModCreativeModeTab {
    public static void register(CreativeModeTabEvent.Register event) {
        LifeSteal.LOGGER.debug("Registering ModCreativeModeTab for " + LifeSteal.MOD_ID);
        event.registerCreativeModeTab(TAB, builder ->
                builder.title(Component.translatable("itemGroup.lifesteal"))
                        .icon(ModCreativeModeTabHelper::makeIcon)
                        .displayItems((flags, output, isOp) ->
                                ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()))));
    }
}