package gtmaterials.block.container;

import gregtech.api.enums.Materials;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gtmaterials.GTMItem;
import gtmaterials.GTMUtil;
import gtmaterials.recipe.Recipe;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerToolWorkbench extends Container {
   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
   public IInventory craftResult = new InventoryCraftResult();
   private World worldObj;
   private int posX;
   private int posY;
   private int posZ;
   private int metadata;

   public ContainerToolWorkbench(World world, int x, int y, int z, EntityPlayer player) {
      this.worldObj = world;
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.metadata = world.getBlockMetadata(x, y, z);
      this.addSlotToContainer(new SlotCrafting(player, this.craftMatrix, this.craftResult, 0, 124, 35));

      for(int l = 0; l < 3; ++l) {
         for(int i1 = 0; i1 < 3; ++i1) {
            this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
         }
      }

      for(int var8 = 0; var8 < 3; ++var8) {
         for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlotToContainer(new Slot(player.inventory, i1 + var8 * 9 + 9, 8 + i1 * 18, 84 + var8 * 18));
         }
      }

      for(int var9 = 0; var9 < 9; ++var9) {
         this.addSlotToContainer(new Slot(player.inventory, var9, 8 + var9 * 18, 142));
      }

      this.onCraftMatrixChanged(this.craftMatrix);
   }

   public void onCraftMatrixChanged(IInventory inventory) {
      if(this.metadata == 0) {
         this.craftResult.setInventorySlotContents(0, ContainerToolWorkbench.ToolAnalysis.get(this.craftMatrix));
      } else if(this.metadata == 1) {
         this.craftResult.setInventorySlotContents(0, Recipe.getInstance().getItem(this.craftMatrix));
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer player, int number) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.inventorySlots.get(number);
      if(slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if(number == 0) {
            if(!this.mergeItemStack(itemstack1, 10, 46, true)) {
               return null;
            }

            slot.onSlotChange(itemstack1, itemstack);
         } else if(number >= 10 && number < 37) {
            if(!this.mergeItemStack(itemstack1, 37, 46, false)) {
               return null;
            }
         } else if(number >= 37 && number < 46) {
            if(!this.mergeItemStack(itemstack1, 10, 37, false)) {
               return null;
            }
         } else if(!this.mergeItemStack(itemstack1, 10, 46, false)) {
            return null;
         }

         if(itemstack1.stackSize == 0) {
            slot.putStack((ItemStack)null);
         } else {
            slot.onSlotChanged();
         }

         if(itemstack1.stackSize == itemstack.stackSize) {
            return null;
         }

         slot.onPickupFromSlot(player, itemstack1);
      }

      return itemstack;
   }

   public boolean canInteractWith(EntityPlayer p_75145_1_) {
      return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != GTMItem.ToolWorkbench?false:p_75145_1_.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
   }

   public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_) {
      return p_94530_2_.inventory != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
   }

   private static class ToolAnalysis {
      public static ItemStack get(IInventory inventory) {
         ArrayList<String> list = new ArrayList();

         for(int i = 0; i < 9; ++i) {
            String str = GTMUtil.getOreName(inventory.getStackInSlot(i));
            if(!str.equals("Unknown")) {
               list.add(str);
            }
         }

         return list.size() == 2?getTool((String)list.get(0), (String)list.get(1)):null;
      }

      private static ItemStack getTool(String toolhead, String stick) {
         int damage = -1;

         try {
            damage = getToolDamage(toolhead);
         } catch (Throwable var5) {
            ;
         }

         if(toolhead.indexOf("Arrow") == -1) {
            Materials HeadMaterial = getToolMaterial(toolhead);
            Materials StickMaterial = getStickMaterial(stick);
            if(damage != -1 && HeadMaterial != null && StickMaterial != null) {
               return GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(damage, 1, HeadMaterial, StickMaterial, (long[])null);
            }
         }

         return null;
      }

      private static int getToolDamage(String ore) throws Throwable {
         if(ore.startsWith("toolHead")) {
            String mat = getMaterialName(ore);
            String ID = ore.replace("toolHead", "").replace(mat, "").toUpperCase();
            if(ID.equals("HAMMER")) {
               ID = "HARDHAMMER";
            } else if(ID.equals("DRILL") || ID.equals("CHAINSAW")) {
               int l = Materials.get(mat).mDurability;
               if(l < 512) {
                  ID = ID + "_LV";
               } else if(l >= 512 && l < 1280) {
                  ID = ID + "_MV";
               } else if(l >= 1280) {
                  ID = ID + "_HV";
               }
            }

            Class<?> clazz = Class.forName("gregtech.common.items.GT_MetaGenerated_Tool_01");
            Short f1 = null;

            try {
               f1 = (Short)clazz.getField(ID).get((Object)null);
            } catch (NullPointerException var6) {
               f1 = (Short)clazz.getDeclaredField(ID).get((Object)null);
            }

            if(f1 != null) {
               return f1.shortValue();
            }
         }

         return -1;
      }

      private static Materials getToolMaterial(String ore) {
         Materials mat = Materials._NULL;
         if(ore.startsWith("toolHead")) {
            mat = Materials.get(getMaterialName(ore));
         }

         return mat != Materials._NULL?mat:null;
      }

      private static Materials getStickMaterial(String ore) {
         Materials mat = null;
         if(ore.equals("stick")) {
            mat = Materials.get("Wood");
         } else if(ore.startsWith("stick") && ore.length() > 5) {
            mat = Materials.get(ore.replace("stick", ""));
         } else if(ore.startsWith("rod")) {
            mat = Materials.get(ore.replace("rod", ""));
         }

         return mat != Materials._NULL?mat:null;
      }

      private static String getMaterialName(String name) {
         char[] str2 = name.toCharArray();
         int count = 0;
         int y = 0;
         int x = name.indexOf("BuzzSaw") == -1 && name.indexOf("UniversalSpade") == -1?2:3;

         for(int i = 0; i < str2.length; ++i) {
            if(Character.isUpperCase(str2[i])) {
               if(y == x) {
                  count = i;
               }

               ++y;
            }
         }

         String str3 = name.substring(count, name.length());
         return str3;
      }
   }
}
