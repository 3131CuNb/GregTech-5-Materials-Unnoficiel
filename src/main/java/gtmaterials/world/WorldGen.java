package gtmaterials.world;

import cpw.mods.fml.common.registry.GameRegistry;
import gtmaterials.world.WorldGenOre;

public class WorldGen {
   public static void init() {
      GameRegistry.registerWorldGenerator(new WorldGenOre(), 150);
   }
}
