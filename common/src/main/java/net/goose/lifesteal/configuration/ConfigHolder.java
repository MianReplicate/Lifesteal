package net.goose.lifesteal.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHolder {

    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfig SERVER;

    static {
        {
            final Pair<ModConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ModConfig::new);
            SERVER = specPair.getLeft();
            SERVER_SPEC = specPair.getRight();
        }
    }

}
