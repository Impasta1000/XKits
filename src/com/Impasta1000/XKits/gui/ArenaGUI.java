package com.Impasta1000.XKits.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.config.ConfigManager;
import com.Impasta1000.XKits.config.ConfigManager.ConfigFile;
import com.Impasta1000.XKits.utils.ArenaManager;
import com.Impasta1000.XKits.utils.ResourcesAPI;

public class ArenaGUI {

	/*
	 * GUI System for managing Arenas
	 */

	private ResourcesAPI rApi;
	private XKits plugin;
	private ArenaManager arenaManager;
	private ConfigManager configManager;

	public ArenaGUI(XKits plugin) {
		this.plugin = plugin;
		rApi = new ResourcesAPI(plugin);
		arenaManager = new ArenaManager(plugin);
		configManager = new ConfigManager(plugin);
	}

	public void openXKitsGUI(Player player) {

		boolean isPlayerInArena = arenaManager.checkPlayerInArena(player);
		ItemStack currentArena, displayArena, manageArena, fillerItem;
		Inventory arenaGui = Bukkit.createInventory(null, 9, "XKits");

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

		manageArena = rApi.createCustomItem(Material.IRON_SWORD, 1, "&6&lManage Arena", "&7Manage the arena");

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

	public void openArenaManagerGUI(Player player, String arenaName) {

		Inventory arenaManagerInv = Bukkit.createInventory(null, 9, arenaName);
		ItemStack setSpawn, deleteSpawn, fillerItem;

		setSpawn = rApi.createCustomItem(Material.BED, 1, "&6&lSet Spawn",
				"&7Set spawn point at the location you are standing on.");
		deleteSpawn = rApi.createCustomItem(Material.IRON_PICKAXE, 1, "&6&lDelete Arena",
				"&7Delete the Arena you have selected");
		fillerItem = rApi.createGlassPane(Material.STAINED_GLASS_PANE, 1, (byte) 14, " ", " ");

		arenaManagerInv.setItem(0, setSpawn);
		arenaManagerInv.setItem(1, deleteSpawn);

		for (int count = 0; count < arenaManagerInv.getSize(); count++) {
			if (arenaManagerInv.getItem(count) == null) {
				arenaManagerInv.setItem(count, fillerItem);
			}
		}

		player.openInventory(arenaManagerInv);
	}

	public void openArenaSelectorGUI(Player player) {

		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);

		String worldName = player.getWorld().getName();

		if (arenaConfig == null || arenaConfig.getConfigurationSection(worldName) == null) {
			rApi.sendColouredMessage(player, "&c(!) There are no KitPVP Arenas. Please create one first!");
			return;
		}

		Inventory arenaSelectorInv = Bukkit.createInventory(null, 54, "Arena Selector");
		ItemStack arenaItem;

		int count = 0;
		for (String key : arenaConfig.getConfigurationSection(worldName).getKeys(false)) {
			arenaItem = rApi.createCustomItem(Material.COMPASS, 1, "&6&l" + key, "&7Click this to select &6&l" + key);
			arenaSelectorInv.setItem(count, arenaItem);
			count++;
		}

		player.openInventory(arenaSelectorInv);

	}
}