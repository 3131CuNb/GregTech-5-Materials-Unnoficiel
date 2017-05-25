package gtmaterials.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMItem;
import gtmaterials.GTMLog;
import gtmaterials.GTMUtil;
import gtmaterials.GTMaterials;
import gtmaterials.block.tileentity.TileEntityBigChest;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemDataDisk extends Item {
   public ItemDataDisk() {
      this.setMaxStackSize(1);
      this.setTextureName("gtmaterials:matter_potion");
      this.setCreativeTab(GTMaterials.TAB);
   }

   public String getUnlocalizedName(ItemStack par1) {
      return "GTMaterials:DataDisk";
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      par3List.add("TBNG(The Biggest No GUI) Chest will be born");
      if(par1ItemStack.hasTagCompound()) {
         par3List.add("Item Data : ACTIVATED");
      }

   }

   @SideOnly(Side.CLIENT)
   public boolean hasEffect(ItemStack par1ItemStack) {
      return par1ItemStack.hasTagCompound();
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
      if(!world.isRemote && itemstack.hasTagCompound() && player.getDisplayName().equals("m0dfixG")) {
         NBTTagCompound TAG = itemstack.getTagCompound();
         NBTTagList NBT = (NBTTagList)TAG.getTag("ItemData");

         for(int i = 0; i < NBT.tagCount(); ++i) {
            NBTTagCompound data = NBT.getCompoundTagAt(i);
            ItemStack item = GTMUtil.loadItemStackFromNBT(data);
            GTMLog.INSTANCE.view(item.stackSize + " : " + item.getDisplayName());
         }
      }

      return itemstack;
   }

   public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float zX, float zY, float zZ) {
      if(!world.isRemote && itemstack.hasTagCompound()) {
         ForgeDirection dirc = ForgeDirection.VALID_DIRECTIONS[side];
         TileEntityBigChest tbc = new TileEntityBigChest();
         NBTTagCompound TAG = itemstack.getTagCompound();
         NBTTagList NBT = TAG.getTagList("ItemData", 10);
         if(NBT.tagCount() > 0 && NBT.tagCount() < Integer.MAX_VALUE) {
            for(int i = 0; i < NBT.tagCount(); ++i) {
               NBTTagCompound item = NBT.getCompoundTagAt(i);
               tbc.setInventorySlotContents(i, GTMUtil.loadItemStackFromNBT(item));
            }

            world.setBlock(x + dirc.offsetX, y + dirc.offsetY, z + dirc.offsetZ, GTMItem.BigChest);
            world.setTileEntity(x + dirc.offsetX, y + dirc.offsetY, z + dirc.offsetZ, tbc);
            player.inventory.decrStackSize(player.inventory.currentItem, 1);
            return true;
         }

         player.addChatMessage(new ChatComponentText("Sorry, This item dosen\'t work :("));
      }

      return false;
   }
}
