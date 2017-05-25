package gtmaterials;

import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.HashMap;

public class GTMLang {
   private static final GTMLang INSTANCE = new GTMLang();
   private HashMap<String, String> en_US = new HashMap();
   private HashMap<String, String> ja_JP = new HashMap();

   public static void init() {
      INSTANCE.load();
   }

   private void load() {
	   this.set("tile.GTMaterials:ABCBlock_0.name", "dust Ex Nihilo ", "æ–¹é‰›é‰±"); 
	   this.set("tile.GTMaterials:ABCBlock_1.name", "ore Ex Nihilo ", "æ–¹é‰›é‰±"); 
	   this.set("tile.GTMaterials:ABCBlock_2.name", "gem Ex Nihilo ", "æ–¹é‰›é‰±"); 
	   this.set("tile.GTMaterials:ABCBlock_3.name", "component/machine Ex Nihilo ", "æ–¹é‰›é‰±"); 
	   this.set("tile.GTMaterials:ABCBlock_4.name", "modular Ex Nihilo ", "æ–¹é‰›é‰±"); 
	   this.set("tile.GTMaterials:ABCBlock_5.name", "All Ex Nihilo ", "æ–¹é‰›é‰±"); 
      this.set("tile.GTMaterials:GregOres_0.name", "Galena Ore", "æ–¹é‰›é‰±");
      this.set("tile.GTMaterials:GregOres_1.name", "Iridium Ore", "ã‚¤ãƒªã‚¸ã‚¦ãƒ é‰±çŸ³");
      this.set("tile.GTMaterials:GregOres_2.name", "Ruby Ore", "ãƒ«ãƒ“ãƒ¼é‰±çŸ³");
      this.set("tile.GTMaterials:GregOres_3.name", "Sapphire Ore", "ã‚µãƒ•ã‚¡ã‚¤ã‚¢é‰±çŸ³");
      this.set("tile.GTMaterials:GregOres_4.name", "Bauxite Ore", "ãƒœãƒ¼ã‚­ã‚µã‚¤ãƒˆé‰±çŸ³");
      this.set("tile.GTMaterials:GregOres_5.name", "Pyrite Ore", "é»„é‰„é‰±");
      this.set("tile.GTMaterials:GregOres_6.name", "Cinnabar Ore", "è¾°ç ‚");
      this.set("tile.GTMaterials:GregOres_7.name", "Sphalerite Ore", "é–ƒäºœé‰›é‰±");
      this.set("tile.GTMaterials:GregOres_8.name", "Tungsten Ore", "ã‚¿ãƒ³ã‚°ã‚¹ãƒ†ãƒ³é‰±çŸ³");
      this.set("tile.GTMaterials:GregOres_9.name", "Sheldonite Ore", "ã‚·ã‚§ãƒ«ãƒ‰ãƒŠã‚¤ãƒˆé‰±çŸ³");
      this.set("tile.GTMaterials:GregOres_10.name", "Olivine Ore", "æ©„æ¬–çŸ³");
      this.set("tile.GTMaterials:GregOres_11.name", "Sodalite Ore", "æ–¹ã‚½ãƒ¼ãƒ€çŸ³");
      this.set("tile.GTMaterials:GregOres_12.name", "Tetrahedrite Ore", "å››é�¢éŠ…é‰±");
      this.set("tile.GTMaterials:GregOres_13.name", "Cassiterite Ore", "éŒ«çŸ³");
      this.set("tile.GTMaterials:GregOres_14.name", "Nickel Ore", "ãƒ‹ãƒƒã‚±ãƒ«é‰±çŸ³");
      this.set("tile.GTMaterials:MatterOres_0.name", "Red Matter Ore", "ç�«å�ˆé‰±çŸ³");
      this.set("tile.GTMaterials:MatterOres_1.name", "Yellow Matter Ore", "å…‰å�ˆé‰±çŸ³");
      this.set("tile.GTMaterials:MatterOres_2.name", "Green Matter Ore", "æœ¨å�ˆé‰±çŸ³");
      this.set("tile.GTMaterials:MatterOres_3.name", "Blue Matter Ore", "æ°´å�ˆé‰±çŸ³");
      this.set("tile.GTMaterials:MatterFabricator_0.name", "Red Matter Fabricator", "èµ¤ãƒžã‚¿ãƒ¼ç”Ÿæˆ�å™¨");
      this.set("tile.GTMaterials:MatterFabricator_1.name", "Yellow Matter Fabricator", "é»„ãƒžã‚¿ãƒ¼ç”Ÿæˆ�å™¨");
      this.set("tile.GTMaterials:MatterFabricator_2.name", "Green Matter Fabricator", "ç·‘ãƒžã‚¿ãƒ¼ç”Ÿæˆ�å™¨");
      this.set("tile.GTMaterials:MatterFabricator_3.name", "Blue Matter Fabricator", "é�’ãƒžã‚¿ãƒ¼ç”Ÿæˆ�å™¨");
      this.set("tile.GTMaterials:MatterFabricator_4.name", "Ore Fabricator", "é‰±çŸ³ç”Ÿæˆ�å™¨");
      this.set("tile.GTMaterials:MatterFabricator_5.name", "Material Fabricator", "ç´ æ��ç”Ÿæˆ�å™¨");
      this.set("tile.GTMaterials:MatterFabricator_6.name", "Machine Matter Fabricator", "æ©Ÿæ¢°ç”Ÿæˆ�å™¨");
      this.set("tile.GTMaterials:MatterGenerator_0.name", "Matter Generator ULV", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(ULV)");
      this.set("tile.GTMaterials:MatterGenerator_1.name", "Matter Generator LV", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(LV)");
      this.set("tile.GTMaterials:MatterGenerator_2.name", "Matter Generator MV", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(MV)");
      this.set("tile.GTMaterials:MatterGenerator_3.name", "Matter Generator HV", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(HV)");
      this.set("tile.GTMaterials:MatterGenerator_4.name", "Matter Generator EV", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(EV)");
      this.set("tile.GTMaterials:MatterGenerator_5.name", "Matter Generator IV", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(IV)");
      this.set("tile.GTMaterials:MatterGenerator_6.name", "Matter Generator LuV", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(LuV)");
      this.set("tile.GTMaterials:MatterGenerator_7.name", "Matter Generator ZPM", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(ZPM)");
      this.set("tile.GTMaterials:MatterGenerator_8.name", "Matter Generator UV", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(UV)");
      this.set("tile.GTMaterials:MatterGenerator_9.name", "Matter Generator MAX", "ãƒžã‚¿ãƒ¼ç™ºé›»æ©Ÿ(MAX)");
      this.set("tile.GTMaterials:ToolWorkbench_0.name", "Tool Crafting Table", "ãƒ„ãƒ¼ãƒ«ãƒ†ãƒ¼ãƒ–ãƒ«");
      this.set("tile.GTMaterials:ToolWorkbench_1.name", "Matter Crafting Table", "ãƒžã‚¿ãƒ¼ãƒ†ãƒ¼ãƒ–ãƒ«");
      this.set("tile.GTMaterials:AdvancedMiner.name", "Re-Advanced Miner", "æŽ¡æŽ˜æ©Ÿ");
      this.set("tile.GTMaterials:BigChest.name", "TBCN0GUI", "ã�§ã�£ã�‹ã�„ãƒ�ã‚§ã‚¹ãƒˆ");
      this.set("tile.GTMaterials:SteamGenerator.name", "Steam Generator", "è’¸æ°—ç™ºé›»æ©Ÿ");
      this.set("tile.GTMaterials:AccelMachine.name", "Accel Torch", "ã‚¢ã‚¯ã‚»ãƒ«ãƒ–ãƒ¼ã‚¹ã‚¿ãƒ¼");
      this.set("GTMaterials:Matter_0.name", "Red Matter", "èµ¤è‰²ãƒžã‚¿ãƒ¼");
      this.set("GTMaterials:Matter_1.name", "Yellow Matter", "é»„è‰²ãƒžã‚¿ãƒ¼");
      this.set("GTMaterials:Matter_2.name", "Green Matter", "ç·‘è‰²ãƒžã‚¿ãƒ¼");
      this.set("GTMaterials:Matter_3.name", "Blue Matter", "é�’è‰²ãƒžã‚¿ãƒ¼");
      this.set("GTMaterials:Matter_4.name", "Wood Matter", "æœ¨ç›®ãƒžã‚¿ãƒ¼");
      this.set("GTMaterials:Matter_5.name", "Stone Matter", "çŸ³åŒ–ãƒžã‚¿ãƒ¼");
      this.set("GTMaterials:Matter_6.name", "Iron Matter", "é‰„åˆ†ãƒžã‚¿ãƒ¼");
      this.set("GTMaterials:Matter_7.name", "Diamond Matter", "çµ�æ™¶ãƒžã‚¿ãƒ¼");
      this.set("GTMaterials:MatterPlate_0.name", "Matter Plate", "ãƒžã‚¿ãƒ¼ãƒ—ãƒ¬ãƒ¼ãƒˆ");
      this.set("GTMaterials:MatterPlate_1.name", "Matter Pressure Plate", "ãƒžã‚¿ãƒ¼ãƒ—ãƒ¬ãƒ¼ãƒˆ(åœ§åŠ›åž‹)");
      this.set("GTMaterials:MatterPlate_2.name", "Matter Speed Plate", "ãƒžã‚¿ãƒ¼ãƒ—ãƒ¬ãƒ¼ãƒˆ(é€Ÿåº¦åž‹)");
      this.set("GTMaterials:FabricatMeter_0.name", "Collecting Meter", "æ¸¬å®šå™¨");
      this.set("GTMaterials:FabricatMeter_1.name", "Machine Collecting Meter", "æ©Ÿæ¢°æ¸¬å®šå™¨");
      this.set("GTMaterials:DataDisk.name", "Data Disk", "ã‚¢ã‚¤ãƒ†ãƒ ãƒ‡ãƒ¼ã‚¿");
      this.set("GTMaterials:Armor_0.name", "Matter Helmet", "ãƒ˜ãƒ«ãƒ¡ãƒƒãƒˆ");
      this.set("GTMaterials:Armor_1.name", "Matter Chestplate", "ãƒ�ã‚§ã‚¹ãƒˆãƒ—ãƒ¬ãƒ¼ãƒˆ");
      this.set("GTMaterials:Armor_2.name", "Matter Leggings", "ãƒ¬ã‚®ãƒ³ã‚¹");
      this.set("GTMaterials:Armor_3.name", "Matter Boots", "ãƒ–ãƒ¼ãƒ„");
      this.set("itemGroup.GTMTab", "GTMaterials", "GTMaterials");
      LanguageRegistry.instance().injectLanguage("en_US", this.en_US);
      LanguageRegistry.instance().injectLanguage("ja_JP", this.ja_JP);
   }

   private void set(String unLocal, String usLocal, String jpLocal) {
      if(!this.en_US.containsKey(unLocal) && !this.ja_JP.containsKey(unLocal)) {
         this.en_US.put(unLocal, usLocal);
         this.ja_JP.put(unLocal, jpLocal);
      }

   }
}
