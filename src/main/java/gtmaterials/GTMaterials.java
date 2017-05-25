package gtmaterials;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.GTMConfig;
import gtmaterials.GTMItem;
import gtmaterials.GTMLang;
import gtmaterials.GTMList;
import gtmaterials.packet.PacketHandler;
import gtmaterials.proxy.Common;
import gtmaterials.recipe.Recipe;
import gtmaterials.world.WorldGen;
import gtmaterials.world.manager.SafetyAreaData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

@Mod(
   modid = "GTMaterials",
   name = "GTMaterials",
   version = "2.2.1fix",
   dependencies = "required-after:IC2;required-after:gregtech"
)
public class GTMaterials {
   @Instance("GTMaterials")
   public static GTMaterials instance;
   @SidedProxy(
      clientSide = "gtmaterials.proxy.Client",
      serverSide = "gtmaterials.proxy.Common"
   )
   public static Common Proxy;
   public static final String DEVELOPER = "getget";
   public static final String RESPECT = "GregoriusT & Blood_Asp";
   public static final GTMaterials.GTMTab TAB = new GTMaterials.GTMTab("GTMTab") {
      public Item getTabIconItem() {
         return Item.getItemFromBlock(Blocks.furnace);
      }
   };

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) throws Exception {
      GTMConfig.preInit();
      GTMList.preInit();
      Proxy.preInit();
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      GTMItem.init();
      GTMLang.init();
      Recipe.init();
      PacketHandler.init();
      WorldGen.init();
      Proxy.init();
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) throws Exception {
      GTMList.postInit();
      Recipe.postInit();
      Proxy.postInit();
   }

   @EventHandler
   public void onStartServer(FMLServerStartingEvent event) {
      SafetyAreaData.INSTANCE = new SafetyAreaData();
   }

   @EventHandler
   public void onStopServer(FMLServerStoppingEvent event) {
      SafetyAreaData.INSTANCE.reset();
   }

   public abstract static class GTMTab extends CreativeTabs {
      private String label;

      public GTMTab(String arg0) {
         super(arg0);
         this.label = arg0;
      }

      public abstract Item getTabIconItem();

      @SideOnly(Side.CLIENT)
      public String getTabLabel() {
         return this.label;
      }
   }
}
