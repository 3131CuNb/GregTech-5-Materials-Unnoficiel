package gtmaterials.block;

import gtmaterials.GTMaterials;
import gtmaterials.block.tileentity.TileEntitySteamGenerator;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSteamGenerator extends BlockContainer {
   float p = 0.0625F;

   public BlockSteamGenerator(Material material) {
      super(material);
      this.setBlockTextureName("gtmaterials:cable");
      this.setBlockBounds(4.0F * this.p, 4.0F * this.p, 4.0F * this.p, 12.0F * this.p, 12.0F * this.p, 12.0F * this.p);
   }

   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
      return new TileEntitySteamGenerator();
   }

   public int getRenderType() {
      return GTMaterials.Proxy.getRenderingID();
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
      TileEntity tile = world.getTileEntity(x, y, z);
      TileEntitySteamGenerator tac = null;
      if(tile != null && tile instanceof TileEntitySteamGenerator) {
         tac = (TileEntitySteamGenerator)tile;
      }

      float MinX = (float)this.minX;
      float MinY = (float)this.minY;
      float MinZ = (float)this.minZ;
      float MaxX = (float)this.maxX;
      float MaxY = (float)this.maxY;
      float MaxZ = (float)this.maxZ;
      float adjust = 4.0F * this.p;
      if(tac != null) {
         if(tac.getConnect(ForgeDirection.WEST)) {
            MinX -= adjust;
         }

         if(tac.getConnect(ForgeDirection.EAST)) {
            MaxX += adjust;
         }

         if(tac.getConnect(ForgeDirection.DOWN)) {
            MinY -= adjust;
         }

         if(tac.getConnect(ForgeDirection.UP)) {
            MaxY += adjust;
         }

         if(tac.getConnect(ForgeDirection.NORTH)) {
            MinZ -= adjust;
         }

         if(tac.getConnect(ForgeDirection.SOUTH)) {
            MaxZ += adjust;
         }
      }

      return AxisAlignedBB.getBoundingBox((double)x + (double)MinX, (double)y + (double)MinY, (double)z + (double)MinZ, (double)x + (double)MaxX, (double)y + (double)MaxY, (double)z + (double)MaxZ);
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
      return this.getCollisionBoundingBoxFromPool(world, x, y, z);
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
      player.openGui(GTMaterials.instance, 2, world, x, y, z);
      return true;
   }
}
