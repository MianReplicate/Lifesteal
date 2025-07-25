package mc.mian.lifesteal.mixin;

import mc.mian.lifesteal.api.ILSRetrieve;
import mc.mian.lifesteal.common.data.LSData;
import mc.mian.lifesteal.data.FabricLSData;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LSDataMixin implements ILSRetrieve {
    @Unique
    private FabricLSData lifesteal$lsData;

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(EntityType<?> entityType, Level level, CallbackInfo ci){
        this.lifesteal$lsData = new FabricLSData((LivingEntity)(Object)this);
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    public void addLSData(ValueOutput output, CallbackInfo ci){
        this.lifesteal$lsData.serializeOutput(output);
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData")
    public void readLSData(ValueInput input, CallbackInfo ci){
        lifesteal$lsData.deserializeInput(input);
    }

    @Override
    public LSData lifesteal_1_21$getData() {
        return this.lifesteal$lsData;
    }
}
