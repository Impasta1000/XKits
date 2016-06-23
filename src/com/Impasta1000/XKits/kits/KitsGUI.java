package com.Impasta1000.XKits.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class KitsGUI {
	
	/*
	 * Manages the GUI when players type /xkits kits
	 */
	
	private ResourcesAPI rApi;
	
	public KitsGUI(XKits plugin) {
		this.rApi = new ResourcesAPI(plugin);
	}
	
	public void openKitGUI(Player player) {
		
		Inventory kitInv = Bukkit.createInventory(player, 9, rApi.colourize("&eKits"));
		
		//ItemStacks displayed in kitInv
		ItemStack fighterLogo = rApi.createCustomItem(Material.SHIELD, 1, "&cFighter", "&3Get the Fighter Kit!");
		ItemStack rangerLogo = rApi.createCustomItem(Material.BOW, 1, "&fRanger", "&3Get the Ranger Kit!");
		
		kitInv.setItem(0, fighterLogo);
		kitInv.setItem(1, rangerLogo);
		
		player.openInventory(kitInv);
	}

}
