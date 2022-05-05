package me.shop.VillagerShop;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import me.coins.Utils.Chat;
import me.shop.API.ShopAPI;
import me.shop.GUI.GUI.GuiType;
import me.shop.GUI.GUI_Categories;
import me.shop.GUI.GUI_Shop;

public class Listener_VillagerShopClickEvent implements Listener {
	
	ShopAPI api = new ShopAPI();
	
	@EventHandler
	public void click(PlayerInteractEntityEvent e) {
		
		if(e.getRightClicked().getType() == EntityType.VILLAGER) {
			if(e.getRightClicked().getCustomName() != null) {
				if(e.getRightClicked().getCustomName().equalsIgnoreCase(Chat.format("&b&lItemShop"))) {
					GUI_Categories.sendCategorieGUI(e.getPlayer(), GuiType.Categories);
					e.setCancelled(true);
				} else if(api.yml.getConfigurationSection("Categories").contains(ChatColor.stripColor(e.getRightClicked().getCustomName()))) {
					e.setCancelled(true);
					GUI_Shop.sendGUI(e.getPlayer(), ChatColor.stripColor(e.getRightClicked().getCustomName()));
				}
			}
		}
	}

	
}
