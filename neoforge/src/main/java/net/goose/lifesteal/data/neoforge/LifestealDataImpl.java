package net.goose.lifesteal.data.neoforge;

import net.goose.lifesteal.api.ILifestealData;
import net.goose.lifesteal.data.LifestealData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LifestealDataImpl {

    public static Optional<ILifestealData> get(final Entity entity) {
        return Optional.ofNullable(entity.getCapability(ModCapabilities.LIFESTEAL_DATA));
    }

    private static AttachmentType<?> getAttachmentFromLocation(ResourceLocation data){
        for (int i = 0; i < ModDataAttachments.ATTACHMENT_TYPES.getEntries().stream().count(); i++) {
            DeferredHolder<AttachmentType<?>, ? extends AttachmentType<?>> attachment = ModDataAttachments.ATTACHMENT_TYPES.getEntries().stream().toList().get(i);
            if(attachment.is(data)){
                return attachment.get();
            }
        }
        return null;
    }

    public static Collection<ResourceLocation> getKeys(LifestealData lifestealData){
        List<ResourceLocation> collection = List.of();
        ModDataAttachments.ATTACHMENT_TYPES.getEntries().forEach(attachmentTypeHolder -> {
            if(lifestealData.getLivingEntity().getExistingData(attachmentTypeHolder.get()).isPresent()){
                collection.add(attachmentTypeHolder.getId());
            }
        });
        return collection;
    }

    public static <T> T getValue(LifestealData lifestealData, ResourceLocation data){
        return (T) lifestealData.getLivingEntity().getData(getAttachmentFromLocation(data));
    }

    public static <T> void setValue(LifestealData lifestealData, ResourceLocation data, T value){
        lifestealData.getLivingEntity().setData((AttachmentType<? super Object>) getAttachmentFromLocation(data), value);
    }
}