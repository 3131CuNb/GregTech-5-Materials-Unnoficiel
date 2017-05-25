package gtmaterials.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMItem;
import gtmaterials.GTMaterials;
import gtmaterials.block.tileentity.TileEntityMatterFabricator;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMatterFabricator extends BlockContainer {
   private IIcon[] icon = new IIcon[2];

   public BlockMatterFabricator(Material p_i45386_1_) {
      super(p_i45386_1_);
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
      if(!world.isRemote) {
         ItemStack hand = player.getCurrentEquippedItem();
         if(hand != null && hand.getItem() == GTMItem.FabricatMeter) {
            return false;
         }

         player.openGui(GTMaterials.instance, 1, world, x, y, z);
      }

      return true;
   }

   public TileEntity createNewTileEntity(World world, int metadata) {
      switch(metadata) {
      case 0:
      case 1:
      case 2:
      case 3:
         return new TileEntityMatterFabricator(metadata);
      case 4:
         return new TileEntityMatterFabricator.TileEntityOreFabricator();
      case 5:
         return new TileEntityMatterFabricator.TileEntityMaterialFabricator();
      case 6:
         return new TileEntityMatterFabricator.TileEntityMachineFabricator();
      default:
         return null;
      }
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
      for(int j = 0; j < 7; ++j) {
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
      TileEntityMatterFabricator tileentity = (TileEntityMatterFabricator)world.getTileEntity(x, y, z);
      Random rand = new Random();
      if(tileentity != null) {
         for(int i1 = 0; i1 < tileentity.getSizeInventory(); ++i1) {
            ItemStack itemstack = tileentity.getStackInSlot(i1);
            if(itemstack != null) {
               float f = rand.nextFloat() * 0.8F + 0.1F;
               float f1 = rand.nextFloat() * 0.8F + 0.1F;
               float f2 = rand.nextFloat() * 0.8F + 0.1F;

               while(itemstack.stackSize > 0) {
                  int j1 = rand.nextInt(21) + 10;
                  if(j1 > itemstack.stackSize) {
                     j1 = itemstack.stackSize;
                  }

                  itemstack.stackSize -= j1;
                  EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                  if(itemstack.hasTagCompound()) {
                     entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                  }

                  float f3 = 0.05F;
                  entityitem.motionX = (double)((float)rand.nextGaussian() * f3);
                  entityitem.motionY = (double)((float)rand.nextGaussian() * f3 + 0.2F);
                  entityitem.motionZ = (double)((float)rand.nextGaussian() * f3);
                  world.spawnEntityInWorld(entityitem);
               }
            }
         }

         world.func_147453_f(x, y, z, block);
      }

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
