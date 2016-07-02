package com.Impasta1000.XKits.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class FighterKit implements Kit {

	private ResourcesAPI rApi;
	private XKits plugin;
	
	public FighterKit(XKits plugin) {
		this.rApi = new ResourcesAPI(plugin);
	}
	
	public FighterKit(String name, int id) {

		this.plugin = XKits.getInstance();
		this.rApi = new ResourcesAPI(plugin);
		
		this.kitName = name;
		this.kitID = id;
	}

	private String kitName;
	private int kitID;
	
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
				rApi.createCustomItem(Material.IRON_BOOTS, 1, "&cIron Boots", "&eKit: " + getKitName()),
				rApi.createCustomItem(Material.IRON_LEGGINGS, 1, "&cIron Leggings", "&eKit: " +  getKitName()),
				rApi.createCustomItem(Material.IRON_CHESTPLATE, 1, "&cIron Chestplate", "&eKit: " + getKitName()),
				rApi.createCustomItem(Material.IRON_HELMET, 1, "&cIron Helmet", "&eKit: " + getKitName()) };
		player.getInventory().setArmorContents(armorContents);
		player.updateInventory();
		rApi.buffArmor(player, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
	}

	@Override
	public void setInventoryContents(Player player) {
		Inventory playerInv = player.getInventory();
		ItemStack[] inventoryContents = new ItemStack[] { 
				rApi.createCustomItem(Material.STONE_SWORD, 1, "&cStone Sword","&eKit: " + getKitName()),
				rApi.createCustomItem(Material.COOKED_BEEF, 12, "&cSteak", "&eKit: " + getKitName()) };
		playerInv.setContents(inventoryContents);
	}
	
	@Override
	public void setPotionEffect(Player player) {
		return;
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