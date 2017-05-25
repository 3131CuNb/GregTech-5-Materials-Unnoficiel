package gtmaterials.block.tileentity;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaPipeEntity_Cable;
import gregtech.api.util.GT_Utility;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.Info;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import gtmaterials.GTMUtil;
import gtmaterials.block.BlockMatterGenerator;

public class TileEntityMatterGenerator extends TileEntity implements IEnergySource, IEnergyConnected, IGregTechDeviceInformation {
   public static double[] energy = new double[]{8.0D, 32.0D, 128.0D, 512.0D, 2048.0D, 8192.0D, 32768.0D, 131072.0D, 524288.0D, 2.147483647E9D};
   int meta;
   int amp;
   private boolean addedToEnet;

   public TileEntityMatterGenerator(int metadata) {
      this.addedToEnet = false;
      this.meta = metadata;
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.meta = nbt.getByte("meta");
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.setByte("meta", (byte)this.meta);
   }

   public void updateEntity() {
      if(!this.addedToEnet) {
         this.onLoaded();
      }

      for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; ++i) {
         ForgeDirection dirc = ForgeDirection.VALID_DIRECTIONS[i];
         TileEntity object = this.worldObj.getTileEntity(this.xCoord + dirc.offsetX, this.yCoord + dirc.offsetY, this.zCoord + dirc.offsetZ);

         try {
            if(object != null && object != this && object instanceof IGregTechTileEntity) {
            	IGregTechTileEntity h =((IGregTechTileEntity)object);
            	long a =h.getOutputAmperage();
            	if(a==0){
            		
            		int k=0;
            		String ab;
					while(true){
						k++;
						 ab=h.getDescription()[1].substring(k);
						String test = Character.toString(ab.charAt(0));
							if(isNumeric(test)){
								break;
								}
					}
            		
            		
            		
            		if(isNumeric(Character.toString(ab.toCharArray()[1])))
            			a=Integer.parseInt(ab.substring(0,2));
            		else
            			a=Integer.parseInt(ab.substring(0,1));	
            			
            	
            	}
            	//System.out.println(a);
               ((IGregTechTileEntity)object).injectEnergyUnits((byte)ForgeDirection.OPPOSITES[i], (long)energy[this.meta],a);
            }
         }
          catch (Throwable var5) {
            ;
         }
      }

   }
   

   
   public static boolean isNumeric(String c)  
   {  
     try  
     {  
       double d = Double.parseDouble(c);  
     }  
     catch(NumberFormatException nfe)  
     {  
       return false;  
     }  
     return true;  
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

   public boolean isGivingInformation() {
      return true;
   }

   public String[] getInfoData() {
      return new String[]{"GregTech\'s Scanner Test Comments"};
   }

   public final World getWorld() {
      return this.worldObj;
   }

   public final int getXCoord() {
      return this.xCoord;
   }

   public final short getYCoord() {
      return (short)this.yCoord;
   }

   public final int getZCoord() {
      return this.zCoord;
   }

   public final int getOffsetX(byte aSide, int aMultiplier) {
      return this.xCoord + ForgeDirection.getOrientation(aSide).offsetX * aMultiplier;
   }

   public final short getOffsetY(byte aSide, int aMultiplier) {
      return (short)(this.yCoord + ForgeDirection.getOrientation(aSide).offsetY * aMultiplier);
   }

   public final int getOffsetZ(byte aSide, int aMultiplier) {
      return this.zCoord + ForgeDirection.getOrientation(aSide).offsetZ * aMultiplier;
   }

   public final boolean isServerSide() {
      return !this.worldObj.isRemote;
   }

   public final boolean isClientSide() {
      return this.worldObj.isRemote;
   }

   public final boolean openGUI(EntityPlayer aPlayer) {
      return false;
   }

   public final boolean openGUI(EntityPlayer aPlayer, int aID) {
      return false;
   }

   public final int getRandomNumber(int aRange) {
      return this.worldObj.rand.nextInt(aRange);
   }

   public final BiomeGenBase getBiome(int aX, int aZ) {
      return this.worldObj.getBiomeGenForCoords(aX, aZ);
   }

   public final BiomeGenBase getBiome() {
      return this.getBiome(this.xCoord, this.zCoord);
   }

   public final Block getBlockOffset(int aX, int aY, int aZ) {
      return this.getBlock(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
   }

   public final Block getBlockAtSide(byte aSide) {
      return this.getBlockAtSideAndDistance(aSide, 1);
   }

   public final Block getBlockAtSideAndDistance(byte aSide, int aDistance) {
      return this.getBlock(this.getOffsetX(aSide, aDistance), this.getOffsetY(aSide, aDistance), this.getOffsetZ(aSide, aDistance));
   }

   public final byte getMetaIDOffset(int aX, int aY, int aZ) {
      return this.getMetaID(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
   }

   public final byte getMetaIDAtSide(byte aSide) {
      return this.getMetaIDAtSideAndDistance(aSide, 1);
   }

   public final byte getMetaIDAtSideAndDistance(byte aSide, int aDistance) {
      return this.getMetaID(this.getOffsetX(aSide, aDistance), this.getOffsetY(aSide, aDistance), this.getOffsetZ(aSide, aDistance));
   }

   public final byte getLightLevelOffset(int aX, int aY, int aZ) {
      return this.getLightLevel(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
   }

   public final byte getLightLevelAtSide(byte aSide) {
      return this.getLightLevelAtSideAndDistance(aSide, 1);
   }

   public final byte getLightLevelAtSideAndDistance(byte aSide, int aDistance) {
      return this.getLightLevel(this.getOffsetX(aSide, aDistance), this.getOffsetY(aSide, aDistance), this.getOffsetZ(aSide, aDistance));
   }

   public final boolean getOpacityOffset(int aX, int aY, int aZ) {
      return this.getOpacity(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
   }

   public final boolean getOpacityAtSide(byte aSide) {
      return this.getOpacityAtSideAndDistance(aSide, 1);
   }

   public final boolean getOpacityAtSideAndDistance(byte aSide, int aDistance) {
      return this.getOpacity(this.getOffsetX(aSide, aDistance), this.getOffsetY(aSide, aDistance), this.getOffsetZ(aSide, aDistance));
   }

   public final boolean getSkyOffset(int aX, int aY, int aZ) {
      return this.getSky(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
   }

   public final boolean getSkyAtSide(byte aSide) {
      return this.getSkyAtSideAndDistance(aSide, 1);
   }

   public final boolean getSkyAtSideAndDistance(byte aSide, int aDistance) {
      return this.getSky(this.getOffsetX(aSide, aDistance), this.getOffsetY(aSide, aDistance), this.getOffsetZ(aSide, aDistance));
   }

   public final boolean getAirOffset(int aX, int aY, int aZ) {
      return this.getAir(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
   }

   public final boolean getAirAtSide(byte aSide) {
      return this.getAirAtSideAndDistance(aSide, 1);
   }

   public final boolean getAirAtSideAndDistance(byte aSide, int aDistance) {
      return this.getAir(this.getOffsetX(aSide, aDistance), this.getOffsetY(aSide, aDistance), this.getOffsetZ(aSide, aDistance));
   }

   public final TileEntity getTileEntityOffset(int aX, int aY, int aZ) {
      return this.getTileEntity(this.xCoord + aX, this.yCoord + aY, this.zCoord + aZ);
   }

   public final TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance) {
      return aDistance == 1?this.getTileEntityAtSide(aSide):this.getTileEntity(this.getOffsetX(aSide, aDistance), this.getOffsetY(aSide, aDistance), this.getOffsetZ(aSide, aDistance));
   }

   public final IInventory getIInventory(int aX, int aY, int aZ) {
      TileEntity tTileEntity = this.getTileEntity(aX, aY, aZ);
      return tTileEntity instanceof IInventory?(IInventory)tTileEntity:null;
   }

   public final IInventory getIInventoryOffset(int aX, int aY, int aZ) {
      TileEntity tTileEntity = this.getTileEntityOffset(aX, aY, aZ);
      return tTileEntity instanceof IInventory?(IInventory)tTileEntity:null;
   }

   public final IInventory getIInventoryAtSide(byte aSide) {
      TileEntity tTileEntity = this.getTileEntityAtSide(aSide);
      return tTileEntity instanceof IInventory?(IInventory)tTileEntity:null;
   }

   public final IInventory getIInventoryAtSideAndDistance(byte aSide, int aDistance) {
      TileEntity tTileEntity = this.getTileEntityAtSideAndDistance(aSide, aDistance);
      return tTileEntity instanceof IInventory?(IInventory)tTileEntity:null;
   }

   public final IFluidHandler getITankContainer(int aX, int aY, int aZ) {
      TileEntity tTileEntity = this.getTileEntity(aX, aY, aZ);
      return tTileEntity instanceof IFluidHandler?(IFluidHandler)tTileEntity:null;
   }

   public final IFluidHandler getITankContainerOffset(int aX, int aY, int aZ) {
      TileEntity tTileEntity = this.getTileEntityOffset(aX, aY, aZ);
      return tTileEntity instanceof IFluidHandler?(IFluidHandler)tTileEntity:null;
   }

   public final IFluidHandler getITankContainerAtSide(byte aSide) {
      TileEntity tTileEntity = this.getTileEntityAtSide(aSide);
      return tTileEntity instanceof IFluidHandler?(IFluidHandler)tTileEntity:null;
   }

   public final IFluidHandler getITankContainerAtSideAndDistance(byte aSide, int aDistance) {
      TileEntity tTileEntity = this.getTileEntityAtSideAndDistance(aSide, aDistance);
      return tTileEntity instanceof IFluidHandler?(IFluidHandler)tTileEntity:null;
   }

   public final IGregTechTileEntity getIGregTechTileEntity(int aX, int aY, int aZ) {
      TileEntity tTileEntity = this.getTileEntity(aX, aY, aZ);
      return tTileEntity instanceof IGregTechTileEntity?(IGregTechTileEntity)tTileEntity:null;
   }

   public final IGregTechTileEntity getIGregTechTileEntityOffset(int aX, int aY, int aZ) {
      TileEntity tTileEntity = this.getTileEntityOffset(aX, aY, aZ);
      return tTileEntity instanceof IGregTechTileEntity?(IGregTechTileEntity)tTileEntity:null;
   }

   public final IGregTechTileEntity getIGregTechTileEntityAtSide(byte aSide) {
      TileEntity tTileEntity = this.getTileEntityAtSide(aSide);
      return tTileEntity instanceof IGregTechTileEntity?(IGregTechTileEntity)tTileEntity:null;
   }

   public final IGregTechTileEntity getIGregTechTileEntityAtSideAndDistance(byte aSide, int aDistance) {
      TileEntity tTileEntity = this.getTileEntityAtSideAndDistance(aSide, aDistance);
      return tTileEntity instanceof IGregTechTileEntity?(IGregTechTileEntity)tTileEntity:null;
   }

   public final Block getBlock(int aX, int aY, int aZ) {
      return this.crossedChunkBorder(aX, aZ) && !this.worldObj.blockExists(aX, aY, aZ)?Blocks.air:this.worldObj.getBlock(aX, aY, aZ);
   }

   public final byte getMetaID(int aX, int aY, int aZ) {
      return this.crossedChunkBorder(aX, aZ) && !this.worldObj.blockExists(aX, aY, aZ)?0:(byte)this.worldObj.getBlockMetadata(aX, aY, aZ);
   }

   public final byte getLightLevel(int aX, int aY, int aZ) {
      return this.crossedChunkBorder(aX, aZ) && !this.worldObj.blockExists(aX, aY, aZ)?0:(byte)((int)(this.worldObj.getLightBrightness(aX, aY, aZ) * 15.0F));
   }

   public final boolean getSky(int aX, int aY, int aZ) {
      return this.crossedChunkBorder(aX, aZ) && !this.worldObj.blockExists(aX, aY, aZ)?true:this.worldObj.canBlockSeeTheSky(aX, aY, aZ);
   }

   public final boolean getOpacity(int aX, int aY, int aZ) {
      return this.crossedChunkBorder(aX, aZ) && !this.worldObj.blockExists(aX, aY, aZ)?false:GT_Utility.isOpaqueBlock(this.worldObj, aX, aY, aZ);
   }

   public final boolean getAir(int aX, int aY, int aZ) {
      return this.crossedChunkBorder(aX, aZ) && !this.worldObj.blockExists(aX, aY, aZ)?true:GT_Utility.isOpaqueBlock(this.worldObj, aX, aY, aZ);
   }

   public final TileEntity getTileEntity(int aX, int aY, int aZ) {
      return this.crossedChunkBorder(aX, aZ) && !this.worldObj.blockExists(aX, aY, aZ)?null:this.worldObj.getTileEntity(aX, aY, aZ);
   }

   public final TileEntity getTileEntityAtSide(byte aSide) {
      int tX = this.getOffsetX(aSide, 1);
      int tY = this.getOffsetY(aSide, 1);
      int tZ = this.getOffsetZ(aSide, 1);
      return aSide >= 0 && aSide < 6?this.getTileEntity(tX, tY, tZ):null;
   }

   private boolean crossedChunkBorder(int aX, int aZ) {
      return aX >> 4 != this.xCoord >> 4 || aZ >> 4 != this.zCoord >> 4;
   }

   public boolean isDead() {
      return false;
   }

   public void sendBlockEvent(byte aID, byte aValue) {
   }

   public long getTimer() {
      return 0L;
   }

   public void setLightValue(byte aLightValue) {
   }

   public boolean isInvalidTileEntity() {
      return false;
   }

   public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperage) {
      return 0L;
   }

   public boolean inputEnergyFrom(byte aSide) {
      return false;
   }

   public boolean outputsEnergyTo(byte aSide) {
      return true;
   }

   public byte getColorization() {
      return (byte)-1;
   }

   public byte setColorization(byte aColor) {
      return (byte)-1;
   }

   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
      return !(receiver instanceof IGregTechTileEntity);
   }

   public double getOfferedEnergy() {
      return energy[this.meta];
   }

   public void drawEnergy(double amount) {
   }

   public int getSourceTier() {
      return 1;
   }
}
