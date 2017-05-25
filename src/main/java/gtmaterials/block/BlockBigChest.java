package gtmaterials.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.block.tileentity.TileEntityBigChest;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBigChest extends BlockContainer {
   private IIcon[] icon = new IIcon[2];

   public BlockBigChest(Material material) {
      super(material);
      this.setLightLevel(0.0F);
   }

   public TileEntity createNewTileEntity(World arg0, int arg1) {
      return new TileEntityBigChest();
   }

   public int damageDropped(int metadata) {
      return metadata;
   }

   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList<ItemStack> ret = new ArrayList();
      return ret;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      this.icon[0] = register.registerIcon("gtmaterials:chest/top");
      this.icon[1] = register.registerIcon("gtmaterials:chest/side");
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

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float zX, float zY, float zZ) {
      if(!world.isRemote) {
         TileEntity sTile = world.getTileEntity(x, y, z);
         if(sTile instanceof TileEntityBigChest) {
            TileEntityBigChest chest = (TileEntityBigChest)sTile;
            HashMap<String, Integer> map = new HashMap();

            for(int i = 0; i < chest.getSizeInventory(); ++i) {
               ItemStack item = chest.getStackInSlot(i);
               if(item != null) {
                  ItemStack c = item.copy();
                  String name = c.getDisplayName();
                  if(!map.containsKey(name)) {
                     map.put(name, Integer.valueOf(c.stackSize));
                  } else {
                     int d = ((Integer)map.get(name)).intValue();
                     d = d + c.stackSize;
                     map.put(name, Integer.valueOf(d));
                  }
               }
            }

            if(!map.isEmpty()) {
               this.add(player);

               for(String name : map.keySet()) {
                  player.addChatMessage(new ChatComponentText(name + " " + map.get(name)));
               }

               this.add(player);
            }

            return true;
         }
      }

      return true;
   }

   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
      TileEntityBigChest tileentitychest = (TileEntityBigChest)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
      if(tileentitychest != null) {
         for(int i1 = 0; i1 < tileentitychest.getSizeInventory(); ++i1) {
            ItemStack itemstack = tileentitychest.getStackInSlot(i1);
            if(itemstack != null) {
               float f = p_149749_1_.rand.nextFloat() * 0.8F + 0.1F;
               float f1 = p_149749_1_.rand.nextFloat() * 0.8F + 0.1F;

               EntityItem entityitem;
               for(float f2 = p_149749_1_.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; p_149749_1_.spawnEntityInWorld(entityitem)) {
                  int j1 = p_149749_1_.rand.nextInt(21) + 10;
                  if(j1 > itemstack.stackSize) {
                     j1 = itemstack.stackSize;
                  }

                  itemstack.stackSize -= j1;
                  entityitem = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + f), (double)((float)p_149749_3_ + f1), (double)((float)p_149749_4_ + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                  float f3 = 0.05F;
                  entityitem.motionX = (double)((float)p_149749_1_.rand.nextGaussian() * f3);
                  entityitem.motionY = (double)((float)p_149749_1_.rand.nextGaussian() * f3 + 0.2F);
                  entityitem.motionZ = (double)((float)p_149749_1_.rand.nextGaussian() * f3);
                  if(itemstack.hasTagCompound()) {
                     entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                  }
               }
            }
         }

         p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
      }

      super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
   }

   private void add(EntityPlayer player) {
      player.addChatMessage(new ChatComponentText(""));
      player.addChatMessage(new ChatComponentText("------Inventory------"));
      player.addChatMessage(new ChatComponentText(""));
   }
}
