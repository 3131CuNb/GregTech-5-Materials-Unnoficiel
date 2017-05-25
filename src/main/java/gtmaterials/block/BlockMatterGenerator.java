package gtmaterials.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.block.tileentity.TileEntityMatterGenerator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMatterGenerator extends BlockContainer {
   private IIcon[] icon = new IIcon[11];
   public  int amp = 1; 

   public BlockMatterGenerator(Material material) {
      super(material);
      this.setHarvestLevel("pickaxe", 2);
   }
   
   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
	      if(!world.isRemote) {

	      }
	      return true;
	   }
   
   public int getAmp() {
	   
	return this.amp;
	   
   }
   
   
   public TileEntity createNewTileEntity(World world, int metadata) {
      return new TileEntityMatterGenerator(metadata);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      for(int x = 0; x < 10; ++x) {
         this.icon[x] = register.registerIcon("gtmaterials:generator/side_" + x);
      }

      this.icon[10] = register.registerIcon("gtmaterials:generator/top");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      switch(side) {
      case 0:
      case 1:
         return this.icon[10];
      default:
         return this.icon[meta];
      }
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int j = 0; j < TileEntityMatterGenerator.energy.length; ++j) {
         par3List.add(new ItemStack(par1, 1, j));
      }

   }

   public int damageDropped(int metadata) {
      return metadata;
   }

   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList<ItemStack> ret = new ArrayList();
      ret.add(new ItemStack(Item.getItemFromBlock(this), 1, metadata));
      return ret;
   }
}
