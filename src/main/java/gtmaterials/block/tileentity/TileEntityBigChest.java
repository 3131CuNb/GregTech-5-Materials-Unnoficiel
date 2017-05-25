package gtmaterials.block.tileentity;

import gtmaterials.GTMUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBigChest extends TileEntity implements IInventory {
   public static final int storageCount = 587520;
   private ItemStack[] chestContents = new ItemStack[587520];

   public void updateEntity() {
   }

   public void readFromNBT(NBTTagCompound p_readFromNBT_1_) {
      super.readFromNBT(p_readFromNBT_1_);
      NBTTagList localNBTTagList = p_readFromNBT_1_.getTagList("BigChest", 10);
      this.chestContents = new ItemStack[this.getSizeInventory()];

      for(int i = 0; i < localNBTTagList.tagCount(); ++i) {
         NBTTagCompound localNBTTagCompound = localNBTTagList.getCompoundTagAt(i);
         int j = localNBTTagCompound.getInteger("Slot");
         if(j >= 0 && j < this.chestContents.length) {
            this.chestContents[j] = GTMUtil.loadItemStackFromNBT(localNBTTagCompound);
         }
      }

   }

   public void writeToNBT(NBTTagCompound p_writeToNBT_1_) {
      super.writeToNBT(p_writeToNBT_1_);
      NBTTagList localNBTTagList = new NBTTagList();

      for(int i = 0; i < this.chestContents.length; ++i) {
         if(this.chestContents[i] != null) {
            NBTTagCompound localNBTTagCompound = new NBTTagCompound();
            localNBTTagCompound.setInteger("Slot", i);
            GTMUtil.writeToNBT(this.chestContents[i], localNBTTagCompound);
            localNBTTagList.appendTag(localNBTTagCompound);
         }
      }

      p_writeToNBT_1_.setTag("BigChest", localNBTTagList);
   }

   public void closeInventory() {
   }

   public ItemStack decrStackSize(int p_decrStackSize_1_, int p_decrStackSize_2_) {
      if(this.chestContents[p_decrStackSize_1_] != null) {
         if(this.chestContents[p_decrStackSize_1_].stackSize <= p_decrStackSize_2_) {
            ItemStack localItemStack = this.chestContents[p_decrStackSize_1_];
            this.chestContents[p_decrStackSize_1_] = null;
            this.markDirty();
            return localItemStack;
         } else {
            ItemStack localItemStack = this.chestContents[p_decrStackSize_1_].splitStack(p_decrStackSize_2_);
            if(this.chestContents[p_decrStackSize_1_].stackSize == 0) {
               this.chestContents[p_decrStackSize_1_] = null;
            }

            this.markDirty();
            return localItemStack;
         }
      } else {
         return null;
      }
   }

   public String getInventoryName() {
      return "Big Chest";
   }

   public int getInventoryStackLimit() {
      return Integer.MAX_VALUE;
   }

   public int getSizeInventory() {
      return 587520;
   }

   public ItemStack getStackInSlot(int arg0) {
      return this.chestContents[arg0];
   }

   public ItemStack getStackInSlotOnClosing(int p_getStackInSlotOnClosing_1_) {
      if(this.chestContents[p_getStackInSlotOnClosing_1_] != null) {
         ItemStack localItemStack = this.chestContents[p_getStackInSlotOnClosing_1_];
         this.chestContents[p_getStackInSlotOnClosing_1_] = null;
         return localItemStack;
      } else {
         return null;
      }
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public boolean isItemValidForSlot(int arg0, ItemStack arg1) {
      return true;
   }

   public boolean isUseableByPlayer(EntityPlayer p_isUseableByPlayer_1_) {
      return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this?false:p_isUseableByPlayer_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
   }

   public void openInventory() {
   }

   public void setInventorySlotContents(int slot, ItemStack itemstack) {
      this.chestContents[slot] = itemstack;
      if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
         itemstack.stackSize = this.getInventoryStackLimit();
      }

      this.markDirty();
   }
}
