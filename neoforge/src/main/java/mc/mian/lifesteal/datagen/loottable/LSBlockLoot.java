package mc.mian.lifesteal.datagen.loottable;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.ArrayList;

public class LSBlockLoot extends VanillaBlockLoot {
    public LSBlockLoot(LootTableSubProvider.Context context) {
        super(context);
    }

    @Override
    protected void generate() {
        this.add(LSBlocks.CRYSTAL_ORE.get(), (block) -> createOreDrop(block, LSItems.CRYSTAL_FRAGMENT.get()));
        this.add(LSBlocks.DEEPSLATE_CRYSTAL_ORE.get(), (block) -> createOreDrop(block, LSItems.CRYSTAL_FRAGMENT.get()));
        this.add(LSBlocks.NETHERRACK_CRYSTAL_ORE.get(), (block) -> createOreDrop(block, LSItems.CRYSTAL_FRAGMENT.get()));

        this.add(LSBlocks.REVIVE_HEAD.get(), (block) -> LootTable.lootTable().withPool((LootPool.lootPool().add(LootItem.lootTableItem(LSItems.REVIVE_HEAD_ITEM.get())
                .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                        .include(DataComponents.PROFILE).include(DataComponents.NOTE_BLOCK_SOUND))))));
        dropSelf(LSBlocks.CRYSTAL_BLOCK.get());
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> entry : LSBlocks.BLOCKS.getEntries()) {
            blocks.add(entry.get());
        }
        return blocks;
    }
}
