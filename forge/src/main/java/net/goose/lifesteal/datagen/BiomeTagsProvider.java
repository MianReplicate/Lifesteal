package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BiomeTagsProvider extends net.minecraft.data.tags.BiomeTagsProvider {
    public BiomeTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, LifeSteal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.OVERWORLD_ORE).addTags(BiomeTags.IS_OVERWORLD, Tags.Biomes.IS_UNDERGROUND);
        tag(ModTags.NETHER_ORE).addTags(BiomeTags.IS_NETHER, Tags.Biomes.IS_DRY_NETHER, Tags.Biomes.IS_HOT_NETHER);

        tag(ModTags.BASE_MINERS_LOCATION).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.SNOWY_PLAINS, Biomes.MEADOW);

        tag(ModTags.MINERS_LOCATION_1).add(Biomes.FOREST);
        tag(ModTags.MINERS_LOCATION_1).addTag(ModTags.BASE_MINERS_LOCATION);

        tag(ModTags.MINERS_LOCATION_2).add(Biomes.TAIGA);
        tag(ModTags.MINERS_LOCATION_2).addTag(ModTags.BASE_MINERS_LOCATION);

        tag(ModTags.MINERS_LOCATION_3).addTags(ModTags.BASE_MINERS_LOCATION, ModTags.MINERS_LOCATION_2, ModTags.MINERS_LOCATION_1);

        tag(ModTags.MINERS_LOCATION_4).add(Biomes.TAIGA, Biomes.DARK_FOREST);

    }
}
