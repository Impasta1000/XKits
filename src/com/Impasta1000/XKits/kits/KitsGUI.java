package com.Impasta1000.XKits.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class KitGUI {
	
	/*
	 * Manages the GUI when players type /xkits kits
	 */
	
	private ResourcesAPI rApi;
	
	public KitGUI(XKits plugin) {
		this.rApi = new ResourcesAPI(plugin);
	}
	
	public void openKitGUI(Player player) {
		
		Inventory kitInv = Bukkit.createInventory(player, 9, rApi.colourize("&eKits"));
		
		//ItemStacks displayed in kitInv
		ItemStack fighterLogo = rApi.createNewItem(Material.IRON_SWORD, 1, "&cFighter", "&3Get the Fighter Kit!");
		
		kitInv.setItem(0, fighterLogo);
		
		player.openInventory(kitInv);
	}

}
