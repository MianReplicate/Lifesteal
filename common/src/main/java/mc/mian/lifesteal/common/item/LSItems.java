package mc.mian.lifesteal.common.item;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.custom.CrystalCoreItem;
import mc.mian.lifesteal.common.item.custom.HeartCrystalItem;
import mc.mian.lifesteal.common.item.custom.ReviveCrystalItem;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.Rarity;

import java.util.function.Function;

public class LSItems {
    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(LSConstants.MOD_ID, Registries.ITEM);

    public static FoodProperties alwaysEdible = (new FoodProperties.Builder()).alwaysEdible().build();

    public static final RegistrySupplier<Item> CRYSTAL_FRAGMENT = registerItem("crystal_fragment",
            Item::new);
    public static final RegistrySupplier<Item> CRYSTAL_CORE = registerItem("crystal_core",
            (properties) -> new CrystalCoreItem(properties.food(alwaysEdible)));
    public static final RegistrySupplier<Item> HEART_CRYSTAL = registerItem("heart_crystal",
            (properties) -> new HeartCrystalItem(properties.stacksTo(1).fireResistant().food(alwaysEdible)));
    public static final RegistrySupplier<Item> REVIVE_CRYSTAL = registerItem("revive_crystal",
            (properties) -> new ReviveCrystalItem(properties.stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> REVIVE_HEAD_ITEM = registerItem("revive_head",
            (properties) -> new PlayerHeadItem(LSBlocks.REVIVE_HEAD.get(), LSBlocks.REVIVE_WALL_HEAD.get(), properties.rarity(Rarity.UNCOMMON).stacksTo(1).fireResistant().useBlockDescriptionPrefix()));

    public static RegistrySupplier<Item> registerItem(String name, Function<Item.Properties, Item> itemFunc){
        return ITEMS.register(name, () -> itemFunc.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(LSConstants.MOD_ID, name)))));
    }

    public static void register() {
        LSConstants.LOGGER.debug("Registering ModItems for " + LSConstants.MOD_ID);
        ITEMS.register();
    }
}
