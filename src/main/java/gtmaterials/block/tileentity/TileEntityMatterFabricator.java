package gtmaterials.block.tileentity;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gtmaterials.GTMItem;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.Info;
import ic2.core.Ic2Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMatterFabricator extends TileEntity implements IEnergySink, IInventory {
   private long Max;
   public long energyStored;
   protected boolean addedToEnet;
   private int meta;
   private Item item;
   public int tier;
   private ItemStack field;
   private ItemStack scrap;

   public TileEntityMatterFabricator() {
      this(-1);
   }

   public TileEntityMatterFabricator(int meta) {
      this.Max = 1000000L;
      this.meta = -1;
      this.item = null;
      this.tier = 1;
      this.meta = meta;
      this.item = GTMItem.Matter;
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.energyStored = nbt.getLong("energy");
      this.meta = nbt.getInteger("meta");
      this.item = Item.getItemById(nbt.getInteger("item"));
      this.field = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbt.getTag("field"));
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.setDouble("energy", (double)this.energyStored);
      nbt.setInteger("meta", this.meta);
      nbt.setInteger("item", Item.getIdFromItem(this.item));
      NBTTagCompound itemdata = new NBTTagCompound();
      if(this.field != null) {
         this.field.writeToNBT(itemdata);
      }

      nbt.setTag("field", itemdata);
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();
      this.writeToNBT(nbtTagCompound);
      return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      this.readFromNBT(pkt.func_148857_g());
   }

   public void updateEntity() {
      if(!this.addedToEnet) {
         this.onLoaded();
      }

      if(this.item != null && this.meta >= 0) {
         this.matter(this.item, this.meta);
      }

      if(this.scrap != null && this.scrap.isItemEqual(Ic2Items.scrap)) {
         this.scrap(1);
      } else if(this.scrap != null && this.scrap.isItemEqual(Ic2Items.scrapBox)) {
         NBTTagCompound nbt = this.scrap.getTagCompound();
         if(nbt != null && nbt.hasKey("update")) {
            this.scrap(18);
         } else {
            this.scrap(9);
         }
      }

      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
   }

   protected void matter(Item item, int ID) {
      ItemStack matter = new ItemStack(item, 1, ID);
      boolean check = false;
      if(this.energyStored >= this.Max) {
         if(this.field == null) {
            this.setInventorySlotContents(0, matter);
            check = true;
         } else if(this.field.stackSize < 64) {
            ++this.field.stackSize;
            check = true;
         } else {
            check = false;
         }

         if(check) {
            this.energyStored -= this.Max;
         }
      }

   }

   private void scrap(int i) {
      i = i * 1000;
      if(this.energyStored > 0L && this.energyStored <= this.Max) {
         this.energyStored += (long)i;
         this.decrStackSize(1, 1);
      }

   }

   public void onLoaded() {
      if(!this.addedToEnet && !FMLCommonHandler.instance().getEffectiveSide().isClient() && Info.isIc2Available()) {
         MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
         this.addedToEnet = true;
      }

   }

   public void invalidate() {
      super.invalidate();
      this.onChunkUnload();
   }

   public void onChunkUnload() {
      if(this.addedToEnet && Info.isIc2Available()) {
         MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
         this.addedToEnet = false;
      }

   }

   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
      return true;
   }

   public double getDemandedEnergy() {
      return Math.max(0.0D, (double)(this.Max - this.energyStored));
   }

   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
      long free = this.Max - this.energyStored;
      long amounts = (long)amount;
      if(free >= amounts) {
         this.energyStored += amounts;
         return 0.0D;
      } else {
         this.energyStored = this.Max;
         return (double)(amounts - free);
      }
   }

   public int getSinkTier() {
      return this.tier;
   }

   public boolean find() {
      return this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) instanceof IGregTechTileEntity;
   }

   public void setMeta(int Meta) {
      this.meta = Meta;
   }
   public int getMeta() {
	      return this.meta;
	   }
   public void setItem(Item item) {
      this.item = item;
   }



   public Item getItem() {
      return this.item;
   }

   public long getEnergyStored() {
      return this.energyStored;
   }

   protected void setEnergyMaxed(long max) {
      this.Max = max;
   }

   public long getEnergyMaxed() {
      return this.Max;
   }

   public int getPercentage() {
      return (int)(this.energyStored * 100L / this.Max);
   }

   public int getSizeInventory() {
      return 1;
   }

   public ItemStack getStackInSlot(int slot) {
      switch(slot) {
      case 0:
         return this.field;
      case 1:
         return this.scrap;
      default:
         return null;
      }
   }

   public ItemStack decrStackSize(int slot, int size) {
      if(slot == 0) {
         if(this.field.stackSize <= size) {
            ItemStack local2 = this.field;
            this.field = null;
            return local2;
         }

         if(this.field.stackSize > size) {
            ItemStack local2 = this.field.splitStack(size);
            if(this.field.stackSize <= 0) {
               this.field = null;
            }

            return local2;
         }
      } else if(slot == 1) {
         if(this.scrap.stackSize <= size) {
            ItemStack local2 = this.scrap;
            this.scrap = null;
            return local2;
         }

         if(this.scrap.stackSize > size) {
            ItemStack local2 = this.scrap.splitStack(size);
            if(this.scrap.stackSize <= 0) {
               this.scrap = null;
            }

            return local2;
         }
      }

      return null;
   }

   public ItemStack getStackInSlotOnClosing(int slot) {
      return null;
   }

   public void setInventorySlotContents(int slot, ItemStack item) {
      if(slot == 0) {
         this.field = item;
      }

      if(slot == 1) {
         this.scrap = item;
      }

      if(item != null && item.stackSize > this.getInventoryStackLimit()) {
         item.stackSize = this.getInventoryStackLimit();
      }

   }

   public String getInventoryName() {
      return "Fabricator";
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer player) {
      return true;
   }

   public void openInventory() {
   }

   public void closeInventory() {
   }

   public boolean isItemValidForSlot(int slot, ItemStack item) {
      return true;
   }

   public static class TileEntityMachineFabricator extends TileEntityMatterFabricator {
      public TileEntityMachineFabricator() {
         this.setEnergyMaxed(100000000L);
         this.setItem((Item)null);
         this.tier = 3;
      }
   }

   public static class TileEntityMaterialFabricator extends TileEntityMatterFabricator {
      public TileEntityMaterialFabricator() {
         this.setEnergyMaxed(4000000L);
         this.setItem((Item)null);
         this.tier = 2;
      }
   }

   public static class TileEntityOreFabricator extends TileEntityMatterFabricator {
      public TileEntityOreFabricator() {
         this.setEnergyMaxed(4000000L);
         this.setItem((Item)null);
         this.tier = 2;
      }
   }
}
