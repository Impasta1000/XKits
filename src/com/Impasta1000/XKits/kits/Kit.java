package com.Impasta1000.XKits.kits;

import org.bukkit.entity.Player;

public interface Kit {
	
	public String getKitName();
	
	public int getKitID();
	
	public void setArmorContents(Player player);
	
	public void setInventoryContents(Player player);
	
	public void setPotionEffect(Player player);
	
	public void setKit(Player player);

}