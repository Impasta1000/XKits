package com.Impasta1000.XKits.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.resources.ArenaManager;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class ArenaGUIListener implements Listener {

	private ResourcesAPI rApi;
	private ArenaManager arenaManager;
	private XKits plugin;

	public ArenaGUIListener(XKits plugin) {
		this.plugin = plugin;
		rApi = new ResourcesAPI(plugin);
		arenaManager = new ArenaManager(plugin);
	}

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory clickedInv = event.getClickedInventory();
		ItemStack clickedItem = event.getCurrentItem();

		if (clickedItem == null) {
			return;
		}

		if (clickedInv.getName().equals("XKits Arena")) {
			
			event.setCancelled(true);

			if (clickedItem.hasItemMeta()) {

				if (clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().hasLore()) {

					String name = clickedItem.getItemMeta().getDisplayName();
					
					if (name.equals(rApi.colourize("&6&lList Arenas")) && clickedItem.getType() == Material.BOOK_AND_QUILL) {
						
						if (!rApi.checkPerm(player, "XKits.Arena.List")) {
							rApi.sendColouredMessage(player, plugin.getMessages().get("NO-PERMISSION"));
							return;
						}
						event.setCancelled(true);
						player.closeInventory();
						arenaManager.listArenas(player);
					}
				}
			}
		}
	}
}
