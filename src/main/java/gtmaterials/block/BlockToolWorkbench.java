package gtmaterials.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMaterials;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockToolWorkbench extends Block {
   private IIcon[] icon = new IIcon[3];

   public BlockToolWorkbench(Material rock) {
      super(rock);
      this.setHarvestLevel("axe", 0);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister par1IconRegister) {
      this.icon[0] = par1IconRegister.registerIcon("gtmaterials:wb/top");
      this.icon[1] = par1IconRegister.registerIcon("gtmaterials:wb/side");
      this.icon[2] = par1IconRegister.registerIcon("gtmaterials:wb/mw");
   }

   public IIcon getIcon(int side, int meta) {
      return meta == 0?(side == 1?this.icon[0]:this.icon[1]):this.icon[2];
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
      player.openGui(GTMaterials.instance, 0, world, x, y, z);
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int j = 0; j < 2; ++j) {
         par3List.add(new ItemStack(par1, 1, j));
      }

   }

   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList<ItemStack> ret = new ArrayList();
      ret.add(new ItemStack(Item.getItemFromBlock(this), 1, metadata));
      return ret;
   }
}
