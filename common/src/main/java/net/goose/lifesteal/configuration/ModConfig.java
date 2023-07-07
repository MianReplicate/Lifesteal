package net.goose.lifesteal.configuration;

import net.minecraft.commands.Commands;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;

public class ModConfig {

    public final ForgeConfigSpec.IntValue startingHeartDifference;
    public final ForgeConfigSpec.BooleanValue shouldAllMobsGiveHearts;
    public final ForgeConfigSpec.BooleanValue loseHeartsWhenKilledByPlayer;
    public final ForgeConfigSpec.BooleanValue loseHeartsWhenKilledByMob;
    public final ForgeConfigSpec.BooleanValue loseHeartsWhenKilledByEnvironment;
    public final ForgeConfigSpec.IntValue amountOfHealthLostUponLoss;
    public final ForgeConfigSpec.IntValue maximumamountofhitpointsGainable;
    public final ForgeConfigSpec.IntValue maximumamountofhitpointsLoseable;
    public final ForgeConfigSpec.BooleanValue disableLifesteal;
    public final ForgeConfigSpec.BooleanValue preventFromUsingCrystalIfMax;
    public final ForgeConfigSpec.BooleanValue preventFromUsingCoreIfMax;
    public final ForgeConfigSpec.BooleanValue disableHeartCrystals;
    public final ForgeConfigSpec.BooleanValue disableUnnaturalHeartCrystals;
    public final ForgeConfigSpec.BooleanValue disableHeartCores;
    public final ForgeConfigSpec.BooleanValue disableReviveCrystals;
    public final ForgeConfigSpec.BooleanValue playersGainHeartsifKillednoHeart;
    public final ForgeConfigSpec.IntValue heartCrystalAmountGain;
    public final ForgeConfigSpec.BooleanValue crystalInstantUse;
    public final ForgeConfigSpec.DoubleValue heartCoreHeal;
    public final ForgeConfigSpec.BooleanValue coreInstantUse;
    public final ForgeConfigSpec.BooleanValue disableStatusEffects;
    public final ForgeConfigSpec.BooleanValue disableLightningEffect;
    public final ForgeConfigSpec.BooleanValue customHeartDifferenceWhenRevived;
    public final ForgeConfigSpec.IntValue startingHeartDifferenceFromCrystal;
    public final ForgeConfigSpec.BooleanValue tellPlayersIfHitPointChanged;
    public final ForgeConfigSpec.ConfigValue advancementUsedForWithdrawing;
    public final ForgeConfigSpec.ConfigValue textUsedForRequirementOnWithdrawing;
    public final ForgeConfigSpec.BooleanValue tellPlayersIfReachedMaxHearts;
    public final ForgeConfigSpec.BooleanValue silentlyRevivePlayer;
    public final ForgeConfigSpec.BooleanValue playersSpawnHeadUponDeath;
    public final ForgeConfigSpec.BooleanValue playerDropsHeartCrystalWhenKilled;
    public final ForgeConfigSpec.BooleanValue playerDropsHeartCrystalWhenKillerHasMax;
    public final ForgeConfigSpec.BooleanValue uponDeathBanned;
    public final ForgeConfigSpec.IntValue permissionLevelForWithdraw;
    public final ForgeConfigSpec.IntValue permissionLevelForSettingHitPoints;
    public final ForgeConfigSpec.IntValue permissionLevelForGettingHitPoints;
    public final ForgeConfigSpec.BooleanValue unbreakableReviveHeads;

    public ModConfig(final ForgeConfigSpec.Builder builder) {
        builder.comment("IMPORTANT NOTE: 2 Hitpoints = 1 Heart");
        builder.comment("We recommend editting the config BEFORE starting Minecraft. Most configs will work fine, even if changed in-game, but some require restarting Minecraft to work correctly.");
        builder.comment("This category holds general values that most people will want to change.");
        builder.push("General Settings");
        this.startingHeartDifference = buildInt(builder, "Starting additional Hitpoints:", 0, -19, Integer.MAX_VALUE, "Determines the number of additional Hitpoints beyond 20 you should start with. (2 hitpoints = 1 heart)");
        this.amountOfHealthLostUponLoss = buildInt(builder, "Number of HitPoints lost/given upon death/kill:", 2, 1, Integer.MAX_VALUE, "How many Hitpoints are lost/gained upon death or being killed.");
        this.playersSpawnHeadUponDeath = buildBoolean(builder, "Spawn Revive Head upon player elimination:", true, "Determines if Revive Heads should spawn upon a player being eliminated (no more hearts). NOTE: Always false in Singleplayer");
        this.uponDeathBanned = buildBoolean(builder, "Ban players upon losing all hearts:", true, "Determines if players who have lost all hearts get banned, or simply go into Spectator mode. Singleplayer will always go into Spectator");
        this.playerDropsHeartCrystalWhenKilled = buildBoolean(builder, "Players drop a Heart Crystal on death:", false, "Determines if players should drop a heart crystal upon death.");

        builder.pop();

        builder.comment("Settings for when/how hearts are lost");
        builder.push("Losing Hearts");
        this.loseHeartsWhenKilledByPlayer = buildBoolean(builder, "Lose heart(s) when killed by a player:", true, "Determines if players should lose hearts when killed by another player.");
        this.loseHeartsWhenKilledByMob = buildBoolean(builder, "Lose heart(s) when killed by a mob:", true, "Determines if players should lose hearts when killed by mobs");
        this.loseHeartsWhenKilledByEnvironment = buildBoolean(builder, "Lose heart(s) when killed by the environment:", true, "Determines if players lose hearts when killed by the environment. (Lava, fall damage, etc)");
        builder.pop();

        builder.comment("Settings for Items and Blocks");
        builder.push("Items and Blocks");
        builder.push("Heart Cores");
        this.disableHeartCores = buildBoolean(builder, "Disable Heart Cores:", false, "Determines if Heart Cores are disabled.");
        this.heartCoreHeal = buildDouble(builder, "Percentage of max health recovered from Heart Cores", 0.33, 0.01, 1, "The percentage of health recovered when a Heart Core is used.");
        this.preventFromUsingCoreIfMax = buildBoolean(builder, "Prevent players at max hp from using Heart Cores:", true, "Determines if Heart Cores can be used by players already at maximum health");
        this.coreInstantUse = buildBoolean(builder, "Instantly use Heart Cores:", false, "Determines if heart cores should be used instantly, or eaten.");

        builder.pop();
        builder.push("Heart Crystals");
        this.disableHeartCrystals = buildBoolean(builder, "Disable Heart Crystals:", false, "Determines if Natural Heart Cores should be disabled. (Doesn't affect Unnatural Heart Cores)");
        this.disableUnnaturalHeartCrystals = buildBoolean(builder, "Disable Unnatural Heart Crystals:", false, "Determines if Unnatural Heart Cores should be disabled. (Doesn't affect Natural Heart Cores)");
        this.heartCrystalAmountGain = buildInt(builder, "Number of Hitpoints Heart Crystal(s) Permanently Give:", 2, 1, Integer.MAX_VALUE, "Determines how many Hitpoints are given when a Heart Crystal is used. (2 Hitpoints = 1 Heart)");
        this.preventFromUsingCrystalIfMax = buildBoolean(builder, "Prevent players at max Hitpoints from using Heart Crystals:", true, "Determines if Heart Crystals can be used by players at maximum health (if a max is set)");
        this.crystalInstantUse = buildBoolean(builder, "Instantly use Heart Crystals:", false, "Determines if Heart Cores should be used instantly or eaten.");

        builder.pop();
        builder.push("Revive Crystals");
        this.disableReviveCrystals = buildBoolean(builder, "Disable Revive Crystals:", false, "Determines if Revive Crystals should be disabled. (Always disabled in Singleplayer)");
        this.silentlyRevivePlayer = buildBoolean(builder, "Silently Revive Players:", false, "Determines if a chat message should NOT be sent when a player is revived.");
        this.disableLightningEffect = buildBoolean(builder, "Disable Lightning Effect:", false, "Determines if lightning should be summoned upon player revive.");
        this.disableStatusEffects = buildBoolean(builder, "Disable Status Effects:", false, "Determines if revived players receiving status effects should be disabled. (Leave enabled if you plan to keep the lightning effect)");
        this.customHeartDifferenceWhenRevived = buildBoolean(builder, "Use custom additional Hitpoint value:", false, "Determines if the custom Hitpoint value should be used for revived players.");
        this.startingHeartDifferenceFromCrystal = buildInt(builder, "Number of additional Hitpoints upon revival:", 0, -19, Integer.MAX_VALUE, "Determines the number of additional Hitpoints revived players should have. (2 Hitpoints = 1 Heart, use a negative number to start them with <20)");

        builder.pop();
        builder.push("Revive Heads");
        this.unbreakableReviveHeads = buildBoolean(builder, "Indestructible Revive Heads:", false, "Determines if Revive Heads should be indestructible. WARNING: This setting is highly destructive, and makes Revive Heads truly un-removable, even with /setblock and creative mode!");

        builder.pop();
        builder.pop();
        builder.comment("Lifesteal settings.");
        builder.push("Lifesteal Related");
        this.disableLifesteal = buildBoolean(builder, "Disable Lifesteal:", false, "Disables gaining hearts from killing players. (Does not affect losing hearts)");
        this.playersGainHeartsifKillednoHeart = buildBoolean(builder, "Players gain Hearts from killing players at minimum HP:", false, "Determines if killing a player with the minimum hp will still give heart(s)");

        builder.pop();
        builder.comment("Contains various maximum values.");
        builder.push("Maximums");
        this.maximumamountofhitpointsGainable = buildInt(builder, "Maximum number of additional Hitpoints:", -1, -1, Integer.MAX_VALUE, "Determines the maximum Hitpoints beyond 20. (Remember, 2 Hitpoints = 1 Heart.) Set to -1 to disable.");
        this.maximumamountofhitpointsLoseable = buildInt(builder, "Maximum number of Hitpoints lost before elimination:", -1, -1, Integer.MAX_VALUE, "Determines the maximum number of Hitpoints a player can lose before being eliminated. Set to -1 to disable.");
        this.tellPlayersIfReachedMaxHearts = buildBoolean(builder, "Notify players if they have max HP:", true, "Determines if players attempting to use a Heart Crystal should be notified if they are already at the maximum.");
        this.playerDropsHeartCrystalWhenKillerHasMax = buildBoolean(builder, "Players drop a Heart Crystal when killer has max HP", false, "Determines players should drop a Heart Crystal even if the killer has the maximum HP. NOTE: This requires Maximum Hitpoints to be enabled.");

        builder.pop();
        builder.comment("Settings related to commands. Permission Levels range from 0 to 4, 0: Everyone, 1: Moderators, 2: Gamemasters, 3: Admins, 4: Owners");
        builder.push("Commands");
        this.tellPlayersIfHitPointChanged = buildBoolean(builder, "Notify players if their HP is changed:", true, "Notify players when their HP has been changed by an admin.");
        builder.push("Withdrawing");
        this.advancementUsedForWithdrawing = buildString(builder, "Advancement needed to unlock Withdrawing:", "lifesteal:get_heart_crystal", "Determines which achievement must be obtained before the player may use the withdraw command. Leave empty to have unlocked by default. You can use /advancement to figure out achievement IDs.");
        this.textUsedForRequirementOnWithdrawing = buildString(builder, "Text to display if Withdrawing isn't unlocked:", "You need to at least have gotten one heart crystal in this world to withdraw", "Determines the message sent to players who try using the withdraw command before they have unlocked it.");
        this.permissionLevelForWithdraw = buildInt(builder, "Permission Level:", Commands.LEVEL_ALL, Commands.LEVEL_ALL, Commands.LEVEL_OWNERS, null);
        builder.pop();
        builder.push("Set-Hitpoints");
        this.permissionLevelForSettingHitPoints = buildInt(builder, "Permission Level:", Commands.LEVEL_GAMEMASTERS, Commands.LEVEL_ALL, Commands.LEVEL_OWNERS, null);
        builder.pop();
        builder.push("Get-Hitpoints");
        this.permissionLevelForGettingHitPoints = buildInt(builder, "Permission Level:", Commands.LEVEL_GAMEMASTERS, Commands.LEVEL_ALL, Commands.LEVEL_OWNERS, null);
        builder.pop();

        builder.pop();
        builder.comment("Settings which don't fit anywhere else, or add non-standard gameplay mechanics");
        builder.push("Misc/Fun");
        this.shouldAllMobsGiveHearts = buildBoolean(builder, "Killing mobs gives hearts:", false, "Determines if killing mobs will give Hearts.");
        builder.pop();
    }

    private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, int defaultValue, int min, int max, @Nullable String comment) {
        return comment == null ? builder.translation(name).defineInRange(name, defaultValue, min, max) : builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
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

    private static ForgeConfigSpec.EnumValue buildEnum(ForgeConfigSpec.Builder builder, String name, Enum defaultValue, String comment) {
        return builder.comment(comment).translation(name).defineEnum(name, defaultValue);
    }

}