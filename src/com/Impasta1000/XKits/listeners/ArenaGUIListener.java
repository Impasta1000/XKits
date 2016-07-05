package com.Impasta1000.XKits.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.config.ConfigManager;
import com.Impasta1000.XKits.config.ConfigManager.ConfigFile;
import com.Impasta1000.XKits.gui.ArenaGUI;
import com.Impasta1000.XKits.utils.ArenaManager;
import com.Impasta1000.XKits.utils.ResourcesAPI;

public class ArenaGUIListener implements Listener {

	private ResourcesAPI rApi;
	private ArenaManager arenaManager;
	private XKits plugin;
	private ArenaGUI arenaGui;
	private ConfigManager configManager;

	public ArenaGUIListener(XKits plugin) {
		this.plugin = plugin;
		rApi = new ResourcesAPI(plugin);
		arenaManager = new ArenaManager(plugin);
		arenaGui = new ArenaGUI(plugin);
		configManager = new ConfigManager(plugin);
	}

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {

		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);

		Player player = (Player) event.getWhoClicked();
		Inventory clickedInv = event.getClickedInventory();
		ItemStack clickedItem = event.getCurrentItem();

		if (clickedItem == null || !clickedItem.hasItemMeta()) {
			return;
		}

		if (clickedInv.getName().equals("XKits")) {

			if (clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().hasLore()) {

				String name = clickedItem.getItemMeta().getDisplayName();

				if (name.equals(rApi.colourize("&6&lList Arenas")) && clickedItem.getType() == Material.BOOK_AND_QUILL) {

					if (!rApi.checkPerm(player, "XKits.Arena.List")) {
						rApi.sendColouredMessage(player, plugin.getMessages().get("NO-PERMISSION"));
						player.closeInventory();
						return;
					}

					event.setCancelled(true);
					player.closeInventory();
					arenaManager.listArenas(player);

				} else if (name.equals(rApi.colourize("&6&lManage Arena")) || clickedItem.getType() == Material.IRON_SWORD) {

					if (!rApi.checkPerm(player, "XKits.Arena.Manage")) {
						rApi.sendColouredMessage(player, plugin.getMessages().get("NO-PERMISSION"));
						player.closeInventory();
						return;
					}

					event.setCancelled(true);
					arenaGui.openArenaSelectorGUI(player);
				} else {
					event.setCancelled(true);
				}
			} 
		}
		
		if (clickedInv.getName().equals("Arena Selector")) {
			
			if (clickedItem.getType() != Material.COMPASS) {
				return;
			}
			arenaGui.openArenaManagerGUI(player, ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()));
			event.setCancelled(true);
		}

		for (String arenaName : arenaConfig.getConfigurationSection(player.getWorld().getName()).getKeys(false)) {

			if (clickedInv.getName().equals(arenaName)) {

				String name = clickedItem.getItemMeta().getDisplayName();

				if (name.equals(rApi.colourize("&6&lSet Spawn"))) {
					arenaManager.setArenaSpawn(player, arenaName);
					event.setCancelled(true);
					player.closeInventory();
				} else if (name.equals(rApi.colourize("&6&lDelete Arena"))) {
					arenaManager.deleteArena(player, arenaName);
					event.setCancelled(true);
					player.closeInventory();
				} else {
					event.setCancelled(true);
				}
			}
		}
	}
}
