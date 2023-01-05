package net.goose.lifesteal.forge.item;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.block.ModBlocks;
import net.goose.lifesteal.item.ModItems;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
public class ModCreativeModeTab {
    public static final ResourceLocation TAB = new ResourceLocation(LifeSteal.MOD_ID, "creativemodetab");

    private static ItemStack makeIcon() {
        ItemStack stack = new ItemStack(ModItems.HEART_CRYSTAL.get());
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsCreativeTab", true);
        stack.setTag(tag);
        return stack;
    }

    public static void registerTabItems(final CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.HEART_CRYSTAL.get());
            event.accept(ModItems.HEART_CORE.get());
        }
        if (event.getTab() == CreativeModeTabs.COLORED_BLOCKS) {
            event.accept(ModBlocks.HEART_CORE_BLOCK.get());
        }
        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.HEART_CORE.get());
            event.accept(ModBlocks.HEART_CORE_BLOCK.get());
            event.accept(ModItems.HEART_FRAGMENT.get());
        }
        if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.HEART_ORE.get());
            event.accept(ModBlocks.DEEPSLATE_HEART_ORE.get());
            event.accept(ModBlocks.NETHERRACK_HEART_ORE.get());
        }
    }

    public static void registerTab(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(TAB, builder -> builder.title(Component.translatable("itemGroup.lifesteal")).icon(ModCreativeModeTab::makeIcon).displayItems((flags, output, isOp) -> {
            for (RegistrySupplier<Item> item : ModItems.ITEMS.getEntries()) {
                output.accept(item.get());
            }
        }));
    }
}