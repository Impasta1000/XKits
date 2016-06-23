package com.Impasta1000.XKits.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.kits.FighterKit;
import com.Impasta1000.XKits.kits.RangerKit;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class KitsGUIListener implements Listener {

	private ResourcesAPI rApi;
	private FighterKit fighterKit;
	private RangerKit rangerKit;
	private XKits plugin;

	public KitsGUIListener(XKits plugin) {
		this.plugin = plugin;
		this.rApi = new ResourcesAPI(plugin);
		this.fighterKit = new FighterKit(plugin);
		this.rangerKit = new RangerKit(plugin);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory clickedInv = event.getClickedInventory();
		ItemStack clickedItem = event.getCurrentItem();

		if (!clickedInv.getName().equals(rApi.colourize("&eKits")) || clickedItem == null) {
			return;
		}

		if (clickedItem.hasItemMeta()) {

			if (clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().hasLore()) {

				String name = clickedItem.getItemMeta().getDisplayName();

				if (name.equals(rApi.colourize("&cFighter")) && clickedItem.getType() == Material.SHIELD) {

					event.setCancelled(true);
					player.closeInventory();

					if (!plugin.getPlayersInArenaMap().containsKey(player.getName())) {
						rApi.sendColouredMessage(player, "&c(!) You need to join the KitPVP Arena first!");
						return;
					} else {
						fighterKit.setKit(player);
					}
				}

				if (name.equals(rApi.colourize("&fRanger")) && clickedItem.getType() == Material.BOW) {

					event.setCancelled(true);
					player.closeInventory();

					if (!plugin.getPlayersInArenaMap().containsKey(player.getName())) {
						rApi.sendColouredMessage(player, "&c(!) You need to join the KitPVP Arena first!");
						return;
					} else {
						rangerKit.setKit(player);
					}
				}
			}
		}
	}

}
