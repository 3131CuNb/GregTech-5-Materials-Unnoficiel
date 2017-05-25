package gtmaterials.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import gtmaterials.GTMaterials;
import gtmaterials.proxy.Common;
import gtmaterials.recipe.NEI;
import gtmaterials.render.GTMSimpleBlockRenderintHandler;

public class Client extends Common {
   public void preInit() {
      super.preInit();
      this.renderID = RenderingRegistry.getNextAvailableRenderId();
   }

   public void init() {
      super.init();
      RenderingRegistry.registerBlockHandler(GTMaterials.Proxy.getRenderingID(), new GTMSimpleBlockRenderintHandler());
   }

   public void postInit() {
      if(Loader.isModLoaded("NotEnoughItems")) {
         NEI.load();
      }

   }

   public boolean isClient() {
      return true;
   }

   public boolean isServer() {
      return false;
   }
}
