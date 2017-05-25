package gtmaterials.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMItem;
import gtmaterials.GTMList;
import gtmaterials.GTMLog;
import gtmaterials.GTMUtil;
import gtmaterials.GTMaterials;
import gtmaterials.block.tileentity.TileEntityMatterFabricator;
import ic2.core.Ic2Items;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFabricatMeter extends Item {
   private IIcon[] icon = new IIcon[3];

   public ItemFabricatMeter() {
      this.setMaxStackSize(1);
      this.setCreativeTab(GTMaterials.TAB);
      this.setHasSubtypes(true);

      for(int i = 0; i < 3; ++i) {
         GameRegistry.addShapelessRecipe(new ItemStack(this, 1, i), new Object[]{new ItemStack(this, 1, i)});
      }

   }

   public String getUnlocalizedName(ItemStack par1) {
      return "GTMaterials:FabricatMeter_" + par1.getItemDamage();
   }

   public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float disX, float disY, float disZ) {
      Block sBlock = world.getBlock(x, y, z);
      int sMeta = world.getBlockMetadata(x, y, z);
      TileEntity sTileEntity = world.getTileEntity(x, y, z);
      if(player.isSneaking()) {
         if(!world.isRemote && sBlock != GTMItem.MatterFabricator) {
            int sGregMeta = -1;
            if(sTileEntity != null) {
               sGregMeta = this.getGregDamage(sTileEntity);
            }

            if(sGregMeta >= 0 && sGregMeta > sMeta) {
               sMeta = sGregMeta;
            }

            NBTTagCompound sNBT = new NBTTagCompound();
            ItemStack sData = sBlock != Blocks.chest && sBlock != Blocks.trapped_chest?new ItemStack(sBlock, 1, sMeta):((TileEntityChest)sTileEntity).getStackInSlot(0);
            String sDataName = GTMUtil.getOreName(sData);
            sNBT.setInteger("X", x);
            sNBT.setInteger("Y", y);
            sNBT.setInteger("Z", z);
            boolean method1 = (GTMList.getBlock(sDataName, 1) != null || GTMList.getItem(sDataName, 1) != null) && itemstack.getItemDamage() == 0;
            boolean method2 = (sBlock instanceof BlockContainer || sTileEntity instanceof IInventory) && itemstack.getItemDamage() == 1;
            boolean method3 = sData != null && sData.getItem() == Ic2Items.FluidCell.getItem() && itemstack.getItemDamage() == 2;
            int flag = !itemstack.hasTagCompound()?(method1?1:(method2?2:(method3?3:4))):0;
            String[] key = new String[]{sDataName, "Unknown[data:" + sBlock.getUnlocalizedName() + "-" + Item.getIdFromItem(Item.getItemFromBlock(sBlock)) + "-" + sMeta + "]", flag == 3?((NBTTagCompound)sData.getTagCompound().getTag("Fluid")).getString("FluidName"):null, null};
            switch(flag) {
            case 0:
               player.addChatMessage(new ChatComponentText("Scanning --- Already"));
               break;
            case 1:
            case 2:
            case 3:
               sNBT.setString("OreName", key[flag - 1]);
               itemstack.setTagCompound(sNBT);
               player.addChatMessage(new ChatComponentText("Scanning --- Success"));
               break;
            case 4:
               player.addChatMessage(new ChatComponentText("Scanning --- Failed"));
            }

            if(player.getDisplayName().equals("m0dfixG")) {
               GTMLog.INSTANCE.view("XYZ - " + String.valueOf(x) + "," + y + "," + z);
               GTMLog.INSTANCE.view("Meta - " + String.valueOf(sMeta));
               GTMLog.INSTANCE.view("Flag - " + String.valueOf(flag));
               GTMLog.INSTANCE.view("Method - " + String.valueOf(method1) + "," + method2 + "," + method3);
               GTMLog.INSTANCE.view("Name - " + sDataName);
            }

            return true;
         }

         if(sTileEntity != null && sBlock == GTMItem.MatterFabricator && sTileEntity instanceof TileEntityMatterFabricator && itemstack.hasTagCompound()) {
            TileEntityMatterFabricator sMatterTileEntity = (TileEntityMatterFabricator)world.getTileEntity(x, y, z);
            String sOreName = itemstack.getTagCompound().getString("OreName");
            boolean flag = false;
            if(sMeta == 4 && GTMList.getBlock(sOreName, 1) != null) {
               if(sMatterTileEntity.getItem() == null) {
                  ItemStack item = GTMList.getBlock(sOreName, 1);
                  sMatterTileEntity.setItem(item.getItem());
                  sMatterTileEntity.setMeta(item.getItemDamage());
                  flag = true;
               } else {
                  flag = false;
               }
            } else if(sMeta == 5 && GTMList.getItem(sOreName, 1) != null) {
               if(sMatterTileEntity.getItem() == null) {
                  ItemStack item = GTMList.getItem(sOreName, 1);
                  sMatterTileEntity.setItem(item.getItem());
                  sMatterTileEntity.setMeta(item.getItemDamage());
                  flag = true;
               } else {
                  flag = false;
               }
            } else if(sMeta == 6 && this.getStringArray(sOreName).length > 0 && itemstack.getItemDamage() == 1) {
               if(sMatterTileEntity.getItem() == null) {
                  sMatterTileEntity.setItem(Item.getItemById(Integer.valueOf(this.getStringArray(sOreName)[1]).intValue()));
                  sMatterTileEntity.setMeta(Integer.valueOf(this.getStringArray(sOreName)[2]).intValue());
                  flag = true;
               } else {
                  flag = false;
               }
            }

            if(!world.isRemote) {
               if(flag) {
                  player.addChatMessage(new ChatComponentText("Setting --- Success"));
               } else {
                  player.addChatMessage(new ChatComponentText("Setting --- Failed"));
               }
            }

            return true;
         }
      }

      return false;
   }

   public int getGregDamage(TileEntity sTile) {
      NBTTagCompound sNBT = new NBTTagCompound();
      sTile.writeToNBT(sNBT);
      int i = -1;
      if(sNBT.hasKey("m")) {
         i = sNBT.getInteger("m");
      }

      if(sNBT.hasKey("mID")) {
         i = sNBT.getInteger("mID");
      }

      return i;
   }

   public String[] getStringArray(String str) {
      String str2 = str.substring("Unknown[data:".length(), str.length());
      String str3 = str2.substring(0, str2.length() - 1);
      String[] str4 = str3.split("-");
      return str4;
   }

   @SideOnly(Side.CLIENT)
   public boolean hasEffect(ItemStack par1ItemStack) {
      return par1ItemStack.hasTagCompound();
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      if(par1ItemStack.hasTagCompound()) {
         NBTTagCompound data = par1ItemStack.getTagCompound();
         par3List.add("Coordinate : §eX " + String.valueOf(data.getInteger("X")) + " Y " + data.getInteger("Y") + " Z " + data.getInteger("Z"));
         par3List.add("Key : " + data.getString("OreName"));
      }

   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int j = 0; j < 2; ++j) {
         par3List.add(new ItemStack(par1, 1, j));
      }

   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      this.icon[0] = par1IconRegister.registerIcon("gtmaterials:fabricatmeter");
      this.icon[1] = par1IconRegister.registerIcon("gtmaterials:fabricatmeter_2");
      this.icon[2] = par1IconRegister.registerIcon("gtmaterials:fabricatmeter_3");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int meta) {
      return this.icon[meta];
   }
}
