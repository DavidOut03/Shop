package me.shop.Utils;

import java.io.File;

import org.bukkit.ChatColor;

import me.shop.API.ShopAPI;
import me.shop.Core.Main;

public class Chat {
	
	private static Main plugin = Main.getPlugin(Main.class);
	

	public static String format(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
