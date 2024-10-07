package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class LSLootProvider {
    public static class ModBlockLoot extends VanillaBlockLoot {
        @Override
        protected void generate() {
            this.add(LSBlocks.CRYSTAL_ORE.get(), (block) -> createOreDrop(block, LSItems.CRYSTAL_FRAGMENT.get()));
            this.add(LSBlocks.DEEPSLATE_CRYSTAL_ORE.get(), (block) -> createOreDrop(block, LSItems.CRYSTAL_FRAGMENT.get()));
            this.add(LSBlocks.NETHERRACK_CRYSTAL_ORE.get(), (block) -> createOreDrop(block, LSItems.CRYSTAL_FRAGMENT.get()));
            this.add(LSBlocks.REVIVE_HEAD.get(), (block) -> LootTable.lootTable().withPool((LootPool.lootPool().add(LootItem.lootTableItem(LSItems.REVIVE_HEAD_ITEM.get()).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("SkullOwner", "SkullOwner").copy("note_block_sound", "BlockEntityTag.note_block_sound"))))));
            dropSelf(LSBlocks.CRYSTAL_BLOCK.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            ArrayList<Block> blocks = new ArrayList<>();
            for (RegistrySupplier<Block> entry : LSBlocks.BLOCKS.getEntries()) {
                blocks.add(entry.get());
            }
            return blocks;
        }
    }

    public static class ModChestLoot implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
            biConsumer.accept(LSConstants.BARREL_1_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(4.0F))
                            .add(LootItem.lootTableItem(Items.STRING).setWeight(10)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 24.0F))))
                            .add(LootItem.lootTableItem(Items.PAPER).setWeight(10)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                            .add(LootItem.lootTableItem(Items.BOOK).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))));
            biConsumer.accept(LSConstants.MINERS_HOME_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1F, 4F))
                            .add(LootItem.lootTableItem(LSItems.CRYSTAL_FRAGMENT.get()).setWeight(55)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F))))
                            .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_ORE.get().asItem()).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                            .add(LootItem.lootTableItem(LSBlocks.DEEPSLATE_CRYSTAL_ORE.get().asItem()).setWeight(25)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                            .add(LootItem.lootTableItem(LSBlocks.NETHERRACK_CRYSTAL_ORE.get()).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 14.0F))))
                            .add(LootItem.lootTableItem(LSItems.HEART_CRYSTAL.get()).setWeight(2)
                                    .when(LootItemRandomChanceCondition.randomChance(0.01F)))
                            .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_BLOCK.get()).setWeight(25)))
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1F, 4F))
                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(50)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(Items.COAL).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 32.0F))))
                            .add(LootItem.lootTableItem(Items.EMERALD_ORE).setWeight(25)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F)))))
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0F, 1F))
                            .add(LootItem.lootTableItem(Items.STONE_PICKAXE).setWeight(75))
                            .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(20)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(1.0F, 10.0F))
                                            .when(LootItemRandomChanceCondition.randomChance(0.10F))))));
            biConsumer.accept(LSConstants.MINERS_RUINED_SHACK_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(50)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                            .add(LootItem.lootTableItem(Items.COAL).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 12.0F))))
                            .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(70)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 32.0F))))
                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(25)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))));
            biConsumer.accept(LSConstants.RICH_CART_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 7.0F)).setBonusRolls(ConstantValue.exactly(5))
                            .add(LootItem.lootTableItem(Items.IRON_ORE).setWeight(5000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                            .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 18.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_ORE).setWeight(5000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(5000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 18.0F))))
                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(4000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(4000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 11.0F))))
                            .add(LootItem.lootTableItem(LSItems.CRYSTAL_FRAGMENT.get()).setWeight(4000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 24.0F))))
                            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2500)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 9.0F))))
                            .add(LootItem.lootTableItem(Items.EMERALD_ORE).setWeight(2500)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                            .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_ORE.get().asItem()).setWeight(2500)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 14.0F))))
                            .add(LootItem.lootTableItem(Items.DIAMOND_ORE).setWeight(1000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 27.0F))))
                            .add(LootItem.lootTableItem(LSItems.CRYSTAL_CORE.get()).setWeight(1000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                            .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).setWeight(100)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                            .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_BLOCK.get()).setWeight(100)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                            .add(LootItem.lootTableItem(LSItems.HEART_CRYSTAL.get()).setWeight(100))
                            .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(10)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F))))));
            biConsumer.accept(LSConstants.RUINED_LIBRARY_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2.0F))
                            .add(LootItem.lootTableItem(Items.PAPER).setWeight(10)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 18.0F))))
                            .add(LootItem.lootTableItem(Items.BOOK).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(1.0F, 24.0F))
                                            .when(LootItemRandomChanceCondition.randomChance(0.10F)))))
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 4.0F))
                            .add(LootItem.lootTableItem(LSItems.CRYSTAL_CORE.get()).setWeight(35))
                            .add(LootItem.lootTableItem(LSItems.CRYSTAL_FRAGMENT.get()).setWeight(60)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 15.0F))))
                            .add(LootItem.lootTableItem(LSBlocks.CRYSTAL_BLOCK.get().asItem()).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(LSItems.HEART_CRYSTAL.get()).setWeight(1)
                                    .when(LootItemRandomChanceCondition.randomChance(0.50F)))));

        }
    }
}
