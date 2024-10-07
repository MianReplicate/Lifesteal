package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.advancement.LSAdvancementTrigger;
import mc.mian.lifesteal.advancement.LSCriteria;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LSAdvancementsProvider extends ForgeAdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     * @param subProviders       the generators used to create the advancements
     */
    public LSAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, List<ForgeAdvancementProvider.AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, subProviders);
    }

    private static DisplayInfo simpleDisplay(ItemLike icon, String name, FrameType frameType) {
        return simpleDisplayWithBackground(icon, name, frameType, null);
    }

    private static DisplayInfo simpleDisplayWithBackground(ItemLike icon, String name, FrameType frameType, @Nullable ResourceLocation background) {
        return display(new ItemStack(icon), name, frameType, background, true, true, false);
    }

    private static DisplayInfo display(ItemStack icon, String name, FrameType frameType, ResourceLocation background, boolean showToast, boolean announceChat, boolean hidden) {
        String expandedName = "advancement." + LifeSteal.MOD_ID + ":" + name;
        return new DisplayInfo(icon, Component.translatable(expandedName), Component.translatable(expandedName + ".desc"), background, frameType, showToast, announceChat, hidden);
    }

    public static class AdvancementsGenerator implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            Advancement ROOT = Advancement.Builder.advancement()
                    .display(display(
                            LSItems.CRYSTAL_FRAGMENT.get().getDefaultInstance(),
                            "root",
                            FrameType.TASK,
                            LSUtil.modLoc("textures/block/crystal_block.png"),
                            false,
                            false,
                            false))
                    .addCriterion("has_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(LSItems.CRYSTAL_FRAGMENT.get()))
                    .save(saver, LSConstants.ROOT.toString());
            Advancement.Builder.advancement()
                    .parent(ROOT)
                    .display(simpleDisplay(LSBlocks.REVIVE_HEAD.get(), LSConstants.BACK_FROM_THE_DEAD.getPath(), FrameType.CHALLENGE))
                    .addCriterion("back_from_the_dead", new LSAdvancementTrigger.Instance(ContextAwarePredicate.ANY, LSCriteria.BACK_FROM_THE_DEAD.getId()))
                    .save(saver, LSConstants.BACK_FROM_THE_DEAD.toString());
            Advancement CRYSTAL_CORE = Advancement.Builder.advancement()
                    .parent(ROOT)
                    .display(simpleDisplay(LSItems.CRYSTAL_CORE.get(), LSConstants.GET_CRYSTAL_CORE.getPath(), FrameType.TASK))
                    .addCriterion("has_crystal_core", InventoryChangeTrigger.TriggerInstance.hasItems(LSItems.CRYSTAL_CORE.get()))
                    .save(saver, LSConstants.GET_CRYSTAL_CORE.toString());
            Advancement HEART_CRYSTAL = Advancement.Builder.advancement()
                    .parent(CRYSTAL_CORE)
                    .display(simpleDisplay(LSItems.HEART_CRYSTAL.get(), LSConstants.GET_HEART_CRYSTAL.getPath(), FrameType.TASK))
                    .addCriterion("has_heart_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(LSItems.HEART_CRYSTAL.get()))
                    .save(saver, LSConstants.GET_HEART_CRYSTAL.toString());
            Advancement GET_10_MAX_HEARTS = Advancement.Builder.advancement()
                    .parent(HEART_CRYSTAL)
                    .display(simpleDisplay(LSBlocks.CRYSTAL_BLOCK.get(), LSConstants.GET_10_MAX_HEARTS.getPath(), FrameType.GOAL))
                    .addCriterion("has_10_max_hearts", new LSAdvancementTrigger.Instance(ContextAwarePredicate.ANY, LSCriteria.GET_10_MAX_HEARTS.getId()))
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, LSConstants.GET_10_MAX_HEARTS.toString());
            Advancement REVIVE_CRYSTAL = Advancement.Builder.advancement()
                    .parent(HEART_CRYSTAL)
                    .display(simpleDisplay(LSItems.REVIVE_CRYSTAL.get(), LSConstants.GET_REVIVE_CRYSTAL.getPath(), FrameType.CHALLENGE))
                    .addCriterion("has_revive_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(LSItems.REVIVE_CRYSTAL.get()))
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, LSConstants.GET_REVIVE_CRYSTAL.toString());
            Advancement.Builder.advancement()
                    .parent(REVIVE_CRYSTAL)
                    .display(simpleDisplay(Items.DIRT, LSConstants.REVIVED.getPath(), FrameType.CHALLENGE))
                    .addCriterion("revived_player", new LSAdvancementTrigger.Instance(ContextAwarePredicate.ANY, LSCriteria.REVIVED.getId()))
                    .rewards(AdvancementRewards.Builder.experience(1000))
                    .save(saver, LSConstants.REVIVED.toString());
            Advancement.Builder.advancement()
                    .parent(GET_10_MAX_HEARTS)
                    .display(simpleDisplay(Items.TOTEM_OF_UNDYING, LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS.getPath(), FrameType.CHALLENGE))
                    .addCriterion("used_totem_while_20_max_hearts", new LSAdvancementTrigger.Instance(ContextAwarePredicate.ANY, LSCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS.getId()))
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS.toString());
            Advancement.Builder.advancement()
                    .parent(GET_10_MAX_HEARTS)
                    .display(simpleDisplay(Items.NETHERITE_HELMET, LSConstants.GET_10_MAX_HEARTS_WITH_NETHERITE_ARMOR.getPath(), FrameType.CHALLENGE))
                    .addCriterion("has_full_netherite_armor", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS))
                    .addCriterion("has_10_max_hearts", new LSAdvancementTrigger.Instance(ContextAwarePredicate.ANY, LSCriteria.GET_10_MAX_HEARTS.getId()))
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, LSConstants.GET_10_MAX_HEARTS_WITH_NETHERITE_ARMOR.toString());
        }
    }
}
