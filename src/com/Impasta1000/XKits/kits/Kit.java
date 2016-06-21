package com.Impasta1000.XKits.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Kit {
	
	public abstract String getKitName();
	
	public abstract int getKitID();
	
	public abstract ItemStack[] getArmorContents();
	
	public abstract void setArmorContents(Player player);
	
	public abstract void setInventoryContents(Player player);
	
	public abstract void setKit(Player player);

}
