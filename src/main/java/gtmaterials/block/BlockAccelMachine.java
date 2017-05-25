package gtmaterials.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.packet.PacketHandler;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import gtmaterials.GTMList;

public class BlockAccelMachine extends BlockTorch {
   public BlockAccelMachine(Material material) {
      this.setBlockTextureName("gtmaterials:am");
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
	   if(!world.isRemote){
	   player.entityDropItem(GTMList.getRandom(1),1);
	   }
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
      int l = world.getBlockMetadata(x, y, z);
      double d0 = (double)((float)x + 0.5F);
      double d1 = (double)((float)y + 0.7F);
      double d2 = (double)((float)z + 0.5F);
      double d3 = 0.2199999988079071D;
      double d4 = 0.27000001072883606D;
      switch(l) {
      case 1:
         world.spawnParticle("smoke", d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
         break;
      case 2:
         world.spawnParticle("smoke", d0 + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
         break;
      case 3:
         world.spawnParticle("smoke", d0, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
         break;
      case 4:
         world.spawnParticle("smoke", d0, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
         break;
      default:
         world.spawnParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
      }

      PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(x, (short)y, z, this.getClass(), new Object[0]));
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int side) {
      super.breakBlock(world, x, y, z, block, side);
   }
}
