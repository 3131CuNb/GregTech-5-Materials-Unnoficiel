package gtmaterials.block;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMItem;
import gtmaterials.GTMaterials;
import gtmaterials.block.tileentity.TileEntityBlockABC;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockABC extends BlockContainer {
   private IIcon[] icon = new IIcon[2];
   private static int mod = 0;
   public BlockABC(Material p_i45386_1_) {
      super(p_i45386_1_);
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
	   if(!world.isRemote)
	   if (player.isSneaking()){
		   TileEntity First = world.getTileEntity(x, y+1, z);
		   if (First!=null)
		   if (First instanceof IInventory)
		   {
			   IInventory Inventory = (IInventory ) First; 
			   for(int o=0;o<Inventory.getSizeInventory();o++){
				   Inventory.setInventorySlotContents(o, null);
			   }
			   
		   
		   return true;
	   }
	   }
	   
	   //System.out.println(world.getBlockMetadata(x, y, z));
	   int meta = world.getBlockMetadata(x, y, z);
      if(!world.isRemote && meta==4) {
        TileEntityBlockABC test=(TileEntityBlockABC)world.getTileEntity(x, y, z);
        	if (side==1 || side==2 || side==3 ){
        	test.ModChange(test.ModSee()+1);
        	if (test.ModSee()>test.ModMaxSize()){
        		test.ModChange(0);
        	}
        }
        else {
        	if (test.ModSee()==0){
        	}
        	else
        	test.ModChange(test.ModSee()-1);
        }

        player.addChatMessage(new ChatComponentText(((Integer)test.ModSee()).toString()));
        player.addChatMessage(new ChatComponentText(test.ModName()));
        
      }

      return true;
   }

   public TileEntity createNewTileEntity(World world, int metadata) {
	   System.out.println(metadata);
         return new TileEntityBlockABC(metadata);

      
   }


   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      this.icon[0] = register.registerIcon("gtmaterials:matter_fabricator/top");
      this.icon[1] = register.registerIcon("gtmaterials:matter_fabricator/side");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      switch(side) {
      case 0:
      case 1:
         return this.icon[0];
      default:
         return this.icon[1];
      }
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int j = 0; j < 10; ++j) {
         par3List.add(new ItemStack(par1, 1, j));
      }

   }

   @SideOnly(Side.CLIENT)
   public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
      int[] color = new int[]{16711680, 16776960, '\uff00', 255, 16777215, 8421504, '\uffff', 8421504};
      int meta = world.getBlockMetadata(x, y, z);
      return color[meta < color.length?meta:0];
   }

   public MapColor getMapColor(int meta) {
      MapColor[] color = new MapColor[]{MapColor.redColor, MapColor.yellowColor, MapColor.greenColor, MapColor.blueColor, MapColor.ironColor, MapColor.blackColor, MapColor.cyanColor};
      return color[meta < color.length?meta:0];
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
      TileEntityBlockABC tileentity = (TileEntityBlockABC)world.getTileEntity(x, y, z);
    
      
               
         
       
         world.func_147453_f(x, y, z, block);
      
      super.breakBlock(world, x, y, z, block, meta);
   }

   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList<ItemStack> ret = new ArrayList();
      ret.add(new ItemStack(Item.getItemFromBlock(this), 1, metadata));
      return ret;
   }

   public int damageDropped(int metadata) {
      return metadata;
   }
}

