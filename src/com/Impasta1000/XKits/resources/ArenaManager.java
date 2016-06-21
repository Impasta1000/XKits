package com.Impasta1000.XKits.resources;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.listeners.ArenaListener;
import com.Impasta1000.XKits.resources.ConfigManager.ConfigFile;

public class ArenaManager {
	
	/*
	 * How Arenas will work
	 * Servers have to create their own arenas (currently) - feature can be implemented in the future
	 * The plug-in will handle Arena Lobbies (where players will spawn)
	 */
	
	private ConfigManager configManager;
	private ResourcesAPI rApi;
	
	public ArenaManager(XKits plugin) {
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
		
		try {
			arenaConfig.save(configManager.getConfigFile(ConfigFile.ARENAS));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//TODO Add overwritten message
		rApi.sendColouredMessage(player, " &2(!) Spawn has been set for &9" + arenaName);
		rApi.sendColouredMessage(player, " &2(!) The co-ordinates are: &3" + x + ", " + y + ", " + z);
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
		player.sendMessage("You have been teleported to " + arenaName + ".");
	}
	
	public boolean checkArenaInFile(Player player, String arenaName) {
		
		String worldName = player.getWorld().getName();
		
		configManager.loadConfig(ConfigFile.ARENAS);
		FileConfiguration arenaConfig = configManager.getConfig(ConfigFile.ARENAS);
		
		if (!arenaConfig.contains(worldName + "." + arenaName)) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public boolean checkPlayerInArena(Player player) {
		if (ArenaListener.playersInArena.contains(player.getName())) {
			return true;
		} else {
			return false;
		}
	}

}
