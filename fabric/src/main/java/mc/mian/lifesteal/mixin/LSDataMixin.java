package mc.mian.lifesteal.mixin;

import mc.mian.lifesteal.api.ILSRetrieve;
import mc.mian.lifesteal.data.LSData;
import mc.mian.lifesteal.data.fabric.LSDataImpl;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LSDataMixin implements ILSRetrieve {
    private LSDataImpl lsData;

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(EntityType entityType, Level level, CallbackInfo ci){
        this.lsData = new LSDataImpl((LivingEntity)(Object)this);
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    public void addLSData(CompoundTag compound, CallbackInfo ci){
        this.lsData.writeToNbt(compound);
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData")
    public void readLSData(CompoundTag compound, CallbackInfo ci){
        this.lsData.deserializeNBT(compound.getCompound(LSConstants.LIFESTEAL_DATA.getPath()));
    }

    @Override
    public LSData lifesteal_1_21$getData() {
        return this.lsData;
    }
}
