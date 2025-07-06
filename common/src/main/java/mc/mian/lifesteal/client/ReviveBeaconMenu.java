package mc.mian.lifesteal.client;

import mc.mian.lifesteal.common.block.LSBlocks;
import mc.mian.lifesteal.common.item.LSItems;
import mc.mian.lifesteal.common.menu.LSMenuTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class ReviveBeaconMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final PaymentSlot paymentSlot;
    private final Container beacon = new SimpleContainer(1) {
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return stack.is(LSItems.HEART_CRYSTAL.get());
        }

        public int getMaxStackSize() {
            return 1;
        }
    };;

    public ReviveBeaconMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ReviveBeaconMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access){
        super(LSMenuTypes.REVIVE_BEACON.get(), containerId);
        this.access = access;

        this.paymentSlot = new PaymentSlot(this.beacon, 0, 136, 110);
        this.addSlot(paymentSlot);
        this.addStandardInventorySlots(playerInventory, 36, 137);
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
            return stack.is(LSItems.HEART_CRYSTAL.get());
        }

        public int getMaxStackSize() {
            return 1;
        }
    }
}
