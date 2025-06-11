package mc.mian.lifesteal.client;

import mc.mian.lifesteal.common.block.LSBlocks;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ReviveBeaconMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;

    public ReviveBeaconMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ReviveBeaconMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access){
        super(MenuType.BEACON, containerId);
        this.access = access;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, LSBlocks.REVIVE_BEACON.get());
    }

    static class PaymentSlot extends Slot {
        public PaymentSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        public boolean mayPlace(ItemStack stack) {
            return stack.is(ItemTags.BEACON_PAYMENT_ITEMS);
        }

        public int getMaxStackSize() {
            return 1;
        }
    }
}
