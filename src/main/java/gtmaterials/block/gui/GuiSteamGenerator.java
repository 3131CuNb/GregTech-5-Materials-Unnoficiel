package gtmaterials.block.gui;

import gtmaterials.GTMUtil;
import gtmaterials.block.tileentity.TileEntitySteamGenerator;
import gtmaterials.packet.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSteamGenerator extends GuiContainer {
   private static final ResourceLocation Texture = new ResourceLocation("gtmaterials", "textures/gui/cable.png");
   private TileEntitySteamGenerator Tileentity;

   public GuiSteamGenerator(TileEntitySteamGenerator Tileentity) {
      super(GTMUtil.nullContainer());
      this.Tileentity = Tileentity;
      this.xSize = 176;
      this.ySize = 86;
   }

   public void initGui() {
      super.initGui();
      int k = (this.width - this.xSize) / 2;
      int l = (this.height - this.ySize) / 2;
      this.buttonList.add(new GuiSteamGenerator.GuiGreenButton(0, k + 12, l + 20 + 24, false));
      this.buttonList.add(new GuiSteamGenerator.GuiGreenButton(1, k + 74, l + 20 + 24, true));
      this.buttonList.add(new GuiSteamGenerator.GuiGreenButton(2, k + 95, l + 20 + 24, false));
      this.buttonList.add(new GuiSteamGenerator.GuiGreenButton(3, k + 157, l + 20 + 24, true));
      this.buttonList.add(new GuiSteamGenerator.GuiChangeButton(4, k + 153, l + 6, false));
      this.buttonList.add(new GuiSteamGenerator.GuiChangeButton(5, k + 163, l + 6, true));
      this.buttonList.add(new GuiSteamGenerator.GuiInvisibleButton(100, k + 141, l + 6));
   }

   protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
      String temp = !this.Tileentity.getMode()?"Steam Generator":"RF Generator";
      this.fontRendererObj.drawString(temp, 6, 6, 4210752);
      temp = "Voltage";
      this.fontRendererObj.drawString(temp, (69 - this.fontRendererObj.getStringWidth(temp)) / 2 + 12, 24, 5635925);
      temp = String.valueOf(this.Tileentity.getVoltage());
      this.fontRendererObj.drawString(temp, (69 - this.fontRendererObj.getStringWidth(temp)) / 2 + 12, 44, 5635925);
      temp = "Ampere";
      this.fontRendererObj.drawString(temp, (69 - this.fontRendererObj.getStringWidth(temp)) / 2 + 95, 24, 5635925);
      temp = String.valueOf(this.Tileentity.getAmpere());
      this.fontRendererObj.drawString(temp, (69 - this.fontRendererObj.getStringWidth(temp)) / 2 + 95, 44, 5635925);
   }

   protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(Texture);
      int k = (this.width - this.xSize) / 2;
      int l = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
   }

   public void actionPerformed(GuiButton id) {
      switch(id.id) {
      case 0:
         PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(this.Tileentity.xCoord, (short)this.Tileentity.yCoord, this.Tileentity.zCoord, this.getClass(), new Object[]{Integer.valueOf(1)}));
         break;
      case 1:
         PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(this.Tileentity.xCoord, (short)this.Tileentity.yCoord, this.Tileentity.zCoord, this.getClass(), new Object[]{Integer.valueOf(0)}));
         break;
      case 2:
         PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(this.Tileentity.xCoord, (short)this.Tileentity.yCoord, this.Tileentity.zCoord, this.getClass(), new Object[]{Integer.valueOf(3)}));
         break;
      case 3:
         PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(this.Tileentity.xCoord, (short)this.Tileentity.yCoord, this.Tileentity.zCoord, this.getClass(), new Object[]{Integer.valueOf(2)}));
         break;
      case 4:
         PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(this.Tileentity.xCoord, (short)this.Tileentity.yCoord, this.Tileentity.zCoord, this.getClass(), new Object[]{Integer.valueOf(5)}));
         break;
      case 5:
         PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(this.Tileentity.xCoord, (short)this.Tileentity.yCoord, this.Tileentity.zCoord, this.getClass(), new Object[]{Integer.valueOf(4)}));
         break;
      case 100:
         PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(this.Tileentity.xCoord, (short)this.Tileentity.yCoord, this.Tileentity.zCoord, this.getClass(), new Object[]{Integer.valueOf(100)}));
      }

   }

   public static class GuiChangeButton extends GuiButton {
      private boolean right;

      public GuiChangeButton(int id, int x, int y, boolean isRight) {
         super(id, x, y, 7, 9, "");
         this.right = isRight;
      }

      public void drawButton(Minecraft mc, int xMouse, int yMouse) {
         if(this.visible) {
            boolean onMouse = this.xPosition <= xMouse && xMouse < this.xPosition + this.width && this.yPosition <= yMouse && yMouse < this.yPosition + this.height;
            mc.getTextureManager().bindTexture(GuiSteamGenerator.Texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if(this.right) {
               if(onMouse) {
                  this.drawTexturedModalRect(this.xPosition, this.yPosition, 183, 18, 7, 9);
               } else {
                  this.drawTexturedModalRect(this.xPosition, this.yPosition, 183, 9, 7, 9);
               }
            } else if(onMouse) {
               this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, 18, 7, 9);
            } else {
               this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, 9, 7, 9);
            }
         }

      }
   }

   public static class GuiGreenButton extends GuiButton {
      private boolean right;

      public GuiGreenButton(int id, int x, int y, boolean isRight) {
         super(id, x, y, 7, 9, "");
         this.right = isRight;
      }

      public void drawButton(Minecraft mc, int xMouse, int yMouse) {
         if(this.visible) {
            float p = 0.00390625F;
            int r = this.right?183:176;
            mc.getTextureManager().bindTexture(GuiSteamGenerator.Texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double)this.xPosition, (double)(this.yPosition + this.height), (double)this.zLevel, (double)((float)r * p), (double)(9.0F * p));
            tessellator.addVertexWithUV((double)(this.xPosition + this.width), (double)(this.yPosition + this.height), (double)this.zLevel, (double)((float)(r + 7) * p), (double)(9.0F * p));
            tessellator.addVertexWithUV((double)(this.xPosition + this.width), (double)this.yPosition, (double)this.zLevel, (double)((float)(r + 7) * p), 0.0D);
            tessellator.addVertexWithUV((double)this.xPosition, (double)this.yPosition, (double)this.zLevel, (double)((float)r * p), 0.0D);
            tessellator.draw();
         }

      }
   }

   public static class GuiInvisibleButton extends GuiButton {
      public GuiInvisibleButton(int id, int x, int y) {
         super(id, x, y, 9, 9, "");
      }

      public void drawButton(Minecraft mc, int xMouse, int yMouse) {
         boolean onMouse = this.xPosition <= xMouse && xMouse < this.xPosition + this.width && this.yPosition <= yMouse && yMouse < this.yPosition + this.height;
         if(onMouse) {
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + 9, this.yPosition + 9, GTMUtil.rgba(255, 255, 255, 128));
         }

      }
   }
}
