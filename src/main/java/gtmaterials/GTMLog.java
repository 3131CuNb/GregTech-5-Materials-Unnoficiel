package gtmaterials;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GTMLog {
   public static GTMLog INSTANCE = new GTMLog();
   public Logger log = LogManager.getLogger("GTMaterials");

   public void view(String str) {
      this.log.info(str);
   }

   public void view(boolean str) {
      this.log.info(String.valueOf(str));
   }

   public void view(int str) {
      this.log.info(String.valueOf(str));
   }
}
