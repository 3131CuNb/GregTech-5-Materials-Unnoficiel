package gtmaterials.world;

import cpw.mods.fml.common.IWorldGenerator;
import gregtech.api.GregTech_API;
import gtmaterials.GTMItem;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenOre implements IWorldGenerator {
   public void generate(Random rand, int xChunk, int zChunk, World world, IChunkProvider icp1, IChunkProvider icp2) {
      switch(world.provider.dimensionId) {
      case -1:
         this.nether(rand, xChunk * 16, zChunk * 16, world);
         break;
      case 0:
         this.over(rand, xChunk * 16, zChunk * 16, world);
         break;
      case 1:
         this.end(rand, xChunk * 16, zChunk * 16, world);
      }
   }

   private void over(Random rand, int xChunk, int zChunk, World world) {
      for(int j = 0; j < 20; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(47) + 5;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(Blocks.coal_ore, 0, rand.nextInt(15) + 5, Blocks.stone)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 10; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(49) + 5;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(Blocks.iron_ore, 0, rand.nextInt(10) + 5, Blocks.stone)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 8; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(2) + 14;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(Blocks.lapis_ore, 0, rand.nextInt(3) + 1, Blocks.stone)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 6; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(24) + 5;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(Blocks.gold_ore, 0, rand.nextInt(3) + 1, Blocks.stone)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 20; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(7) + 5;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(Blocks.diamond_ore, 0, rand.nextInt(10) + 3, Blocks.stone)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 10; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(7) + 5;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(Blocks.redstone_ore, 0, rand.nextInt(6) + 3, Blocks.stone)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 15; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(24) + 5;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(Blocks.emerald_ore, 0, rand.nextInt(10) + 1, Blocks.stone)).generate(world, rand, x, y, z);
      }

      Block[] target = new Block[]{Blocks.stone, GregTech_API.sBlockGranites};

      for(Block stone : target) {
         for(int j = 0; j < 8; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y = rand.nextInt(31) + 1;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 0, rand.nextInt(10) + 4, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 5; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y = rand.nextInt(99) + 1;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 1, rand.nextInt(3) + 1, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 7; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y = rand.nextInt(31) + 1;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 2, rand.nextInt(5) + 2, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 7; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y = rand.nextInt(31) + 1;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 3, rand.nextInt(5) + 2, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 8; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y1 = rand.nextInt(48) + 32;
            int y2 = rand.nextInt(50) + 70;
            int y = rand.nextBoolean()?y1:y2;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 4, rand.nextInt(10) + 4, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 9; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y = rand.nextInt(63) + 1;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 5, rand.nextInt(8) + 2, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 9; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y = rand.nextInt(63) + 1;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 6, rand.nextInt(8) + 2, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 8; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y = rand.nextInt(12) + 2;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 7, rand.nextInt(9) + 3, stone)).generate(world, rand, x, y, z);
         }

         for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 7; ++j) {
               int x = xChunk + rand.nextInt(16);
               int y = rand.nextInt(50) + 2;
               int z = zChunk + rand.nextInt(16);
               (new WorldGenMinable(GTMItem.GregOres, 8 + i, rand.nextInt(16) + 5, stone)).generate(world, rand, x, y, z);
            }
         }

         for(int j = 0; j < 8; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y1 = rand.nextInt(38) + 32;
            int y2 = rand.nextInt(50) + 70;
            int y = rand.nextBoolean()?y1:y2;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 12, rand.nextInt(15) + 8, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 9; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y1 = rand.nextInt(38) + 32;
            int y2 = rand.nextInt(50) + 70;
            int y = rand.nextBoolean()?y1:y2;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 13, rand.nextInt(15) + 8, stone)).generate(world, rand, x, y, z);
         }

         for(int j = 0; j < 6; ++j) {
            int x = xChunk + rand.nextInt(16);
            int y = rand.nextInt(22) + 10;
            int z = zChunk + rand.nextInt(16);
            (new WorldGenMinable(GTMItem.GregOres, 14, rand.nextInt(10) + 3, stone)).generate(world, rand, x, y, z);
         }
      }

   }

   private void nether(Random rand, int xChunk, int zChunk, World world) {
      for(int j = 0; j < 20; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(110) + 10;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(Blocks.quartz_ore, 0, rand.nextInt(15) + 5, Blocks.netherrack)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 25; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(128) + 1;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(GTMItem.MatterOres, 0, 24, Blocks.nether_brick)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 25; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(128) + 1;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(GTMItem.MatterOres, 1, 24, Blocks.nether_brick)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 25; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(128) + 1;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(GTMItem.MatterOres, 2, 24, Blocks.nether_brick)).generate(world, rand, x, y, z);
      }

      for(int j = 0; j < 25; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(128) + 1;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(GTMItem.MatterOres, 3, 24, Blocks.nether_brick)).generate(world, rand, x, y, z);
      }

   }

   private void end(Random rand, int xChunk, int zChunk, World world) {
      for(int j = 0; j < 10; ++j) {
         int x = xChunk + rand.nextInt(16);
         int y = rand.nextInt(20) + 15;
         int z = zChunk + rand.nextInt(16);
         (new WorldGenMinable(GTMItem.GregOres, rand.nextInt(4) + 8, rand.nextInt(6) + 5, Blocks.air)).generate(world, rand, x, y, z);
      }

   }
}
