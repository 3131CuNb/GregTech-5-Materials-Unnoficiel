package gtmaterials.block.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

@Interface(
   iface = "cofh.api.energy.IEnergyReceiver",
   modid = "CoFHCore"
)
public class TileEntitySteamGenerator extends TileEntity implements IFluidHandler, IEnergyReceiver, IEnergyConnected, IGregTechDeviceInformation {
   private FluidTank tank = new FluidTank(16000);
   private EnergyStorage tankRF = new EnergyStorage(16000);
   private int generatePerVoltage = 8;
   private int generatePerAmpere = 1;
   private int magnification;
   private boolean mode = false;
   private int powerMode = 0;

   public void updateEntity() {
      this.magnification = this.generatePerAmpere * (log(4, this.generatePerVoltage / 8) + 1);
      if(this.checkActive() && this.checkMachine() && !this.mode) {
         FluidStack var10000 = this.tank.getFluid();
         var10000.amount -= 1 * this.magnification;
         this.sendEnergy();
      } else if(this.checkActiveRF() && this.checkMachine() && this.mode) {
         this.tankRF.extractEnergy(this.magnification, false);
         this.sendEnergy();
      }

   }

   public static int log(int a, int b) {
      return (int)(Math.log((double)b) / Math.log((double)a));
   }

   private boolean checkActive() {
      FluidStack fluid = this.tank.getFluid();
      return fluid != null && fluid.amount - this.magnification > 0;
   }

   private boolean checkActiveRF() {
      int energy = this.tankRF.getEnergyStored();
      return energy - this.magnification > 0;
   }

   private boolean checkMachine() {
      boolean b = false;

      for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; ++i) {
         ForgeDirection dirc = ForgeDirection.VALID_DIRECTIONS[i];
         TileEntity tile = this.worldObj.getTileEntity(this.xCoord + dirc.offsetX, this.yCoord + dirc.offsetY, this.zCoord + dirc.offsetZ);
         if(tile != null && tile instanceof IGregTechTileEntity && !b) {
            b = true;
         }
      }

      return b;
   }

   private void sendEnergy() {
      for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; ++i) {
         ForgeDirection dirc = ForgeDirection.VALID_DIRECTIONS[i];
         TileEntity tile = this.worldObj.getTileEntity(this.xCoord + dirc.offsetX, this.yCoord + dirc.offsetY, this.zCoord + dirc.offsetZ);
         if(tile != null && tile instanceof IGregTechTileEntity) {
            ((IGregTechTileEntity)tile).injectEnergyUnits((byte)ForgeDirection.OPPOSITES[i], (long)this.generatePerVoltage, (long)this.generatePerAmpere);
         }
      }

   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.tank.readFromNBT(nbt);
      this.tankRF.readFromNBT(nbt);
      this.generatePerAmpere = nbt.getInteger("Ampere");
      this.generatePerVoltage = nbt.getInteger("Voltage");
      this.mode = nbt.getInteger("Mode") == 1;
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      this.tank.writeToNBT(nbt);
      this.tankRF.writeToNBT(nbt);
      nbt.setInteger("Ampere", this.generatePerAmpere);
      nbt.setInteger("Voltage", this.generatePerVoltage);
      nbt.setInteger("Mode", this.mode?1:0);
   }

   public boolean getConnect(ForgeDirection dirction) {
      TileEntity tile = this.getTile(dirction);
      return tile != null && (tile instanceof IFluidHandler || tile instanceof IEnergyProvider || tile instanceof IGregTechTileEntity) && !(tile instanceof TileEntitySteamGenerator);
   }

   public TileEntity getTile(ForgeDirection dirction) {
      return this.worldObj != null?this.worldObj.getTileEntity(this.xCoord + dirction.offsetX, this.yCoord + dirction.offsetY, this.zCoord + dirction.offsetZ):null;
   }

   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
      return null;
   }

   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
      return null;
   }

   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
      if(resource != null && resource.getFluid() != null) {
         if(resource.getFluid().getName().indexOf("steam") == -1) {
            return 0;
         } else {
            FluidStack current = this.tank.getFluid();
            FluidStack resourceCopy = resource.copy();
            if(current != null && current.amount > 0 && !current.isFluidEqual(resourceCopy)) {
               return 0;
            } else {
               int i = 0;
               int used = this.tank.fill(resourceCopy, doFill);
               resourceCopy.amount -= used;
               i = i + used;
               return i;
            }
         }
      } else {
         return 0;
      }
   }

   public boolean canFill(ForgeDirection from, Fluid fluid) {
      return fluid != null && this.isFluidEmpty();
   }

   public boolean canDrain(ForgeDirection from, Fluid fluid) {
      return false;
   }

   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
      return new FluidTankInfo[]{this.tank.getInfo()};
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound nbtTagCompound = new NBTTagCompound();
      this.writeToNBT(nbtTagCompound);
      return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      this.readFromNBT(pkt.func_148857_g());
   }

   public boolean isFluidEmpty() {
      return this.tank.getFluid() == null || this.tank.getFluid().amount <= 0;
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

   public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperege) {
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

   public boolean isGivingInformation() {
      return true;
   }

   public String[] getInfoData() {
      return new String[]{"This Machine requires Steam or RF", "Voltage : " + this.generatePerVoltage, "Ampere : " + this.generatePerAmpere, "Magnet : " + this.getMagnet(), "Mode : " + this.getMode(), "Name : " + (this.isFluidEmpty()?"Null Fluid : ":this.tank.getFluid().getFluid().getName()), "Amount : " + String.valueOf(this.tank.getFluidAmount()) + "/" + this.tank.getCapacity(), "RF : " + String.valueOf(this.tankRF.getEnergyStored()) + "/" + this.tankRF.getMaxEnergyStored()};
   }

   public void upLevelVoltage() {
      if(8912 > this.generatePerVoltage) {
         this.generatePerVoltage *= 4;
      }

      this.update();
   }

   public void downLevelVoltage() {
      if(8 < this.generatePerVoltage) {
         this.generatePerVoltage /= 4;
      }

      this.update();
   }

   public void addAmpere() {
      if(32 > this.generatePerAmpere) {
         ++this.generatePerAmpere;
      }

      this.update();
   }

   public void decrAmpere() {
      if(1 < this.generatePerAmpere) {
         --this.generatePerAmpere;
      }

      this.update();
   }

   public void changeRF() {
      if(!this.mode) {
         this.mode = true;
      }

      this.update();
   }

   public void changeSteam() {
      if(this.mode) {
         this.mode = false;
      }

      this.update();
   }

   private void update() {
      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
   }

   public IFluidTank getTank() {
      return this.tank;
   }

   public int getMagnet() {
      return this.magnification;
   }

   public int getVoltage() {
      return this.generatePerVoltage;
   }

   public int getAmpere() {
      return this.generatePerAmpere;
   }

   public boolean getMode() {
      return this.mode;
   }

   public int getPowerMode() {
      return this.powerMode;
   }

   @Method(
      modid = "CoFHCore"
   )
   public int receiveEnergy(ForgeDirection paramForgeDirection, int maxReceive, boolean simulate) {
      return this.tankRF.receiveEnergy(maxReceive, simulate);
   }

   @Method(
      modid = "CoFHCore"
   )
   public int getEnergyStored(ForgeDirection paramForgeDirection) {
      return this.tankRF.getEnergyStored();
   }

   @Method(
      modid = "CoFHCore"
   )
   public int getMaxEnergyStored(ForgeDirection paramForgeDirection) {
      return this.tankRF.getMaxEnergyStored();
   }

   @Method(
      modid = "CoFHCore"
   )
   public boolean canConnectEnergy(ForgeDirection paramForgeDirection) {
      return true;
   }
}
