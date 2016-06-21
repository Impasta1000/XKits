package com.Impasta1000.XKits;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.Impasta1000.XKits.listeners.ArenaListener;
import com.Impasta1000.XKits.resources.ConfigManager;
import com.Impasta1000.XKits.resources.ConfigManager.ConfigFile;

public class XKits extends JavaPlugin {
	
	private ConfigManager configManager;
	private CommandsHandler commandsHandler;
	
	public void onEnable() {
		configManager = new ConfigManager(this);
		commandsHandler = new CommandsHandler(this);
		
		loadConfigs();
		registerCommands();
	}
	
	public void onDisable() { 
		ArenaListener.playersInArena.clear(); 
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
