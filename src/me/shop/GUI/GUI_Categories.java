package me.shop.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.shop.API.ShopAPI;
import me.shop.Core.Main;
import me.shop.GUI.GUI.GuiType;
import me.shop.Utils.Chat;
import me.shop.Utils.Messages;

public class GUI_Categories implements Listener {

	private static Main plugin = Main.getPlugin(Main.class);
	
	public static void sendCategorieGUI(Player p, GuiType type) {
		ShopAPI ShopAPI = new ShopAPI();
		GUI GUI = new GUI();
		if(plugin.getConfig().getString("GUI.GUI_Categories.gui-name") != null && plugin.getConfig().get("GUI.GUI_Categories.rows") != null && isInt(plugin.getConfig().getString("GUI.GUI_Categories.rows"))) {
 		Inventory inv = Bukkit.createInventory(null, plugin.getConfig().getInt("GUI.GUI_Categories.rows") * 9, Chat.format(plugin.getConfig().getString("GUI.GUI_Categories.gui-name")));
		GUI.type.put(inv, type);
		ArrayList<ItemStack> items = (ArrayList<ItemStack>) GUI.cyml.get("GUI.content");
		ItemStack[] content = items.toArray(new ItemStack[0]);
		if (content.length <= inv.getSize() || content.length == inv.getSize()) {
			inv.setContents(content);
		} else {
			p.sendMessage(Messages.getMessage("GUI_ERROR").replace("%gui%", "GUI_Categories"));
			}
		for(String item : ShopAPI.yml.getConfigurationSection("Categories").getKeys(false)) {
			String categorie = "Categories." + item;
			ItemStack itemstack = new ItemStack(Material.matchMaterial(ShopAPI.yml.get(categorie + ".Icon.material") + ""));
			ItemMeta itemmeta = itemstack.getItemMeta();
			ArrayList<String> lore = new ArrayList<>();
			itemmeta.setDisplayName(Chat.format(ShopAPI.yml.getString(categorie + ".Icon.displayname")));
			for(String newlore : ShopAPI.yml.getStringList(categorie + ".Icon.lore")) {
				lore.add(Chat.format(newlore).replace("%categorie%", item));
			}
			itemmeta.setLore(lore);
			itemstack.setItemMeta(itemmeta);
			inv.addItem(itemstack);
		}
		p.openInventory(inv);
		} else {
			p.closeInventory();
			p.sendMessage(Messages.getMessage("GUI_ERROR").replace("%gui%", "GUI_Categories"));
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		ShopAPI ShopAPI = new ShopAPI();
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if(e.getInventory() != null && e.getClickedInventory() != p.getInventory()) {
				if(e.getView().getTitle() != null) {
				if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
					if(e.getView().getTitle() != null && e.getView().getTitle().equals(Chat.format(plugin.getConfig().getString("GUI.GUI_Categories.gui-name")))) {
						e.setCancelled(true);
						for(String categorie : ShopAPI.yml.getConfigurationSection("Categories").getKeys(false)) {
							if(e.getCurrentItem().getItemMeta().getDisplayName() != null) {
							if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Chat.format(ShopAPI.yml.getString("Categories." + categorie + ".Icon.displayname")))) {
								e.setCancelled(true);
								GUI_Shop.sendGUI(p, categorie);
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
		}
	}
	
	private static boolean isInt(String message) {
		try {
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	
}
