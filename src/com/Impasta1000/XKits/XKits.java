package com.Impasta1000.XKits;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Impasta1000.XKits.config.ConfigManager;
import com.Impasta1000.XKits.config.ConfigManager.ConfigFile;
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

	public HashMap<String, String> getPlayersInArenaMap() {
		return playersInArena;
	}

	private ConfigManager configManager;
	private CommandsHandler commandsHandler;

	public void onEnable() {
		configManager = new ConfigManager(this);
		commandsHandler = new CommandsHandler(this);

		plugin = this;

		loadConfigs();
		registerCommands();
		registerEvents();
	}

	public void onDisable() {
		playersInArena.clear();
	}

	private void loadConfigs() {
		configManager.loadConfigs();
		
		FileConfiguration config = configManager.getConfig(ConfigFile.CONFIG);
		config.options().copyDefaults(true);
	}

	private void registerCommands() {
		getCommand("xkits").setExecutor(commandsHandler);
	}

	private void registerEvents() {
		PluginManager PM = Bukkit.getPluginManager();
		PM.registerEvents(new KitsGUIListener(this), this);
	}

}
