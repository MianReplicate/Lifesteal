package mc.mian.lifesteal.datagen.worldgen.structure;

import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Map;

public class LSStructureProvider {
    public static void bootstrap(BootstrapContext<Structure> context){
        HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> poolGetter = context.lookup(Registries.TEMPLATE_POOL);

        context.register(
                LSConstants.ABANDONED_TRADING_CART,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ABANDONED_TRADING_CART_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.COLLAPSED_MINESHAFT,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_HILL),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.COLLAPSED_MINESHAFT_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_BROKEN_PORTAL,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.HAS_RUINED_PORTAL_STANDARD),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_BROKEN_PORTAL_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false
                )
        );
        context.register(
                LSConstants.MINERS_BROKEN_PORTAL_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.HAS_RUINED_PORTAL_STANDARD),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_BROKEN_PORTAL_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false
                )
        );
        context.register(
                LSConstants.MINERS_BROKEN_PORTAL_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.HAS_RUINED_PORTAL_STANDARD),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_BROKEN_PORTAL_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_HOME,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(LSTags.MINERS_LOCATION_1),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_HOME_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_HOME_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(LSTags.MINERS_LOCATION_1),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_HOME_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_HOME_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(LSTags.MINERS_LOCATION_1),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_HOME_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_HOME_TAIGA,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(LSTags.MINERS_LOCATION_2),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_HOME_TAIGA_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_HOME_TAIGA_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(LSTags.MINERS_LOCATION_2),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_HOME_TAIGA_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_HOME_TAIGA_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(LSTags.MINERS_LOCATION_2),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_HOME_TAIGA_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_SHACK,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(LSTags.MINERS_LOCATION_3),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_SHACK_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.MINERS_RUINED_SHACK,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(LSTags.MINERS_LOCATION_3),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.MINERS_RUINED_SHACK_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ORE_CART,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ORE_CART_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ORE_CART_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ORE_CART_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ORE_CART_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ORE_CART_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ORE_CART_3,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ORE_CART_POOL_3),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ORE_CART_4,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ORE_CART_POOL_4),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ORE_CART_5,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ORE_CART_POOL_5),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ROBBED_CART,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ROBBED_CART_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ROBBED_CART_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ROBBED_CART_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.ROBBED_CART_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.ROBBED_CART_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                LSConstants.RUINED_LIBRARY,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                HolderSet.direct(biomeGetter.getOrThrow(Biomes.DARK_FOREST)),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(LSConstants.RUINED_LIBRARY_START_POOL),
                        2,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
    }
}
