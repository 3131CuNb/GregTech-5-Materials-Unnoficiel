package gtmaterials.block.tileentity;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.common.blocks.GT_Block_Machines;
import gtmaterials.GTMItem;
import gtmaterials.GTMUtil;
import gtmaterials.world.manager.SafetyAreaData;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.info.Info;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAdvancedMiner extends TileEntity implements IEnergySink {
   private double Max = 1.0E8D;
   private double energyStored;
   protected boolean addedToEnet;
   private boolean digWork = false;
   private int tY;
   public static int[] size = new int[]{1, 2, 3, 4, 6, 8, 12, 16, 24, 48};
   private LinkedList<ItemStack> inventory = new LinkedList();

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      NBTTagList ItemData = nbt.getTagList("ItemData", 10);
      this.energyStored = nbt.getDouble("energy");
      this.tY = nbt.getInteger("targetY");
      this.digWork = nbt.getBoolean("digWorking");
      this.inventory.clear();

      for(int i = 0; i < ItemData.tagCount(); ++i) {
         NBTTagCompound localNBTTagCompound = ItemData.getCompoundTagAt(i);
         this.inventory.add(GTMUtil.loadItemStackFromNBT(localNBTTagCompound));
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      NBTTagList ItemData = new NBTTagList();
      nbt.setDouble("energy", this.energyStored);
      nbt.setInteger("targetY", this.tY);
      nbt.setBoolean("digWorking", this.digWork);

      for(ItemStack item : this.inventory) {
         if(item != null) {
            NBTTagCompound localNBTTagCompound = new NBTTagCompound();
            GTMUtil.writeToNBT(item, localNBTTagCompound);
            ItemData.appendTag(localNBTTagCompound);
         }
      }

      nbt.setTag("ItemData", ItemData);
   }

   public void updateEntity() {
      if(!this.addedToEnet) {
         this.onLoaded();
      }

      Chunk chunk = this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord);
      int x = (chunk.xPosition - 1) * 16;
      int z = (chunk.zPosition - 1) * 16;
      int y = this.yCoord - 1;
      int set = 48;
      if(this.energyStored >= 460800.0D) {
         this.digWork = true;
      } else {
         this.digWork = false;
      }

      if(this.digWork) {
         int aY = y + this.tY;
         if(!this.worldObj.isRemote) {
            SafetyAreaData.INSTANCE.setSafety(this.worldObj, x, x + 47, z, z + 47, y, this.worldObj.getWorldInfo().getVanillaDimension());
         }

         for(int i = 0; i < set; ++i) {
            for(int j = 0; j < set; ++j) {
               int a2X = x + i;
               int a2Z = z + j;
               Block sBlock = this.worldObj.getBlock(a2X, aY, a2Z);
               int sMeta = this.worldObj.getBlockMetadata(a2X, aY, a2Z);
               if(sBlock != null && !(sBlock instanceof BlockContainer) && !(sBlock instanceof GT_Block_Machines)) {
                  this.findLiquid(this.worldObj, a2X, aY, a2Z);
                  this.fillInv(sBlock.getDrops(this.worldObj, a2X, aY, a2Z, sMeta, 3));
                  this.worldObj.setBlock(a2X, aY, a2Z, Blocks.air);
                  this.energyStored -= 200.0D;
               }
            }
         }

         --this.tY;
         if(this.tY + y < 1) {
            this.digWork = false;
         }
      }

      if(!this.digWork && this.tY + y < 1) {
         if(!this.worldObj.isRemote) {
            SafetyAreaData.INSTANCE.removeSafety(this.worldObj, x, x + 47, z, z + 47, y, this.worldObj.getWorldInfo().getVanillaDimension());
         }

         this.spawnChest();
      }

   }

   public Packet getDescriptionPacket() {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();
      this.writeToNBT(nbtTagCompound);
      return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      this.readFromNBT(pkt.func_148857_g());
   }

   public void spawnChest() {
      TileEntityChest sTileEntity = new TileEntityChest();
      ItemStack DataDisk = new ItemStack(GTMItem.DataDisk, 1);
      NBTTagCompound nbt = new NBTTagCompound();
      NBTTagList ItemData = new NBTTagList();
      HashMap<String, ItemStack> list = new HashMap();
      ArrayList<ItemStack> extra = new ArrayList();

      for(ItemStack item : this.inventory) {
         if(item != null) {
            if(!item.hasTagCompound()) {
               String copy = GTMUtil.resetAmount(item, 1).getDisplayName();
               if(!list.containsKey(copy)) {
                  list.put(copy, item);
               } else {
                  ItemStack map = (ItemStack)list.get(copy);
                  int amount = map.stackSize + item.stackSize;
                  ((ItemStack)list.get(copy)).stackSize = amount;
               }
            } else {
               extra.add(item);
            }
         }
      }

      for(Entry<String, ItemStack> entry : list.entrySet()) {
         extra.add(entry.getValue());
      }

      for(ItemStack item : extra) {
         NBTTagCompound localNBTTagCompound = new NBTTagCompound();
         GTMUtil.writeToNBT(item, localNBTTagCompound);
         ItemData.appendTag(localNBTTagCompound);
      }

      nbt.setTag("ItemData", ItemData);
      DataDisk.setTagCompound(nbt);
      sTileEntity.setInventorySlotContents(0, new ItemStack(GTMItem.AdvancedMiner, 1, 0));
      sTileEntity.setInventorySlotContents(1, DataDisk);
      this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.chest);
      this.worldObj.setTileEntity(this.xCoord, this.yCoord, this.zCoord, sTileEntity);
   }

   private void fillInv(ArrayList<ItemStack> drops) {
      this.inventory.addAll(drops);
   }

   private void findLiquid(World world, int x, int y, int z) {
      int[] xP = new int[]{1, -1, 0, 0};
      int[] zP = new int[]{0, 0, 1, -1};

      for(int i = 0; i < 4; ++i) {
         int cX = x + xP[i];
         int cZ = z + zP[i];
         Block sBlock = world.getBlock(cX, y, cZ);
         if(sBlock.getMaterial() == Material.water || sBlock.getMaterial() == Material.lava) {
            world.setBlock(cX, y, cZ, Blocks.glass);
         }
      }

   }

   private void fillFlatGround(int x, int z) {
      Block[] fill = new Block[]{Blocks.stone, Blocks.grass, Blocks.torch};

      for(int a = 0; a < 48; ++a) {
         for(int b = 0; b < 48; ++b) {
            for(int c = 0; c < 2; ++c) {
               Block block = this.worldObj.getBlock(x + a, 61 + c, z + b);
               if(block != null && (c == 2 && b % 4 == 0 && a % 4 == 0 || c < 2)) {
                  this.worldObj.setBlock(x + a, 61 + c, z + b, fill[c]);
               }
            }
         }
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
      return Math.max(0.0D, this.Max - this.energyStored);
   }

   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
      double free = this.Max - this.energyStored;
      if(this.energyStored + amount < this.Max) {
         this.energyStored += amount;
      } else {
         this.energyStored = this.Max;
      }

      return free < amount?amount - free:0.0D;
   }

   public int getSinkTier() {
      return 1;
   }

   public boolean onWork() {
      return this.digWork && this.inventory.size() != 0;
   }

   public void setMode(int mode) {
      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
   }

   public double getBattery() {
      return this.energyStored;
   }
}
