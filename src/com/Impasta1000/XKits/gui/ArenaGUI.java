package com.Impasta1000.XKits.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.resources.ArenaManager;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class ArenaGUI {

	/*
	 * GUI System for managing Arenas
	 */

	private ResourcesAPI rApi;
	private XKits plugin;
	private ArenaManager arenaManager;

	public ArenaGUI(XKits plugin) {
		this.plugin = plugin;
		rApi = new ResourcesAPI(plugin);
		arenaManager = new ArenaManager(plugin);
	}
	
	public void openXKitsGUI(Player player) {
		
		boolean isPlayerInArena = arenaManager.checkPlayerInArena(player);
		
		ItemStack currentArena, displayArena, manageArena, fillerItem;
		
		Inventory arenaGui = Bukkit.createInventory(null, 9, "XKits Arena");

		displayArena = rApi.createCustomItem(Material.BOOK_AND_QUILL, 1, "&6&lList Arenas",
				"&7Display list of arenas.");
		
		fillerItem = rApi.createGlassPane(Material.STAINED_GLASS_PANE, 1, (byte) 14, " ", " ");
		
		if (isPlayerInArena) {
		currentArena = rApi.createCustomItem(Material.IRON_CHESTPLATE, 1, "&6&lArena",
				"&7You are currently in &e" + plugin.getPlayersInArenaMap().get(player.getName()));
		} else {
			currentArena = rApi.createCustomItem(Material.IRON_CHESTPLATE, 1, "&6&lArena",
					"&7You are not in any arena");
		}
		
		manageArena = rApi.createCustomItem(Material.IRON_SWORD, 1, "&6&lManage Arena", "&fManage the arena");
		
		if (!player.hasPermission("XKits.Arena.Manage")) {
			arenaGui.setItem(0, displayArena);
			arenaGui.setItem(arenaGui.getSize() - 1, currentArena);
		} else {
			arenaGui.setItem(0, displayArena);
			arenaGui.setItem(1, manageArena);
			arenaGui.setItem(arenaGui.getSize() - 1, currentArena);
		}
		
		for (int count = 0; count < arenaGui.getSize(); count++) {
			if (arenaGui.getItem(count) == null) {
				arenaGui.setItem(count, fillerItem);
			}
		}

		player.openInventory(arenaGui);
	}
	
	public void openArenaManagerGUI(Player player) {
		
		Inventory arenaManagerInv = Bukkit.createInventory(null, 9, "Arena Manager");
		
		player.openInventory(arenaManagerInv);
	}
}
