package gtmaterials.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gtmaterials.block.tileentity.TileEntityAdvancedMiner;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAdvancedMiner extends BlockContainer {
   private IIcon[] icon = new IIcon[2];

   public BlockAdvancedMiner(Material p_i45394_1_) {
      super(p_i45394_1_);
   }

   public TileEntity createNewTileEntity(World arg0, int arg1) {
      return new TileEntityAdvancedMiner();
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      this.icon[0] = register.registerIcon("gtmaterials:quarry/top");
      this.icon[1] = register.registerIcon("gtmaterials:quarry/side");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      switch(side) {
      case 0:
      case 1:
         return this.icon[0];
      default:
         return this.icon[1];
      }
   }

   public int damageDropped(int metadata) {
      return metadata;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
      TileEntityAdvancedMiner tile = (TileEntityAdvancedMiner)world.getTileEntity(x, y, z);
      if(tile != null && tile instanceof TileEntityAdvancedMiner && !world.isRemote) {
         if(tile.onWork()) {
            player.addChatMessage(new ChatComponentText("This Quarry is working now."));
            player.addChatMessage(new ChatComponentText("Battery : " + String.valueOf(tile.getBattery())));
         }

         return true;
      } else {
         return true;
      }
   }
}
