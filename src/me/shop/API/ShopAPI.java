package me.shop.API;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.shop.Core.Main;
import me.shop.Utils.Chat;
import me.shop.Utils.Messages;

public class ShopAPI {

	public static HashMap<Entity, String> villagershop = new HashMap<Entity, String>();
	private Main plugin = Main.getPlugin(Main.class);
	public File shop = new File(plugin.getDataFolder() + "//shop.yml");
	public YamlConfiguration yml = YamlConfiguration.loadConfiguration(shop);
	
	public void getFiles() {
		if(!new File(plugin.getDataFolder() + "").exists()) {
			plugin.getDataFolder().mkdirs();
		}
		if(!new File(plugin.getDataFolder(), "config.yml").exists()) {
			plugin.saveDefaultConfig();
			plugin.saveConfig();
		}
		if(!shop.exists()) {
			try {
				shop.createNewFile();
				yml.createSection("Categories");
				yml.createSection("Shop");
				yml.save(shop);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public boolean categorieExist(String categorie) {
		if(yml.getConfigurationSection("Categories") != null) {
		for(String currentcagetorie : yml.getConfigurationSection("Categories").getKeys(false)) {
			if(currentcagetorie.toUpperCase().equals(categorie.toUpperCase())) {
				return true;
			} else {
				return false;
			}
		}
		} else {
			return false;
		}
		return false;
	}
	
	public boolean itemExist(String ItemName, String categorie) {
		if(yml.getConfigurationSection("Shop") != null) {
			for(String currentitem : yml.getConfigurationSection("Shop." + categorie).getKeys(false)) {
			if(currentitem.toUpperCase().equals(ItemName.toUpperCase())) {
				return true;
			} else {
				return false;
			}
			}
		} else {
			return false;
		}
		return false;
	}
	
	
	
	
	
	public void addItem(String itemName, String categorie, ItemStack item, double buy_price, double sell_price) {
		ItemStack newitem = new ItemStack(item.getType(), item.getAmount(), (short) item.getData().getData());
	
					try {
						if(itemName != null) {
						    yml.set("Shop." + categorie + "." + itemName + ".item", newitem);
							ItemMeta im = newitem.getItemMeta();
							im.setDisplayName(Chat.format(itemName));
							newitem.setItemMeta(im);
							yml.set("Shop." + categorie + "." + itemName + ".item", newitem);
							yml.set("Shop." + categorie + "." + itemName + ".buy_price", buy_price);
							yml.set("Shop." + categorie + "." + itemName + ".sell_price", sell_price);
						} else {
							ItemMeta im = newitem.getItemMeta();
							im.setDisplayName(null);
							newitem.setItemMeta(im);
							itemName = item.getType().toString();
							yml.set("Shop." + categorie + "." + itemName + ".item", newitem);
							yml.set("Shop." + categorie + "." + itemName + ".buy_price", buy_price);
							yml.set("Shop." + categorie + "." + itemName + ".sell_price", sell_price);
						}
						
						yml.save(shop);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
			
		
	}
	
	public String getPrefix() {
		if(plugin.getConfig().getString("Settings.Prefix") != null) {
			return Chat.format(plugin.getConfig().getString("Settings.Prefix"));
		} else {
			return Chat.format("&8(&a&lShop)");
		}
	}
	
	public void removeItem(String name, String categorie) {
		if(shop.exists()) {
			if(yml.getConfigurationSection("Categories").contains(categorie)) {
			if(yml.getString("Shop." + categorie + "." + name) != null) {
				try {
					yml.set("Shop." + categorie + "." + name, null);
					yml.save(shop);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} 
			}
		}
	}
	
	public ItemStack getItem(String name, String categorie) {
		if(shop.exists()) {
			if(yml.getConfigurationSection("Shop") != null && yml.get("Shop." + categorie) != null) {
				for(String item : yml.getConfigurationSection("Shop." + categorie).getKeys(false)) {
					if(item.toUpperCase().equalsIgnoreCase(name.toUpperCase())) {
						return yml.getItemStack("Shop." + categorie + "." + name + ".item");
					} else {
						return null;
					}
				}
			} else {
				return null;
			}
		}
		return null;
	}
	
	public void createCategorie(String name, ItemStack itemstack) {
		if(shop.exists()) {
			if(yml.getConfigurationSection("Categories") != null && !yml.getConfigurationSection("Categories").contains(name)) {
				try {
					 yml.set("Categories." + name + ".Icon.material" , itemstack.getType().toString());
					 yml.set("Categories." + name + ".Icon.displayname" , Chat.format(name).toString());
					 ArrayList<String> lore = new ArrayList<>();
					 lore.add(Chat.format("&7This is the categorie &e%categorie%&7."));
					 yml.set("Categories." + name + ".Icon.lore" , lore);
					 yml.save(shop);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void deleteCateCorie(String name) {
		if(shop.exists()) {
			if(yml.getConfigurationSection("Categories") != null && yml.getConfigurationSection("Categories").contains(name)) { 
				try {
					 yml.set("Categories." + name, null);
					 yml.set("Shop." + name, null);
					 yml.save(shop);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void createItemShop(String name, Location loc) {
		Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		villager.setCustomName(Chat.format(name));
		villager.setCustomNameVisible(true);
		villager.setCanPickupItems(false);
		villager.setAgeLock(true);
		villager.setPersistent(true);
		villager.setAdult();
		villager.setSilent(true);
		villager.setVillagerType(Type.PLAINS);
		villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 255, true));
	}
	
	public void removeItemShops(World w, String shop) {
		for(Entity entity : w.getEntities()) {
			if(entity.getType() == EntityType.VILLAGER) {
				if(entity.getCustomName() != null) {
					if(shop != null) {
						if(entity.getCustomName().toLowerCase().equalsIgnoreCase(Chat.format("&b&lItemShop").toLowerCase())) {
							entity.remove();
						} else if(entity.getCustomName().toLowerCase().equalsIgnoreCase(Chat.format("&b&l" + shop).toLowerCase())) {
							entity.remove();
						}
					} else {
						entity.remove();
					}
				}
			}
		}
	}
	
}
