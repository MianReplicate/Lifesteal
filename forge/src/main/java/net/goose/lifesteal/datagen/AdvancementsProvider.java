package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.advancement.LSAdvancementTrigger;
import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AdvancementsProvider extends AdvancementProvider {
    public AdvancementsProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
        super(generatorIn, fileHelperIn);
    }

    private static DisplayInfo simpleDisplay(ItemLike icon, String name, FrameType frameType) {
        return simpleDisplayWithBackground(icon, name, frameType, null);
    }

    private static DisplayInfo simpleDisplayWithBackground(ItemLike icon, String name, FrameType frameType, @Nullable ResourceLocation background) {
        return display(new ItemStack(icon), name, frameType, background, true, true, false);
    }

    private static DisplayInfo display(ItemStack icon, String name, FrameType frameType, ResourceLocation background, boolean showToast, boolean announceChat, boolean hidden) {
        String expandedName = "advancement." + LifeSteal.MOD_ID + ":" + name;
        return new DisplayInfo(icon, new TranslatableComponent(expandedName), new TranslatableComponent(expandedName + ".desc"), background, frameType, showToast, announceChat, hidden);
    }
    @Override
    public void registerAdvancements(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement ROOT = Advancement.Builder.advancement()
                .display(display(
                        ModItems.HEART_FRAGMENT.get().getDefaultInstance(),
                        "root",
                        FrameType.TASK,
                        new ResourceLocation("lifesteal:textures/block/heart_core_block.png"),
                        false,
                        false,
                        false))
                .addCriterion("has_heart_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HEART_FRAGMENT.get()))
                .save(saver, LifeSteal.modLoc("root"), existingFileHelper);
        Advancement.Builder.advancement()
                .parent(ROOT)
                .display(simpleDisplay(ModBlocks.REVIVE_HEAD.get(), "revived_from_dead", FrameType.CHALLENGE))
                .addCriterion("revived", new LSAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, ModCriteria.REVIVED.resourceLocation))
                .save(saver, LifeSteal.modLoc("revived_from_dead"), existingFileHelper);
        Advancement HEART_CORE = Advancement.Builder.advancement()
                .parent(ROOT)
                .display(simpleDisplay(ModItems.HEART_CORE.get(), "get_heart_core", FrameType.TASK))
                .addCriterion("has_heart_core", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HEART_CORE.get()))
                .save(saver, LifeSteal.modLoc("get_heart_core"), existingFileHelper);
        Advancement HEART_CRYSTAL = Advancement.Builder.advancement()
                .parent(HEART_CORE)
                .display(simpleDisplay(ModItems.HEART_CRYSTAL.get(), "get_heart_crystal", FrameType.TASK))
                .addCriterion("has_heart_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HEART_CRYSTAL.get()))
                .save(saver, LifeSteal.modLoc("get_heart_crystal"), existingFileHelper);
        Advancement GET_10_MAX_HEARTS = Advancement.Builder.advancement()
                .parent(HEART_CRYSTAL)
                .display(simpleDisplay(ModBlocks.HEART_CORE_BLOCK.get(), "get_10_max_hearts", FrameType.GOAL))
                .addCriterion("has_10_max_hearts", new LSAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, ModCriteria.GET_10_MAX_HEARTS.resourceLocation))
                .rewards(AdvancementRewards.Builder.experience(500))
                .save(saver, LifeSteal.modLoc("get_10_max_hearts"), existingFileHelper);
        Advancement.Builder.advancement()
                .parent(GET_10_MAX_HEARTS)
                .display(simpleDisplay(Items.TOTEM_OF_UNDYING, "use_totem_while_20_max_hearts", FrameType.CHALLENGE))
                .addCriterion("used_totem_while_20_max_hearts", new LSAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, ModCriteria.USE_TOTEM_WHILE_20_MAX_HEARTS.resourceLocation))
                .rewards(AdvancementRewards.Builder.experience(500))
                .save(saver, LifeSteal.modLoc("use_totem_while_20_max_hearts"), existingFileHelper);
        Advancement.Builder.advancement()
                .parent(GET_10_MAX_HEARTS)
                .display(simpleDisplay(Items.NETHERITE_HELMET, "get_10_max_hearts_with_netherite_armor", FrameType.CHALLENGE))
                .addCriterion("has_full_netherite_armor", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS))
                .addCriterion("has_10_max_hearts", new LSAdvancementTrigger.Instance(EntityPredicate.Composite.ANY, ModCriteria.GET_10_MAX_HEARTS.resourceLocation))
                .rewards(AdvancementRewards.Builder.experience(500))
                .save(saver, LifeSteal.modLoc("get_10_max_hearts_with_netherite_armor"), existingFileHelper);
    }
}
