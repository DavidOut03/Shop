package me.shop.Core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.shop.API.ShopAPI;
import me.shop.Commands.Command_ItemShop;
import me.shop.Commands.Command_Sell;
import me.shop.Commands.Command_Shop;
import me.shop.GUI.GUI;
import me.shop.GUI.GUI_Categories;
import me.shop.GUI.GUI_SetGUI;
import me.shop.GUI.GUI_Shop;
import me.shop.Utils.Chat;
import me.shop.Utils.Messages;
import me.shop.VillagerShop.Listener_VillagerShopClickEvent;
import me.shop.VillagerShop.Listener_VillagerShopDamageEvent;

public class Main extends JavaPlugin {
	

	
	@Override
	public void onEnable() {
		if(Bukkit.getServer().getPluginManager().getPlugin("Coins") != null) {
		getFiles();
		registerCommands();
		registerTabCompleters();
		registerListeners();
		registerGUI();
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage(Chat.format("&c&lInstall the Coins plugin first before you enable this plugin."));
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Messages.getMessage("DISABLED_PLUGIN"));
	}

	
	private void registerCommands() {
		getCommand("itemshop").setExecutor(new Command_ItemShop());
		getCommand("shop").setExecutor(new Command_Shop());
		getCommand("sell").setExecutor(new Command_Sell());
	}
	
	private void registerTabCompleters() {

	}
	
	private void registerGUI() {	
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new GUI_SetGUI(), this);
		pm.registerEvents(new GUI_Categories(), this);
		pm.registerEvents(new GUI_Shop(), this);
	}
	
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Listener_VillagerShopDamageEvent(), this);
		pm.registerEvents(new Listener_VillagerShopClickEvent(), this);
	}
	private void getFiles() {
		ShopAPI ShopAPI = new ShopAPI();
		ShopAPI.getFiles();
		Messages.getFiles();
		GUI GUI = new GUI();
		GUI.getFiles();
		
	}
}
