package com.Impasta1000.XKits.resources;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;

public class PlayerManager {
	
	public PlayerManager(XKits plugin) {
		
	}
	
	//Save player inventory to HashMap
	public void saveInvToHashMap(Player player, HashMap<String, ItemStack[]> invMap) {
		ItemStack[] inventoryContents = player.getInventory().getContents();
		String playerUUID = player.getUniqueId().toString();
		
		invMap.put(playerUUID, inventoryContents);
	}
	
	//Load player inventory from HashMap
	public void loadInvFromMap(Player player, HashMap<String, ItemStack[]> invMap) {
		String playerUUID = player.getUniqueId().toString();
		
		ItemStack[] inventoryContents = invMap.get(playerUUID);
		player.getInventory().setContents(inventoryContents);
	}

}
