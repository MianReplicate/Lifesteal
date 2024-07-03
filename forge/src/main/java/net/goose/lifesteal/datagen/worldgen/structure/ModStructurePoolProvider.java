package net.goose.lifesteal.datagen.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.*;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ModStructurePoolProvider {

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePoolGetter = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> emptyHolder = templatePoolGetter.getOrThrow(Pools.EMPTY);
        context.register(ModResources.ABANDONED_TRADING_CART_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(Pair.of(StructurePoolElement.single("lifesteal:abandoned_trading_cart/abandoned_trading_cart"), 1)),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.COLLAPSED_MINESHAFT_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(Pair.of(StructurePoolElement.single("lifesteal:collapsed_mineshaft/collapsed_mineshaft"), 1)),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_BROKEN_PORTAL_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_broken_portal/miners_broken_portal"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_BROKEN_PORTAL_POOL_1, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_broken_portal/miners_broken_portal_1"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_BROKEN_PORTAL_POOL_2, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_broken_portal/miners_broken_portal_2"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_HOME_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_home/miners_home"), 1)
                        ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_HOME_POOL_1, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_home/miners_home_1"), 1)
                        ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_HOME_POOL_2, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_home/miners_home_2"), 1)
                        ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_HOME_TAIGA_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_home/miners_home_taiga"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_HOME_TAIGA_POOL_1, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_home/miners_home_taiga_1"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_HOME_TAIGA_POOL_2, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_home/miners_home_taiga_2"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_SHACK_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_shack/miners_shack"), 1)
                        ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.MINERS_RUINED_SHACK_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:miners_shack/miners_ruined_shack"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ORE_CART_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/ore_cart"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ORE_CART_POOL_1, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/ore_cart_1"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ORE_CART_POOL_2, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/ore_cart_2"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ORE_CART_POOL_3, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/ore_cart_3"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ORE_CART_POOL_4, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/ore_cart_4"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ORE_CART_POOL_5, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/ore_cart_5"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ROBBED_CART_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/robbed_cart"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ROBBED_CART_POOL_1, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/robbed_cart_1"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.ROBBED_CART_POOL_2, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ore_cart/robbed_cart_2"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.RUINED_LIBRARY_START_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ruined_library/ruined_library"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
        context.register(ModResources.RUINED_LIBRARY_SIDE_POOL, new StructureTemplatePool(
                emptyHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.single("lifesteal:ruined_library/ruined_library_secondhalf"), 1)
                ),
                StructureTemplatePool.Projection.RIGID));
    }
}
