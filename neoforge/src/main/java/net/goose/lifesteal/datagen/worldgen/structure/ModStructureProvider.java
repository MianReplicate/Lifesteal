package net.goose.lifesteal.datagen.worldgen.structure;

import net.goose.lifesteal.util.ModResources;
import net.goose.lifesteal.util.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.AncientCityStructurePieces;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModStructureProvider {
    public static void bootstrap(BootstrapContext<Structure> context){
        HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> poolGetter = context.lookup(Registries.TEMPLATE_POOL);

        context.register(BuiltinStructures.ANCIENT_CITY, new JigsawStructure((new Structure.StructureSettings.Builder(biomeGetter.getOrThrow(BiomeTags.HAS_ANCIENT_CITY))
        ).generationStep(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                .terrainAdapation(TerrainAdjustment.BEARD_BOX).build(),
                poolGetter.getOrThrow(AncientCityStructurePieces.START),
                Optional.of(ResourceLocation.withDefaultNamespace("city_anchor")),
                7,
                ConstantHeight.of(VerticalAnchor.absolute(-27)),
                false,
                Optional.empty(),
                116, List.of(), JigsawStructure.DEFAULT_DIMENSION_PADDING, JigsawStructure.DEFAULT_LIQUID_SETTINGS));


        context.register(
                ModResources.ABANDONED_TRADING_CART,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ABANDONED_TRADING_CART_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.COLLAPSED_MINESHAFT,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_HILL),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.COLLAPSED_MINESHAFT_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_BROKEN_PORTAL,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.HAS_RUINED_PORTAL_STANDARD),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_BROKEN_PORTAL_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false
                )
        );
        context.register(
                ModResources.MINERS_BROKEN_PORTAL_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.HAS_RUINED_PORTAL_STANDARD),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_BROKEN_PORTAL_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false
                )
        );
        context.register(
                ModResources.MINERS_BROKEN_PORTAL_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.HAS_RUINED_PORTAL_STANDARD),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_BROKEN_PORTAL_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_HOME,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(ModTags.MINERS_LOCATION_1),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_HOME_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_HOME_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(ModTags.MINERS_LOCATION_1),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_HOME_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_HOME_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(ModTags.MINERS_LOCATION_1),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_HOME_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_HOME_TAIGA,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(ModTags.MINERS_LOCATION_2),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_HOME_TAIGA_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_HOME_TAIGA_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(ModTags.MINERS_LOCATION_2),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_HOME_TAIGA_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_HOME_TAIGA_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(ModTags.MINERS_LOCATION_2),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_HOME_TAIGA_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_SHACK,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(ModTags.MINERS_LOCATION_3),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_SHACK_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.MINERS_RUINED_SHACK,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(ModTags.MINERS_LOCATION_3),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.MINERS_RUINED_SHACK_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ORE_CART,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ORE_CART_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ORE_CART_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ORE_CART_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ORE_CART_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ORE_CART_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ORE_CART_3,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ORE_CART_POOL_3),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ORE_CART_4,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ORE_CART_POOL_4),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ORE_CART_5,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ORE_CART_POOL_5),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ROBBED_CART,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ROBBED_CART_POOL),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ROBBED_CART_1,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ROBBED_CART_POOL_1),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.ROBBED_CART_2,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                biomeGetter.getOrThrow(BiomeTags.IS_FOREST),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.ROBBED_CART_POOL_2),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
        context.register(
                ModResources.RUINED_LIBRARY,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                HolderSet.direct(biomeGetter.getOrThrow(Biomes.DARK_FOREST)),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_THIN
                        ),
                        poolGetter.getOrThrow(ModResources.RUINED_LIBRARY_START_POOL),
                        2,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
    }
}
