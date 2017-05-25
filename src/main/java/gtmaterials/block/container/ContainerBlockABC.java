package gtmaterials.block.container;

import gtmaterials.block.tileentity.TileEntityBlockABC;
import gtmaterials.block.tileentity.TileEntityMatterFabricator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBlockABC extends Container {
   private TileEntityBlockABC tileentity;

   public ContainerBlockABC(TileEntityBlockABC tileentity, EntityPlayer player) {
      this.tileentity = tileentity;
      this.addSlotToContainer(new Slot(this.tileentity, 0, 80, 30));
      //this.addSlotToContainer(new Slot(this.tileentity, 1, 152, 64));

      for(int h = 0; h < 3; ++h) {
         for(int w = 0; w < 9; ++w) {
            this.addSlotToContainer(new Slot(player.inventory, w + h * 9 + 9, 8 + w * 18, 84 + h * 18));
         }
      }

      for(int w = 0; w < 9; ++w) {
         this.addSlotToContainer(new Slot(player.inventory, w, 8 + w * 18, 142));
      }

   }
   
   public boolean isItemValid(ItemStack itemstack) {
       return true;
    }

   public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
         return null;
      }
   

   public boolean canInteractWith(EntityPlayer player) {
      return true;
   }
}
