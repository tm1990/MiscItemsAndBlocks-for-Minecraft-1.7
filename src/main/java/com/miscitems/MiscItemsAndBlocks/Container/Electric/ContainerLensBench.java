package com.miscitems.MiscItemsAndBlocks.Container.Electric;

import MiscUtils.Utils.ContainerBase;
import com.miscitems.MiscItemsAndBlocks.GuiObjects.Slots.ModSlotLensSlot;
import com.miscitems.MiscItemsAndBlocks.TileEntity.Electric.TileEntityLensBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerLensBench  extends ContainerBase {

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return tile.isUseableByPlayer(entityplayer);
	}
	
    public TileEntityLensBench tile;

	
    public ContainerLensBench(InventoryPlayer InvPlayer, TileEntityLensBench tile)
    {
    	this.tile = tile;
    	
    	for(int x = 0; x < 9; x++){
    		
    		addSlotToContainer(new Slot(InvPlayer, x, 8 + 18 * x, 232));
    	}
    	
    	for(int y = 0; y < 3; y++){
    		for(int x = 0; x < 9; x++){
    			
    			addSlotToContainer(new Slot(InvPlayer, x + y * 9 + 9, 8 + 18 * x, 174 + y * 18));
    		}
    }
    	
    	
    	
    	addSlotToContainer(new ModSlotLensSlot(tile, 0, 7, 7));

}


    @Override
    public IInventory getTile() {
        return tile;
    }

}