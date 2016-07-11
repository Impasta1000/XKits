package com.Impasta1000.XKits;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Impasta1000.XKits.gui.ArenaGUI;
import com.Impasta1000.XKits.gui.KitGUI;
import com.Impasta1000.XKits.utils.ArenaManager;
import com.Impasta1000.XKits.utils.PlayerManager;
import com.Impasta1000.XKits.utils.ResourcesAPI;

public class CommandsHandler implements CommandExecutor {
	
	private XKits plugin;
	private ArenaManager arenaManager;
	private PlayerManager playerManager;
	private ResourcesAPI rApi;
	private KitGUI kitGui;
	private ArenaGUI arenaGui;
	
	public CommandsHandler(XKits plugin) {
		this.plugin = plugin;
		arenaManager = new ArenaManager(plugin);
		rApi = new ResourcesAPI(plugin);
		kitGui = new KitGUI(plugin);
		playerManager = new PlayerManager(plugin);
		arenaGui = new ArenaGUI(plugin);
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
				arenaGui.openXKitsGUI(player);
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
						arenaManager.teleportToArenaSpawn(player, arenaName);
					}
				}
				
				if (args[0].equalsIgnoreCase("kits")) {
					kitGui.openKitGUI(player);
				}
				
				if (args[0].equalsIgnoreCase("leave")) {
					if (!plugin.getPlayersInArenaMap().containsKey(player.getName())) {
						rApi.sendColouredMessage(player, "&c(!) You are not in a KitPVP Arena.");
					} else {
						rApi.sendColouredMessage(player, "&6(!) You have &cleft &6KitPVP &9" + plugin.getPlayersInArenaMap().get(player.getName()) + "&6.");
						plugin.getPlayersInArenaMap().remove(player.getName());
						player.getInventory().clear();
						playerManager.loadInvFromMap(player, plugin.getPlayerInventories());
					}
				}
				
			}
		}
		return false;
	}

}
