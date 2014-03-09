package com.miscitems.MiscItemsAndBlocks.Items;

import com.miscitems.MiscItemsAndBlocks.Lib.Refrence;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ModItemCircuit extends Item{

	public ModItemCircuit() {
		super();
		this.setHasSubtypes(true);
	}
	
	IIcon Icon1;
	IIcon Icon2;

	   @SideOnly(Side.CLIENT)
	   public void registerIcons(IIconRegister par1IconRegister)
	   {
		   this.Icon1 = par1IconRegister.registerIcon(Refrence.Mod_Id + ":Circuit");
		   this.Icon2 = par1IconRegister.registerIcon(Refrence.Mod_Id + ":AdvancedCircuit");
		   
	   }
	   
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
	    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	    {
	    	
	    	int meta = itemstack.getItemDamage();
	    	if(meta == 0)
	            list.add(StatCollector.translateToLocal("items.desc.circuit.basic"));
	    	
	    	if(meta == 1)
	    		list.add(StatCollector.translateToLocal("items.desc.circuit.advanced"));
	    }
	    
	    
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	    {
	        super.getSubItems(par1, par2CreativeTabs, list);
	        
	        list.add(new ItemStack(par1, 1, 1));
	        
	    }
	    
	    public IIcon getIconFromDamage(int meta)
	    {
	    	if(meta == 0)
	    		return Icon1;
	    	else if(meta == 1)
	    		return Icon2;
	    	
	    	
	    	
	    	return Icon1;
	    	
	    }
	    
	    public String getUnlocalizedName(ItemStack stack)
	    {
	    	int meta = stack.getItemDamage();

	    	
	    	if(meta == 0)return "item.circuit.basic";
	    	if(meta == 1)return "item.circuit.advanced";

	    	return null;
	    }
	
}
