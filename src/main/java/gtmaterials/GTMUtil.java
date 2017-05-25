package gtmaterials;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class GTMUtil {
   public static ItemStack copyAmount(ItemStack item, int i) {
      if(item != null && i != 0) {
         ItemStack s = item.copy();
         s.stackSize = i;
         return s;
      } else {
         return null;
      }
   }

   public static ItemStack resetAmount(ItemStack item, int i) {
      if(item != null && i != 0) {
         ItemStack s = new ItemStack(item.getItem(), i, item.getItemDamage());
         return s;
      } else {
         return null;
      }
   }

   public static String getOreName(ItemStack itemstack) {
      return OreDictionary.getOreName(OreDictionary.getOreID(itemstack));
   }

   public static int rgba(int r, int g, int b, int a) {
      return a << 24 | r << 16 | g << 8 | b;
   }

   public static Container nullContainer() {
      return new Container() {
         public boolean canInteractWith(EntityPlayer player) {
            return true;
         }
      };
   }

   public static NBTTagCompound writeToNBT(ItemStack item, NBTTagCompound nbt) {
      if(item != null && nbt != null) {
         nbt.setInteger("id", Item.getIdFromItem(item.getItem()));
         nbt.setInteger("Count", item.stackSize);
         nbt.setInteger("Damage", item.getItemDamage());
         if(item.hasTagCompound()) {
            nbt.setTag("tag", item.getTagCompound());
         }

         return nbt;
      } else {
         return new NBTTagCompound();
      }
   }

   public static ItemStack loadItemStackFromNBT(NBTTagCompound nbt) {
      if(nbt == null) {
         return null;
      } else {
         ItemStack item = null;
         if(nbt.hasKey("id") && nbt.hasKey("Count") && nbt.hasKey("Count")) {
            item = new ItemStack(Item.getItemById(nbt.getInteger("id")), nbt.getInteger("Count"), nbt.getInteger("Damage"));
         }

         if(item != null && nbt.hasKey("tag")) {
            item.setTagCompound(nbt.getCompoundTag("tag"));
         }

         return item;
      }
   }
}
