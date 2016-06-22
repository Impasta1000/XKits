package com.Impasta1000.XKits.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Kit {
	
	public String getKitName();
	
	public int getKitID();
	
	public ItemStack[] getArmorContents();
	
	public void setArmorContents(Player player);
	
	public void setInventoryContents(Player player);
	
	public void setKit(Player player);

}