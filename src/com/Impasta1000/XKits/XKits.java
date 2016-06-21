package com.Impasta1000.XKits;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.Impasta1000.XKits.resources.ConfigManager;
import com.Impasta1000.XKits.resources.ConfigManager.ConfigFile;

public class XKits extends JavaPlugin {
	
	/*
	 * HashMap to store the players in the arenas
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
		
		loadConfigs();
		registerCommands();
	}
	
	public void onDisable() { 
		playersInArena.clear();
	}
	
	private void loadConfigs() {
		configManager.loadConfigs();
		
		FileConfiguration config = configManager.getConfig(ConfigFile.CONFIG);
		config.addDefault("prefix", "<&8&lXKits>");
		config.addDefault("messages.no-permission", "&cYou have insufficient permission!");
		config.options().copyDefaults(true);
	}
	
	private void registerCommands() {
		getCommand("xkits").setExecutor(commandsHandler);
	}

}
