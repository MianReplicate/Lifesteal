package mc.mian.lifesteal.datagen.worldgen.structure;

import com.google.common.collect.ImmutableList;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class LSStructureSetProvider {
    public static void bootstrap(BootstrapContext<StructureSet> context){
        HolderGetter<Structure> holderGetter = context.lookup(Registries.STRUCTURE);
        context.register(LSConstants.BIG_STRUCTURES, new StructureSet(
                ImmutableList.of(
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.COLLAPSED_MINESHAFT), 1),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.RUINED_LIBRARY), 1)
                        ),
                new RandomSpreadStructurePlacement(100, 50, RandomSpreadType.LINEAR, 1542327652)));
        context.register(LSConstants.CARTS, new StructureSet(
                ImmutableList.of(
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ORE_CART), 80),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ORE_CART_1), 10),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ORE_CART_2), 20),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ORE_CART_3), 1),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ORE_CART_4), 100),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ORE_CART_5), 90),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ORE_CART_1), 10),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ABANDONED_TRADING_CART), 40),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ROBBED_CART), 150),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ROBBED_CART_1), 150),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.ROBBED_CART_2), 150)
                        ),
                new RandomSpreadStructurePlacement(40, 15, RandomSpreadType.LINEAR, 1542327653)));
        context.register(LSConstants.MINERS_LORE, new StructureSet(
                ImmutableList.of(
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_HOME), 12),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_HOME_1), 10),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_HOME_2), 10),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_HOME_TAIGA), 12),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_HOME_TAIGA_1), 8),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_HOME_TAIGA_2), 8),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_BROKEN_PORTAL), 24),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_BROKEN_PORTAL_1), 24),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_BROKEN_PORTAL_2), 24),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_SHACK), 24),
                        new StructureSet.StructureSelectionEntry(holderGetter.getOrThrow(LSConstants.MINERS_RUINED_SHACK), 28)
                ),
                new RandomSpreadStructurePlacement(40, 20, RandomSpreadType.LINEAR, 1542327654)));
    }

}
