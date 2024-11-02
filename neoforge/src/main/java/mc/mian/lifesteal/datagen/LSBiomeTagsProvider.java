package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class LSBiomeTagsProvider extends BiomeTagsProvider {
    public LSBiomeTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, LSConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(LSTags.OVERWORLD_ORE).addTags(BiomeTags.IS_OVERWORLD, Tags.Biomes.IS_UNDERGROUND);
        tag(LSTags.NETHER_ORE).addTags(BiomeTags.IS_NETHER, Tags.Biomes.IS_DRY_NETHER, Tags.Biomes.IS_HOT_NETHER);

        tag(LSTags.BASE_MINERS_LOCATION).add(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.SNOWY_PLAINS, Biomes.MEADOW);

        tag(LSTags.MINERS_LOCATION_1).add(Biomes.FOREST).addTag(LSTags.BASE_MINERS_LOCATION);

        tag(LSTags.MINERS_LOCATION_2).add(Biomes.TAIGA).addTag(LSTags.BASE_MINERS_LOCATION);

        tag(LSTags.MINERS_LOCATION_3).addTags(LSTags.BASE_MINERS_LOCATION, LSTags.MINERS_LOCATION_2, LSTags.MINERS_LOCATION_1);

        tag(LSTags.MINERS_LOCATION_4).add(Biomes.TAIGA, Biomes.DARK_FOREST);

    }
}
