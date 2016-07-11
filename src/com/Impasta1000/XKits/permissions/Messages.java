package com.Impasta1000.XKits.permissions;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.config.LocaleManager;

public class Messages {
	
	private LocaleManager localeManager;
	public Messages(XKits plugin) {
		localeManager = new LocaleManager(plugin);
		loadMessages();
	}
	
	public String NOPERMISSION, INSUFFICIENTARGUMENTS, PLAYERJOINEDARENA;
	
	private void loadMessages() {
		NOPERMISSION  = localeManager.replacePlaceholders(localeManager.getLocaleMessage("Messages.NoPermission"));
		INSUFFICIENTARGUMENTS  = localeManager.replacePlaceholders(localeManager.getLocaleMessage("Messages.InsufficientArguments"));
		PLAYERJOINEDARENA  = localeManager.replacePlaceholders(localeManager.getLocaleMessage("Messages.PlayerJoinedArena"));
	}

}
