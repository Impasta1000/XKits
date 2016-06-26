package com.Impasta1000.XKits.resources;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.config.ConfigManager;
import com.Impasta1000.XKits.config.ConfigManager.ConfigFile;

public class ArenaManager {
	
	/*
	 * How Arenas will work
	 * Servers have to create their own arenas (currently) - feature can be implemented in the future
	 * The plug-in will handle Arena Lobbies (where players will spawn)
	 */
	
	private ConfigManager configManager;
	private ResourcesAPI rApi;
	private XKits plugin;
	
	public ArenaManager(XKits plugin) {
		this.plugin = plugin;
		this.configManager = new ConfigManager(plugin);
		this.rApi = new ResourcesAPI(plugin);
	}
	
	public void setArenaLobby(Player player, String arenaName) {
		Location playerLoc = player.getLocation();
		String worldName = player.getWorld().getName();
		double x = playerLoc.getX();
		double y = playerLoc.getY();
		double z = playerLoc.getZ();
		float pitch = playerLoc.getPitch();
		float yaw = playerLoc.getYaw();
		
		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		arenaConfig.set(worldName + "." + arenaName + ".X", x);
		arenaConfig.set(worldName + "." + arenaName + ".Y", y);
		arenaConfig.set(worldName + "." + arenaName + ".Z", z);
		arenaConfig.set(worldName + "." + arenaName + ".Pitch", pitch);
		arenaConfig.set(worldName + "." + arenaName + ".Yaw", yaw);
		
		saveCustomConfig(configManager.getConfigFile(ConfigFile.ARENAS), arenaConfig);
		
		//TODO Add overwritten message
		rApi.sendColouredMessage(player, " &6(!) Spawn has been set for &9" + arenaName);
		rApi.sendColouredMessage(player, " &6(!) The co-ordinates are: &c" + x + ", " + y + ", " + z);
	}
	
	public void listArenaLobbies(Player player) {
		
		String worldName = player.getWorld().getName();
		
		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		ConfigurationSection section = arenaConfig.getConfigurationSection(worldName);
		
		if (section == null) {
			rApi.sendColouredMessage(player, "&c(!) There are no KitPVP Arenas.");
			return;
		}
		
		if (section.getKeys(false).isEmpty()) {
			rApi.sendColouredMessage(player, "&c(!) There are no KitPVP Arenas.");
			return;
		}
		
		int counter = 1;
		for (String spawns : section.getKeys(false)) {
			rApi.sendColouredMessage(player, "&6" + counter + ". " + spawns);
			counter++;
		}
	}
	
	public void deleteArenaLobby(Player player, String arenaName) {
		
		//TODO Add permission check
		String worldName = player.getWorld().getName();
		
		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		
		if (!checkArenaInFile(player, arenaName)) {
			rApi.sendColouredMessage(player, "&c(!) Unable to find  KitPVP Arena &e" + arenaName + "&c.");
		} else {
			arenaConfig.set(worldName + "." + arenaName, null);
			rApi.sendColouredMessage(player, "&6(!) Successfully &cdeleted &6KitPVP Arena &9" + arenaName + "&6.");
		}
		
		saveCustomConfig(configManager.getConfigFile(ConfigFile.ARENAS), arenaConfig);
		
	}
 	
	public void teleportToArenaLobby(Player player, String arenaName) {
		String worldName = player.getWorld().getName();
		
		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		
		if (!checkArenaInFile(player, arenaName)) {
			rApi.sendColouredMessage(player, "&c(!) Unable to find Arena with the name of &e'" + arenaName + "'&c.");
			rApi.sendColouredMessage(player, "&c(!) Please input a valid Arena name.");
			return;
		}
		
		double x = arenaConfig.getDouble(worldName + "." + arenaName + ".X");
		double y = arenaConfig.getDouble(worldName + "." + arenaName + ".Y");
		double z = arenaConfig.getDouble(worldName + "." + arenaName + ".Z");
		float pitch = (float) arenaConfig.getDouble(worldName + "." + arenaName + ".Pitch");
		float yaw = (float) arenaConfig.getDouble(worldName + "." + arenaName + ".Yaw");
		
		Location arenaLobby = new Location(player.getWorld(), x, y, z, yaw, pitch);
		player.teleport(arenaLobby);
		rApi.sendColouredMessage(player, "&6(!) You have been &asuccessfully &6teleported to &9" + arenaName + " &6spawn.");
	}
	
	public boolean checkArenaInFile(Player player, String arenaName) {
		
		String worldName = player.getWorld().getName();
		
		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		
		if (!arenaConfig.contains(worldName + "." + arenaName)) {
			rApi.sendColouredMessage(player, "&c(!) Unable to find Arena with the name of &e" + arenaName + "&c.");
			rApi.sendColouredMessage(player, "&c(!) Please input a valid Arena name.");
			return false;
		} else {
			return true;
		}
		
	}
	
	public boolean checkPlayerInArena(Player player) {
		if (plugin.getPlayersInArenaMap().containsKey(player.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	public void saveCustomConfig(File file, FileConfiguration config) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
