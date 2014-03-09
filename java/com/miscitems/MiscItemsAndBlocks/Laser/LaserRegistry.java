package com.miscitems.MiscItemsAndBlocks.Laser;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class LaserRegistry {

	/**
	 * @author ProPercivalalb <https://github.com/ProPercivalalb/LaserMod>
	 */
	
private static Hashtable<String, ILaser> mappings = new Hashtable<String, ILaser>();
private static Hashtable<List<Object>, ILaser> mappingItems = new Hashtable<List<Object>, ILaser>();

public static void registerLaser(String id, ILaser laser) {
if(!mappings.keySet().contains(id))
mappings.put(id, laser);
}

public static void registerItemToLaser(Item item, int meta, ILaser laser) {
List<Object> list = Arrays.asList(item, meta);
if(!mappingItems.keySet().contains(list))
mappingItems.put(list, laser);
}

public static ILaser getLaserFromItem(ItemStack stack) {
List<Object> list = Arrays.asList(stack.getItem(), stack.getItemDamage());
if(mappingItems.keySet().contains(list))
return mappingItems.get(list);
return null;
}

public static ILaser getLaserFromId(String id) {
return mappings.get(id);
}

public static String getIdFromLaser(ILaser laser) {
Set<String> keySet = mappings.keySet();
for(String id : keySet) {
if(mappings.get(id) == laser)
return id;
}
return "default";
}
}