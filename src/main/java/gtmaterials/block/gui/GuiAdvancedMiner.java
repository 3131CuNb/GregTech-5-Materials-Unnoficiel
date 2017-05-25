package gtmaterials.block.gui;

import gtmaterials.GTMUtil;
import gtmaterials.block.tileentity.TileEntityAdvancedMiner;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiAdvancedMiner extends GuiContainer {
   private static final ResourceLocation Textures = new ResourceLocation("gtmaterials", "textures/gui/quarry.png");
   private TileEntityAdvancedMiner tileentity;
   private int WofMI;
   private int HofMI;
   private int[] size;

   public GuiAdvancedMiner(TileEntityAdvancedMiner tileentity) {
      super(GTMUtil.nullContainer());
      this.HofMI = this.WofMI = 48;
      this.size = TileEntityAdvancedMiner.size;
      this.tileentity = tileentity;
      this.xSize = 176;
      this.ySize = 86;
   }

   public void initGui() {
      super.initGui();
   }

   protected void drawGuiContainerForegroundLayer(int p_drawGuiContainerForegroundLayer_1_, int p_drawGuiContainerForegroundLayer_2_) {
      String str = "Control Panel";
      this.fontRendererObj.drawString(str, (this.xSize - this.fontRendererObj.getStringWidth(str)) / 2, 6, 4210752);
      this.fontRendererObj.drawString(str, (this.xSize - this.fontRendererObj.getStringWidth(str)) / 2, this.ySize - 15, 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float p_drawGuiContainerBackgroundLayer_1_, int p_drawGuiContainerBackgroundLayer_2_, int p_drawGuiContainerBackgroundLayer_3_) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(Textures);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
   }

   public void actionPerformed(GuiButton id) {
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
