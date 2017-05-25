package gtmaterials.old;

import cpw.mods.fml.common.registry.GameRegistry;
import gtmaterials.GTMItem;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class OldID {
   public static void register() {
      GameRegistry.registerBlock(new OldID.OldMultiBlock(), OldID.OldItemBlock.class, "GregTech_NosOres");
      GameRegistry.registerBlock(new OldID.OldMultiBlock(), OldID.OldItemBlock.class, "Solar");
      GameRegistry.registerBlock(new OldID.OldMultiBlock(), OldID.OldItemBlock.class, "ToolWorkbench");
      GameRegistry.registerBlock(new OldID.OldMultiBlock(), OldID.OldItemBlock.class, "BusHatch");
      GameRegistry.registerBlock(new OldID.OldBlock(), "GregQuarry");
      GameRegistry.registerBlock(new OldID.OldBlock(), "GregQuarryChest");
      GameRegistry.registerBlock(new OldID.OldBlock(), "RecipeMachine");
      GameRegistry.registerBlock(new OldID.OldBlock(), "SteamGenerator");
      GameRegistry.registerBlock(new OldID.OldBlock(), "MatterWorkbench");
      GameRegistry.registerBlock(new OldID.OldBlock(), "AccelTorch");
      GameRegistry.registerItem(new OldID.OldItem(), "GregTech_Materials");
      GameRegistry.registerItem(new OldID.OldItem(), "SolarParts");
      GameRegistry.registerItem(new OldID.OldItem(), "FluidRound");
      GameRegistry.registerItem(new OldID.OldItem(), "DataDisk");
      GameRegistry.registerItem(new OldID.OldItem(), "GTMaterials:Armor_0");
      GameRegistry.registerItem(new OldID.OldItem(), "GTMaterials:Armor_1");
      GameRegistry.registerItem(new OldID.OldItem(), "GTMaterials:Armor_2");
      GameRegistry.registerItem(new OldID.OldItem(), "GTMaterials:Armor_3");
   }

   public static void recipe() {
      for(int i = 0; i < 15; ++i) {
         GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.GregOres, 1, i), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "GregTech_NosOres"), 1, i)});
      }

      for(int i = 0; i < 2; ++i) {
         GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.ToolWorkbench, 1, i), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "ToolWorkbench"), 1, i)});
      }

      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.AdvancedMiner, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "GregQuarry"), 1)});
      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.BigChest, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "GregQuarryChest"), 1)});
      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.SteamGenerator, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "SteamGenerator"), 1)});
      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.AccelMachine, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "AccelTorch"), 1)});
      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.DataDisk, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "DataDisk"), 1)});
      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.ArmorHelmet, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "Armor_0"), 1)});
      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.ArmorChestplate, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "Armor_1"), 1)});
      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.ArmorLeggings, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "Armor_2"), 1)});
      GameRegistry.addShapelessRecipe(new ItemStack(GTMItem.ArmorBoots, 1), new Object[]{new ItemStack(GameRegistry.findItem("GTMaterials", "Armor_3"), 1)});
   }

   public static class OldBlock extends Block {
      public OldBlock() {
         super(Material.rock);
         this.setBlockTextureName("gtmaterials:troll");
         this.setHardness(1.5F);
      }

      public int quantityDropped(int metadata, int fortune, Random rand) {
         return 1;
      }

      public Item getItemDropped(int metadata, Random rand, int fortune) {
         return Item.getItemFromBlock(this);
      }

      public int damageDropped(int metadata) {
         return 0;
      }
   }

   public static class OldItem extends Item {
      public OldItem() {
         this.setTextureName("gtmaterials:troll");
      }
   }

   public static class OldItemBlock extends ItemBlock {
      public OldItemBlock(Block block) {
         super(block);
         this.setHasSubtypes(true);
      }

      public int getMetadata(int metadata) {
         return metadata;
      }
   }

   public static class OldMultiBlock extends OldID.OldBlock {
      public int damageDropped(int metadata) {
         return metadata;
      }
   }
}
