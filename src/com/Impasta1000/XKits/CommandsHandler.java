package com.Impasta1000.XKits;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Impasta1000.XKits.kits.KitGUI;
import com.Impasta1000.XKits.resources.ArenaManager;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class CommandsHandler implements CommandExecutor {
	
	private XKits plugin;
	private ArenaManager arenaManager;
	private ResourcesAPI rApi;
	private KitGUI kitGui;
	
	public CommandsHandler(XKits plugin) {
		this.plugin = plugin;
		this.arenaManager = new ArenaManager(plugin);
		this.rApi = new ResourcesAPI(plugin);
		this.kitGui = new KitGUI(plugin);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to do this command!");
			return true;
		}
		
		String cmdName = cmd.getName();
		Player player = (Player) sender;
		
		if (cmdName.equalsIgnoreCase("XKits")) {
			
			if (args.length == 0) {
				player.sendMessage("Insufficient arguments! Please do /xkits help for more information");
			}
			
			if (args.length == 1) {
				
				if (args[0].equalsIgnoreCase("help")) {
					rApi.printHelpMenu(player);
				}
				
				if (args[0].equalsIgnoreCase("spawn")) {
					if (!plugin.getPlayersInArenaMap().containsKey(player.getName())) {
						rApi.sendColouredMessage(player, "&c(!) You are not in a KitPVP Arena.");
					} else {
						String arenaName = plugin.getPlayersInArenaMap().get(player.getName());
						arenaManager.teleportToArenaLobby(player, arenaName);
					}
				}
				
				if (args[0].equalsIgnoreCase("kits")) {
					kitGui.openKitGUI(player);
				}
				
				if (args[0].equalsIgnoreCase("leave")) {
					if (!plugin.getPlayersInArenaMap().containsKey(player.getName())) {
						rApi.sendColouredMessage(player, "&c(!) You are not in a KitPVP Arena.");
					} else {
						rApi.sendColouredMessage(player, "&6(!) You have &cleft &6KitPVP Arena &9" + plugin.getPlayersInArenaMap().get(player.getName()) + "&6.");
						plugin.getPlayersInArenaMap().remove(player.getName());
					}
				}
				
				if (args[0].equalsIgnoreCase("arenas")) {
					if (!checkPerm(player, "XKits.Arena.List")) {
						rApi.sendColouredMessage(player, "&c(!) You have insufficient permission.");
						return true;
					}
					arenaManager.listArenaLobbies(player);
				}
			}
			
			if (args.length == 2) {
				
				/*
				 * In future implementations, players can use /xkits "arenaName" edit
				 * Then, they will be able to do cmds like /setSpawn
				 */
				if (args[0].equalsIgnoreCase("setLobby")) {
					if (!checkPerm(player, "XKits.Arena.SetLobby")) {
						rApi.sendColouredMessage(player, "&c(!) You have insufficient permission.");
						return true;
					}
					
					String arenaName = args[1];
					arenaManager.setArenaLobby(player, arenaName);
				}
				
				if (args[0].equalsIgnoreCase("join")) {
					String arenaName = args[1];
					if (!arenaManager.checkArenaInFile(player, arenaName)) {
						
						return true;
					}
					if (plugin.getPlayersInArenaMap().containsKey(player.getName())) {
						rApi.sendColouredMessage(player, "&c(!) You are already in a KitPVP Arena.");
						rApi.sendColouredMessage(player, "&6(!) Current Arena: &9" + plugin.getPlayersInArenaMap().get(player.getName()));
					} else {
						plugin.getPlayersInArenaMap().put(player.getName(), arenaName);
						rApi.sendColouredMessage(player, "&6(!) You have &ajoined &6KitPVP Arena &9" + arenaName + "&6.");
						arenaManager.teleportToArenaLobby(player, arenaName);
					}
				}
				
				if (args[0].equalsIgnoreCase("deletelobby")) {
					
					if (!checkPerm(player, "XKits.Arena.DeleteLobby")) {
						rApi.sendColouredMessage(player, "&c(!) You have insufficient permission.");
						return true;
					}
					
					String arenaName = args[1];
					if (!arenaManager.checkArenaInFile(player, arenaName)) {
						return true;
					}
					arenaManager.deleteArenaLobby(player, arenaName);
				}
			}
		}
		return false;
	}
	
	private boolean checkPerm(Player player, String perm) {
		if (!player.hasPermission(perm)) {
			return false;
		} else {
			return true;
		}
	}

}
