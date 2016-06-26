package com.Impasta1000.XKits.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class ArenaGUI {
	
	/*
	 * GUI System for managing Arenas
	 */
	
	private ResourcesAPI rApi;
	private XKits plugin;

	public ArenaGUI(XKits plugin) {
		this.plugin = plugin;
		rApi = new ResourcesAPI(plugin);
	}
	
	public void openArenaGUI(Player player) {
		Inventory arenaGui = Bukkit.createInventory(player, 9, rApi.colourize("&c&nArena Manager"));
		
		//TODO Add whether commands is locked or not
		ItemStack displayArena = rApi.createCustomItem(Material.BOOK_AND_QUILL, 1, "&a&lArenas List", "&7Click this to view list of arenas.");
		
		ItemStack currentArena = rApi.createCustomItem(Material.IRON_CHESTPLATE, 1, "&6&lKitPVP Arena", "&7You are currently in &9" + plugin.getPlayersInArenaMap().get(player.getName()));
		
		arenaGui.setItem(0, displayArena);
		arenaGui.setItem(arenaGui.getSize() - 1, currentArena);
		
		player.openInventory(arenaGui);
	}
}
