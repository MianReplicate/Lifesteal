package mc.mian.lifesteal.datagen.loottable;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class LSChestLoot implements LootTableSubProvider {
    private final LootTableSubProvider.Context output;
    private final HolderGetter<Enchantment> enchantments;

    public LSChestLoot(LootTableSubProvider.Context output) {
        this.output = output;
        this.enchantments = output.lookup(Registries.ENCHANTMENT);
    }

    @Override
    public void run() {
        this.output.accept(LSConstants.BARREL_1_TABLE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(4))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 24))))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))));
        this.output.accept(LSConstants.MINERS_HOME_TABLE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(1, 4))
                        .add(LootItem.lootTableItem(LSItems.CRYSTAL_FRAGMENT.get()).setWeight(55)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 7))))
                        .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_ORE.get().asItem()).setWeight(35)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                        .add(LootItem.lootTableItem(LSBlocks.DEEPSLATE_CRYSTAL_ORE.get().asItem()).setWeight(25)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                        .add(LootItem.lootTableItem(LSBlocks.NETHERRACK_CRYSTAL_ORE.get()).setWeight(35)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 14))))
                        .add(LootItem.lootTableItem(LSItems.HEART_CRYSTAL.get()).setWeight(2)
                                .when(LootItemRandomChanceCondition.randomChance(0.01F)))
                        .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_BLOCK.get()).setWeight(25)))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(1, 4))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(50)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(35)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(16, 32))))
                        .add(LootItem.lootTableItem(Items.EMERALD_ORE).setWeight(25)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(35)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8)))))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(0, 1))
                        .add(LootItem.lootTableItem(Items.STONE_PICKAXE).setWeight(75))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(20)
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(1, 10))
                                        .when(LootItemRandomChanceCondition.randomChance(0.10F))))));
        this.output.accept(LSConstants.MINERS_RUINED_SHACK_TABLE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(2, 3))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(50)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(35)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 12))))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(70)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(16, 32))))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(25)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(35)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))));
        this.output.accept(LSConstants.RICH_CART_TABLE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(3, 7)).setBonusRolls(ContextFloatProviders.exactly(5))
                        .add(LootItem.lootTableItem(Items.IRON_ORE).setWeight(5000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 18))))
                        .add(LootItem.lootTableItem(Items.GOLD_ORE).setWeight(5000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(5000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 18))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(4000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 7))))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(4000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 11))))
                        .add(LootItem.lootTableItem(LSItems.CRYSTAL_FRAGMENT.get()).setWeight(4000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 24))))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2500)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 9))))
                        .add(LootItem.lootTableItem(Items.EMERALD_ORE).setWeight(2500)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                        .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_ORE.get().asItem()).setWeight(2500)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 14))))
                        .add(LootItem.lootTableItem(Items.DIAMOND_ORE).setWeight(1000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 27))))
                        .add(LootItem.lootTableItem(LSItems.CRYSTAL_CORE.get()).setWeight(1000)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).setWeight(100)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                        .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_BLOCK.get()).setWeight(100)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        .add(LootItem.lootTableItem(LSItems.HEART_CRYSTAL.get()).setWeight(100))
                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 12))))));
        this.output.accept(LSConstants.RUINED_LIBRARY_TABLE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(2))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 18))))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(1, 24))
                                        .when(LootItemRandomChanceCondition.randomChance(0.10F)))))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(3, 4))
                        .add(LootItem.lootTableItem(LSItems.CRYSTAL_CORE.get()).setWeight(35))
                        .add(LootItem.lootTableItem(LSItems.CRYSTAL_FRAGMENT.get()).setWeight(60)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 15))))
                        .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_BLOCK.get().asItem()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        .add(LootItem.lootTableItem(LSItems.HEART_CRYSTAL.get()).setWeight(1)
                                .when(LootItemRandomChanceCondition.randomChance(0.50F)))));

    }
}