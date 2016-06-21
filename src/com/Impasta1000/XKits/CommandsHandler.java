package com.Impasta1000.XKits;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Impasta1000.XKits.kits.FighterKit;
import com.Impasta1000.XKits.listeners.ArenaListener;
import com.Impasta1000.XKits.resources.ArenaManager;
import com.Impasta1000.XKits.resources.ResourcesAPI;

public class CommandsHandler implements CommandExecutor {
	
	private ArenaManager arenaManager;
	private ResourcesAPI rApi;
	private FighterKit fighterKit;
	
	public CommandsHandler(XKits plugin) {
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
					checkPerm(player, "XKits.Arena.setLobby");
					
					String arenaName = args[1];
					arenaManager.setArenaLobby(player, arenaName);
				}
				
				if (args[0].equalsIgnoreCase("join")) {
					String arenaName = args[1];
					if (ArenaListener.playersInArena.contains(player.getName())) {
						player.sendMessage("You are already in the KitPVP Arena!");
						return true;
					} else {
						arenaManager.teleportToArenaLobby(player, arenaName);
						ArenaListener.playersInArena.add(player.getName());
						player.sendMessage("You have joined the KitPVP Arena!");
					}
				}
				
				if (args[0].equalsIgnoreCase("kits")) {
					if (args[1].equalsIgnoreCase("fighter")) {
						if (!ArenaListener.playersInArena.contains(player.getName())) {
							rApi.sendColouredMessage(player, "You need to join the Arena.");
							return true;
						}
						fighterKit.setKit(player);
					}
				}
			}
		}
		return false;
	}
	
	private boolean checkPerm(Player player, String perm) {
		if (!player.hasPermission(perm)) {
			return true;
		} else {
			return false;
		}
	}

}
