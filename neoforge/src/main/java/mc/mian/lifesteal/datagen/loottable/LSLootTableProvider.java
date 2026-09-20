package mc.mian.lifesteal.datagen.loottable;

import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.*;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;

public class LSLootTableProvider {
    public static SingleRegistryBootstrap<LootTable> create() {
        return new LootTableProvider(
                Collections.emptySet(),
                List.of(
                        new LootTableProvider.SubProviderEntry(LSBlockLoot::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(LSChestLoot::new, LootContextParamSets.CHEST)
                )
        );
    }
}
