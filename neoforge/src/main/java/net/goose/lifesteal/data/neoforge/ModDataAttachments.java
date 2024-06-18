package net.goose.lifesteal.data.neoforge;

import com.mojang.serialization.Codec;
import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.util.ModResources;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, LifeSteal.MOD_ID);
    public static final Supplier<AttachmentType<Integer>> HEALTH_DATA = ATTACHMENT_TYPES.register(
            ModResources.HEALTH_DATA.getPath(), () -> AttachmentType.builder(() -> LifeSteal.config.startingHealthDifference.get()).serialize(Codec.INT).copyOnDeath().build());
}
