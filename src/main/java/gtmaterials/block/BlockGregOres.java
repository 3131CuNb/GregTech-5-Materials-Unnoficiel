package gtmaterials.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.common.blocks.GT_Block_Granites;
import gtmaterials.GTMList;
import gregtech.api.enums.OrePrefixes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGregOres extends Block {
   protected ArrayList<IIcon> ICON = new ArrayList();
   public static final String[] name = new String[]{"Galena", "Iridium", "Ruby", "Sapphire", "Bauxite", "Pyrite", "Cinnabar", "Sphalerite", "Tungsten", "Cooperite", "Olivine", "Sodalite", "Tetrahedrite", "Cassiterite", "Nickel"};
   public static final String[] post = new String[]{"Black", "Red", "Full"};

   public BlockGregOres(Material rock) {
      super(rock);
   }

   public static String[] getString() {
      ArrayList<String> index = new ArrayList();

      for(int b = -1; b < post.length; ++b) {
         for(int a = 0; a < name.length; ++a) {
            index.add(name[a] + (b < 0?"":"_" + post[b]));
         }
      }

      return (String[])index.toArray(new String[index.size()]);
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int j = 0; j < name.length; ++j) {
         par3List.add(new ItemStack(par1, 1, j));
      }

   }

   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
	   ArrayList ret = new ArrayList();
	   Random rand = new Random();

      switch(metadata) {
      case 0:
         ret.add(GTMList.getBlock("oreGalena", 1));
         break;
      case 1:
         ret.add(GTMList.getItem("gemIridium", 1));
         break;
      case 2:
         ret.add(GTMList.getItem("gemRuby", 1));
         if(rand.nextInt(100) < 10) {
            ret.add(GTMList.getItem("gemGarnetRed", 1));
         }
         break;
      case 3:
         ret.add(GTMList.getItem("gemSapphire", 1));
         if(rand.nextInt(100) < 10) {
            ret.add(GTMList.getItem("gemGreenSapphire", 1));
         }
         break;
      case 4:
         ret.add(GTMList.getBlock("oreBauxite", 1));
         ret.add(GTMList.getBlock("oreAluminium", 1));
         
         break;
      case 5:
          ret.add(GTMList.getItem("dustImpurePyrite", 2));
          break;
      case 6:
         ret.add(GTMList.getItem("dustImpureCinnabar", 2));
         if(rand.nextInt(100) < 35) {
            ret.add(GTMList.getItem("dustRedstone", 1));
         }
         break;
      case 7:
         ret.add(GTMList.getItem("dustSphalerite", 2));
         if(rand.nextInt(100) < 40) {
            ret.add(GTMList.getItem("dustZinc", 1));
         }

         if(rand.nextInt(100) < 10) {
            ret.add(GTMList.getItem("gemGarnetYellow", 1));
         }
         break;
      case 8:
         ret.add(GTMList.getBlock("oreTungsten", 1));
         ret.add(GTMList.getBlock("oreNaquadah", 1));
         break;
      case 9:
         ret.add(GTMList.getBlock("oreCooperite", 1));
         break;
      case 10:
         ret.add(GTMList.getItem("gemOlivine", 1));
         break;
      case 11:
         ret.add(GTMList.getItem("dustImpureSodalite", 6));
         if(rand.nextInt(100) < 35) {
            ret.add(GTMList.getItem("dustAluminium", 1));
         }
         break;
      case 12:
         ret.add(GTMList.getBlock("oreTetrahedrite", 2));
         break;
      case 13:
         ret.add(GTMList.getBlock("oreCassiterite", 2));
         break;
      case 14:
         ret.add(GTMList.getBlock("oreNickel", 2));
      }
      if(ret.size()==0){
    	  
      }
      return ret;
   }

   public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
      int type = 0;
      int metadata = world.getBlockMetadata(x, y, z);

      for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS) {
         Block block = world.getBlock(x + fd.offsetX, y + fd.offsetY, z + fd.offsetZ);
         int meta = world.getBlockMetadata(x + fd.offsetX, y + fd.offsetY, z + fd.offsetZ);
         if(block != null) {
            if(block == Blocks.stone) {
               type = 0;
            } else if(block instanceof GT_Block_Granites) {
               if(meta >= 0 && meta < 8) {
                  type = 1;
               }

               if(meta >= 8 && meta < 16) {
                  type = 2;
               }
            } else if(Minecraft.getMinecraft().theWorld.provider.dimensionId == 1) {
               type = 3;
            }
         }
      }

      return this.getIcon(side, name.length * type + metadata);
   }

   public int damageDropped(int metadata) {
      return metadata;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister par1IconRegister) {
      for(String name : getString()) {
         this.ICON.add(par1IconRegister.registerIcon("gtmaterials:gt_addon_ore/" + name));
      }

   }

   public IIcon getIcon(int side, int meta) {
      return (IIcon)this.ICON.get(meta);
   }
}
