package com.Impasta1000.XKits.listeners;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.gui.ArenaGUI;
import com.Impasta1000.XKits.permissions.Messages;
import com.Impasta1000.XKits.permissions.Permissions;
import com.Impasta1000.XKits.utils.ArenaManager;
import com.Impasta1000.XKits.utils.PlayerManager;
import com.Impasta1000.XKits.utils.ResourcesAPI;

public class ArenaGUIListener implements Listener {

	private ResourcesAPI rApi;
	private ArenaManager arenaManager;
	private XKits plugin;
	private ArenaGUI arenaGui;
	private Permissions Permissions;
	private PlayerManager playerManager;
	private Messages Messages;

	public ArenaGUIListener(XKits plugin) {
		this.plugin = plugin;
		rApi = new ResourcesAPI(plugin);
		arenaManager = new ArenaManager(plugin);
		arenaGui = new ArenaGUI(plugin);
		Permissions = new Permissions();
		playerManager = new PlayerManager(plugin);
		Messages = new Messages(plugin);
	}

	private HashSet<String> creatingArena = new HashSet<String>();
	private HashSet<String> joiningArena = new HashSet<String>();
	private HashSet<String> managingArena = new HashSet<String>();

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String eventMessage = event.getMessage();

		if (!creatingArena.contains(player.getName())) {
			return;
		} else {
			event.setCancelled(true);
			creatingArena.remove(player.getName());
			arenaManager.createNormalArena(player, eventMessage);
		}
	}

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();
		Inventory clickedInv = event.getClickedInventory();
		ItemStack clickedItem = event.getCurrentItem();

		if (clickedItem == null || !clickedItem.hasItemMeta()) {
			return;
		}

		if (clickedInv.getName().equals("XKits")) {

			if (clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().hasLore()) {

				String name = clickedItem.getItemMeta().getDisplayName();

				if (name.equals(rApi.colourize("&6&lList Arenas"))
						&& clickedItem.getType() == Material.BOOK_AND_QUILL) {

					if (!rApi.checkPerm(player, Permissions.ARENA_LIST)) {
						rApi.sendColouredMessage(player, Messages.NOPERMISSION);
						event.setCancelled(true);
						player.closeInventory();
						return;
					}

					event.setCancelled(true);
					player.closeInventory();
					arenaManager.listArenas(player);

				}
				if (name.equals(rApi.colourize("&6&lManage Arena")) || clickedItem.getType() == Material.IRON_SWORD) {

					if (!rApi.checkPerm(player, Permissions.ARENA_MANAGE)) {
						rApi.sendColouredMessage(player, Messages.NOPERMISSION);
						event.setCancelled(true);
						player.closeInventory();
						return;
					}

					event.setCancelled(true);
					managingArena.add(player.getName());
					arenaGui.openArenaSelectorGUI(player);

				}
				if (name.equals(rApi.colourize("&6&lCreate a new Arena")) && clickedItem.getType() == Material.FENCE_GATE) {

					if (!rApi.checkPerm(player, Permissions.ARENA_CREATE)) {
						rApi.sendColouredMessage(player, Messages.NOPERMISSION);
						event.setCancelled(true);
						player.closeInventory();
						return;
					}

					if (creatingArena.contains(player.getName())) {
						rApi.sendColouredMessage(player, "&c&l(!) &cYou are already in progress of creating an Arena.");
						return;
					}

					creatingArena.add(player.getName());
					event.setCancelled(true);
					player.closeInventory();
					rApi.sendColouredMessage(player, "&6&l(!) &6Please enter the name of the Arena. You have &c30 seconds &6to do so.");

					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							if (creatingArena.contains(player.getName())) {
								creatingArena.remove(player.getName());
								rApi.sendColouredMessage(player, "&c&l(!) &cYou did not enter the name in time. Please restart if you want to create a new KitPVP Arena.");
							}
						}
					}, 600);

				}
				if (name.equals(rApi.colourize("&6&lJoin an Arena")) && clickedItem.getType() == Material.WATCH) {
					arenaGui.openArenaSelectorGUI(player);
					if (arenaManager.checkPlayerInArena(player)) {
						player.closeInventory();
						event.setCancelled(true);
						rApi.sendColouredMessage(player, "&c(!) You are already in a KitPVP Arena.");
						rApi.sendColouredMessage(player, "&6(!) Current Arena: &9" + plugin.getPlayersInArenaMap().get(player.getName()));
						return;
					}
					joiningArena.add(player.getName());
					event.setCancelled(true);

				} if (name.equals(rApi.colourize("&6&lLeave Arena")) && clickedItem.getType() == Material.WOOD_DOOR) {
					if (!plugin.getPlayersInArenaMap().containsKey(player.getName())) {
						rApi.sendColouredMessage(player, "&c(!) You are not in a KitPVP Arena.");
						event.setCancelled(true);
						player.closeInventory();
					} else {
						rApi.sendColouredMessage(player, "&6(!) You have &cleft &6KitPVP &9" + plugin.getPlayersInArenaMap().get(player.getName()) + "&6.");
						plugin.getPlayersInArenaMap().remove(player.getName());
						player.getInventory().clear();
						playerManager.loadInvFromMap(player, plugin.getPlayerInventories());
						event.setCancelled(true);
						player.closeInventory();
					}	
				} else {
					event.setCancelled(true);
				}
			}
		}

		if (clickedInv.getName().equals("Arena Selector")) {

			if (clickedItem.getType() == Material.COMPASS) {

				if (joiningArena.contains(player.getName())) {
					player.sendMessage("DEBUG 1");
					arenaManager.joinArena(player, ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()));
					event.setCancelled(true);
					joiningArena.remove(player.getName());

				}

				else if (managingArena.contains(player.getName())) {

					arenaGui.openArenaManagerGUI(player,
							ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()));
					event.setCancelled(true);
					managingArena.remove(player.getName());
				}
			}
		}

		if (clickedInv.getName().contains("Arena Manager: ")) {
			String name = clickedItem.getItemMeta().getDisplayName();
			String arenaName = clickedInv.getName().replace("Arena Manager: ", "");

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
