package mc.mian.lifesteal.datagen;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import mc.mian.lifesteal.util.LSTags;
import mc.mian.lifesteal.util.LSUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LSLangProvider extends LanguageProvider {
    public LSLangProvider(PackOutput output) {
        super(output, LSConstants.MOD_ID, "en_us");
    }

    public void addAdvancement(ResourceLocation advancementLocation, String title, String desc){
        add("advancement."+LSConstants.MOD_ID+":"+advancementLocation.getPath(), title);
        add("advancement."+LSConstants.MOD_ID+":"+advancementLocation.getPath()+".desc", desc);
    }

    public void addGuiMessage(String title, String translation){
        add("gui."+ LSConstants.MOD_ID+"."+title, translation);
    }

    public void addChatMessage(String title, String translation){
        add("chat.message."+ LSConstants.MOD_ID+"."+title, translation);
    }

    public void addBannedMessage(String title, String translation){
        add("bannedmessage."+ LSConstants.MOD_ID+"."+title, translation);
    }

    @Override
    protected void addTranslations() {
        addTag(() -> LSTags.ORIGINS_IGNORE_DIET, "Ignore Diet");

        addItem(LSItems.CRYSTAL_FRAGMENT, "Crystal Fragment");
        addItem(LSItems.CRYSTAL_CORE, "Crystal Core");
        addItem(LSItems.HEART_CRYSTAL, "Heart Crystal");
        addItem(LSItems.REVIVE_CRYSTAL, "Revive Crystal");

        addBlock(LSBlocks.CRYSTAL_BLOCK, "Crystal Block");
        addBlock(LSBlocks.REVIVE_HEAD, "Revive Head");
        addBlock(LSBlocks.CRYSTAL_ORE, "Crystal Ore");
        addBlock(LSBlocks.NETHERRACK_CRYSTAL_ORE, "Netherrack Crystal Ore");
        addBlock(LSBlocks.DEEPSLATE_CRYSTAL_ORE, "Deepslate Crystal Ore");
        add("block.lifesteal.revive_wall_head", "Revive Wall Head"); // idk why but we cant use addBlock on this one

        addAdvancement(LSConstants.ROOT, "Fragment of the Past", "The beginning of the end.");
        addAdvancement(LSConstants.GET_CRYSTAL_CORE, "A Beacon of Hope", "Silent screams come from the core, begging to be crafted into something greater.");
        addAdvancement(LSConstants.GET_HEART_CRYSTAL, "Once Upon a Time", "The crystal shines brighter, tempting you to consume, but perhaps you could make it into something even greater?");
        addAdvancement(LSConstants.GET_REVIVE_CRYSTAL, "The End of Time", "What have you done?");
        addAdvancement(LSConstants.GET_10_MAX_HEARTS, "Immortality", "A rush of adrenaline engulfs you, reminding you of what you truly are now.");
        addAdvancement(LSConstants.USE_TOTEM_WHILE_20_MAX_HEARTS, "Defying Mortality", "Save yourself from death with a Totem of Undying, even at the peak of immortality.");
        addAdvancement(LSConstants.GET_10_MAX_HEARTS_WITH_NETHERITE_ARMOR, "Challenging Death", "EAT. CONSUME. GAIN. RETRIEVE POWER.");
        addAdvancement(LSConstants.BACK_FROM_THE_DEAD, "???", "...");
        addAdvancement(LSConstants.REVIVED, "Existence", "Defy life.");

        addChatMessage("reached_max_hearts", "You have reached max hearts.");
        addChatMessage("lost_max_hearts", "You have lost all your max hearts. You are now permanently dead.");
        addChatMessage("no_more_hearts_to_steal", "This player doesn't have any hearts you can steal.");
        addChatMessage("revived_player", "%s has been revived from the dead!");
        addChatMessage("get_hit_point_for_player", "%s's HitPoint difference is %s.");
        addChatMessage("set_hit_point_for_self", "Your HitPoint difference has been set to %s.");
        addChatMessage("set_hit_point_for_player", "Set %s's HitPoint difference to %s.");
        addChatMessage("revived_player_success", "Successfully revived %s.");
        addChatMessage("revived_player_failed", "Failed to revive due to no position being given.");

        addBannedMessage("lost_max_hearts", "You have lost all your max hearts, you are now permanently dead till further notice.");
        addBannedMessage("revive_head_location", "Your revive head is located at [%s, %s, %s].");
        addBannedMessage("auto_revive_time", "You will be automatically revived on %s.");

        addGuiMessage("crystal_core_disabled", "Crystal Cores have been disabled in the configurations");
        addGuiMessage("heart_crystal_disabled", "Heart Crystals have been disabled in the configurations");
        addGuiMessage("unnatural_heart_crystal_disabled", "Unnatural Heart Crystals have been disabled in the configurations");
        addGuiMessage("revive_crystal_disabled", "Revive Crystals have been disabled in the configurations");
        addGuiMessage("heart_crystal_reaching_max", "You are already at the max amount of hearts");
        addGuiMessage("crystal_core_at_max_health", "You are already at max health");
        addGuiMessage("cant_withdraw_less_than_maximum", "You can't withdraw over the maximum amount of hearts you can lose");
        addGuiMessage("invaild_revive_block", "You need to right click a specified player's revive head to revive them");
        addGuiMessage("null_revive_block", "This revive head doesn't have a player assigned to it. Did you grab this out of creative mode?");
        addGuiMessage("error_revive_block", "This player was unable to be revived");
        addGuiMessage("already_revived", "This player is already alive");
        addGuiMessage("revived", "This player has successfully been revived");
        addGuiMessage("multiplayer_only", "This item can only be used in multiplayer");
        addGuiMessage("withdrawing_disabled", "Withdrawing is disabled in the configurations");
        addGuiMessage("cant_withdraw_less_than_amount_have", "You don't have enough hearts to justify this withdrawal");

        add("itemGroup.lifesteal", "Lifesteal");
        add("block.lifesteal.revive_head.named", "%s's Revive Head");
        add("item.lifesteal.revive_head.named", "%s's Revive Head");
        add("item.lifesteal.heart_crystal.named", "%s's Heart");
        add("item.lifesteal.heart_crystal.unnatural", "Unnatural Heart Crystal");
    }
}
