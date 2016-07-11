package com.Impasta1000.XKits.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.config.ConfigManager.ConfigFile;

public class LocaleManager {

	private ConfigManager configManager;

	public LocaleManager(XKits plugin) {
		configManager = new ConfigManager(plugin);
		
	}

	public String getLocaleMessage(String configPath) {
		configManager.loadConfig(ConfigFile.LOCALE);
		FileConfiguration config = configManager.getConfig(ConfigFile.LOCALE);
		
		configPath = config.getString(configPath);
		
		return configPath;
	}

	public String replacePlaceholders(String string) {
		configManager.loadConfig(ConfigFile.LOCALE);
		FileConfiguration config = configManager.getConfig(ConfigFile.LOCALE);
		
		string = string.replace("%prefix%", config.getString("Configuration.Prefix"));
		string = string.replace("%prefix2%", config.getString("Configuration.Prefix2"));
		return string;
	}
}
