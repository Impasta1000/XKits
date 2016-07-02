package com.Impasta1000.XKits;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Impasta1000.XKits.config.ConfigManager;
import com.Impasta1000.XKits.config.ConfigManager.ConfigFile;
import com.Impasta1000.XKits.config.LocaleManager;
import com.Impasta1000.XKits.listeners.ArenaGUIListener;
import com.Impasta1000.XKits.listeners.KitsGUIListener;

public class XKits extends JavaPlugin {

	private static XKits plugin;

	public static XKits getInstance() {
		return plugin;
	}

	/*
	 * HashMap to store the players in the arenas
	 * 
	 * @param playerName, arenaName
	 */

	private HashMap<String, String> playersInArena = new HashMap<String, String>();
	
	/*
	 * HashMap to store player inventories when they join KitPVP Arena
	 * 
	 * @param playerUUID, itemStack[]
	 */
	private HashMap<String, ItemStack[]> playerInventories = new HashMap<String, ItemStack[]>();

	public HashMap<String, String> getPlayersInArenaMap() {
		return playersInArena;
	}
	
	public HashMap<String, ItemStack[]> getPlayerInventories() {
		return playerInventories;
	}
	
	private HashMap<String, String> messages = new HashMap<String, String>();
	
	public HashMap<String, String> getMessages() {
		return messages;
	}
	
	private void loadLocalization() {
		messages.put("NO-PERMISSION", localeManager.getLocaleMessage("Messages.no-permission"));
		messages.put("INSUFFICIENT-ARGUMENTS", localeManager.getLocaleMessage("Messages.insufficient-arguments"));
		
		for (String msg : messages.values()) {
			localeManager.replacePlaceholders(msg);
		}
	}

	private ConfigManager configManager;
	private CommandsHandler commandsHandler;
	private LocaleManager localeManager;

	public void onEnable() {
		configManager = new ConfigManager(this);
		commandsHandler = new CommandsHandler(this);
		localeManager = new LocaleManager(this);

		plugin = this;

		loadConfigs();
		registerCommands();
		registerEvents();
		
		loadLocalization();
	}

	public void onDisable() {
		playersInArena.clear();
	}

	private void loadConfigs() {
		configManager.loadConfigs();
		configManager.getConfig(ConfigFile.LOCALE).options().copyDefaults(true);
	}

	private void registerCommands() {
		getCommand("xkits").setExecutor(commandsHandler);
	}

	private void registerEvents() {
		PluginManager PM = Bukkit.getPluginManager();
		PM.registerEvents(new KitsGUIListener(this), this);
		PM.registerEvents(new ArenaGUIListener(this), this);
	}

}
