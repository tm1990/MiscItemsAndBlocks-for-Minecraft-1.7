package com.miscitems.MiscItemsAndBlocks.Container.Machines;

import MiscUtils.GuiObjects.Slots.SlotOutput;
import MiscUtils.Utils.ContainerBase;
import com.miscitems.MiscItemsAndBlocks.GuiObjects.Slots.SlotFuel;
import com.miscitems.MiscItemsAndBlocks.GuiObjects.Slots.SlotOven;
import com.miscitems.MiscItemsAndBlocks.TileEntity.Machines.TileEntityOven;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerPizzaOven  extends ContainerBase {

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return tile.isUseableByPlayer(entityplayer);
	}
	
    private TileEntityOven tile;
    
    int lastWorkTime = 0;
    int lastHeat = 0;
	
    public ContainerPizzaOven(InventoryPlayer InvPlayer, TileEntityOven tile)
    {
    	this.tile = tile;
    	
    	for(int x = 0; x < 9; x++){
    		
    		addSlotToContainer(new Slot(InvPlayer, x, 8 + 18 * x, 142));
    	}
    	
    	for(int y = 0; y < 3; y++){
    		for(int x = 0; x < 9; x++){
    			
    			addSlotToContainer(new Slot(InvPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
    		}
    		
    		addSlotToContainer(new SlotFuel(tile, 0, 80, 54));
    		addSlotToContainer(new SlotOven(tile, 1, 80, 13));
    		addSlotToContainer(new SlotOutput(tile, 2, 134, 33));
    	
    }

}


    @Override
    public IInventory getTile() {
        return tile;
    }
    
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.GetWorkTime());
        par1ICrafting.sendProgressBarUpdate(this, 1, this.tile.GetHeat());
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastWorkTime != this.tile.GetWorkTime())
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.GetWorkTime());
            }
            
            if (this.lastHeat != this.tile.GetHeat())
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tile.GetHeat());
            }
        }

        this.lastHeat = this.tile.GetHeat();
        this.lastWorkTime = this.tile.GetWorkTime();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.tile.SetWorkTime(par2);
        }
        
        if(par1 == 1){
        	this.tile.SetHeat(par2);
        }
    }


}
