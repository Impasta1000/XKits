package com.Impasta1000.XKits.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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
	
	private List<String> creatingArenaName = new ArrayList<String>();
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String eventMessage = event.getMessage();
		
		if (!creatingArenaName.contains(player.getName())) {
			return;
		} else {
			event.setCancelled(true);
			creatingArenaName.remove(player.getName());
			arenaManager.createNormalArena(player, eventMessage);
		}
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
						event.setCancelled(true);
						player.closeInventory();
						return;
					}

					event.setCancelled(true);
					player.closeInventory();
					arenaManager.listArenas(player);

				} else if (name.equals(rApi.colourize("&6&lManage Arena")) || clickedItem.getType() == Material.IRON_SWORD) {

					if (!rApi.checkPerm(player, "XKits.Arena.Manage")) {
						rApi.sendColouredMessage(player, plugin.getMessages().get("NO-PERMISSION"));
						event.setCancelled(true);
						player.closeInventory();
						return;
					}

					event.setCancelled(true);
					arenaGui.openArenaSelectorGUI(player);
					
				} else if (name.equals(rApi.colourize("&6&lCreate a new Arena")) && clickedItem.getType() == Material.FENCE_GATE) {
					
					if (!rApi.checkPerm(player, "XKits.Arena.Manage")) {
						rApi.sendColouredMessage(player, plugin.getMessages().get("NO-PERMISSION"));
						event.setCancelled(true);
						player.closeInventory();
						return;
					}
					
					if (creatingArenaName.contains(player.getName())) {
						rApi.sendColouredMessage(player, "&c&l(!) &cYou are already in progress of creating an Arena.");
						return;
					}
					
					creatingArenaName.add(player.getName());
					event.setCancelled(true);
					player.closeInventory();
					rApi.sendColouredMessage(player, "&6&l(!) &6Please enter the name of the Arena. You have &c30 seconds &6to do so.");
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							if (creatingArenaName.contains(player.getName())) {
								creatingArenaName.remove(player.getName());
								rApi.sendColouredMessage(player, "&c&l(!) &cYou did not enter the name in time. Please restart if you want to create a new KitPVP Arena.");
							}
						}
					}, 600);
					
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
