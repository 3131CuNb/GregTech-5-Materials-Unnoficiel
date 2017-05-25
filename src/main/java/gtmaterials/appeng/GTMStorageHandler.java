package gtmaterials.appeng;

import appeng.api.implementations.tiles.IChestOrDrive;
import appeng.api.storage.ICellHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.ISaveProvider;
import appeng.api.storage.StorageChannel;
import appeng.client.texture.ExtraBlockTextures;
import appeng.core.sync.GuiBridge;
import appeng.tile.AEBaseTile;
import appeng.util.Platform;
import gtmaterials.GTMItem;
import gtmaterials.appeng.GTMMassEnergyInventoryHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GTMStorageHandler implements ICellHandler {
   public boolean isCell(ItemStack is) {
      return is != null && is.getItem() == GTMItem.DataDisk;
   }

   public IMEInventoryHandler getCellInventory(ItemStack is, ISaveProvider host, StorageChannel channel) {
      return is != null && is.getItem() == GTMItem.DataDisk && channel == StorageChannel.ITEMS?GTMMassEnergyInventoryHandler.get(is):null;
   }

   public IIcon getTopTexture_Light() {
      return ExtraBlockTextures.BlockMEChestItems_Light.getIcon();
   }

   public IIcon getTopTexture_Medium() {
      return ExtraBlockTextures.BlockMEChestItems_Medium.getIcon();
   }

   public IIcon getTopTexture_Dark() {
      return ExtraBlockTextures.BlockMEChestItems_Dark.getIcon();
   }

   public void openChestGui(EntityPlayer player, IChestOrDrive chest, ICellHandler cellHandler, IMEInventoryHandler inv, ItemStack is, StorageChannel chan) {
      Platform.openGUI(player, (AEBaseTile)chest, chest.getUp(), GuiBridge.GUI_ME);
   }

   public int getStatusForCell(ItemStack is, IMEInventory handler) {
      return 3;
   }

   public double cellIdleDrain(ItemStack is, IMEInventory handler) {
      return 1.0D;
   }
}
