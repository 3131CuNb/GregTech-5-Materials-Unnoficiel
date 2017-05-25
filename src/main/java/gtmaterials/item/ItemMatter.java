package gtmaterials.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMaterials;
import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMatter extends Item {
   private int[] color;

   public ItemMatter() {
      this.color = new int[]{16711680, 16776960, '\uff00', 255, MapColor.woodColor.colorValue, MapColor.stoneColor.colorValue, MapColor.ironColor.colorValue, MapColor.diamondColor.colorValue};
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setCreativeTab(GTMaterials.TAB);
      this.setTextureName("gtmaterials:matter/matter");
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "GTMaterials:Matter_" + par1ItemStack.getItemDamage();
   }

   @SideOnly(Side.CLIENT)
   public int getColorFromItemStack(ItemStack itemstack, int p_82790_2_) {
      return this.color[itemstack.getItemDamage() < 8?itemstack.getItemDamage():0];
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int j = 0; j < this.color.length; ++j) {
         par3List.add(new ItemStack(par1, 1, j));
      }

   }
}
