package com.miscitems.MiscItemsAndBlocks.Items;

import com.miscitems.MiscItemsAndBlocks.Lib.Refrence;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ModItemBattery extends ModItemPowerStorage{

	public ModItemBattery() {

	}


	   @SideOnly(Side.CLIENT)
	   public void registerIcons(IIconRegister par1IconRegister)
	   {
           super.registerIcons(par1IconRegister);
		   
		   itemIcon = par1IconRegister.registerIcon(Refrence.Mod_Id + ":Battery");

		   
	   }

	   
		@Override
		public int MaxPower(ItemStack stack) {
			return 16;
		}

		@Override
		public int ChargeAmount(ItemStack stack) {
			return 1;
		}

		@Override
		public boolean CanBackpackRecharge(ItemStack stack) {
			return false;
		}
}
