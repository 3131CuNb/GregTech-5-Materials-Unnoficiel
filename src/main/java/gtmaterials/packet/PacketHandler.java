package gtmaterials.packet;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gtmaterials.GTMList;
import gtmaterials.block.BlockABC;
import gtmaterials.block.BlockAccelMachine;
import gtmaterials.block.gui.GuiAdvancedMiner;
import gtmaterials.block.gui.GuiSteamGenerator;
import gtmaterials.block.tileentity.TileEntityAdvancedMiner;
import gtmaterials.block.tileentity.TileEntitySteamGenerator;
import ic2.core.Ic2Items;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class PacketHandler {
   public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("GTMaterials");
   private static ArrayList<Class<?>> classToID = Lists.newArrayList();

   public static void init() {
      classToID.add(BlockAccelMachine.class);
      classToID.add(GuiSteamGenerator.class);
      classToID.add(GuiAdvancedMiner.class);
      classToID.add(GTMList.class);
      classToID.add(BlockABC.class);
      INSTANCE.registerMessage(PacketHandler.GTMMessageHandler.class, PacketHandler.GTMPacket.class, 0, Side.SERVER);
   }

   public static int getType(Class<?> clazz) {
      return !classToID.contains(clazz)?-1:classToID.indexOf(clazz);
   }

   public static class GTMMessageHandler implements IMessageHandler<PacketHandler.GTMPacket, IMessage> {
      public IMessage onMessage(PacketHandler.GTMPacket message, MessageContext ctx) {
         EntityPlayer player = ctx.getServerHandler().playerEntity;
         World world = player.worldObj;
         if(message.type != -1) {
            switch(message.type) {
            case 0:
               this.accelMachine(world, message.x, message.y, message.z);
               break;
            case 1:
               this.steamChange(world, player, message.x, message.y, message.z, ((Integer)message.object[0]).intValue());
               break;
            case 2:
               this.minerMode(world, player, message.x, message.y, message.z, ((Integer)message.object[0]).intValue());
               break;
            case 3:
               this.itemList((String)message.object[0]);
            }
         }

         return null;
      }

      private void itemList(String nbt) {
         try {
            NBTTagList list = (NBTTagList)JsonToNBT.func_150315_a(nbt);

            for(int i = 0; i < list.tagCount(); ++i) {
               ItemStack item = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
               GTMList.gregtechItems.add(item);
            }
         } catch (Throwable var5) {
            ;
         }

      }

      private void minerMode(World world, EntityPlayer player, int x, short y, int z, int intValue) {
         TileEntityAdvancedMiner miner = (TileEntityAdvancedMiner)world.getTileEntity(x, y, z);
         miner.setMode(intValue);
      }

      private void steamChange(World world, EntityPlayer player, int x, short y, int z, int mode) {
         TileEntitySteamGenerator steam = (TileEntitySteamGenerator)world.getTileEntity(x, y, z);
         if(mode != 100) {
            switch(mode) {
            case 0:
               steam.upLevelVoltage();
               break;
            case 1:
               steam.downLevelVoltage();
               break;
            case 2:
               steam.addAmpere();
               break;
            case 3:
               steam.decrAmpere();
               break;
            case 4:
               steam.changeRF();
               break;
            case 5:
               steam.changeSteam();
            }
         } else {
            InventoryPlayer inv = player.inventory;
            int s = 0;

            for(ItemStack item : inv.mainInventory) {
               if(item != null && item.isItemEqual(Ic2Items.scrapBox)) {
                  NBTTagCompound nbt = new NBTTagCompound();
                  NBTTagCompound display = new NBTTagCompound();
                  NBTTagList list = new NBTTagList();
                  list.appendTag(new NBTTagString("Updated"));
                  display.setTag("Lore", list);
                  nbt.setBoolean("update", true);
                  nbt.setTag("display", display);
                  item.setTagCompound(nbt);
                  player.addChatMessage(new ChatComponentText("Updated your scrap box : slot " + s));
               }

               ++s;
            }
         }

      }

      private void accelMachine(World world, int x, int y, int z) {
         int size = 3;

         for(int x1 = -size; x1 <= size; ++x1) {
            for(int y1 = -size; y1 <= size; ++y1) {
               for(int z1 = -size; z1 <= size; ++z1) {
                  TileEntity tileentity = world.getTileEntity(x + x1, y + y1, z + z1);
                  if(tileentity != null && tileentity instanceof IGregTechTileEntity) {
                     IMetaTileEntity metatileentity = ((IGregTechTileEntity)tileentity).getMetaTileEntity();
                     if(metatileentity != null) {
                        int high = 0;
                        if(metatileentity instanceof GT_MetaTileEntity_BasicMachine) {
                           GT_MetaTileEntity_BasicMachine single = (GT_MetaTileEntity_BasicMachine)metatileentity;
                           high = single.mMaxProgresstime > 200?single.mMaxProgresstime / 200:1;
                           if(single.getEUVar() > 0L && single.mMaxProgresstime > single.mProgresstime && single.mMaxProgresstime != single.mProgresstime) {
                              single.mProgresstime += high;
                           }
                        } else if(metatileentity instanceof GT_MetaTileEntity_MultiBlockBase) {
                           GT_MetaTileEntity_MultiBlockBase multi = (GT_MetaTileEntity_MultiBlockBase)metatileentity;
                           high = multi.mMaxProgresstime > 200?multi.mMaxProgresstime / 200:1;
                           if(multi.mEUt > 0 && multi.mMaxProgresstime > multi.mProgresstime && multi.mMaxProgresstime != multi.mProgresstime) {
                              multi.mProgresstime += high;
                           }
                        }
                     }
                  }
               }
            }
         }

      }
   }

   public static class GTMPacket implements IMessage {
      public int x;
      public int z;
      public int type;
      public short y;
      public Object[] object;

      public GTMPacket() {
      }

      public GTMPacket(int x, short y, int z, Class<?> type, Object... objects) {
         this.x = x;
         this.y = y;
         this.z = z;
         this.type = PacketHandler.getType(type);
         this.object = objects;
      }

      public void fromBytes(ByteBuf buf) {
         this.x = buf.readInt();
         this.y = buf.readShort();
         this.z = buf.readInt();
         this.type = buf.readInt();
         switch(this.type) {
         case 0:
         default:
            break;
         case 1:
            this.object = new Object[]{Integer.valueOf(buf.readInt())};
            break;
         case 2:
            this.object = new Object[]{Integer.valueOf(buf.readInt())};
            break;
         case 3:
            char[] nbtstr = new char[buf.readInt()];

            for(int i = 0; i < nbtstr.length; ++i) {
               nbtstr[i] = buf.readChar();
            }

            this.object = new Object[]{String.valueOf(nbtstr)};
         }

      }

      public void toBytes(ByteBuf buf) {
         buf.writeInt(this.x);
         buf.writeShort(this.y);
         buf.writeInt(this.z);
         buf.writeInt(this.type);
         switch(this.type) {
         case 0:
         default:
            break;
         case 1:
            buf.writeInt(((Integer)this.object[0]).intValue());
            break;
         case 2:
            buf.writeInt(((Integer)this.object[0]).intValue());
            break;
         case 3:
            String nbtstr = (String)this.object[0];
            buf.writeInt(nbtstr.length());

            for(char c : nbtstr.toCharArray()) {
               buf.writeChar(c);
            }
         }

      }
   }
}
