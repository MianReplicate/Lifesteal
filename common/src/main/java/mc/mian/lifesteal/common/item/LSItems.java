package mc.mian.lifesteal.common.item;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.custom.CrystalCoreItem;
import mc.mian.lifesteal.common.item.custom.HeartCrystalItem;
import mc.mian.lifesteal.common.item.custom.ReviveCrystalItem;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.Rarity;

public class LSItems {
    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(LSConstants.MOD_ID, Registries.ITEM);

    public static FoodProperties alwaysEdible = (new FoodProperties.Builder()).alwaysEdible().build();

    public static final RegistrySupplier<Item> CRYSTAL_FRAGMENT = ITEMS.register("crystal_fragment",
            () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> CRYSTAL_CORE = ITEMS.register("crystal_core",
            () -> new CrystalCoreItem(new Item.Properties().food(alwaysEdible)));
    public static final RegistrySupplier<Item> HEART_CRYSTAL = ITEMS.register("heart_crystal",
            () -> new HeartCrystalItem(new Item.Properties().stacksTo(1).fireResistant().food(alwaysEdible)));
    public static final RegistrySupplier<Item> REVIVE_CRYSTAL = ITEMS.register("revive_crystal",
            () -> new ReviveCrystalItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> REVIVE_HEAD_ITEM = ITEMS.register("revive_head",
            () -> new PlayerHeadItem(LSBlocks.REVIVE_HEAD.get(), LSBlocks.REVIVE_WALL_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1).fireResistant()));

    public static void register() {
        LSConstants.LOGGER.debug("Registering ModItems for " + LSConstants.MOD_ID);
        ITEMS.register();
    }
}
