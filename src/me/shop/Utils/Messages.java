package me.shop.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import me.shop.API.ShopAPI;
import me.shop.Core.Main;

public class Messages {
	
	private static Main plugin = Main.getPlugin(Main.class);
	private static File database = new File(plugin.getDataFolder() + "//dataBase");
	private static File messagesfile = new File(plugin.getDataFolder() + "//dataBase//Messages.yml");
	private static YamlConfiguration yaml = YamlConfiguration.loadConfiguration(messagesfile);

	
	public static void getFiles() {
		if(!database.exists()) {
			database.mkdir();
		}
		if(!messagesfile.exists()) {
			try {
				messagesfile.createNewFile();
				yaml.createSection("Messages");
				yaml.set("Messages.ENABLED_PLUGIN", ENABLED_PLUGIN);
				yaml.set("Messages.DISABLED_PLUGIN", DISABLED_PLUGIN);
				yaml.set("Messages.NOPERMISSION", NOPERMISSION);
				yaml.set("Messages.CONSOLE", CONSOLE);
				yaml.set("Messages.USE_COMMAND", USE_COMMAND);
				yaml.set("Messages.USE_NUMBERS", USE_NUMBERS);
				yaml.set("Messages.SPAWNED_SHOP", SPAWNED_SHOP);
				yaml.set("Messages.DESPAWNED_SHOP", DESPAWNED_SHOP);
				yaml.set("Messages.ADDED_CATEGORIE", ADDED_CATEGORIE);
				yaml.set("Messages.ADDED_ITEM", ADDED_ITEM);
				yaml.set("Messages.REMOVED_CATEGORIE", REMOVED_CATEGORIE);
				yaml.set("Messages.REMOVED_ITEM", REMOVED_ITEM);
				yaml.set("Messages.BOUGHT_ITEM", BOUGHT_ITEM);
				yaml.set("Messages.SOLD_ITEM", SOLD_ITEM);
				yaml.set("Messages.GUI_ERROR", GUI_ERROR);
				yaml.set("Messages.ITEM_ALREADY_IN_SHOP", ITEM_ALREADY_IN_SHOP);
				yaml.set("Messages.ITEM_IS_NOT_IN_SHOP", ITEM_IS_NOT_IN_SHOP);
				yaml.set("Messages.CATEGORIE_ALREADY_EXIST", CATEGORIE_ALREADY_EXIST);
				yaml.set("Messages.CATEGORIE_DOES_NOT_EXIST", CATEGORIE_DOES_NOT_EXIST);
				yaml.set("Messages.SHOP_IS_NOT_ENABLED", SHOP_IS_NOT_ENABLED);
				yaml.save(messagesfile);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static String getMessage(String message) {
		ShopAPI ShopAPI = new ShopAPI();
		if(messagesfile.exists() && yaml.getString("Messages." + message) != null) {
			return Chat.format(yaml.getString("Messages." + message).replace("%prefix%", ShopAPI.getPrefix()));
		} else if(message.equals("ENABLED_PLUGIN")) {
			return ENABLED_PLUGIN.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("DISABLED_PLUGIN")) {
			return DISABLED_PLUGIN.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("NOPERMISSION")) {
			return NOPERMISSION.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("CONSOLE")) {
			return CONSOLE.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("USE_COMMAND")) {
			return USE_COMMAND.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("USE_NUMBERS")) {
			return USE_NUMBERS.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("ADDED_CATEGORIE")) {
			return ADDED_CATEGORIE.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("ADDED_ITEM")) {
			return ADDED_ITEM.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("REMOVED_CATEGORIE")) {
			return REMOVED_CATEGORIE.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("REMOVED_ITEM")) {
			return REMOVED_ITEM.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("BOUGHT_ITEM")) {
			return BOUGHT_ITEM.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("SOLD_ITEM")) {
			return SOLD_ITEM.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("GUI_ERROR")) {
			return GUI_ERROR.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("ITEM_ALREADY_IN_SHOP")) {
			return ITEM_ALREADY_IN_SHOP.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("ITEM_IS_NOT_IN_SHOP")) {
			return ITEM_IS_NOT_IN_SHOP.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("CATEGORIE_ALREADY_EXIST")) {
			return CATEGORIE_ALREADY_EXIST.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("CATEGORIE_DOES_NOT_EXIST")) {
			return CATEGORIE_DOES_NOT_EXIST.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("SHOP_IS_NOT_ENABLED")) {
			return SHOP_IS_NOT_ENABLED.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("SPAWNED_SHOP")) {
			return SPAWNED_SHOP.replace("%prefix%", ShopAPI.getPrefix());
		} else if(message.equals("DESPAWNED_SHOP")) {
			return DESPAWNED_SHOP.replace("%prefix%", ShopAPI.getPrefix());
		} else {
			return Chat.format("&cMessage not found.");
		}
	}
	
	// plugin messages
	public static String ENABLED_PLUGIN = Chat.format("%prefix% &7Turned " + plugin.getName() + " &aON&7.");
	public static String DISABLED_PLUGIN = Chat.format("%prefix% &7Turned " + plugin.getName() + " &cOFF&7.");
	public static String NOPERMISSION = Chat.format("&cYou don't have permission for this command.");
	public static String CONSOLE = Chat.format("&cOnly players can use this command.");
	public static String USE_COMMAND = Chat.format("&a&lUse&7: %command%");
	public static String USE_NUMBERS = Chat.format("&cYou have to use numbers for this.");
	public static String SPAWNED_SHOP = Chat.format("%prefix% &7You spawned a villagershop at &a%location%&7.");
	public static String DESPAWNED_SHOP = Chat.format("%prefix% &7You despawned all the villagershop called &e%villagershop%&7.");
	// shop messages
	public static String ADDED_CATEGORIE = Chat.format("%prefix% &7You added the categorie: &e%categorie% &7to the store.");
	public static String ADDED_ITEM = Chat.format("%prefix% &7You added an item to the store.");
	public static String REMOVED_CATEGORIE = Chat.format("%prefix% &7You removed the categorie: &e%categorie% &7from the store.");
	public static String REMOVED_ITEM = Chat.format("%prefix% &7You removed an item from the store.");
	public static String BOUGHT_ITEM = Chat.format("%prefix% &7You bought an item from the shop for %price%&7.");
	public static String SOLD_ITEM = Chat.format("%prefix% &7You sold an item to the shop for %price%&7.");
	
	// errors
	public static String GUI_ERROR = Chat.format("&cCan't open &4%gui% &cbecause there is an error, please contact a staffmember.");
	public static String CATEGORIE_ALREADY_EXIST = Chat.format("&c%categorie% does already exist.");
	public static String CATEGORIE_DOES_NOT_EXIST = Chat.format("&c%categorie% does not exist.");
	public static String ITEM_ALREADY_IN_SHOP = Chat.format("&cThis item is already in the shop.");
	public static String ITEM_IS_NOT_IN_SHOP = Chat.format("&cThe shop does not contain this item.");
	public static String SHOP_IS_NOT_ENABLED = Chat.format("&cThe shop is not enabled.");
	
	
	

}
