package com.Impasta1000.XKits.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import com.Impasta1000.XKits.XKits;

public class ResourcesAPI {

	private XKits plugin;

	public ResourcesAPI(XKits plugin) {
		this.plugin = plugin;
	}
	
	public void saveCustomConfig(File file, FileConfiguration config) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Help menu
	public void printHelpMenu(Player player) {
		List<String> helpMenu = new ArrayList<String>();
		helpMenu.add("&8*---------------------------------------------------*");
		helpMenu.add("  &3XKits &f(v" + plugin.getDescription().getVersion() + ")");
		helpMenu.add(" ");
		helpMenu.add("  &2[] &f= optional arguments");
		helpMenu.add("  &6<> &f= required arguments");
		helpMenu.add(" ");
		helpMenu.add("  &9&nPlayer Commands");
		helpMenu.add("  &f/xkits &e- Opens XKits GUI.");
		helpMenu.add("  &f/xkits help &e- Shows help.");
		helpMenu.add("  &f/xkits kits &e- Open Kits GUI.");
		helpMenu.add("  &f/xkits spawn &e- Teleport to Arena spawn.");
		helpMenu.add(" ");
		helpMenu.add("&8*---------------------------------------------------*");
		for (String msgToSend : helpMenu) {
			sendColouredMessage(player, msgToSend);
		}
	}
	
	public boolean checkPerm(Player player, String perm) {
		if (!player.hasPermission(perm)) {
			return false;
		} else {
			return true;
		}
	}

	public ItemStack createCustomItem(Material material, int amount, String colouredDisplayName,
			String... colouredLore) {

		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		ItemMeta itemMeta = item.getItemMeta();

		itemMeta.setDisplayName(colourize(colouredDisplayName));
		List<String> loreList = new ArrayList<String>();
		for (String x : colouredLore) {
			loreList.add(colourize(x));
		}
		itemMeta.setLore(loreList);
		item.setItemMeta(itemMeta);

		return item;
	}
	
	public ItemStack createGlassPane(Material material, int amount, short data, String colouredDisplayName,
			String... colouredLore) {

		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		item.setDurability(data);
		ItemMeta itemMeta = item.getItemMeta();

		itemMeta.setDisplayName(colourize(colouredDisplayName));
		List<String> loreList = new ArrayList<String>();
		for (String x : colouredLore) {
			loreList.add(colourize(x));
		}
		itemMeta.setLore(loreList);
		item.setItemMeta(itemMeta);

		return item;
	}

	public ItemStack createItem(Material material, int amount) {
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		return item;
	}

	public ItemStack addItemMeta(ItemStack stack, String displayName, String... lore) {
		ItemMeta itemMeta = stack.getItemMeta();
		itemMeta.setDisplayName(colourize(displayName));
		List<String> loreList = new ArrayList<String>();
		for (String x : lore) {
			loreList.add(colourize(x));
		}
		itemMeta.setLore(loreList);
		stack.setItemMeta(itemMeta);
		return stack;
	}

	public void sendColouredMessage(Player player, String msg) {
		if (msg == null) {
			player.sendMessage("");
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	public String colourize(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public void clearInventory(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		sendColouredMessage(player, "Your inventory has been cleared.");
	}

	public void buffArmor(Player player, Enchantment ench, int level) {
		ItemStack playerHelmet = player.getInventory().getHelmet();
		playerHelmet.addEnchantment(ench, level);
		player.getInventory().setHelmet(playerHelmet);

		ItemStack playerChestplate = player.getInventory().getChestplate();
		playerChestplate.addEnchantment(ench, level);
		player.getInventory().setChestplate(playerChestplate);

		ItemStack playerLeggings = player.getInventory().getLeggings();
		playerLeggings.addEnchantment(ench, level);
		player.getInventory().setLeggings(playerLeggings);

		ItemStack playerBoots = player.getInventory().getBoots();
		playerBoots.addEnchantment(ench, level);
		player.getInventory().setBoots(playerBoots);

		player.updateInventory();
	}

	public void removeAllPotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

}
