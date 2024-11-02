package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.advancement.LSAdvancementTrigger;
import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LSAdvancementsProvider extends AdvancementProvider {
    public LSAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, List<AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, subProviders);
    }

    private static DisplayInfo simpleDisplay(ItemLike icon, String name, AdvancementType frameType) {
        return simpleDisplayWithBackground(icon, name, frameType, null);
    }

    private static DisplayInfo simpleDisplayWithBackground(ItemLike icon, String name, AdvancementType frameType, @Nullable ResourceLocation background) {
        return display(new ItemStack(icon), name, frameType, background, true, true, false);
    }

    private static DisplayInfo display(ItemStack icon, String name, AdvancementType frameType, ResourceLocation background, boolean showToast, boolean announceChat, boolean hidden) {
        String expandedName = "advancement." + LSConstants.MOD_ID + ":" + name;
        return new DisplayInfo(icon, Component.translatable(expandedName), Component.translatable(expandedName + ".desc"), Optional.ofNullable(background), frameType, showToast, announceChat, hidden);
    }

    public static class AdvancementsGenerator implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder ROOT = Advancement.Builder.advancement()
                    .display(display(
                            LSItems.CRYSTAL_FRAGMENT.get().getDefaultInstance(),
                            "root",
                            AdvancementType.TASK,
                            LSConstants.modLoc("textures/block/crystal_block.png"),
                            false,
                            false,
                            false))
                    .addCriterion("has_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(LSItems.CRYSTAL_FRAGMENT.get()))
                    .save(saver, LSConstants.ROOT.toString());
            Advancement.Builder.advancement()
                    .parent(ROOT)
                    .display(simpleDisplay(LSBlocks.REVIVE_HEAD.get(), LSConstants.BACK_FROM_THE_DEAD.getPath(), AdvancementType.CHALLENGE))
                    .addCriterion("back_from_the_dead", LSAdvancementTrigger.TriggerInstance.BACK_FROM_THE_DEAD())
                    .save(saver, LSConstants.BACK_FROM_THE_DEAD.toString());
            AdvancementHolder CRYSTAL_CORE = Advancement.Builder.advancement()
                    .parent(ROOT)
                    .display(simpleDisplay(LSItems.CRYSTAL_CORE.get(), LSConstants.GET_CRYSTAL_CORE.getPath(), AdvancementType.TASK))
                    .addCriterion("has_crystal_core", InventoryChangeTrigger.TriggerInstance.hasItems(LSItems.CRYSTAL_CORE.get()))
                    .save(saver, LSConstants.GET_CRYSTAL_CORE.toString());
            AdvancementHolder HEART_CRYSTAL = Advancement.Builder.advancement()
                    .parent(CRYSTAL_CORE)
                    .display(simpleDisplay(LSItems.HEART_CRYSTAL.get(), LSConstants.GET_HEART_CRYSTAL.getPath(), AdvancementType.TASK))
                    .addCriterion("has_heart_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(LSItems.HEART_CRYSTAL.get()))
                    .save(saver, LSConstants.GET_HEART_CRYSTAL.toString());
            AdvancementHolder GET_10_MAX_HEARTS = Advancement.Builder.advancement()
                    .parent(HEART_CRYSTAL)
                    .display(simpleDisplay(LSBlocks.CRYSTAL_BLOCK.get(), LSConstants.GET_10_MAX_HEARTS.getPath(), AdvancementType.GOAL))
                    .addCriterion("has_10_max_hearts", LSAdvancementTrigger.TriggerInstance.GET_10_MAX_HEARTS())
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, LSConstants.GET_10_MAX_HEARTS.toString());
            AdvancementHolder REVIVE_CRYSTAL = Advancement.Builder.advancement()
                    .parent(HEART_CRYSTAL)
                    .display(simpleDisplay(LSItems.REVIVE_CRYSTAL.get(), LSConstants.GET_REVIVE_CRYSTAL.getPath(), AdvancementType.CHALLENGE))
                    .addCriterion("has_revive_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(LSItems.REVIVE_CRYSTAL.get()))
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, LSConstants.GET_REVIVE_CRYSTAL.toString());
            Advancement.Builder.advancement()
                    .parent(REVIVE_CRYSTAL)
                    .display(simpleDisplay(Items.DIRT, LSConstants.REVIVED.getPath(), AdvancementType.CHALLENGE))
                    .addCriterion("revived_player", LSAdvancementTrigger.TriggerInstance.REVIVED())
                    .rewards(AdvancementRewards.Builder.experience(1000))
                    .save(saver, LSConstants.REVIVED.toString());
            Advancement.Builder.advancement()
                    .parent(GET_10_MAX_HEARTS)
                    .display(simpleDisplay(Items.TOTEM_OF_UNDYING, LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS.getPath(), AdvancementType.CHALLENGE))
                    .addCriterion("used_totem_while_20_max_hearts", LSAdvancementTrigger.TriggerInstance.USE_TOTEM_WHILE_20_MAX_HEARTS())
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS.toString());
            Advancement.Builder.advancement()
                    .parent(GET_10_MAX_HEARTS)
                    .display(simpleDisplay(Items.NETHERITE_HELMET, LSConstants.GET_10_MAX_HEARTS_WITH_NETHERITE_ARMOR.getPath(), AdvancementType.CHALLENGE))
                    .addCriterion("has_full_netherite_armor", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS))
                    .addCriterion("has_10_max_hearts", LSAdvancementTrigger.TriggerInstance.GET_10_MAX_HEARTS())
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, LSConstants.GET_10_MAX_HEARTS_WITH_NETHERITE_ARMOR.toString());
        }
    }
}
