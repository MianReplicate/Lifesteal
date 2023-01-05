package net.goose.lifesteal.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {

    public final ForgeConfigSpec.IntValue startingHeartDifference;
    public final ForgeConfigSpec.BooleanValue shouldAllMobsGiveHearts;
    public final ForgeConfigSpec.BooleanValue loseHeartsOnlyWhenKilledByPlayer;
    public final ForgeConfigSpec.BooleanValue loseHeartsOnlyWhenKilledByEntity;
    public final ForgeConfigSpec.IntValue amountOfHealthLostUponLoss;
    public final ForgeConfigSpec.IntValue maximumamountofheartsGainable;
    public final ForgeConfigSpec.IntValue maximumamountofheartsLoseable;
    public final ForgeConfigSpec.BooleanValue disableLifesteal;
    public final ForgeConfigSpec.BooleanValue preventFromUsingCrystalIfMax;
    public final ForgeConfigSpec.BooleanValue preventFromUsingCoreIfMax;
    public final ForgeConfigSpec.BooleanValue disableHeartCrystals;
    public final ForgeConfigSpec.BooleanValue disableHeartCores;
    public final ForgeConfigSpec.BooleanValue disableReviveCrystals;
    public final ForgeConfigSpec.BooleanValue playersGainHeartsifKillednoHeart;
    public final ForgeConfigSpec.BooleanValue disableHeartLoss;
    public final ForgeConfigSpec.IntValue heartCrystalAmountGain;
    public final ForgeConfigSpec.DoubleValue heartCoreHeal;
    public final ForgeConfigSpec.BooleanValue tellPlayersIfHitPointChanged;
    public final ForgeConfigSpec.ConfigValue advancementUsedForWithdrawing;
    public final ForgeConfigSpec.ConfigValue textUsedForRequirementOnWithdrawing;
    public final ForgeConfigSpec.BooleanValue tellPlayersIfReachedMaxHearts;
    public final ForgeConfigSpec.BooleanValue silentlyRevivePlayer;
    public final ForgeConfigSpec.BooleanValue playersSpawnHeadUponDeath;


    public ModConfig(final ForgeConfigSpec.Builder builder) {
        builder.comment("It's recommended to edit the config BEFORE you make/play a world. While editing the config in an already generated world can work sometimes, there may be visual bugs or just bugs in general.");
        builder.comment("This category holds general values that will mostly be customized by most.");
        builder.push("Starting Configurations");
        this.startingHeartDifference = buildInt(builder, "Starting HitPoint Difference:", 0, -19, Integer.MAX_VALUE, "This value modifies how many hearts you'll start at in a world. 2 would mean 1 extra heart, -2 would mean 1 less heart. If you have lives enabled, you'll gain a life when you get max hearts double your starting hearts. EX: If 3 hearts is your starting value, you'll gain a life if you get 3 more hearts. ");
        this.loseHeartsOnlyWhenKilledByPlayer = buildBoolean(builder, "Lose Hearts Only When Killed By a Player:", false, "When this is true, you will lose hearts when killed by a player. Otherwise, you can lose max hearts just by any sorts of death.. (This is overridden by the mob value below if it's true)");
        this.loseHeartsOnlyWhenKilledByEntity = buildBoolean(builder, "Lose Hearts Only When Killed By an Entity:", false, "When this is true, you will lose hearts when killed by a mob. Otherwise, you can lose max hearts just by any sorts of death.");
        this.amountOfHealthLostUponLoss = buildInt(builder, "Amount of HitPoints/Health Lost/Given Upon Death/Kill:", 2, 1, Integer.MAX_VALUE, "This values modifies the amount of hit points that should be lost when you die. The same also applies when you gain max health from lifestealing. 2 hit points = 1 health.");
        this.disableHeartLoss = buildBoolean(builder, "Disable Heart Loss:", false, "This value determines if a PLAYER should lose HEARTS AT ALL.");
        this.silentlyRevivePlayer = buildBoolean(builder, "Silently Revive Players:", false, "When a player is revived with the Revive Crystal, this value determines whether or not a chat message will be sent indicating they have been brought back from the dead.");
        this.playersSpawnHeadUponDeath = buildBoolean(builder, "Players Spawn Their Head Upon Death:", true, "In multiplayer, this value determines whether heads spawn or not when a player dies. NOTE: In singleplayer, this value is always false.");

        builder.pop();

        builder.comment("This category is the configuration for items and enchantments in this mod");
        builder.push("Items and Enchantments");
        this.heartCrystalAmountGain = buildInt(builder, "Amount of HitPoints Heart Crystal Permanently Gives:", 2, 1, Integer.MAX_VALUE, "This is the amount of hit points a Heart Crystal should give when used. 2 HitPoints = 1 Heart, 3 = 1.5 Heart.");
        this.heartCoreHeal = buildDouble(builder, "Percentage of max Health Heart Core Heals", 0.25, 0.01, 1, "The percentage of max health a heart core should heal when used.");
        this.preventFromUsingCrystalIfMax = buildBoolean(builder, "Prevent Players From Using Heart Crystals If At Max Hearts:", true, "If a max is set for the amount of hearts you can get, this option when true, makes it so players can't eat heart crystals if they're already at the max.");
        this.preventFromUsingCoreIfMax = buildBoolean(builder, "Prevent Players From Using Heart Cores If At Max Health:", true, "If this option is true, a player cannot eat heart cores if they are already at their max health.");
        this.disableHeartCrystals = buildBoolean(builder, "Disable Heart Crystals:", false, "If you just want the generic Lifesteal mod, you can disable this and nobody can gain hearts through Heart Crystals but only through lifestealing.");
        this.disableHeartCores = buildBoolean(builder, "Disable Heart Cores:", false, "Heart Cores can heal on default 25% of your health if right clicked. This value determines if they should be disabled.");
        this.disableReviveCrystals = buildBoolean(builder, "Disable Revive Crystals:", false, "This value determines whether or not revive crystals are disabled. If you're in singleplayer, this value is always true.");

        builder.pop();
        builder.comment("This category is everything related to life stealing from someone.");
        builder.push("Lifesteal Related");
        this.disableLifesteal = buildBoolean(builder, "Disable Lifesteal:", false, "This option changes the entire mod into more of a permanent heart gaining system. This makes it so nobody can gain hearts from lifestealing but ONLY through Heart Crystals. MOBS can still take your hearts away if they kill you though, UNLESS you have that option disabled.");
        this.playersGainHeartsifKillednoHeart = buildBoolean(builder, "Players Gain Hearts From No Heart Players:", false, "This value determines if a player should still earn hearts from a player they killed even if the player doesn't have hearts to spare. EX: MinimumHeartHave");

        builder.pop();
        builder.comment("This category will hold the maximums for certain values");
        builder.push("Maximums");
        this.maximumamountofheartsGainable = buildInt(builder, "Maximum Amount of Health/Hitpoints a Player can get:", -1, -1, Integer.MAX_VALUE, "This value makes a limit SET after your Starting HitPoint Difference for how many hit points/hearts a player can get. 2 hit points = 1 heart. Set this to less than 1 to disable the feature.");
        this.maximumamountofheartsLoseable = buildInt(builder, "Maximum Amount Of Health/Hitpoints a Player can Lose:", -1, -1, Integer.MAX_VALUE, "This value makes a limit set on how many hit points/hearts a player can lose, this value is actually set depending on the Starting Health Difference. EX: Starting Health Difference - MinimumHeartHave. Set this to less than 0 to disable the feature.");
        this.tellPlayersIfReachedMaxHearts = buildBoolean(builder, "Tell Players if They Have Reached max Hearts:", true, "When a player has reached max hearts or attempt to go higher than the max, if this value is true, a message will let them know indicating they cannot go higher.");

        builder.pop();
        builder.comment("This category holds values related to commands.");
        builder.push("Commands");
        this.tellPlayersIfHitPointChanged = buildBoolean(builder, "Tell Players if Their HitPoint Difference Changed:", true, "This just makes it so when an admin changed a person's hitpoints, this value would determine if the game should tell the person in chat that their hitpoints was changed.");
        builder.push("Withdrawing");
        this.advancementUsedForWithdrawing = buildString(builder, "The Advancement Needed to Unlock Withdrawing:", "lifesteal:lifesteal/get_heart_crystal", "This value determines the advancement used to unlock withdrawing. You would find the advancement you want to use by using the ID of the advancement which is found with the /advancement command. If the value is empty, withdraw will be unlocked automatically.");
        this.textUsedForRequirementOnWithdrawing = buildString(builder, "The Text Shown When Withdrawing Isn't Unlocked:", "You need to at least have gotten one heart crystal in this world to withdraw", "This value determines what text will pop up when a player hasn't unlocked withdrawing. If this value is empty, no text will pop up.");

        builder.pop();
        builder.pop();
        builder.comment("This category holds values that don't fit in other categories OR are not made for gameplay usage.");
        builder.push("Misc/Fun");
        this.shouldAllMobsGiveHearts = buildBoolean(builder, "Killing any Mobs Gives Hearts:", false, "When this is false, you can only gain hearts from killing players. Otherwise, any mob will give you hearts.");
    }

    private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, int defaultValue, int min, int max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    private static ForgeConfigSpec.DoubleValue buildDouble(ForgeConfigSpec.Builder builder, String name, double defaultValue, double min, double max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    private static ForgeConfigSpec.ConfigValue buildString(ForgeConfigSpec.Builder builder, String name, String defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

    private static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String name, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

}