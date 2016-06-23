package com.Impasta1000.XKits.config;

import java.io.File;
import java.io.InputStream;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Impasta1000.XKits.XKits;

public class Locale {

	private XKits plugin;

	private File configFile;
	private FileConfiguration config;

	public Locale(XKits plugin) {
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "locale.yml");

		this.reload();
		this.saveDefault();
	}

	public void reload() {
		config = YamlConfiguration.loadConfiguration(configFile);

		InputStream defaultConfigStream = plugin.getResource("lang.yml");
		if (defaultConfigStream != null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
			config.setDefaults(defaultConfig);
		}
	}

	public void saveDefault() {
		if (!configFile.exists()) {
			plugin.saveResource("lang.yml", false);
		}
	}

	public String getLocaleMessage(String name) {
		return getCaption(name, true);
	}

	public String getCaption(String name, boolean isColour) {
		String caption = config.getString(name);
		if (caption == null) {
			plugin.getLogger().warning("Missing message: " + name);
			caption = "&c[missing locale]";
		}

		if (isColour) {
			replacePlaceholders(caption);
			caption = ChatColor.translateAlternateColorCodes('&', caption);
		}
		replacePlaceholders(caption);
		return caption;
	}

	public String replacePlaceholders(String string) {
		string = string.replace("%prefix%", config.getString("Prefix"));
		return string;
	}
}
