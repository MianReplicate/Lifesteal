package net.goose.lifesteal.common.blockentity;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.blockentity.custom.ReviveSkullBlockEntity;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
    public static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegistry.create(LifeSteal.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<ReviveSkullBlockEntity>> REVIVE_HEAD =
            BLOCK_ENTITY_TYPES.register("revive_head", () -> BlockEntityType.Builder.of(ReviveSkullBlockEntity::new, ModBlocks.REVIVE_HEAD.get(), ModBlocks.REVIVE_WALL_HEAD.get()).build(null));

    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModBlockEntityTypes for " + LifeSteal.MOD_ID);
        BLOCK_ENTITY_TYPES.register();
    }
}
