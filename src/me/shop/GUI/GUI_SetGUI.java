package me.shop.GUI;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.shop.Core.Main;
import me.shop.GUI.GUI.GuiType;
import me.shop.Utils.Chat;
import me.shop.Utils.Messages;

public class GUI_SetGUI implements Listener {

	private static Main plugin = Main.getPlugin(Main.class);
	
	public static void sendGUI(Player p) {
		GUI GUI = new GUI();
		if(plugin.getConfig().getString("GUI.GUI_ChooseGUI.gui-name") != null) {
		Inventory inv = Bukkit.createInventory(null, 9, Chat.format(plugin.getConfig().getString("GUI.GUI_ChooseGUI.gui-name")));
		ItemStack item = new ItemStack(Material.BEDROCK);
		
		ArrayList<String> compasslore = new ArrayList<>();
		compasslore.add(Chat.format("&7Click to edit the categories gui."));
		compasslore.add(Chat.format("&c&lRemember: &7Leave open slots for the categories."));
		GUI.setItem(Material.COMPASS, "&c&lCategories", null, 1, 0, inv, 3);
		ArrayList<String> shoplore = new ArrayList<>();
		shoplore.add(Chat.format("&7Click to edit the categories gui."));
		shoplore.add(Chat.format("&c&lRemember: &7Leave open slots for the items."));
		GUI.setItem(Material.ITEM_FRAME, "&a&lItemShop", shoplore, 1, 0, inv, 5);
		
		for(int slot = 0; slot < 9; slot++) {
			if(inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
			inv.setItem(slot, item);
			}
		}
		p.openInventory(inv);
		} else {
			p.sendMessage(Messages.getMessage("GUI_ERROR").replace("%gui%", "GUI_ChooseGUI"));
		}
	}


	public static void sendGUI(Player p, GuiType guitype) {
		GUI GUI = new GUI();
		if(guitype.equals(GuiType.Categories) && isInt(plugin.getConfig().getString("GUI.GUI_Categories.rows")) && plugin.getConfig().getString("GUI.GUI_Editor.gui-name") != null) {
		int rows = plugin.getConfig().getInt("GUI.GUI_Categories.rows") * 9;
		String title = plugin.getConfig().getString("GUI.GUI_Editor.gui-name").replace("%gui%", plugin.getConfig().getString("GUI.GUI_Categories.gui-name"));
		Inventory inv = Bukkit.createInventory(null, rows, Chat.format(title));
		if (GUI.categories.exists()) {
			ArrayList<ItemStack> items = (ArrayList<ItemStack>) GUI.cyml.get("GUI.content");
			ItemStack[] content = items.toArray(new ItemStack[0]);
			if (content != null) {
				for (String item : GUI.cyml.getConfigurationSection("GUI").getKeys(false)) {
					if (GUI.cyml.get("GUI.content") + item != null) {
						if (content.length <= inv.getSize() || content.length == inv.getSize()) {
							inv.setContents(content);
						} else {
							p.sendMessage(Messages.getMessage("GUI_ERROR").replace("%gui%", "GUI_Categories"));
						}
					}
				}
			}
		}
		GUI.type.put(inv, GuiType.Categories);
		p.openInventory(inv);
	
			} else if(guitype.equals(GuiType.ItemShop) && isInt(plugin.getConfig().getString("GUI.GUI_ItemShop.rows")) && plugin.getConfig().getString("GUI.GUI_Editor.gui-name") != null && plugin.getConfig().getString("GUI.GUI_ItemShop.gui-name") != null) {
				int rows = plugin.getConfig().getInt("GUI.GUI_ItemShop.rows") * 9;
				String title = plugin.getConfig().getString("GUI.GUI_Editor.gui-name").replace("%gui%", plugin.getConfig().getString("GUI.GUI_ItemShop.gui-name"));
				Inventory inv = Bukkit.createInventory(null, rows, Chat.format(title));
				if (GUI.categories.exists()) {
					ArrayList<ItemStack> items = (ArrayList<ItemStack>) GUI.isyml.get("GUI.content");
					ItemStack[] content = items.toArray(new ItemStack[0]);
					if (content != null) {
						for (String item : GUI.isyml.getConfigurationSection("GUI").getKeys(false)) {
							if (GUI.isyml.get("GUI.content") + item != null) {
								if (content.length <= inv.getSize() || content.length == inv.getSize()) {
									inv.setContents(content);
								} else {
									p.sendMessage(Messages.getMessage("GUI_ERROR").replace("%gui%", "GUI_Categories"));
								}
							}
						}
					}
				}
				GUI.type.put(inv, GuiType.ItemShop);
				p.openInventory(inv);
			
					}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		GUI GUI = new GUI();
		if(e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer(); 
			if(e.getInventory() != p.getInventory()) {
			if(e.getView().getTitle() != null) {
				if(e.getView().getTitle().equalsIgnoreCase(Chat.format(plugin.getConfig().getString("GUI.GUI_Editor.gui-name").replace("%gui%", plugin.getConfig().getString("GUI.GUI_Categories.gui-name"))))) {
					try {
						GUI.cyml.set("GUI.content", e.getInventory().getContents());
						GUI.cyml.save(GUI.categories);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else 	if(e.getView().getTitle().equalsIgnoreCase(Chat.format(plugin.getConfig().getString("GUI.GUI_Editor.gui-name").replace("%gui%", plugin.getConfig().getString("GUI.GUI_ItemShop.gui-name"))))) {
					try {
						GUI.isyml.set("GUI.content", e.getInventory().getContents());
						GUI.isyml.save(GUI.itemshop);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if(e.getClickedInventory() != null && e.getClickedInventory() != p.getInventory() && e.getView().getTitle() != null) {
				if(e.getView().getTitle().equalsIgnoreCase(Chat.format(plugin.getConfig().getString("GUI.GUI_ChooseGUI.gui-name")))) {
					if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
					if(e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Chat.format("&c&lCategories"))) {
						sendGUI(p, GuiType.Categories);
						e.setCancelled(true);
					} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Chat.format("&a&lItemShop"))) {
						sendGUI(p, GuiType.ItemShop);
						e.setCancelled(true);
					} else {
						e.setCancelled(true);
					}
				}
					}
					e.setCancelled(true);
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
