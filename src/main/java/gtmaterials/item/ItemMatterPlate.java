package gtmaterials.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMaterials;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMatterPlate extends Item {
   public IIcon[] icon = new IIcon[3];

   public ItemMatterPlate() {
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setMaxStackSize(16);
      this.setCreativeTab(GTMaterials.TAB);
   }

   public IIcon getIconFromDamage(int par1) {
      return this.icon[par1];
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "GTMaterials:MatterPlate_" + par1ItemStack.getItemDamage();
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int j = 0; j < 3; ++j) {
         par3List.add(new ItemStack(par1, 1, j));
      }

   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      this.icon[0] = par1IconRegister.registerIcon("gtmaterials:matter_plate");
      this.icon[1] = par1IconRegister.registerIcon("gtmaterials:matter_plate_trans");
      this.icon[2] = par1IconRegister.registerIcon("gtmaterials:matter_plate_over");
   }
}
