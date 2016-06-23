package com.Impasta1000.XKits.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class RangerKit implements Kit {

	private String kitName;
	private int kitID;

	private ResourcesAPI rApi;
	private XKits plugin;

	public RangerKit(XKits plugin) {
		this.rApi = new ResourcesAPI(plugin);
	}

	public RangerKit(String name, int id) {

		this.plugin = XKits.getInstance();
		this.rApi = new ResourcesAPI(plugin);

		this.kitName = name;
		this.kitID = id;
	}

	@Override
	public String getKitName() {
		return kitName;
	}

	@Override
	public int getKitID() {
		return kitID;
	}

	@Override
	public void setArmorContents(Player player) {
		player.getInventory().setArmorContents(null);
		ItemStack[] armorContents = new ItemStack[] {
				rApi.createCustomItem(Material.CHAINMAIL_BOOTS, 1, "&f[Ranger] Boots", "&eKit: " + getKitName()),
				rApi.createCustomItem(Material.CHAINMAIL_LEGGINGS, 1, "&f[Ranger] Leggings", "&eKit: " + getKitName()),
				rApi.createCustomItem(Material.CHAINMAIL_CHESTPLATE, 1, "&f[Ranger] Chestplate","&eKit: " + getKitName()),
				rApi.createCustomItem(Material.CHAINMAIL_HELMET, 1, "&f[Ranger] Helmet", "&eKit: " + getKitName()) };
		player.getInventory().setArmorContents(armorContents);
		player.updateInventory();
	}

	@Override
	public void setInventoryContents(Player player) {
		Inventory playerInv = player.getInventory();
		ItemStack[] inventoryContents = new ItemStack[] {
				rApi.createCustomItem(Material.WOOD_SWORD, 1, "&f[Ranger] Wooden Sword", "&eKit: " + getKitName()),
				rApi.createCustomItem(Material.BOW, 1, "&f[Ranger] Bow", "&eKit: " + getKitName()), 
				rApi.createItem(Material.ARROW, 16),
				rApi.createCustomItem(Material.COOKED_BEEF, 8, "&f[Ranger] Steak", "&eKit: " + getKitName()) };
		playerInv.setContents(inventoryContents);
	}
	
	@Override
	public void setPotionEffect(Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
	}
	
	@Override
	public void setKit(Player player) {
		player.getInventory().clear();
		rApi.removeAllPotionEffects(player);
		setInventoryContents(player);
		setArmorContents(player);
		setPotionEffect(player);
		rApi.sendColouredMessage(player, "&6(!) You have received the " + getKitName() + " &6kit.");
	}

}
