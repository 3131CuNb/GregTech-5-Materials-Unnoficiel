package gtmaterials.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import gtmaterials.GTMaterials;
import gtmaterials.block.tileentity.TileEntitySteamGenerator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class GTMSimpleBlockRenderintHandler implements ISimpleBlockRenderingHandler {
   float p = 0.0625F;

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      if(modelId == this.getRenderId()) {
         this.renderInventoryBlock(block.getClass().getSimpleName(), metadata, renderer);
      }

   }

   private void renderInventoryBlock(String block, int metadata, RenderBlocks renderer) {
      if(block.equals("BlockSteamGenerator")) {
         Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("gtmaterials", "textures/blocks/cable.png"));
         GL11.glTranslatef(-0.375F, -0.375F, -0.375F);
         double MCID_LONG = 0.75D;
         double MCID_SHORT = 0.0D;
         double RLID_LONG = (double)(11.0F * this.p);
         double RLID_SHORT = (double)(5.0F * this.p);
         GL11.glDisable(2896);
         Tessellator instance = new Tessellator();
         instance.startDrawingQuads();
         instance.addVertexWithUV(MCID_LONG, MCID_LONG, MCID_SHORT, RLID_LONG, RLID_LONG);
         instance.addVertexWithUV(MCID_LONG, MCID_SHORT, MCID_SHORT, RLID_LONG, RLID_SHORT);
         instance.addVertexWithUV(MCID_SHORT, MCID_SHORT, MCID_SHORT, RLID_SHORT, RLID_SHORT);
         instance.addVertexWithUV(MCID_SHORT, MCID_LONG, MCID_SHORT, RLID_SHORT, RLID_LONG);
         instance.addVertexWithUV(MCID_SHORT, MCID_LONG, MCID_LONG, RLID_LONG, RLID_LONG);
         instance.addVertexWithUV(MCID_SHORT, MCID_SHORT, MCID_LONG, RLID_LONG, RLID_SHORT);
         instance.addVertexWithUV(MCID_LONG, MCID_SHORT, MCID_LONG, RLID_SHORT, RLID_SHORT);
         instance.addVertexWithUV(MCID_LONG, MCID_LONG, MCID_LONG, RLID_SHORT, RLID_LONG);
         instance.addVertexWithUV(MCID_LONG, MCID_SHORT, MCID_SHORT, RLID_LONG, RLID_LONG);
         instance.addVertexWithUV(MCID_LONG, MCID_SHORT, MCID_LONG, RLID_LONG, RLID_SHORT);
         instance.addVertexWithUV(MCID_SHORT, MCID_SHORT, MCID_LONG, RLID_SHORT, RLID_SHORT);
         instance.addVertexWithUV(MCID_SHORT, MCID_SHORT, MCID_SHORT, RLID_SHORT, RLID_LONG);
         instance.addVertexWithUV(MCID_SHORT, MCID_LONG, MCID_SHORT, RLID_LONG, RLID_LONG);
         instance.addVertexWithUV(MCID_SHORT, MCID_LONG, MCID_LONG, RLID_LONG, RLID_SHORT);
         instance.addVertexWithUV(MCID_LONG, MCID_LONG, MCID_LONG, RLID_SHORT, RLID_SHORT);
         instance.addVertexWithUV(MCID_LONG, MCID_LONG, MCID_SHORT, RLID_SHORT, RLID_LONG);
         instance.addVertexWithUV(MCID_SHORT, MCID_LONG, MCID_SHORT, RLID_LONG, RLID_LONG);
         instance.addVertexWithUV(MCID_SHORT, MCID_SHORT, MCID_SHORT, RLID_LONG, RLID_SHORT);
         instance.addVertexWithUV(MCID_SHORT, MCID_SHORT, MCID_LONG, RLID_SHORT, RLID_SHORT);
         instance.addVertexWithUV(MCID_SHORT, MCID_LONG, MCID_LONG, RLID_SHORT, RLID_LONG);
         instance.addVertexWithUV(MCID_LONG, MCID_LONG, MCID_LONG, RLID_LONG, RLID_LONG);
         instance.addVertexWithUV(MCID_LONG, MCID_SHORT, MCID_LONG, RLID_LONG, RLID_SHORT);
         instance.addVertexWithUV(MCID_LONG, MCID_SHORT, MCID_SHORT, RLID_SHORT, RLID_SHORT);
         instance.addVertexWithUV(MCID_LONG, MCID_LONG, MCID_SHORT, RLID_SHORT, RLID_LONG);
         instance.draw();
         GL11.glEnable(2896);
         GL11.glTranslatef(0.375F, 0.375F, 0.375F);
      }

   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      if(modelId == this.getRenderId()) {
         if(block.getClass().getSimpleName().equals("BlockSteamGenerator")) {
            renderer.setRenderBounds(5.0D * (double)this.p, 5.0D * (double)this.p, 5.0D * (double)this.p, 11.0D * (double)this.p, 11.0D * (double)this.p, 11.0D * (double)this.p);
            renderer.renderStandardBlock(block, x, y, z);
            TileEntitySteamGenerator tar = (TileEntitySteamGenerator)world.getTileEntity(x, y, z);

            for(ForgeDirection dirc : ForgeDirection.VALID_DIRECTIONS) {
               if(tar.getConnect(dirc)) {
                  this.renderArm(x, y, z, block, renderer, dirc);
               }
            }

            return true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return GTMaterials.Proxy.getRenderingID();
   }

   public void renderArm(int x, int y, int z, Block block, RenderBlocks renderer, ForgeDirection dirc) {
      if(dirc.equals(ForgeDirection.UP)) {
         renderer.setRenderBounds(5.0D * (double)this.p, 11.0D * (double)this.p, 5.0D * (double)this.p, 11.0D * (double)this.p, 16.0D * (double)this.p, 11.0D * (double)this.p);
         renderer.renderStandardBlock(block, x, y, z);
      }

      if(dirc.equals(ForgeDirection.DOWN)) {
         renderer.setRenderBounds(5.0D * (double)this.p, 0.0D, 5.0D * (double)this.p, 11.0D * (double)this.p, 5.0D * (double)this.p, 11.0D * (double)this.p);
         renderer.renderStandardBlock(block, x, y, z);
      }

      if(dirc.equals(ForgeDirection.EAST)) {
         renderer.setRenderBounds(11.0D * (double)this.p, 5.0D * (double)this.p, 5.0D * (double)this.p, 16.0D * (double)this.p, 11.0D * (double)this.p, 11.0D * (double)this.p);
         renderer.renderStandardBlock(block, x, y, z);
      }

      if(dirc.equals(ForgeDirection.WEST)) {
         renderer.setRenderBounds(0.0D, 5.0D * (double)this.p, 5.0D * (double)this.p, 5.0D * (double)this.p, 11.0D * (double)this.p, 11.0D * (double)this.p);
         renderer.renderStandardBlock(block, x, y, z);
      }

      if(dirc.equals(ForgeDirection.SOUTH)) {
         renderer.setRenderBounds(5.0D * (double)this.p, 5.0D * (double)this.p, 11.0D * (double)this.p, 11.0D * (double)this.p, 11.0D * (double)this.p, 16.0D * (double)this.p);
         renderer.renderStandardBlock(block, x, y, z);
      }

      if(dirc.equals(ForgeDirection.NORTH)) {
         renderer.setRenderBounds(5.0D * (double)this.p, 5.0D * (double)this.p, 0.0D * (double)this.p, 11.0D * (double)this.p, 11.0D * (double)this.p, 6.0D * (double)this.p);
         renderer.renderStandardBlock(block, x, y, z);
      }

   }
}
