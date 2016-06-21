package com.Impasta1000.XKits;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Impasta1000.XKits.kits.FighterKit;
import com.Impasta1000.XKits.resources.ArenaManager;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class CommandsHandler implements CommandExecutor {
	
	private XKits plugin;
	private ArenaManager arenaManager;
	private ResourcesAPI rApi;
	private FighterKit fighterKit;
	
	public CommandsHandler(XKits plugin) {
		this.plugin = plugin;
		this.arenaManager = new ArenaManager(plugin);
		this.rApi = new ResourcesAPI(plugin);
		this.fighterKit = new FighterKit(plugin);
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
			}
			
			if (args.length == 2) {
				
				/*
				 * In future implementations, players can use /xkits "arenaName" edit
				 * Then, they will be able to do cmds like /setSpawn
				 */
				if (args[0].equalsIgnoreCase("setLobby")) {
					if (!checkPerm(player, "XKits.Arena.setLobby")) {
						rApi.sendColouredMessage(player, "You have insufficient permission.");
						return true;
					}
					
					String arenaName = args[1];
					arenaManager.setArenaLobby(player, arenaName);
				}
				
				if (args[0].equalsIgnoreCase("join")) {
					String arenaName = args[1];
					if (!arenaManager.checkArenaInFile(player, arenaName)) {
						rApi.sendColouredMessage(player, "&c(!) Unable to find Arena with the name of &e" + arenaName + "&c.");
						rApi.sendColouredMessage(player, "&c(!) Please input a valid Arena name.");
						return true;
					}
					if (plugin.getPlayersInArenaMap().containsKey(player.getName())) {
						rApi.sendColouredMessage(player, "&c(!) You are already in a KitPVP Arena.");
						rApi.sendColouredMessage(player, "&6(!) Current Arena: &9" + plugin.getPlayersInArenaMap().get(player.getName()));
					} else {
						plugin.getPlayersInArenaMap().put(player.getName(), arenaName);
						rApi.sendColouredMessage(player, "&a(!) You have joined KitPVP Arena &9" + arenaName + "&a.");
						arenaManager.teleportToArenaLobby(player, arenaName);
					}
					
				}
				
				if (args[0].equalsIgnoreCase("kits")) {
					if (args[1].equalsIgnoreCase("fighter")) {
						if (!plugin.getPlayersInArenaMap().containsKey(player.getName())) {
							rApi.sendColouredMessage(player, "&c(!) You need to join the KitPVP Arena first!");
							return true;
						} else {
							fighterKit.setKit(player);
						}
					}
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
