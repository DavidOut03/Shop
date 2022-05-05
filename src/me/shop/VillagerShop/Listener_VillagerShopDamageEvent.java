package me.shop.VillagerShop;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.coins.Utils.Chat;
import me.shop.API.ShopAPI;

public class Listener_VillagerShopDamageEvent implements Listener {
	
	ShopAPI api = new ShopAPI();
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		
		if(e.getEntityType() == EntityType.VILLAGER) {
			if(e.getEntity().getCustomName() != null) {
				if(e.getEntity().getCustomName().equalsIgnoreCase(Chat.format("&b&lItemShop"))) {
					e.setCancelled(true);
				} else if(api.yml.getConfigurationSection("Categories").contains(ChatColor.stripColor(e.getEntity().getCustomName()))) {
					e.setCancelled(true);
				}
			}
		}
	}

}
