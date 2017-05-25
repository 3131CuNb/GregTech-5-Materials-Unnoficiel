package gtmaterials.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Teleporter;
import gtmaterials.GTMItem;
import gtmaterials.GTMUtil;
import gtmaterials.GTMaterials;
import gtmaterials.block.container.ContainerBlockABC;
import gtmaterials.block.container.ContainerMatterFabricator;
import gtmaterials.block.container.ContainerToolWorkbench;
import gtmaterials.block.gui.GuiAdvancedMiner;
import gtmaterials.block.gui.GuiBlockABC;
import gtmaterials.block.gui.GuiMatterFabricator;
import gtmaterials.block.gui.GuiSteamGenerator;
import gtmaterials.block.gui.GuiToolWorkbench;
import gtmaterials.block.tileentity.TileEntityAdvancedMiner;
import gtmaterials.block.tileentity.TileEntityBlockABC;
import gtmaterials.block.tileentity.TileEntityMatterFabricator;
import gtmaterials.block.tileentity.TileEntitySteamGenerator;
import gtmaterials.world.manager.SafetyAreaData;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Pre;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public class Common implements IGuiHandler {
   protected int renderID;

   public void preInit() {
      NetworkRegistry.INSTANCE.registerGuiHandler(GTMaterials.instance, this);
      this.renderID = -1;
   }

   public void init() {
      MinecraftForge.EVENT_BUS.register(this);
      FMLCommonHandler.instance().bus().register(this);
   }

   public void postInit() {
   }

   public boolean isClient() {
      return false;
   }

   public boolean isServer() {
      return true;
   }

   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      TileEntity tileentity = world.getTileEntity(x, y, z);
      return ID == 0 && world.getBlock(x, y, z)
    		  == GTMItem.ToolWorkbench?new ContainerToolWorkbench(world, x, y, z, player)
    				  :(ID == 1 && tileentity != null && tileentity instanceof TileEntityMatterFabricator?new ContainerMatterFabricator((TileEntityMatterFabricator)tileentity, player)
    						  :(ID == 2 && tileentity != null && tileentity instanceof TileEntitySteamGenerator?GTMUtil.nullContainer()
    								  :(ID == 3 && tileentity != null && tileentity instanceof TileEntityAdvancedMiner?GTMUtil.nullContainer()
    										  :(ID == 4 && tileentity != null && tileentity instanceof TileEntityBlockABC?new ContainerBlockABC((TileEntityBlockABC)tileentity, player):null))));
   }

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      TileEntity tileentity = world.getTileEntity(x, y, z);
      return ID == 0 && world.getBlock(x, y, z) == GTMItem.ToolWorkbench?new GuiToolWorkbench(world, x, y, z, player)
    		  :(ID == 1 && tileentity != null && tileentity instanceof TileEntityMatterFabricator?new GuiMatterFabricator((TileEntityMatterFabricator)tileentity, player)
    				  :(ID == 2 && tileentity != null && tileentity instanceof TileEntitySteamGenerator?new GuiSteamGenerator((TileEntitySteamGenerator)tileentity)
    						  :(ID == 3 && tileentity != null && tileentity instanceof TileEntityAdvancedMiner?new GuiAdvancedMiner((TileEntityAdvancedMiner)tileentity)
    								  :(ID == 4 && tileentity != null && tileentity instanceof TileEntityBlockABC? new GuiBlockABC((TileEntityBlockABC)tileentity, player):null))));
      //return ID == 0 && world.getBlock(x, y, z) == GTMItem.ToolWorkbench?new GuiToolWorkbench(world, x, y, z, player):(ID == 1 && tileentity != null && tileentity instanceof TileEntityMatterFabricator?new GuiMatterFabricator((TileEntityMatterFabricator)tileentity, player):(ID == 2 && tileentity != null && tileentity instanceof TileEntitySteamGenerator?new GuiSteamGenerator((TileEntitySteamGenerator)tileentity):(ID == 3 && tileentity != null && tileentity instanceof TileEntityAdvancedMiner?new GuiAdvancedMiner((TileEntityAdvancedMiner)tileentity):null)));
   }

   public int getRenderingID() {
      return this.renderID;
   }

   @SubscribeEvent
   public void onPlayerTick(PlayerTickEvent event) {
      EntityPlayer player = event.player;
      if(event.side.isServer() && SafetyAreaData.INSTANCE.isSafety(player)) {
         int x = (int)player.posX;
         int y = (int)player.posY;
         int z = (int)player.posZ;
         World world = player.worldObj;

         for(int i1 : new int[]{-1, 0, 1}) {
            for(int i2 : new int[]{-1, 0, 1}) {
               if(world.getBlock(x + i1, y - 1, z + i2) instanceof BlockAir) {
                  world.setBlock(x + i1, y - 1, z + i2, Blocks.glass);
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onBreak(BreakEvent event) {
      Block block = event.block;
      World world = event.world;
      int x = event.x;
      int y = event.y;
      int z = event.z;
      if(block.equals(GTMItem.AdvancedMiner)) {
         Chunk chunk = world.getChunkFromBlockCoords(x, z);
         int x2 = (chunk.xPosition - 1) * 16;
         int z2 = (chunk.zPosition - 1) * 16;
         int y2 = y - 1;
         SafetyAreaData.INSTANCE.removeSafety(world, x2, x2 + 47, z2, z2 + 47, y2, world.getWorldInfo().getVanillaDimension());
      }

   }

   @SubscribeEvent
   public void onPlace(PlaceEvent event) {
      Block block = event.block;
      World world = event.world;
      EntityPlayer player = event.player;
      int x = event.x;
      int y = event.y;
      int z = event.z;
      if(block.equals(GTMItem.AdvancedMiner)) {
         if(world.getBlock(x, y, z) == GTMItem.AdvancedMiner && y <= 1) {
            event.setCanceled(true);
            player.addChatMessage(new ChatComponentText("You can\'t place to Y = 1"));
         }
      } else if(block.equals(GregTech_API.sBlockMachines) && world.getTileEntity(x, y, z) instanceof IGregTechTileEntity) {
         IMetaTileEntity meta = ((IGregTechTileEntity)world.getTileEntity(x, y, z)).getMetaTileEntity();
         if(meta != null && meta instanceof GT_MetaTileEntity_Teleporter) {
            GT_MetaTileEntity_Teleporter teleporter = (GT_MetaTileEntity_Teleporter)meta;
            teleporter.mTargetX = x;
            teleporter.mTargetY = y;
            teleporter.mTargetZ = z;
            teleporter.mTargetD = world.getWorldInfo().getVanillaDimension();
         }
      }

   }

   @SubscribeEvent
   public void onLivingSpawn(LivingSpawnEvent event) {
      EntityLivingBase entity = event.entityLiving;
      if(entity instanceof EntityEnderman && Loader.isModLoaded("gregtech")) {
         entity.addPotionEffect(new PotionEffect(Potion.weakness.id, Integer.MAX_VALUE, 3));
      }

   }

   @SubscribeEvent
   public void onLivingHurt(LivingHurtEvent event) {
      Entity entity = event.entityLiving;
      int analytics = 0;
      if(entity != null && entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         Item[] armor = new Item[]{GTMItem.ArmorBoots, GTMItem.ArmorLeggings, GTMItem.ArmorChestplate, GTMItem.ArmorHelmet};

         for(int i = 3; i >= 0; --i) {
            if(player.getCurrentArmor(i) != null && player.getCurrentArmor(i).getItem() == armor[i]) {
               ++analytics;
            }
         }

         if(analytics == 4 && !event.source.equals(DamageSource.outOfWorld) && !event.source.equals(DamageSource.starve)) {
            event.setCanceled(true);
            if(event.source.equals(DamageSource.drown)) {
               player.setAir(300);
            }

            if(event.source.equals(DamageSource.magic) || event.source.equals(DamageSource.wither)) {
               Collection col = player.getActivePotionEffects();
               PotionEffect[] i = (PotionEffect[])((PotionEffect[])col.toArray(new PotionEffect[col.size()]));

               for(PotionEffect potion : i) {
                  if(!player.worldObj.isRemote && !player.capabilities.isCreativeMode && (potion.getPotionID() == Potion.wither.id || potion.getPotionID() == Potion.poison.id)) {
                     player.removePotionEffect(potion.getPotionID());
                  }
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onInitNoiseGensEvent(InitNoiseGensEvent event) {
   }

   @SubscribeEvent
   public void onPopulateChunkEvent(Pre event) {
      if(event.world.provider.dimensionId == -1) {
         MapGenNetherBridge another = new MapGenNetherBridge() {
            protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_) {
               return this.rand.nextInt(250) < 0;
            }
         };
         another.func_151539_a(event.chunkProvider, event.world, event.chunkX, event.chunkZ, (Block[])null);
         another.generateStructuresInChunk(event.world, event.rand, event.chunkX, event.chunkZ);
      }

   }
}
