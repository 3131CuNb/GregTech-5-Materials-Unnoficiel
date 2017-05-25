package gtmaterials.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMItem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMatterOres extends Block {
   public static String[] name = new String[]{"red", "yellow", "green", "blue"};
   private IIcon[] icon = new IIcon[2];

   public BlockMatterOres(Material material) {
      super(material);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister par1IconRegister) {
      String[] pre = new String[]{"ore_matter", "ore_matter_nether"};

      for(int i = 0; i < 2; ++i) {
         this.icon[i] = par1IconRegister.registerIcon("gtmaterials:" + pre[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      return this.icon[Minecraft.getMinecraft().theWorld.provider.dimensionId == -1?1:0];
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int j = 0; j < 4; ++j) {
         par3List.add(new ItemStack(par1, 1, j));
      }

   }

   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList ret = new ArrayList();
      ret.add(new ItemStack(GTMItem.Matter, 4, metadata % 4));
      return ret;
   }

   public int damageDropped(int metadata) {
      return metadata;
   }
}
