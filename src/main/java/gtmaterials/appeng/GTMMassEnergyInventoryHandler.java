package gtmaterials.appeng;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;
import appeng.util.item.AEItemStack;
import gtmaterials.GTMUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GTMMassEnergyInventoryHandler implements IMEInventoryHandler<IAEItemStack> {
   final IItemList<IAEItemStack> itemListCache = AEApi.instance().storage().createItemList();
   private ItemStack media;

   protected GTMMassEnergyInventoryHandler(ItemStack is) {
      if(is != null && is.hasTagCompound()) {
         NBTTagCompound data = is.getTagCompound();
         this.media = is;
         if(data.hasKey("ItemData")) {
            NBTTagList list = data.getTagList("ItemData", 10);

            for(int i = 0; i < list.tagCount(); ++i) {
               ItemStack item = GTMUtil.loadItemStackFromNBT(list.getCompoundTagAt(i));
               IAEItemStack aeItem = AEItemStack.create(item);
               this.itemListCache.add(aeItem);
            }
         }
      }

   }

   public static IMEInventoryHandler get(ItemStack is) {
      return new GTMMassEnergyInventoryHandler(is);
   }

   public IAEItemStack injectItems(IAEItemStack request, Actionable mode, BaseActionSource src) {
      return null;
   }

   public IAEItemStack extractItems(IAEItemStack request, Actionable mode, BaseActionSource src) {
      if(request == null) {
         return null;
      } else {
         long size = Math.min(2147483647L, request.getStackSize());
         IAEItemStack Results = null;
         IAEItemStack l = (IAEItemStack)this.itemListCache.findPrecise(request);
         if(l != null) {
            Results = l.copy();
            if(l.getStackSize() <= size) {
               Results.setStackSize(l.getStackSize());
               if(mode == Actionable.MODULATE) {
                  l.setStackSize(0L);
                  this.saveItemList(Item.getIdFromItem(l.getItem()), l.getItemDamage(), 0);
               }
            } else {
               Results.setStackSize(size);
               if(mode == Actionable.MODULATE) {
                  l.setStackSize(l.getStackSize() - size);
                  this.saveItemList(Item.getIdFromItem(l.getItem()), l.getItemDamage(), (int)(l.getStackSize() - size));
               }
            }

            return Results;
         } else {
            return null;
         }
      }
   }

   private void saveItemList(int id, int damage, int size) {
      if(this.media != null && this.media.hasTagCompound() && this.media.getTagCompound().hasKey("ItemData")) {
         NBTTagList list = this.media.getTagCompound().getTagList("ItemData", 10);

         for(int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound nbt = list.getCompoundTagAt(i);
            if(nbt.getInteger("id") == id && nbt.getInteger("Damage") == damage) {
               nbt.setInteger("Count", size);
               return;
            }
         }
      }

   }

   public IItemList<IAEItemStack> getAvailableItems(IItemList out) {
      for(IAEItemStack ais : this.itemListCache) {
         out.add(ais);
      }

      return out;
   }

   public StorageChannel getChannel() {
      return StorageChannel.ITEMS;
   }

   public AccessRestriction getAccess() {
      return AccessRestriction.READ;
   }

   public boolean isPrioritized(IAEItemStack input) {
      return this.itemListCache.findPrecise(input) != null;
   }

   public boolean canAccept(IAEItemStack input) {
      return this.itemListCache.findPrecise(input) != null;
   }

   public int getPriority() {
      return 0;
   }

   public int getSlot() {
      return this.itemListCache.size();
   }

   public boolean validForPass(int i) {
      return true;
   }
}
