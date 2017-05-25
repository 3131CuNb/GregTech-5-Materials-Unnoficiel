package gtmaterials;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class GTMConfig {
   public static boolean fuckingmcreator,lol;
   //public static boolean allitemmatterrecipe;

   public static void preInit() {
      Configuration config = new Configuration(new File("config/GTMaterials.cfg"));
      config.load();
   //   allitemmatterrecipe = config.get("Recipe", "Accept_Upgraded_Matter_Recipe", false).getBoolean();
      fuckingmcreator = config.get("FORNOOBS", "Accept_MCreator", false).getBoolean();
      config.save();
   }
}
