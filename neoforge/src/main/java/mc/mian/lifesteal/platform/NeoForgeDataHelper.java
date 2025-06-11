package mc.mian.lifesteal.platform;

import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.data.LSDataAttachments;
import mc.mian.lifesteal.data.NeoForgeLSData;
import mc.mian.lifesteal.platform.services.IDataHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class NeoForgeDataHelper implements IDataHelper {
    public Optional<LSData> get(final LivingEntity entity) {
        return NeoForgeLSData.get(entity).map(iLSData -> (LSData) iLSData);
    }

    private AttachmentType<?> getAttachmentFromLocation(ResourceLocation data){
        for (int i = 0; i < LSDataAttachments.ATTACHMENT_TYPES.getEntries().stream().count(); i++) {
            DeferredHolder<AttachmentType<?>, ? extends AttachmentType<?>> attachment = LSDataAttachments.ATTACHMENT_TYPES.getEntries().stream().toList().get(i);
            if(attachment.is(data)){
                return attachment.get();
            }
        }
        return null;
    }

    public Collection<ResourceLocation> getKeys(LSData lifestealData){
        List<ResourceLocation> collection = List.of();
        LSDataAttachments.ATTACHMENT_TYPES.getEntries().forEach(attachmentTypeHolder -> {
            if(lifestealData.getLivingEntity().getExistingData(attachmentTypeHolder.get()).isPresent()){
                collection.add(attachmentTypeHolder.getId());
            }
        });
        return collection;
    }

    public <T> T getValue(LSData lifestealData, ResourceLocation data){
        return (T) lifestealData.getLivingEntity().getData(getAttachmentFromLocation(data));
    }

    public <T> void setValue(LSData lifestealData, ResourceLocation data, T value){
        lifestealData.getLivingEntity().setData((AttachmentType<? super Object>) getAttachmentFromLocation(data), value);
    }

    public CompoundTag setLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, CompoundTag> function){
        function.apply((CompoundTag) tag.get("neoforge:attachments"), "lifesteal:" + key);
        return tag;
    }

    public <T> T getLifestealDataFromTag(CompoundTag tag, String key, BiFunction<CompoundTag, String, T> function){
        return function.apply((CompoundTag) tag.get("neoforge:attachments"), "lifesteal:" + key);
    }
}
