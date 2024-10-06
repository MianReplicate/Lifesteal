package mc.mian.lifesteal.data.neoforge;

import mc.mian.lifesteal.api.ILifestealData;
import mc.mian.lifesteal.data.LSData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class LSDataImpl {

    public static Optional<ILifestealData> get(final Entity entity) {
        return Optional.ofNullable(entity.getCapability(LSCapabilities.LIFESTEAL_DATA));
    }

    private static AttachmentType<?> getAttachmentFromLocation(ResourceLocation data){
        for (int i = 0; i < LSDataAttachments.ATTACHMENT_TYPES.getEntries().stream().count(); i++) {
            DeferredHolder<AttachmentType<?>, ? extends AttachmentType<?>> attachment = LSDataAttachments.ATTACHMENT_TYPES.getEntries().stream().toList().get(i);
            if(attachment.is(data)){
                return attachment.get();
            }
        }
        return null;
    }

    public static Collection<ResourceLocation> getKeys(LSData lifestealData){
        List<ResourceLocation> collection = List.of();
        LSDataAttachments.ATTACHMENT_TYPES.getEntries().forEach(attachmentTypeHolder -> {
            if(lifestealData.getLivingEntity().getExistingData(attachmentTypeHolder.get()).isPresent()){
                collection.add(attachmentTypeHolder.getId());
            }
        });
        return collection;
    }

    public static <T> T getValue(LSData lifestealData, ResourceLocation data){
        return (T) lifestealData.getLivingEntity().getData(getAttachmentFromLocation(data));
    }

    public static <T> void setValue(LSData lifestealData, ResourceLocation data, T value){
        lifestealData.getLivingEntity().setData((AttachmentType<? super Object>) getAttachmentFromLocation(data), value);
    }
}