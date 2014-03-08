package com.miscitems.MiscItemsAndBlocks.Block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.miscitems.MiscItemsAndBlocks.Lib.Refrence;
import com.miscitems.MiscItemsAndBlocks.Main.Main;
import com.miscitems.MiscItemsAndBlocks.TileEntity.TileEntityElectricFurnace;
import com.miscitems.MiscItemsAndBlocks.TileEntity.TileEntityGenerator;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModBlockGenerator extends BlockContainer{

	protected ModBlockGenerator() {
		super(Material.iron);
		this.setHardness(2);
	}
	
	public static final int META_ISACTIVE = 0x00000008;
	public static final int MASK_DIR = 0x00000007;
	public static final int META_DIR_NORTH = 0x00000001;
	public static final int META_DIR_SOUTH = 0x00000002;
	public static final int META_DIR_EAST = 0x00000003;
	public static final int META_DIR_WEST = 0x00000000;
	
	IIcon IconTop;
	IIcon IconSide;
	IIcon IconFront;
	IIcon IconFrontLit;

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityGenerator();
	}


	
	private static int getSideFromFacing(int facing)
	{
		switch(facing)
		{
		case META_DIR_WEST:
			return 4;
			
		case META_DIR_EAST:
			return 5;
			
		case META_DIR_NORTH:
			return 2;
			
		case META_DIR_SOUTH:
			return 3;
			
		default:
			return 4;
		}
	}
	
	   public void registerBlockIcons(IIconRegister par1IconRegister)
	    {
	    	
	        this.IconTop = par1IconRegister.registerIcon(Refrence.Mod_Id + ":" + "ModuleBlank");
	        this.IconSide = par1IconRegister.registerIcon(Refrence.Mod_Id + ":" + "ModuleBlank");
	        this.IconFront = par1IconRegister.registerIcon(Refrence.Mod_Id + ":" + "GeneratorFront");
	        this.IconFrontLit = par1IconRegister.registerIcon(Refrence.Mod_Id + ":" + "GeneratorFrontLit");
	        
	    }
	    
		@SuppressWarnings("unused")
		@Override
		public IIcon getIcon(int side, int metadata)
		{
			boolean isActive = ((metadata >> 3) == 1);
			int facing = (metadata & MASK_DIR);
			
			return (side == getSideFromFacing(facing) ? (IconFront) : (side == 1 ? this.IconTop : side == 0 ? IconTop : this.IconSide));
		}
	    
		@Override
		public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		{
			int metadata = 0;
			int facing = META_DIR_WEST;
			
			int dir = MathHelper.floor_double((double)(entity.rotationYaw * 4f / 360f) + 0.5) & 3;
			if(dir == 0)
				facing = META_DIR_NORTH;
			if(dir == 1)
				facing = META_DIR_EAST;
			if(dir == 2)
				facing = META_DIR_SOUTH;
			if(dir == 3)
				facing = META_DIR_WEST;
			
			metadata |= facing;
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
		}
		
		
		  @Override
		    public void breakBlock(World World, int x, int y, int z, Block id, int meta)
		    {
		    	TileEntity tile = World.getTileEntity(x, y, z);
		    	
		    	if(tile != null && tile instanceof IInventory){
		    		IInventory inv = (IInventory)tile;
		    		
		    		for(int i = 0; i < inv.getSizeInventory(); i++){
		    			ItemStack stack = inv.getStackInSlotOnClosing(i);
		    			
		    			if(stack != null){
		    				float spawnX = x + World.rand.nextFloat();
		    				float spawnY = y + World.rand.nextFloat();
		    				float spawnZ = z + World.rand.nextFloat();
		    				
		    				
		    				EntityItem droppedItem = new EntityItem(World, spawnX, spawnY, spawnZ, stack);
		    				
		    				float mult = 0.05F;
		    				
		    				droppedItem.motionX = (-0.5 + World.rand.nextFloat()) * mult;
		    				droppedItem.motionY = (4 + World.rand.nextFloat()) * mult;
		    				droppedItem.motionZ = (-0.5 + World.rand.nextFloat()) * mult;
		    				
		    				
		    				World.spawnEntityInWorld(droppedItem);
		    			}
		    			
		    		}
		    	}
				super.breakBlock(World, x, y, z, id, meta);
		    }
		  
		    
		    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
		    {
		        if (par1World.isRemote)
		        {
		        	
		            return true;
		        }
		        else
		        {
		        	
		        	
		        	
		        	FMLNetworkHandler.openGui(par5EntityPlayer, Main.instance, 0, par1World, par2, par3, par4);
		            return true;
		        }
		    }
		    
		    @Override
			public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
			{
				int Meta = world.getBlockMetadata(x, y, z);
				
				if(world.getTileEntity(x, y, z) instanceof TileEntityGenerator){
					TileEntityGenerator tile = (TileEntityGenerator)world.getTileEntity(x, y, z);
				
					boolean Running = tile.GetTimeLeft() > 0;
				
				
				return (side == getSideFromFacing(Meta) ? (Running ?  IconFrontLit : IconFront) : (side == 1 ? this.IconSide : side == 0 ? IconSide : this.IconSide));
				}
				return getIcon(side, Meta);
			}

		    
		    @SideOnly(Side.CLIENT)
		    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
		    {
		    	
		        
		    	if(par1World.getTileEntity(par2, par3, par4) instanceof TileEntityGenerator){
		    		TileEntityGenerator tile = (TileEntityGenerator)par1World.getTileEntity(par2, par3, par4);
		    	
		    	
		    	if(tile.GetTimeLeft() > 0)
		    	
		        {
		            int Meta = par1World.getBlockMetadata(par2, par3, par4);
		            int l = getSideFromFacing(Meta);
		            float f = (float)par2 + 0.5F;
		            float f1 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
		            float f2 = (float)par4 + 0.5F;
		            float f3 = 0.52F;
		            float f4 = par5Random.nextFloat() * 0.6F - 0.3F;

		            if (l == 4)
		            {
		                par1World.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
		                par1World.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
		            }
		            else if (l == 5)
		            {
		                par1World.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
		                par1World.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
		            }
		            else if (l == 2)
		            {
		                par1World.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
		                par1World.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
		            }
		            else if (l == 3)
		            {
		                par1World.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
		                par1World.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
		            }
		        }
		    }
		    }
}
