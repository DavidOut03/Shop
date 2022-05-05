package me.shop.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.coins.API.CoinsAPI;
import me.shop.API.ShopAPI;
import me.shop.Core.Main;
import me.shop.Utils.Chat;
import me.shop.Utils.Messages;

public class GUI_Shop implements Listener {
	
	private static Main plugin = Main.getPlugin(Main.class);
	ShopAPI api = new ShopAPI();
	
	
	public static void sendGUI(Player p, String categorie) {
		GUI GUI = new GUI();
		ShopAPI ShopAPI = new ShopAPI();
		if(GUI.itemshop.exists()) {
			if(plugin.getConfig().getString("GUI.GUI_ItemShop.gui-name") != null && plugin.getConfig().getString("GUI.GUI_ItemShop.rows") != null && isInt(plugin.getConfig().getString("GUI.GUI_ItemShop.rows"))) {
				String title = Chat.format(plugin.getConfig().getString("GUI.GUI_ItemShop.gui-name"));
				int size = plugin.getConfig().getInt("GUI.GUI_ItemShop.rows") * 9;
				Inventory inv = Bukkit.createInventory(null, size, title);
				ArrayList<ItemStack> items = (ArrayList<ItemStack>) GUI.isyml.get("GUI.content");
				ItemStack[] content = items.toArray(new ItemStack[0]);
				if (content.length <= inv.getSize() || content.length == inv.getSize()) {
					inv.setContents(content);
				} else {
					p.sendMessage(Messages.getMessage("GUI_ERROR").replace("%gui%", "GUI_Categories"));
					}
					if(ShopAPI.yml.getConfigurationSection("Categories").contains(categorie)) {
						if(ShopAPI.yml.getConfigurationSection("Shop." + categorie) != null) {
						for(String item : ShopAPI.yml.getConfigurationSection("Shop." + categorie).getKeys(false)) {
							ItemStack itemstack = ShopAPI.yml.getItemStack("Shop." + categorie + "." + item + ".item");
							ItemMeta im = itemstack.getItemMeta();
							ArrayList<String> newlore = new ArrayList<>();
							newlore.add(Chat.format("&aBuy Price&7: &e"+ CoinsAPI.getValue() + " "+ ShopAPI.yml.getDouble("Shop." + categorie + "." + item + ".buy_price")));
							newlore.add(Chat.format("&cSell Price&7: &e"+ CoinsAPI.getValue() + " "+ ShopAPI.yml.getDouble("Shop." + categorie + "." + item + ".sell_price")));
							im.setLore(newlore);
							itemstack.setItemMeta(im);
							inv.addItem(itemstack);
						}
						}
					
				}
					
				p.openInventory(inv);
			}
		}
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		ShopAPI ShopAPI = new ShopAPI();
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if(e.getClickedInventory() != null && e.getClickedInventory() != p.getInventory()) {
				if(e.getView().getTitle() != null) {
					if(e.getView().getTitle().equalsIgnoreCase(me.coins.Utils.Chat.format(plugin.getConfig().getString("GUI.GUI_ItemShop.gui-name")))) {
						if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
						if(e.isLeftClick()) {
							if(e.getCurrentItem().getItemMeta().getLore() != null && e.getCurrentItem().getItemMeta().getLore().size() == 2) {
								e.setCancelled(true);
								ItemStack item = e.getCurrentItem();
								List<String> lore = item.getItemMeta().getLore();
								String line = ChatColor.stripColor(lore.get(1).toLowerCase());
								String[] zin = line.split(" ");
								double money = Double.parseDouble(zin[3]);
								if(CoinsAPI.getBalance(p.getUniqueId().toString()) == money || CoinsAPI.getBalance(p.getUniqueId().toString()) >= money) {
								ItemStack itemstack = null;
								if(item.getItemMeta().getDisplayName().isEmpty() || item.getItemMeta().getDisplayName() == null || item.getItemMeta() == null) {
									 itemstack = new ItemStack(item.getData().getItemType(), item.getAmount(), (short) item.getData().getData());
									 CoinsAPI.removeMoney(p.getUniqueId().toString(), money);
										p.sendMessage(me.shop.Utils.Messages.getMessage("BOUGHT_ITEM").replace("%price%", money + ""));
										p.getInventory().addItem(itemstack);
								} else {
								 itemstack = api.yml.getItemStack("Shop." + getCategorie(item.getItemMeta().getDisplayName())+ "."+ item.getItemMeta().getDisplayName() + ".item");
								 CoinsAPI.removeMoney(p.getUniqueId().toString(), money);
									p.sendMessage(me.shop.Utils.Messages.getMessage("BOUGHT_ITEM").replace("%price%", money + ""));
									p.getInventory().addItem(itemstack);
								}
								} else {
									p.sendMessage(me.coins.API.Messages.getMessage("notEnoughMoney"));
								}
						} else {
							e.setCancelled(true);
						}
						} else if(e.isRightClick()) {
							if(e.getCurrentItem().getItemMeta().getLore() != null && e.getCurrentItem().getItemMeta().getLore().size() == 2) {
								e.setCancelled(true);
								ItemStack item = e.getCurrentItem();
								List<String> lore = item.getItemMeta().getLore();
								String line = ChatColor.stripColor(lore.get(1).toLowerCase());
								String[] zin = line.split(" ");
								double money = Double.parseDouble(zin[3]);
								ItemStack itemstack = null;
								if(item.getItemMeta().getDisplayName().isEmpty() || item.getItemMeta().getDisplayName() == null) {
									 itemstack = new ItemStack(item.getType(), item.getAmount(), (short) item.getData().getData());
								} else {
								 itemstack =  api.yml.getItemStack("Shop." + getCategorie(item.getItemMeta().getDisplayName())+ "."+ item.getItemMeta().getDisplayName() + ".item");
								}
							
								if(p.getInventory().containsAtLeast(itemstack, 1)) {
									Inventory inv = p.getInventory();
									inv.removeItem(itemstack);
									e.setCancelled(true);
									CoinsAPI.addMoney(p.getUniqueId().toString(), money);
									p.sendMessage(me.shop.Utils.Messages.getMessage("SOLD_ITEM").replace("%price%", money + ""));
								} else {
									e.setCancelled(true);
									p.sendMessage(Chat.format("&cYou have nothing to sell,"));
								}
							
						} else {
							e.setCancelled(true);
						}
						} else {
							e.setCancelled(true);
									
						}
						} else {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	public String getCategorie(String itemName) {
		for(String categorie : api.yml.getConfigurationSection("Shop").getKeys(false)) {
			if(api.yml.getConfigurationSection("Shop." + categorie).contains(itemName)) {
				return categorie;
			}
			
		}
		return null;
	}
	public static boolean isInt(String message) {
		try {
			 return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
