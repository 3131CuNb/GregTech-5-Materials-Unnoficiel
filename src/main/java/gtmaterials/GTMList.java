package gtmaterials;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ProgressManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.items.GT_Generic_Item;
import gtmaterials.mod.mcreator.GTMaterialsRejectMCreatorException;
import ic2.core.item.ItemIC2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockOre;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;



public class GTMList {
	 public static final ArrayList<ItemStack>gregtechItems = Lists.newArrayList();
   public static final HashMap<String, ItemStack> oreBlocks = Maps.newHashMap();
   
   public static final HashMap<String, ArrayList> blockModFilter = Maps.newHashMap();
   public static final HashMap<String, ItemStack> oreItems = Maps.newHashMap();
   public static final ArrayList<String> recipePattern = Lists.newArrayList();
   public static final ArrayList<ItemStack> GtItem = Lists.newArrayList();
   public static final ArrayList<ItemStack> AllItems = Lists.newArrayList();
   public static final ArrayList<ItemStack> generatedListB = Lists.newArrayList();
   public static final ArrayList<ItemStack> generatedListI = Lists.newArrayList();
   public static final ArrayList<ItemStack> generatedListI2 = Lists.newArrayList();
   public static final ArrayList<ItemStack> generatedListIgem = Lists.newArrayList();
   public static final ArrayList<ItemStack> generatedListIblock = Lists.newArrayList(); 
   public static final ArrayList<ArrayList<ItemStack>> finalStokage = new ArrayList<ArrayList<ItemStack>>(); 
   public static final ArrayList<String> NameObj = Lists.newArrayList(); 
   public static Integer t = 0;
    public static Integer k = -1;
   private static List rang = new LinkedList();
   public static Item lol;
   
   public static void preInit() {
      if(Package.getPackage("mod.mcreator") != null && !GTMConfig.fuckingmcreator) {
         throw new GTMaterialsRejectMCreatorException();
      } else {
         Map<Integer, String> table = Maps.newHashMap();
         List<String> pattern = Lists.newArrayList();
         table.put(0, "A");
         table.put(1, "B");
         table.put(2, "C");
         table.put(3, "D");

         for(int x = 0; x < ((Map)table).size(); ++x) {
            for(int z = 0; z < ((Map)table).size(); ++z) {
               for(int y = 0; y < ((Map)table).size(); ++y) {
                  pattern.add((String)table.get(x) + (String)table.get(z) + (String)table.get(y));
               }
            }
         }

         for(int u = 0; u < ((List)pattern).size(); ++u) {
            for(int k = 0; k < ((List)pattern).size(); ++k) {
               for(int l = 0; l < ((List)pattern).size(); ++l) {
                  recipePattern.add((String)pattern.get(u) + "," + (String)pattern.get(k) + "," + (String)pattern.get(l));
               }
            }
         }

      }
   }

   public static void postInit() throws Exception {
       //region AllGTThingsRegister
       List<Item> list = Lists.newArrayList();

      HashMap<String, Integer> oreIDs = null ;
      try {
         Field f = OreDictionary.class.getDeclaredField("nameToId");
         f.setAccessible(true);
         oreIDs = (HashMap)f.get((Object)null);
         Field[] f2 = Items.class.getFields();
         
         for(Field f3 : f2) {
            f3.setAccessible(true);
            Object o = f3.get((Object)null);
            if(o instanceof Item) {
               list.add((Item)o);
            }
         }
      } catch (Throwable var15) {
       // return; 
      }
       //endregion
      
      //region GTThingsSeparator
      for(OrePrefixes oName : OrePrefixes.values()) {
    	  if(!oName.name().startsWith("ore") && !oName.name().startsWith("wireGt") && !oName.name().startsWith("pipe") && !oName.name().startsWith("block")) {
            if(oName.name().startsWith("crushed") || oName.name().startsWith("ingot") || oName.name().startsWith("gem") || oName.name().startsWith("dust") || oName.name().startsWith("nugget") || oName.name().startsWith("plate") || oName.name().startsWith("foil") || oName.name().startsWith("stick") || oName.name().startsWith("round") || oName.name().startsWith("bolt") || oName.name().startsWith("screw") || oName.name().startsWith("ring") || oName.name().startsWith("spring") || oName.name().startsWith("wireFine") || oName.name().startsWith("rotor") || oName.name().startsWith("gearGt") || oName.name().startsWith("lens") || oName.name().startsWith("cell") || oName.name().startsWith("cellPlasma") || oName.name().startsWith("arrowGt") || oName.name().startsWith("toolHead") || oName.name().startsWith("circuit") || oName.name().startsWith("battery") || oName.name().startsWith("crateGt")) {
               for(Materials mName : Materials.values()) {
                  String oreName = oName.name() + mName.name();
                  if(oreIDs.get(oreName) != null) {
                     for(ItemStack itemstack : OreDictionary.getOres(oreName)) {
                        Item item = itemstack.getItem();
                        if(item instanceof GT_Generic_Item || item instanceof ItemIC2 || list.contains(item)) {
                        	
                        	oreItems.put(oreName, itemstack);
                        }
                     }
                  }
               }
            }
         } else {
            for(Materials mName : Materials.values()) {
               String oreName = oName.name() + mName.name();
               if(oreIDs.get(oreName) != null) {
                  for(ItemStack itemstack : OreDictionary.getOres(oreName)) {
                     Block block = Block.getBlockFromItem(itemstack.getItem());
                     if(block instanceof GT_Generic_Block || block instanceof BlockCompressed || block instanceof BlockOre) {
                        oreBlocks.put(oreName, itemstack);
                     }
                  }
               }
            }
         }
      }
      //endregion

      if(GTMaterials.Proxy.isClient()) {
    	  HashMap<String,ArrayList<Integer>> itemSeparator = new HashMap<String,ArrayList<Integer>>();
         try {
            ArrayList<ItemStack> f = new ArrayList<ItemStack>();
            ArrayList<Item> f1 = new ArrayList<Item>();
            NBTTagList nbtlist = new NBTTagList();
            ProgressManager.ProgressBar ProgressBar=  ProgressManager.push("Register materials", GameData.getItemRegistry().getKeys().size());
            for(Object objects : GameData.getItemRegistry().getKeys()) {
            	ProgressBar.step(objects.toString());
            	
           try {
            		if (objects.toString().toLowerCase().contains(":microblock")) {
            			 FMLLog.info("blockImcompatible detected",new Object[0]);
            			continue;
            		}
               Item item = (Item)GameData.getItemRegistry().getObject(objects);
               	f1.add(item);
               if(item != null) {

            	   item.getSubItems(item, (CreativeTabs)null, f);
            	   	
               }
               
               
            } catch (Throwable var14) {
        	String a = objects.toString();
        	 FMLLog.info("Bug Valeur",new Object[0]);
        	 FMLLog.info(a,new Object[0]);
        	 FMLLog.info("Bug Valeur",new Object[0]);		
           }
            }

            if(!f.isEmpty()) {
               //NBTTagCompound nbt = new NBTTagCompound();
               for(ItemStack i : f) {
            	   String tr = i.getDisplayName();
            	   if(tr.toLowerCase().contains("universal fluid") || tr.toLowerCase().contains("cable facade") || tr.toLowerCase().contains("facade:") || tr.toLowerCase().contains("null")|| tr.toLowerCase().contains(".name")){
            		 continue;
            	   }




                   try {

                       String man = GameRegistry.findUniqueIdentifierFor(i.getItem()).modId;
                       if (man != null) {
                           k++;
                       }

                       if (itemSeparator.containsKey(man)) {
                           ArrayList<Integer> b = itemSeparator.get(man);
                           b.add(k);
                           itemSeparator.put(man, b);
                       } else {
                           ArrayList<Integer> a = new ArrayList<Integer>();
                           a.add(k);
                           itemSeparator.put(man, a);
                       }

                       AllItems.add(i);
                       // i.writeToNBT(nbt);
                       //  nbtlist.appendTag(nbt);

                   }
                   catch(Throwable var14){

                       FMLLog.bigWarning(i.getItem().toString(),new Object[0]);
               };

                   FMLLog.severe(k.toString()+"      truc trouvé.",new Object[0]);
               }


        	   //TODO no forget to complete this
        	   Set<Entry<String,ArrayList<Integer>>>  itemS = itemSeparator.entrySet();
        	   Iterator <Entry<String,ArrayList<Integer>>> h1 = itemS.iterator();
        	   
        	   while(h1.hasNext()){
        		 t++; 
        		 Entry<String, ArrayList<Integer>> h = h1.next();
        		 ArrayList<Integer> h2 = h.getValue();
        		 FMLLog.severe(h.getKey(),new Object[0]);
        		 FMLLog.severe("mod register DebugGT",new Object[0]);
        		 NameObj.add(h.getKey());	 
        		 ArrayList<ItemStack> h4 = Lists.newArrayList() ;
        		 
        		   for (int truc=0;truc<h2.size();truc++){
        			   ItemStack h3= AllItems.get(h2.get(truc));
        			   h4.add(h3);
            	   }
        		   finalStokage.add(h4);
        	   }

        	 
        	     ProgressManager.pop(ProgressBar);
            //   PacketHandler.INSTANCE.sendToServer(new PacketHandler.GTMPacket(0, (short)0, 0, GTMList.class, new Object[]{nbtlist.toString()}));
            }
         } catch (Throwable var14) {
            var14.printStackTrace();
             FMLLog.severe("debugGT catch something wrong ",new Object[0]);
         }
      }
      
      

      
     
      int ji = 0;
      for(ItemList il : ItemList.values()) {
    	  try{ 
    	  if (il!=null) {
			//System.out.println(ji++);
    		  ItemList[] test =ItemList.values();
    		  ItemStack g =il.get(1);  
    		  //if (g!=null)
    		  //System.out.println(g);
    	  GtItem.add(g);
    	  }
    	  
    	  
      }catch (Throwable var16) {
      
      }
      }
      
      for(OrePrefixes oName : OrePrefixes.values()) {
          if(!oName.name().startsWith("ore")) {
             if(oName.name().startsWith("crushed") || oName.name().startsWith("ingot") || oName.name().startsWith("dust") || oName.name().startsWith("block"))   {
                if(!oName.name().startsWith("ingotDouble") && !oName.name().startsWith("ingotTriple") && !oName.name().startsWith("ingotQuadruple") && !oName.name().startsWith("ingotQuintuple")){
                	if(!oName.name().startsWith("dustSmall") && !oName.name().startsWith("dustTiny") && !oName.name().startsWith("dustImpure") && !oName.name().startsWith("dustRefined") && !oName.name().startsWith("dustPure") && !oName.name().startsWith("dustPure") && !oName.name().startsWith("crushed") && !oName.name().startsWith("ingotHot")){
                		for(Materials mName : Materials.values()) {
                   String oreName = oName.name() + mName.name();
                   if(oreIDs.get(oreName) != null) {
                      for(ItemStack itemstack : OreDictionary.getOres(oreName)) {
                         Item item = itemstack.getItem();
                         if(item instanceof GT_Generic_Item || item instanceof ItemIC2 || list.contains(item)) {
                        	 generatedListI.add(itemstack);
                         }
                      }
                   }
                   }
                	}
                }
             }
          } else {
             for(Materials mName : Materials.values()) {
                String oreName = oName.name() + mName.name();
                if(oreIDs.get(oreName) != null) {
                   for(ItemStack itemstack : OreDictionary.getOres(oreName)) {
                      Block block = Block.getBlockFromItem(itemstack.getItem());
                      if(block instanceof GT_Generic_Block || block instanceof BlockCompressed || block instanceof BlockOre) {
                    	  generatedListB.add(itemstack);
                      }
                   }
                }
             }
          }
       }
      
      
      
      

      for(OrePrefixes oName : OrePrefixes.values()) {
          if(oName.name().matches("gem")) 
      for(Materials mName : Materials.values()) {
          String oreName = oName.name() + mName.name();
          if(oreIDs.get(oreName) != null) {
             for(ItemStack itemstack : OreDictionary.getOres(oreName)) {
                Item item = itemstack.getItem();
                if(item instanceof GT_Generic_Item || item instanceof ItemIC2 || list.contains(item)) {
               	 generatedListIgem.add(itemstack);
                }
             }
          }
          }
          
          if(oName.name().matches("block")) 
              for(Materials mName : Materials.values()) {
                  String oreName = oName.name() + mName.name();
                  if(oreIDs.get(oreName) != null) {
                     for(ItemStack itemstack : OreDictionary.getOres(oreName)) {
                        Item item = itemstack.getItem();
                        if(item instanceof GT_Generic_Item || item instanceof ItemIC2 || list.contains(item)) {
                       	 generatedListIblock.add(itemstack);
          
                        }
                     }
                  }
              }
      
      }
      
      
      
      rang.add(generatedListI);
      rang.add(generatedListB);
      rang.add(generatedListIgem);
      rang.add(GtItem);
      rang.add(null);
      rang.add(AllItems);
      
   }

   public static ItemStack getBlock(String name, int i) {
      return GTMUtil.copyAmount((ItemStack)oreBlocks.get(name), i);
   }
   
   public static String getModName(int e){
	   if (e>=t){
		   return "Out of Mod";   
	   } else
		 return NameObj.get(e)+"/"+t.toString();
	   
   }
   
   
   public static Integer getModSize(){
	return t;
	   
   }
   
   public static ItemStack getRandom(int c, int... d) {
	   ArrayList<ItemStack> a= (ArrayList<ItemStack>)rang.get(c) ; 
	   if (c==4){
		   a=finalStokage.get(d[0]);
		   
	   }
	      
	   int k = 0;
	   ItemStack b = null;
	   Random g = new Random();
	   while(b== null){
	  int h =g.nextInt(a.size());
	  b=a.get(h);
	   }
	   if (b.getMaxStackSize()<1) {
            return null;
	   }
       else {
           return GTMUtil.copyAmount(b, g.nextInt(b.getMaxStackSize()));
       }
	   }

   public static ItemStack getItem(String name, int i) {
      return GTMUtil.copyAmount((ItemStack)oreItems.get(name), i);
   }
}



