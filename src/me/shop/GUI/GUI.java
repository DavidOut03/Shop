package me.shop.GUI;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.shop.Core.Main;
import me.shop.Utils.Chat;

public class GUI {
	
	public enum GuiType {
		Categories, ItemShop;
	}
	
	public HashMap<Inventory, GuiType> type = new HashMap<>();
	
	public GuiType getGUIType(Inventory inv) {
		return type.get(inv);
	}

	private Main plugin = Main.getPlugin(Main.class);
	public File categories = new File(plugin.getDataFolder() + "//GUI//Categories.gui");
	public YamlConfiguration cyml = YamlConfiguration.loadConfiguration(categories);
	
	public File itemshop = new File(plugin.getDataFolder() + "//GUI//ItemShop.gui");
	public YamlConfiguration isyml = YamlConfiguration.loadConfiguration(itemshop);
	
	
	
	public void getFiles() {
		if(!categories.exists()) {
			try {
				cyml.createSection("GUI");
				ArrayList<ItemStack> item = new ArrayList<>();
				item.add(new ItemStack(Material.AIR));
				cyml.set("GUI.content", item);
				cyml.save(categories);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		if(!itemshop.exists()) {
			try {
				isyml.createSection("GUI");
				ArrayList<ItemStack> item = new ArrayList<>();
				item.add(new ItemStack(Material.AIR));
				isyml.set("GUI.content", item);
				isyml.save(itemshop);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void createItem(Material mat, String displayname, ArrayList<String> lore, int amount, int type, Inventory inv) {
		ItemStack item = new ItemStack(mat, amount, (short) type);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Chat.format(displayname));
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.addItem(item);
	}
	
	public void setItem(Material mat, String displayname, ArrayList<String> lore, int amount, int type, Inventory inv, int slot) {
		ItemStack item = new ItemStack(mat, amount, (short) type);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Chat.format(displayname));
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(slot, item);
	}
}
