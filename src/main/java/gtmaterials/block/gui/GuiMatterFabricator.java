package gtmaterials.block.gui;

import gtmaterials.block.container.ContainerMatterFabricator;
import gtmaterials.block.tileentity.TileEntityMatterFabricator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiMatterFabricator extends GuiContainer {
   private static final ResourceLocation Textures = new ResourceLocation("gtmaterials", "textures/gui/fabricator.png");
   private TileEntityMatterFabricator tileentity;

   public GuiMatterFabricator(TileEntityMatterFabricator tileentity, EntityPlayer player) {
      super(new ContainerMatterFabricator(tileentity, player));
      this.tileentity = tileentity;
      this.xSize = 176;
      this.ySize = 166;
   }

   protected void drawGuiContainerForegroundLayer(int p_drawGuiContainerForegroundLayer_1_, int p_drawGuiContainerForegroundLayer_2_) {
      String str = "Fabricator";
      this.fontRendererObj.drawString(str, (this.xSize - this.fontRendererObj.getStringWidth(str)) / 2, 6, 4210752);
      str = this.tileentity.getPercentage() + "%";
      this.fontRendererObj.drawString(str, (this.xSize - this.fontRendererObj.getStringWidth(str)) / 2, 49, 4210752);
      this.fontRendererObj.drawString("Inventory", 8, 72, 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float p_drawGuiContainerBackgroundLayer_1_, int p_drawGuiContainerBackgroundLayer_2_, int p_drawGuiContainerBackgroundLayer_3_) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(Textures);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
