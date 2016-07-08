package com.Impasta1000.XKits.utils;

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
	 * How Arenas will work Servers have to create their own arenas (currently)
	 * - feature can be implemented in the future The plug-in will handle Arena
	 * Lobbies (where players will spawn)
	 */

	private ConfigManager configManager;
	private ResourcesAPI rApi;
	private XKits plugin;
	private PlayerManager playerManager;

	public ArenaManager(XKits plugin) {
		this.plugin = plugin;
		configManager = new ConfigManager(plugin);
		rApi = new ResourcesAPI(plugin);
		playerManager = new PlayerManager(plugin);
	}
	
	public void createNormalArena(Player player, String arenaName) {
		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		
		arenaConfig.set(player.getWorld().getName() + "." + arenaName + ".Arena Type", "Normal KitPVP");
		saveCustomConfig(configManager.getConfigFile(ConfigFile.ARENAS), arenaConfig);
		
		rApi.sendColouredMessage(player, "&6&l(!) &6KitPVP Arena '&9" + arenaName + "&6' has been created. Please proceed to set the Spawn through the GUI.");
	}
	
	public void joinArena(Player player, String arenaName) {
		if (!checkArenaInFile(player, arenaName)) {
			return;
		}
		
		if (plugin.getPlayersInArenaMap().containsKey(player.getName())) {
			rApi.sendColouredMessage(player, "&c(!) You are already in a KitPVP Arena.");
			rApi.sendColouredMessage(player, "&6(!) Current Arena: &9" + plugin.getPlayersInArenaMap().get(player.getName()));
		} else {
			plugin.getPlayersInArenaMap().put(player.getName(), arenaName);
			rApi.sendColouredMessage(player, "&6(!) You have &ajoined &6KitPVP Arena &9" + arenaName + "&6.");
			teleportToArenaSpawn(player, arenaName);
			rApi.removeAllPotionEffects(player);
			playerManager.saveInvToHashMap(player, plugin.getPlayerInventories());
			player.getInventory().clear();
		}
	}

	public void setArenaSpawn(Player player, String arenaName) {
		Location playerLoc = player.getLocation();
		String worldName = player.getWorld().getName();

		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		arenaConfig.set(worldName + "." + arenaName + ".X", playerLoc.getX());
		arenaConfig.set(worldName + "." + arenaName + ".Y", playerLoc.getY());
		arenaConfig.set(worldName + "." + arenaName + ".Z", playerLoc.getZ());
		arenaConfig.set(worldName + "." + arenaName + ".Pitch", playerLoc.getPitch());
		arenaConfig.set(worldName + "." + arenaName + ".Yaw", playerLoc.getYaw());

		saveCustomConfig(configManager.getConfigFile(ConfigFile.ARENAS), arenaConfig);

		// TODO Add overwritten message
		rApi.sendColouredMessage(player, "&6&l(!) &6Spawn has been set for &9" + arenaName);
	}

	public void listArenas(Player player) {

		String worldName = player.getWorld().getName();

		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		ConfigurationSection section = arenaConfig.getConfigurationSection(worldName);

		if (section.getKeys(false).isEmpty()) {
			rApi.sendColouredMessage(player, "&c&l(!) &cThere are no KitPVP Arenas.");
			return;
		}

		int counter = 1;
		for (String spawns : section.getKeys(false)) {
			rApi.sendColouredMessage(player, "&6" + counter + ". " + spawns);
			counter++;
		}
	}

	public void deleteArena(Player player, String arenaName) {

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

	public void teleportToArenaSpawn(Player player, String arenaName) {
		String worldName = player.getWorld().getName();

		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);

		if (!checkArenaInFile(player, arenaName)) {
			rApi.sendColouredMessage(player, "&c(!) Unable to find Arena with the name of &e'" + arenaName + "'&c.");
			rApi.sendColouredMessage(player, "&c(!) Please input a valid Arena name.");
			return;
		}

		Location arenaLobby = new Location(player.getWorld(), arenaConfig.getDouble(worldName + "." + arenaName + ".X"),
				arenaConfig.getDouble(worldName + "." + arenaName + ".Y"),
				arenaConfig.getDouble(worldName + "." + arenaName + ".Z"),
				(float) arenaConfig.getDouble(worldName + "." + arenaName + ".Yaw"),
				(float) arenaConfig.getDouble(worldName + "." + arenaName + ".Pitch"));
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

	private void saveCustomConfig(File file, FileConfiguration config) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
