package mc.mian.lifesteal.configuration;


import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHolder {

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final LSConfig SERVER;

    static {
        {
            final Pair<LSConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(LSConfig::new);
            SERVER = specPair.getLeft();
            SERVER_SPEC = specPair.getRight();
        }
    }

}
