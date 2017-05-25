package gtmaterials;

import appeng.api.AEApi;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import gtmaterials.GTMaterials;
import gtmaterials.appeng.GTMStorageHandler;
import gtmaterials.block.*;
import gtmaterials.block.tileentity.*;
import gtmaterials.item.ItemDataDisk;
import gtmaterials.item.ItemFabricatMeter;
import gtmaterials.item.ItemMatter;
import gtmaterials.item.ItemMatterArmor;
import gtmaterials.item.ItemMatterPlate;
import gtmaterials.old.OldID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GTMItem {
   public static Block GregOres;
   public static Block MatterOres;
   public static Block MatterFabricator;
   public static Block MatterGenerator;
   public static Block AdvancedMiner;
   public static Block BigChest;
   public static Block ToolWorkbench;
   public static Block SteamGenerator;
   public static Block AccelMachine;
   public static Block BlockABC;
   public static Item Matter;
   public static Item MatterPlate;
   public static Item FabricatMeter;
   public static Item DataDisk;
   public static Item ArmorHelmet;
   public static Item ArmorChestplate;
   public static Item ArmorLeggings;
   public static Item ArmorBoots;

   public static void init() {
	   BlockABC = (new BlockABC(Material.wood)).setBlockName("GTMaterials:ABCBlock").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB);
      GregOres = (new BlockGregOres(Material.rock)).setBlockName("GTMaterials:GregOres").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB);
      MatterOres = (new BlockMatterOres(Material.rock)).setBlockName("GTMaterials:MatterOres").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB);
      MatterFabricator = (new BlockMatterFabricator(Material.iron)).setBlockName("GTMaterials:MatterFabricator").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB);
      MatterGenerator = (new BlockMatterGenerator(Material.iron)).setBlockName("GTMaterials:MatterGenerator").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB).setStepSound(Block.soundTypeMetal);
      ToolWorkbench = (new BlockToolWorkbench(Material.rock)).setBlockName("GTMaterials:ToolWorkbench").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB);
      SteamGenerator = (new BlockSteamGenerator(Material.rock)).setBlockName("GTMaterials:SteamGenerator").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB);
      AccelMachine = (new BlockAccelMachine(Material.wood)).setBlockName("GTMaterials:AccelMachine").setCreativeTab(GTMaterials.TAB);
      AdvancedMiner = (new BlockAdvancedMiner(Material.iron)).setBlockName("GTMaterials:AdvancedMiner").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB).setStepSound(Block.soundTypeMetal);
      BigChest = (new BlockBigChest(Material.iron)).setBlockName("GTMaterials:BigChest").setHardness(1.75F).setResistance(10.75F).setCreativeTab(GTMaterials.TAB).setStepSound(Block.soundTypeMetal);
      Matter = new ItemMatter();
      MatterPlate = new ItemMatterPlate();
      FabricatMeter = new ItemFabricatMeter();
      DataDisk = new ItemDataDisk();
      ArmorHelmet = new ItemMatterArmor(0);
      ArmorChestplate = new ItemMatterArmor(1);
      ArmorLeggings = new ItemMatterArmor(2);
      ArmorBoots = new ItemMatterArmor(3);
      GameRegistry.registerBlock(BlockABC, GTMItem.BasicItemBlock.class,"GTMaterials:ABCBlock");
      GameRegistry.registerBlock(GregOres, GTMItem.BasicItemBlock.class, "GregTech_Ores");
      GameRegistry.registerBlock(MatterOres, GTMItem.BasicItemBlock.class, "Matter_Ores");
      GameRegistry.registerBlock(MatterFabricator, GTMItem.BasicItemBlock.class, "Matter_Fabricator");
      GameRegistry.registerBlock(MatterGenerator, GTMItem.BasicItemBlock.class, "Matter_Generator");
      GameRegistry.registerBlock(ToolWorkbench, GTMItem.BasicItemBlock.class, "Tool_Workbench");
      GameRegistry.registerBlock(SteamGenerator, "Steam_Generator");
      GameRegistry.registerBlock(AccelMachine, "Accel_Machine");
      GameRegistry.registerBlock(AdvancedMiner, "Advanced_Miner");
      GameRegistry.registerBlock(BigChest, "Big_Chest");
      GameRegistry.registerItem(Matter, "Matter");
      GameRegistry.registerItem(MatterPlate, "Matter_Plate");
      GameRegistry.registerItem(FabricatMeter, "Fabricat_Meter");
      GameRegistry.registerItem(DataDisk, "Data_Disk");
      GameRegistry.registerItem(ArmorHelmet, "Armor_Helmet");
      GameRegistry.registerItem(ArmorChestplate, "Armor_Chestplate");
      GameRegistry.registerItem(ArmorLeggings, "Armor_Leggings");
      GameRegistry.registerItem(ArmorBoots, "Armor_Boots");
      GameRegistry.registerTileEntity(TileEntityBlockABC.class,"TileEntityBlockABC");
      GameRegistry.registerTileEntity(TileEntityMatterFabricator.class, "TileEntityMatterFabricator");
      GameRegistry.registerTileEntity(TileEntityMatterFabricator.TileEntityOreFabricator.class, "TileEntityOreFabricator");
      GameRegistry.registerTileEntity(TileEntityMatterFabricator.TileEntityMaterialFabricator.class, "TileEntityMaterialFabricator");
      GameRegistry.registerTileEntity(TileEntityMatterFabricator.TileEntityMachineFabricator.class, "TileEntityMachineFabricator");
      GameRegistry.registerTileEntity(TileEntityMatterGenerator.class, "TileEntityMatterGenerator");
      GameRegistry.registerTileEntity(TileEntitySteamGenerator.class, "TileEntitySteamGenerator");
      GameRegistry.registerTileEntity(TileEntityAdvancedMiner.class, "TileEntityAdvancedMiner");
      GameRegistry.registerTileEntity(TileEntityBigChest.class, "TileEntityBigChest");
      OldID.register();
      if(Loader.isModLoaded("appliedenergistics2")) {
         AEApi.instance().registries().cell().addCellHandler(new GTMStorageHandler());
      }

   }

   public static class BasicItemBlock extends ItemBlock {
      public BasicItemBlock(Block p_i45328_1_) {
         super(p_i45328_1_);
         this.setHasSubtypes(true);
         this.setMaxDamage(0);
      } 

      public int getMetadata(int metadata) {
         return metadata;
      }

      public String getUnlocalizedName(ItemStack parItem1) {
         return this.getUnlocalizedName() + "_" + parItem1.getItemDamage();
      }
   }
}
