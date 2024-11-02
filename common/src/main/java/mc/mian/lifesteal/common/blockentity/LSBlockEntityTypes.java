package mc.mian.lifesteal.common.blockentity;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.registry.DeferredRegistry;
import mc.mian.lifesteal.registry.RegistrySupplier;
import mc.mian.lifesteal.util.LSConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import java.util.Set;

public class LSBlockEntityTypes {
    public static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegistry.create(LSConstants.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<SkullBlockEntity>> EXPANDED_SKULL =
            BLOCK_ENTITY_TYPES.register("expanded_skull", () -> new BlockEntityType(
                            SkullBlockEntity::new,
                                    Set.of(Blocks.SKELETON_SKULL,
                                            Blocks.SKELETON_WALL_SKULL,
                                            Blocks.CREEPER_HEAD,
                                            Blocks.CREEPER_WALL_HEAD,
                                            Blocks.DRAGON_HEAD,
                                            Blocks.DRAGON_WALL_HEAD,
                                            Blocks.ZOMBIE_HEAD,
                                            Blocks.ZOMBIE_WALL_HEAD,
                                            Blocks.WITHER_SKELETON_SKULL,
                                            Blocks.WITHER_SKELETON_WALL_SKULL,
                                            Blocks.PLAYER_HEAD,
                                            Blocks.PLAYER_WALL_HEAD,
                                            Blocks.PIGLIN_HEAD,
                                            Blocks.PIGLIN_WALL_HEAD,
                                            LSBlocks.REVIVE_HEAD.get(),
                                            LSBlocks.REVIVE_WALL_HEAD.get())));

    public static void register() {
        LSConstants.LOGGER.debug("Registering ModBlockEntityTypes for " + LSConstants.MOD_ID);
        BLOCK_ENTITY_TYPES.register();
    }
}
