package gtmaterials.world.manager;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SafetyAreaData {
   private final ArrayList<SafetyAreaData.Position> list = new ArrayList();
   public static SafetyAreaData INSTANCE;

   public void setSafety(World world, int xMin, int xMax, int zMin, int zMax, int Y, int D) {
      SafetyAreaData.Position position = new SafetyAreaData.Position(xMin, xMax, zMin, zMax, Y, D);
      if(!this.list.contains(position)) {
         this.list.add(position);
      }

   }

   public void removeSafety(World world, int xMin, int xMax, int zMin, int zMax, int Y, int D) {
      SafetyAreaData.Position position = new SafetyAreaData.Position(xMin, xMax, zMin, zMax, Y, D);
      if(this.list.contains(position)) {
         this.list.remove(position);
      }

   }

   public ArrayList<SafetyAreaData.Position> getSafety() {
      return this.list;
   }

   public boolean isSafety(EntityPlayer player) {
      for(SafetyAreaData.Position position : this.list) {
         int[] p = position.get();
         if(p[0] <= (int)player.posX && (int)player.posX <= p[1] && p[2] <= (int)player.posZ && (int)player.posZ <= p[3] && (int)player.posY <= p[4] && player.worldObj.getWorldInfo().getVanillaDimension() == p[5]) {
            return true;
         }
      }

      return false;
   }

   public void reset() {
      this.list.clear();
   }

   public static class Position {
      int xMin;
      int xMax;
      int zMin;
      int zMax;
      int Y;
      int D;

      public Position() {
      }

      public Position(int xMin, int xMax, int zMin, int zMax, int Y, int D) {
         this.xMin = xMin;
         this.xMax = xMax;
         this.zMin = zMin;
         this.zMax = zMax;
         this.Y = Y;
         this.D = D;
      }

      public int[] get() {
         return new int[]{this.xMin, this.xMax, this.zMin, this.zMax, this.Y, this.D};
      }

      public boolean equals(Object object) {
         SafetyAreaData.Position p = (SafetyAreaData.Position)object;
         return p.xMin == this.xMin && p.xMax == this.xMax && p.zMin == this.zMin && p.zMax == this.zMax && p.Y == this.Y && p.D == this.D;
      }
   }
}
