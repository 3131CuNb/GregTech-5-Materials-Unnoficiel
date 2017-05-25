package gtmaterials.item;

import gtmaterials.GTMaterials;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class ItemMatterArmor extends ItemArmor {
   public ItemMatterArmor(int type) {
      super(EnumHelper.addArmorMaterial("Matter", 44, new int[]{100, 100, 100, 100}, 45), 0, type);
      this.setMaxDamage(-1);
      this.setNoRepair();
      this.setCreativeTab(GTMaterials.TAB);
      this.setTextureName("gtmaterials:armor/" + (new String[]{"helmet", "chestplate", "leggings", "boots"})[type] + "_over");
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
      return "gtmaterials:textures/armor/" + (this.armorType == 2?"2":"1") + ".png";
   }

   public String getUnlocalizedName(ItemStack itemstack) {
      return "GTMaterials:Armor_" + this.armorType;
   }

   public boolean isItemTool(ItemStack p_77616_1_) {
      return true;
   }

   public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
      list.add("Damage proof");
   }
}
