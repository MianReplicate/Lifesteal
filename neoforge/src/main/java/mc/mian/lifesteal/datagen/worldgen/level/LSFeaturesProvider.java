package mc.mian.lifesteal.datagen.worldgen.level;

import com.google.common.collect.ImmutableList;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class LSFeaturesProvider {

    public static void bootstrap(BootstrapContext<Feature> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceable = new TagMatchTest(BlockTags.BASE_STONE_NETHER);

        register(context, LSConstants.DEEPSLATE_HEART_GEODE_CONFIGURED,
                new GeodeFeature(
                        new GeodeBlockSettings(
                                BlockStateProvider.holderOf(Blocks.AIR),
                                BlockStateProvider.holderOf(Blocks.DEEPSLATE),
                                BlockStateProvider.holderOf(LSBlocks.DEEPSLATE_CRYSTAL_ORE.get()),
                                BlockStateProvider.holderOf(Blocks.CALCITE),
                                BlockStateProvider.holderOf(Blocks.SMOOTH_BASALT),
                                List.of(Blocks.GRAVEL.defaultBlockState()),
                                blocks.getOrThrow(BlockTags.FEATURES_CANNOT_REPLACE),
                                blocks.getOrThrow(BlockTags.GEODE_INVALID_BLOCKS)
                        ),
                        new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackSettings(0.95, 2.0, 2),
                        0.35, 0.083, true, UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2), 16, -16, 0.05, 1));
        register(context, LSConstants.NETHER_HEART_GEODE_CONFIGURED,
                new GeodeFeature(
                        new GeodeBlockSettings(
                                BlockStateProvider.holderOf(Blocks.AIR),
                                BlockStateProvider.holderOf(Blocks.NETHERRACK),
                                BlockStateProvider.holderOf(LSBlocks.NETHERRACK_CRYSTAL_ORE.get()),
                                BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK),
                                BlockStateProvider.holderOf(Blocks.BLACKSTONE),
                                List.of(Blocks.NETHER_GOLD_ORE.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()),
                                blocks.getOrThrow(BlockTags.FEATURES_CANNOT_REPLACE),
                                blocks.getOrThrow(BlockTags.GEODE_INVALID_BLOCKS)
                        ),
                        new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackSettings(0.95, 2.0, 2),
                        0.35, 0.083, true, UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2), 16, -16, 0.05, 1));
        register(context, LSConstants.HEART_ORE_CONFIGURED,
                new ScatteredOreFeature(
                        ImmutableList.of(
                                BlockReplacement.replace(stoneReplaceable, LSBlocks.CRYSTAL_ORE.get().defaultBlockState()),
                                BlockReplacement.replace(deepslateReplaceable, LSBlocks.DEEPSLATE_CRYSTAL_ORE.get().defaultBlockState())), 6, 0));
        register(context, LSConstants.NETHER_HEART_ORE_CONFIGURED,
                new ScatteredOreFeature(
                        ImmutableList.of(
                                BlockReplacement.replace(netherReplaceable, LSBlocks.NETHERRACK_CRYSTAL_ORE.get().defaultBlockState())), 7, 0));
    }

    public static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, Feature feature) {
        context.register(key, feature);
    }
}
