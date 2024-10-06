package mc.mian.lifesteal.data.neoforge;

import com.mojang.serialization.Codec;
import mc.mian.lifesteal.LifeSteal;
import mc.mian.lifesteal.util.LSConstants;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class LSDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, LSConstants.MOD_ID);
    public static final Supplier<AttachmentType<Integer>> HEALTH_DIFFERENCE = ATTACHMENT_TYPES.register(
            LSConstants.HEALTH_DIFFERENCE.getPath(), () -> AttachmentType.builder(() -> LifeSteal.config.startingHealthDifference.get()).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Long>> TIME_KILLED = ATTACHMENT_TYPES.register(
            LSConstants.TIME_KILLED.getPath(), () -> AttachmentType.builder(() -> 0L).serialize(Codec.LONG).copyOnDeath().build());
}
