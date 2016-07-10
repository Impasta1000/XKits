package com.Impasta1000.XKits.permissions;

import com.Impasta1000.XKits.XKits;
import com.Impasta1000.XKits.config.LocaleManager;

public class Messages {
	
	private LocaleManager localeManager;
	public Messages(XKits plugin) {
		localeManager = new LocaleManager(plugin);
	}
	
	public final String NOPERMISSION = localeManager.replacePlaceholders(localeManager.getLocaleMessage("Messages.no-permission"));

}
