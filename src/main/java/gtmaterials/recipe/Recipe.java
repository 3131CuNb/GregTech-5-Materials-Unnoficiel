package gtmaterials.recipe;

import advsolar.utils.MTRecipeManager;
import appeng.api.AEApi;
import appeng.api.features.IGrinderEntry;
import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.ToolDictNames;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gtmaterials.GTMItem;
import gtmaterials.GTMList;
import gtmaterials.GTMUtil;
import gtmaterials.old.OldID;
import ic2.core.Ic2Items;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class Recipe {
   private final HashMap<ArrayList<Integer>, ItemStack> MAPPING = new HashMap();
   private static final Recipe INSTANCE = new Recipe();

   public static void init() {
   }

   public static void postInit() {
      String[][] keys = new String[][]{(String[])GTMList.oreBlocks.keySet().toArray(new String[GTMList.oreBlocks.size()]), (String[])GTMList.oreItems.keySet().toArray(new String[GTMList.oreItems.size()])};

      for(int i = 0; i < GTMList.oreBlocks.size() + GTMList.oreItems.size(); ++i) {
         String[] recipe = ((String)GTMList.recipePattern.get(i)).split(",");
         if(i < GTMList.oreBlocks.size()) {
            INSTANCE.addRecipe(GTMList.getBlock(keys[0][i], 2), new Object[]{recipe[0], recipe[1], recipe[2], Character.valueOf('A'), Integer.valueOf(0), Character.valueOf('B'), Integer.valueOf(1), Character.valueOf('C'), Integer.valueOf(2), Character.valueOf('D'), Integer.valueOf(3)});
         } else if(i >= GTMList.oreBlocks.size() && i < GTMList.oreItems.size()) {
            INSTANCE.addRecipe(GTMList.getItem(keys[1][i - GTMList.oreBlocks.size()], 3), new Object[]{recipe[0], recipe[1], recipe[2], Character.valueOf('A'), Integer.valueOf(0), Character.valueOf('B'), Integer.valueOf(1), Character.valueOf('C'), Integer.valueOf(2), Character.valueOf('D'), Integer.valueOf(3)});
         }
      }

      for(int i = 0; i < GTMList.gregtechItems.size(); ++i) {
         String[] recipe = ((String)GTMList.recipePattern.get(i)).split(",");
         if(i < GTMList.gregtechItems.size()) {
            INSTANCE.addRecipe(GTMUtil.copyAmount((ItemStack)GTMList.gregtechItems.get(i), 5).setStackDisplayName("By Matter"), new Object[]{recipe[0], recipe[1], recipe[2], Character.valueOf('A'), Integer.valueOf(4), Character.valueOf('B'), Integer.valueOf(5), Character.valueOf('C'), Integer.valueOf(6), Character.valueOf('D'), Integer.valueOf(7)});
         }
      }

      long damage = 1500000L;
      Materials wMat = Materials.Wood;
      Materials pMat = Materials.Neutronium;
      Materials[] sMat = new Materials[]{Materials.InfusedFire, Materials.InfusedGold, Materials.InfusedEarth, Materials.InfusedWater};
      String[][] rString = new String[][]{{" X ", " X ", " C "}, {"XXX", " C ", " C "}, {" X ", " C ", " C "}, {"XX ", "XC ", " C "}, {"XX ", " C ", " C "}, {"CCC", "XXC", "   "}, {"XX ", "XXC", "XX "}, {" XX", "CXX", " XX"}, {"X X", "XXX", " X "}, {"X  ", " X ", "  C"}, {"PDX", "DXD", "XDR"}, {"  X", " X ", "C  "}, {" X ", "SXS", "SSS"}, {"XRX", "PXV", "C C"}};
      ItemStack[][] Tools = new ItemStack[sMat.length][rString.length];

      for(int i = 0; i < sMat.length; ++i) {
         for(int j = 0; j < rString.length; ++j) {
            boolean inversion = Arrays.asList(new Integer[]{Integer.valueOf(8), Integer.valueOf(10), Integer.valueOf(12), Integer.valueOf(13)}).contains(Integer.valueOf(j));
            NBTTagCompound nbt = new NBTTagCompound();
            NBTTagCompound data = new NBTTagCompound();
            data.setLong("MaxDamage", damage);
            data.setString("PrimaryMaterial", inversion?sMat[i].toString():pMat.toString());
            data.setString("SecondaryMaterial", inversion?pMat.toString():sMat[i].toString());
            nbt.setTag("GT.ToolStats", data);
            Tools[i][j] = GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(j * 2, 1, wMat, wMat, (long[])null);
            Tools[i][j].setTagCompound(nbt);
            GT_ModHandler.addCraftingRecipe(Tools[i][j], new Object[]{rString[j][0], rString[j][1], rString[j][2], Character.valueOf('X'), new ItemStack(GTMItem.Matter, 1, i), Character.valueOf('C'), new ItemStack(Items.stick, 1), Character.valueOf('D'), "dyeBlue", Character.valueOf('P'), ToolDictNames.craftingToolHardHammer, Character.valueOf('R'), ToolDictNames.craftingToolFile, Character.valueOf('S'), "stone", Character.valueOf('V'), ToolDictNames.craftingToolScrewdriver});
         }
      }

      Block gregtech = GregTech_API.sBlockMachines;
      Block table = Blocks.crafting_table;
      ItemStack fabricator = new ItemStack(gregtech, 1, 461);
      ItemStack replicator = new ItemStack(gregtech, 1, 481);
      String[] infused = new String[]{"Fire", "Air", "Earth", "Water"};

      for(int i = 0; i < 4; ++i) {
         GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterFabricator, 1, i), new Object[]{"XCX", "XAX", Character.valueOf('X'), "plateSteel", Character.valueOf('C'), replicator, Character.valueOf('A'), fabricator});
      }

      for(int i = 0; i < 9; ++i) {
         GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterGenerator, 1, i + 1), new Object[]{"XCX", "XDX", "XCX", Character.valueOf('D'), new ItemStack(GTMItem.MatterGenerator, 1, i), Character.valueOf('C'), new ItemStack(GTMItem.MatterPlate, 1, 2), Character.valueOf('X'), new ItemStack(GTMItem.MatterPlate, 1, 1)});
      }
      for(int i = 0; i < 9; ++i) {
          GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.BlockABC, 1, i + 1), new Object[]{"XCX", "XDX", "XCX", Character.valueOf('D'), new ItemStack(GTMItem.BlockABC, 1, i), Character.valueOf('C'), new ItemStack(GTMItem.MatterPlate, 1, 2), Character.valueOf('X'), new ItemStack(GTMItem.MatterPlate, 1, 1)});
       }
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.BlockABC,1,0),new Object[]{"XAX", "DAB", "XCX", Character.valueOf('X'), "plateBlueSteel", Character.valueOf('A'), new ItemStack(GTMItem.MatterFabricator, 1, 0), Character.valueOf('B'), new ItemStack(GTMItem.MatterFabricator, 1, 1), Character.valueOf('C'), new ItemStack(GTMItem.MatterFabricator, 1, 2), Character.valueOf('D'), new ItemStack(GTMItem.MatterFabricator, 1, 3), Character.valueOf('E'), new ItemStack(table, 1)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.FabricatMeter, 1, 0), new Object[]{"XXX", "CDC", "XXX", Character.valueOf('X'), "plateIron", Character.valueOf('C'), "nuggetGold", Character.valueOf('D'), new ItemStack(Items.redstone, 1)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.FabricatMeter, 1, 1), new Object[]{"XXX", "CDC", "XXX", Character.valueOf('X'), "plateTungstenSteel", Character.valueOf('C'), "nuggetChrome", Character.valueOf('D'), "dustElectrum"});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterFabricator, 1, 4), new Object[]{"XAX", "DEB", "XCX", Character.valueOf('X'), "plateBlueSteel", Character.valueOf('A'), new ItemStack(GTMItem.MatterFabricator, 1, 0), Character.valueOf('B'), new ItemStack(GTMItem.MatterFabricator, 1, 1), Character.valueOf('C'), new ItemStack(GTMItem.MatterFabricator, 1, 2), Character.valueOf('D'), new ItemStack(GTMItem.MatterFabricator, 1, 3), Character.valueOf('E'), new ItemStack(table, 1)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterFabricator, 1, 5), new Object[]{"XAX", "BED", "XCX", Character.valueOf('X'), "plateRedSteel", Character.valueOf('A'), new ItemStack(GTMItem.MatterFabricator, 1, 0), Character.valueOf('B'), new ItemStack(GTMItem.MatterFabricator, 1, 1), Character.valueOf('C'), new ItemStack(GTMItem.MatterFabricator, 1, 2), Character.valueOf('D'), new ItemStack(GTMItem.MatterFabricator, 1, 3), Character.valueOf('E'), new ItemStack(table, 1)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterFabricator, 1, 6), new Object[]{"XAX", "AEA", "XAX", Character.valueOf('X'), "plateTungstenSteel", Character.valueOf('A'), new ItemStack(GTMItem.MatterFabricator, 1, 5), Character.valueOf('E'), new ItemStack(table, 1)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterGenerator, 1, 0), new Object[]{"XAX", "XCX", "XBX", Character.valueOf('X'), new ItemStack(GTMItem.MatterPlate, 1, 0), Character.valueOf('A'), new ItemStack(GTMItem.MatterFabricator, 1, 6), Character.valueOf('B'), new ItemStack(GTMItem.MatterFabricator, 1, 5), Character.valueOf('C'), Ic2Items.reBattery});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterGenerator, 1, 0), new Object[]{"XBX", "XCX", "XAX", Character.valueOf('X'), new ItemStack(GTMItem.MatterPlate, 1, 0), Character.valueOf('A'), new ItemStack(GTMItem.MatterFabricator, 1, 6), Character.valueOf('B'), new ItemStack(GTMItem.MatterFabricator, 1, 5), Character.valueOf('C'), Ic2Items.reBattery});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterPlate, 1, 1), new Object[]{"XXX", "XCX", "XXX", Character.valueOf('X'), new ItemStack(GTMItem.MatterPlate, 1, 0), Character.valueOf('C'), Ic2Items.transformerUpgrade});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.MatterPlate, 1, 2), new Object[]{"XXX", "XCX", "XXX", Character.valueOf('X'), new ItemStack(GTMItem.MatterPlate, 1, 0), Character.valueOf('C'), Ic2Items.overclockerUpgrade});
      GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GTMItem.MatterPlate, 1, 0), new Object[]{new ItemStack(GTMItem.Matter, 1, 0), new ItemStack(GTMItem.Matter, 1, 1), new ItemStack(GTMItem.Matter, 1, 2), new ItemStack(GTMItem.Matter, 1, 3), "dustSteel", "dustBatteryAlloy", "dustSteel", "dustBatteryAlloy", "dustElectrum"});
      GT_Values.RA.addAssemblerRecipe(new ItemStack(GTMItem.MatterPlate, 1, 0), Ic2Items.transformerUpgrade, new ItemStack(GTMItem.MatterPlate, 1, 1), 400, 32);
      GT_Values.RA.addAssemblerRecipe(new ItemStack(GTMItem.MatterPlate, 1, 0), Ic2Items.overclockerUpgrade, new ItemStack(GTMItem.MatterPlate, 1, 2), 400, 32);
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.AdvancedMiner, 1), new Object[]{"ABA", "CDC", "FEF", Character.valueOf('A'), "circuitMaster", Character.valueOf('B'), ItemList.ZPM2.get(1L, new Object[0]), Character.valueOf('C'), ItemList.Neutron_Reflector.get(1L, new Object[0]), Character.valueOf('D'), ItemList.Hull_MAX.get(1L, new Object[0]), Character.valueOf('E'), ItemList.Field_Generator_IV.get(1L, new Object[0]), Character.valueOf('F'), "circuitElite"});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.ToolWorkbench, 1, 0), new Object[]{"ABA", "BCB", "ABA", Character.valueOf('A'), "dustCoal", Character.valueOf('B'), "dyeRed", Character.valueOf('C'), new ItemStack(Blocks.crafting_table, 1)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.ToolWorkbench, 1, 1), new Object[]{"ABA", "BCB", "ABA", Character.valueOf('A'), "dustCoal", Character.valueOf('B'), "dyeGreen", Character.valueOf('C'), new ItemStack(Blocks.crafting_table, 1)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.SteamGenerator, 1), new Object[]{"ACA", "ABA", "ACA", Character.valueOf('A'), "plateTin", Character.valueOf('B'), "dustRedstone", Character.valueOf('C'), new ItemStack(Blocks.glass)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.AccelMachine, 1), new Object[]{"A", "B", "C", Character.valueOf('A'), ItemList.Field_Generator_LV.get(1L, new Object[0]), Character.valueOf('B'), ItemList.Electric_Motor_LV.get(1L, new Object[0]), Character.valueOf('C'), new ItemStack(Blocks.torch, 1)});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.ArmorHelmet, 1), new Object[]{"AAA", "ABA", "   ", Character.valueOf('A'), new ItemStack(GTMItem.MatterPlate, 1), Character.valueOf('B'), "craftingToolHardHammer"});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.ArmorChestplate, 1), new Object[]{"ABA", "AAA", "AAA", Character.valueOf('A'), new ItemStack(GTMItem.MatterPlate, 1), Character.valueOf('B'), "craftingToolHardHammer"});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.ArmorLeggings, 1), new Object[]{"AAA", "ABA", "A A", Character.valueOf('A'), new ItemStack(GTMItem.MatterPlate, 1), Character.valueOf('B'), "craftingToolHardHammer"});
      GT_ModHandler.addCraftingRecipe(new ItemStack(GTMItem.ArmorBoots, 1), new Object[]{"A A", "ABA", "   ", Character.valueOf('A'), new ItemStack(GTMItem.MatterPlate, 1), Character.valueOf('B'), "craftingToolHardHammer"});

      try {
         if(Loader.isModLoaded("ThermalExpansion")) {
            Method a = Class.forName("cofh.thermalexpansion.util.crafting.SmelterManager").getMethod("addRecipe", new Class[]{Integer.TYPE, ItemStack.class, ItemStack.class, ItemStack.class});
            a.setAccessible(true);
            ArrayList<ItemStack> Steel = OreDictionary.getOres("ingotSteel");

            for(int i = 0; i <= 1 && Steel.size() > 0; ++i) {
               a.invoke((Object)null, new Object[]{Integer.valueOf(8000), new ItemStack(Items.iron_ingot, 1, 0), new ItemStack(Items.coal, 4, i), OreDictionary.getOres("ingotSteel").get(0)});
               GT_Values.RA.addAlloySmelterRecipe(new ItemStack(Items.iron_ingot, 1, 0), new ItemStack(Items.coal, 4, i), (ItemStack)OreDictionary.getOres("ingotSteel").get(0), 40, 16);
            }
         }

         if(Loader.isModLoaded("appliedenergistics2")) {
            AEApi.instance().registries().grinder().getRecipes().clear();
            Set<String> blockKey = GTMList.oreBlocks.keySet();
            Set<String> itemKey = GTMList.oreItems.keySet();

            for(String ore : blockKey) {
               String source = ore;
               String more = "dustTinyStone";
               if(ore.indexOf("Blackgranite") != -1) {
                  ore = ore.replace("Blackgranite", "");
                  more = "dustTinyGraniteBlack";
               }

               if(ore.indexOf("Redgranite") != -1) {
                  ore = ore.replace("Redgranite", "");
                  more = "dustTinyGraniteRed";
               }

               if(ore.indexOf("Netherrack") != -1) {
                  ore = ore.replace("Netherrack", "");
                  more = "dustTinyNetherrack";
               }

               if(ore.indexOf("Endstone") != -1) {
                  ore = ore.replace("Endstone", "");
                  more = "dustTinyEndstone";
               }

               ore = ore.replace("ore", "dust");
               if(blockKey.contains(source) && itemKey.contains(ore) && !source.equals(ore)) {
                  for(ItemStack item : OreDictionary.getOres(source)) {
                     AEApi.instance().registries().grinder().getRecipes().add(new Recipe.Grinder(item, GTMList.getItem(ore, 2), (ItemStack)GTMList.oreItems.get(more)));
                  }
               }
            }

            for(String ingot : itemKey) {
               String source = ingot;
               if(ingot.startsWith("ingot")) {
                  ingot = ingot.replace("ingot", "dust");
               }

               if(ingot.startsWith("gem")) {
                  ingot = ingot.replace("gem", "dust");
               }

               if(ingot.startsWith("crystal")) {
                  ingot = ingot.replace("crystal", "dust");
               }

               if(itemKey.contains(source) && itemKey.contains(ingot) && !source.equals(ingot)) {
                  for(ItemStack item : OreDictionary.getOres(source)) {
                     AEApi.instance().registries().grinder().getRecipes().add(new Recipe.Grinder(item, GTMList.getItem(ingot, 1)));
                  }
               }
            }
         }

         if(Loader.isModLoaded("AdvancedSolarPanel")) {
            for(int i = 0; i < 4; ++i) {
               MTRecipeManager.instance.addMTRecipe(new ItemStack(GTMItem.Matter, 1, 0 + i), new ItemStack(GTMItem.Matter, 1, 4 + i), 2100000000);
            }
         }



         OldID.recipe();
      } catch (Throwable var21) {
         ;
      }

   }

   public void addRecipe(ItemStack itemstack, Object[] objects) {
      HashMap<Character, Integer> changeList = new HashMap();
      ArrayList<Integer> recipes = new ArrayList();
      char[] recipe = ((String)objects[0] + (String)objects[1] + (String)objects[2]).toCharArray();

      for(int i = 4; i < objects.length; i += 2) {
         Object chars = objects[i - 1];
         Object items = objects[i];
         if(chars instanceof Character && items instanceof Integer) {
            changeList.put((Character)chars, (Integer)items);
         }
      }

      for(char t : recipe) {
         recipes.add(changeList.get(Character.valueOf(t)));
      }

      this.MAPPING.put(recipes, itemstack);
   }

   public static Recipe getInstance() {
      return INSTANCE;
   }

   public HashMap<ArrayList<Integer>, ItemStack> getRecipeMapping() {
      return this.MAPPING;
   }

   public ItemStack getItem(ArrayList<Integer> list) {
      ItemStack item = list.size() == 9?(ItemStack)this.MAPPING.get(list):null;
      return item != null?item.copy():null;
   }

   public ItemStack getItem(Integer[] array) {
      ArrayList<Integer> list = (ArrayList)Arrays.asList(array);
      return this.getItem(list);
   }

   public ItemStack getItem(IInventory inventory) {
      ArrayList<Integer> list = new ArrayList();

      for(int i = 0; i < 9; ++i) {
         ItemStack item = inventory.getStackInSlot(i);
         if(item != null && item.getItem() == GTMItem.Matter) {
            list.add(Integer.valueOf(item.getItemDamage()));
         }
      }

      return this.getItem(list);
   }

   public static class Grinder implements IGrinderEntry {
      private ItemStack input;
      private ItemStack output;
      private ItemStack extra;
      private ItemStack extra2;

      public Grinder(ItemStack input, ItemStack output, ItemStack extra, ItemStack extra2) {
         this.input = input;
         this.output = output;
         this.extra = extra;
         this.extra2 = extra2;
      }

      public Grinder(ItemStack input, ItemStack output, ItemStack extra) {
         this(input, output, extra, (ItemStack)null);
      }

      public Grinder(ItemStack input, ItemStack output) {
         this(input, output, (ItemStack)null);
      }

      public ItemStack getInput() {
         return this.input;
      }

      public void setInput(ItemStack paramItemStack) {
         this.error();
      }

      public ItemStack getOutput() {
         return this.output;
      }

      public void setOutput(ItemStack paramItemStack) {
         this.error();
      }

      public ItemStack getOptionalOutput() {
         return this.extra;
      }

      public ItemStack getSecondOptionalOutput() {
         return this.extra2;
      }

      public void setOptionalOutput(ItemStack paramItemStack, float paramFloat) {
         this.error();
      }

      public float getOptionalChance() {
         return 1.0F;
      }

      public void setSecondOptionalOutput(ItemStack paramItemStack, float paramFloat) {
         this.error();
      }

      public float getSecondOptionalChance() {
         return 1.0F;
      }

      public int getEnergyCost() {
         return 2;
      }

      public void setEnergyCost(int paramInt) {
         this.error2();
      }

      private void error() {
         System.out.println("Use reflection if you want to change this recipe.");
      }

      private void error2() {
         System.out.println("You can\'t change this parameter");
      }
   }
}
