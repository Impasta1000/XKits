package com.Impasta1000.XKits.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class FighterKit extends Kit {

	@SuppressWarnings("unused")
	private XKits plugin;
	private ResourcesAPI rApi;

	public FighterKit(XKits plugin) {
		this.plugin = plugin;
		this.rApi = new ResourcesAPI(plugin);
	}

	private String kitName = "&cFighter";
	private int kitID = 1;
	private ItemStack[] armorContents;

	@Override
	public String getKitName() {
		return kitName;
	}

	@Override
	public int getKitID() {
		return kitID;
	}

	@Override
	public ItemStack[] getArmorContents() {
		armorContents = new ItemStack[] {
			rApi.createNewItem(Material.IRON_HELMET, 1, rApi.colourize("&c[Fighter] Helmet"), rApi.colourize("&eKit: " + kitName)),
			rApi.createNewItem(Material.IRON_CHESTPLATE, 1, rApi.colourize("&c[Fighter] Chestplate"), rApi.colourize("&eKit: " + kitName)),
			rApi.createNewItem(Material.IRON_LEGGINGS, 1, rApi.colourize("&c[Fighter] Leggings"), rApi.colourize("&eKit: " + kitName)),
			rApi.createNewItem(Material.IRON_BOOTS, 1, rApi.colourize("&c[Fighter] Boots"), rApi.colourize("&eKit: " + kitName)) };
		return armorContents;
	}

	@Override
	public void setArmorContents(Player player) {
		player.getInventory().setArmorContents(null);
		armorContents = new ItemStack[] {
				rApi.createNewItem(Material.IRON_BOOTS, 1, rApi.colourize("&c[Fighter] Boots"), rApi.colourize("&eKit: " + kitName)),
				rApi.createNewItem(Material.IRON_LEGGINGS, 1, rApi.colourize("&c[Fighter] Leggings"), rApi.colourize("&eKit: " + kitName)),
				rApi.createNewItem(Material.IRON_CHESTPLATE, 1, rApi.colourize("&c[Fighter] Chestplate"), rApi.colourize("&eKit: " + kitName)),
				rApi.createNewItem(Material.IRON_HELMET, 1, rApi.colourize("&c[Fighter] Helmet"), rApi.colourize("&eKit: " + kitName)) };
		player.getInventory().setArmorContents(armorContents);
		player.updateInventory();
		rApi.buffArmor(player, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		player.updateInventory();
	}
	

	@Override
	public void setInventoryContents(Player player) {
		Inventory playerInv = player.getInventory();
		// TODO Save player inventory in HashMap and load it back to player when
		// server disables
		playerInv.addItem(rApi.createNewItem(Material.STONE_SWORD, 1, rApi.colourize("&c[Fighter] Sword"), rApi.colourize("&eKit: " + kitName)));
		playerInv.addItem(rApi.createNewItem(Material.COOKED_BEEF, 12, rApi.colourize("&c[Fighter] Steak"), rApi.colourize("&eKit: " + kitName)));
	}

	@Override
	public void setKit(Player player) {
		player.getInventory().clear();
		setInventoryContents(player);
		setArmorContents(player);
		rApi.sendColouredMessage(player, "You have received the " + getKitName() + " kit.");
	}

}
