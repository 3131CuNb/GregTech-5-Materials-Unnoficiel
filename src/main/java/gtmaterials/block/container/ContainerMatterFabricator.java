package gtmaterials.block.container;

import gtmaterials.block.tileentity.TileEntityMatterFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMatterFabricator extends Container {
   private TileEntityMatterFabricator tileentity;

   public ContainerMatterFabricator(TileEntityMatterFabricator tileentity, EntityPlayer player) {
      this.tileentity = tileentity;
      this.addSlotToContainer(new Slot(this.tileentity, 0, 80, 30) {
         public boolean isItemValid(ItemStack itemstack) {
            return false;
         }
      });
      this.addSlotToContainer(new Slot(this.tileentity, 1, 152, 64));

      for(int h = 0; h < 3; ++h) {
         for(int w = 0; w < 9; ++w) {
            this.addSlotToContainer(new Slot(player.inventory, w + h * 9 + 9, 8 + w * 18, 84 + h * 18));
         }
      }

      for(int w = 0; w < 9; ++w) {
         this.addSlotToContainer(new Slot(player.inventory, w, 8 + w * 18, 142));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
      Slot clickedIndex = (Slot)this.inventorySlots.get(slot);
      if(clickedIndex != null && clickedIndex.getHasStack()) {
         ItemStack source = clickedIndex.getStack();
         ItemStack copy = source.copy();
         if(slot >= 0 && slot < 2) {
            if(!this.mergeItemStack(source, 2, 38, true)) {
               return null;
            }

            clickedIndex.onSlotChange(source, copy);
         } else if(slot >= 2 && slot < 29) {
            if(!this.mergeItemStack(source, 29, 38, false)) {
               return null;
            }

            clickedIndex.onSlotChange(source, copy);
         } else if(slot >= 29 && slot < 38) {
            if(!this.mergeItemStack(source, 2, 29, false)) {
               return null;
            }

            clickedIndex.onSlotChange(source, copy);
         }

         if(source.stackSize == 0) {
            clickedIndex.putStack((ItemStack)null);
         } else {
            clickedIndex.onSlotChanged();
         }

         if(source.stackSize == copy.stackSize) {
            return null;
         } else {
            clickedIndex.onPickupFromSlot(player, source);
            return copy;
         }
      } else {
         return null;
      }
   }

   public boolean canInteractWith(EntityPlayer player) {
      return true;
   }
}
