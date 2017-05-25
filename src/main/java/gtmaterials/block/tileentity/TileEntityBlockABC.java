package gtmaterials.block.tileentity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import cpw.mods.fml.common.registry.GameData;
import codechicken.nei.api.ItemInfo;
import cofh.lib.util.helpers.InventoryHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.util.RegistryNamespaced;
import gtmaterials.GTMItem;
import gtmaterials.GTMList;
import gtmaterials.GTMUtil;
import gtmaterials.block.*;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.Info;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityBlockABC extends TileEntity implements  IInventory {

	private ArrayList <ItemStack> field = new ArrayList<ItemStack>();
	
	private int x =0;
	private int meta;
	private int mode;
	private boolean actif = false;
	private int k;

   public TileEntityBlockABC(int meta) {

		

	   for (int i = 0; i < this.getSizeInventory(); i++) {
		   field.add(i,null);
		 }
	   actif = true;
		this.meta= meta;
   }
	
   
   
   
   public void readFromNBT(NBTTagCompound nbt) {
	      super.readFromNBT(nbt);
	      this.meta = nbt.getInteger("meta");
	      this.mode = nbt.getInteger("mode");
	   }

	   public void writeToNBT(NBTTagCompound nbt) {
	      super.writeToNBT(nbt);
	      nbt.setInteger("meta", this.meta);
	      nbt.setInteger("mode", this.mode);
	   }   

public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      this.readFromNBT(pkt.func_148857_g());
   }



   public void updateEntity() {
	  
	
	   TileEntity First = this.worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
	   if (First!=null)
	   if (First instanceof IInventory)
	   {
		   IInventory Inventory = (IInventory ) First; 
		   for(int o=0;o<field.size();o++)
		   {
		   ItemStack i = InventoryHelper.insertItemStackIntoInventory(Inventory,field.get(o) , 0);
		   field.set(o,i);
		   
		   }
				}
				   
			

	  
	   
	   
	   
	   
	 if(actif)
	   if(!this.worldObj.isRemote){
		   int b=field.size();
		   for(int i=0;i<b;i++)
	   if (field.get(i)==null){  
		   try {
			   switch (meta){
			   case 0:field.set(i, GTMList.getRandom(0).copy());
			   break;
			   case 1: field.set(i, GTMList.getRandom(1).copy());  
			   break;
			   case 2:field.set(i, GTMList.getRandom(2).copy());
			   break;
			   case 3:field.set(i, GTMList.getRandom(3).copy());
			   break;
			   case 4:
				   if (this.mode<GTMList.getModSize()){
				   field.set(i, GTMList.getRandom(4,this.mode).copy());
				   }
				   else{
					   this.mode=0;
				   }
			   break;
			   case 5:field.set(i, GTMList.getRandom(5).copy());
				   
			   }	   
		 
		   }	catch (NullPointerException e){
			   
		   }
		   
		  
	   }
		   for(int i=0;i<b;i++){
			   if(field.get(i)==null)
				   break;
			   if(i==b-1){
				   if(this.worldObj.getTotalWorldTime()%80==90){
					  ItemStack f = field.get(0);
					   for(int c=0;c<b-1;c++)
						   field.set(c,field.get(c+1));
					   field.set(b-1,f);
				   }
      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			   }
		   }
	   }
   }


   public void invalidate() {
      super.invalidate();
      this.onChunkUnload();
   }
public String ModName(){
	return GTMList.getModName(this.mode);
	
}

 

   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
      return true;
   }

 




   public boolean find() {
      return this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) instanceof IEnergyTile || this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) instanceof IGregTechTileEntity || this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) instanceof IGregTechTileEntity;
   }


   public int getSizeInventory() {
      return 10;
   }


   public String getInventoryName() {
      return "Block ABC";
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
   
   public void setMeta(int Meta) {
	      this.meta = Meta;
	   }
	   public int getMeta() {
		      return this.meta;
		   }

   public void closeInventory() {
   }

   public boolean isItemValidForSlot(int slot, ItemStack item) {
      return true;
   }

public ItemStack decrStackSize(int slot, int size) {
        if(this.field.get(slot).stackSize <= size) {
           ItemStack local2 = this.field.get(slot);
           this.field = null;
           return local2;
        }

        if(this.field.get(slot).stackSize > size) {
           ItemStack local2 = this.field.get(slot).splitStack(size);
           if(this.field.get(slot).stackSize <= 0) {
              this.field = null;
           }
        }
    
	return null;       
     
    }



        

@Override
public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setInventorySlotContents(int slot, ItemStack item) {
    this.field.set(slot,item);
    if(item != null && item.stackSize > this.getInventoryStackLimit()) {
       item.stackSize = this.getInventoryStackLimit();
    }
	
}



@Override
public ItemStack getStackInSlot(int slot) {
	
	return field.get(slot);
}

public void ModChange(int mod) {
	this.mode=mod;
}

public int ModSee() {
	return this.mode;
}

public int ModMaxSize(){
	return GTMList.getModSize();
}






 
}

  