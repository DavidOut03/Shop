package me.shop.Commands;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.shop.API.ShopAPI;
import me.shop.Core.Main;
import me.shop.GUI.GUI_SetGUI;
import me.shop.Utils.Chat;
import me.shop.Utils.Messages;

public class Command_ItemShop implements TabCompleter, CommandExecutor {

	ShopAPI api = new ShopAPI();
	private Main plugin = Main.getPlugin(Main.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("itemshop")) {
			if(sender.isOp() || sender.hasPermission("itemshop.*") || sender.hasPermission("itemshop.manage")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
 				if(args.length == 0) {
					sendHelp(sender);
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("editgui")) {
						GUI_SetGUI.sendGUI(p);
					} else if(args[0].equalsIgnoreCase("help")){
						sendCommandHelp(sender);
					} else if(args[0].equalsIgnoreCase("setShop") || args[0].equalsIgnoreCase("createShop")) {
						api.createItemShop("&b&lItemShop", p.getLocation());
						DecimalFormat df = new DecimalFormat("#");
						p.sendMessage(Messages.getMessage("SPAWNED_SHOP").replace("%location%", "X: " + df.format(p.getLocation().getX()) + " Z: "  + df.format(p.getLocation().getZ())));
					} else if(args[0].equalsIgnoreCase("removeShop")) {
						api.removeItemShops(p.getWorld(), "ItemShop");
						p.sendMessage(Messages.getMessage("DESPAWNED_SHOP").replace("%villagershop%", "ItemShop"));
					} else {
						sendCommandHelp(sender);
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("addCategorie") || args[0].equalsIgnoreCase("ac")) {
						if(!api.yml.getConfigurationSection("Categories").contains(args[1])) {
							if(p.getItemInHand().getType() != Material.AIR) {
								api.createCategorie(args[1], p.getItemInHand());
							} else {
								api.createCategorie(args[1], new ItemStack(Material.PAPER));
							}
						sender.sendMessage(Messages.getMessage("ADDED_CATEGORIE").replace("%categorie%", args[1]));
						} else {
							sender.sendMessage(Messages.getMessage("CATEGORIE_ALREADY_EXIST").replace("%categorie%", args[1]));
						}
					} else if(args[0].equalsIgnoreCase("removeCategorie") || args[0].equalsIgnoreCase("rc")) {
						if(api.yml.getConfigurationSection("Categories").contains(args[1])) {
							api.deleteCateCorie(args[1]);
							sender.sendMessage(Messages.getMessage("REMOVED_CATEGORIE").replace("%categorie%", args[1]));
							} else {
								sender.sendMessage(Messages.getMessage("CATEGORIE_DOES_NOT_EXIST").replace("%categorie%", args[1]));
							}
					} else if(args[0].equalsIgnoreCase("setShop") || args[0].equalsIgnoreCase("createShop")) {
						if(api.yml.getConfigurationSection("Categories").contains(args[1])) {
							api.createItemShop("&b&l" + args[1], p.getLocation());
							DecimalFormat df = new DecimalFormat("#");
							p.sendMessage(Messages.getMessage("SPAWNED_SHOP").replace("%location%", "X: " + df.format(p.getLocation().getX()) + " Z: "  + df.format(p.getLocation().getZ())));
						} else {
							sender.sendMessage(Messages.getMessage("CATEGORIE_DOES_NOT_EXIST").replace("%categorie%", args[1]));
						}
					} else if(args[0].equalsIgnoreCase("removeShop")) {
						if(api.yml.getConfigurationSection("Categories").contains(args[1])) {
							api.removeItemShops(p.getWorld(), args[1]);
							p.sendMessage(Messages.getMessage("DESPAWNED_SHOP").replace("%villagershop%", args[1]));
						} else {
							sender.sendMessage(Messages.getMessage("CATEGORIE_DOES_NOT_EXIST").replace("%categorie%", args[1]));
						}
					} else if(args[0].equalsIgnoreCase("setSellPrice") || args[0].equalsIgnoreCase("ssp")) {
					} else if(args[0].equalsIgnoreCase("setBuyPrice") || args[0].equalsIgnoreCase("sbp")) {
						
					} else {
						sendCommandHelp(sender);
					}
					
				} else if(args.length == 3) {
					 if(args[0].equalsIgnoreCase("removeItem") || args[0].equalsIgnoreCase("ri")) {
							if(api.yml.getConfigurationSection("Categories").contains(args[1])) {
								if(api.yml.getConfigurationSection("Shop." + args[1]).contains(args[2])) {
									api.removeItem(args[2], args[1]);
									sender.sendMessage(Messages.getMessage("REMOVED_ITEM"));
								} else {
									sender.sendMessage(Messages.getMessage("ITEM_IS_NOT_IN_SHOP"));
								}
							} else {
								sender.sendMessage(Messages.getMessage("CATEGORIE_DOES_NOT_EXIST").replace("%categorie%", args[1]));
							}
								}  else {
									sendCommandHelp(sender);
								}
				} else if(args.length == 4) {
					 if(args[0].equalsIgnoreCase("addItem") || args[0].equalsIgnoreCase("ai")) {
						 if(api.yml.getConfigurationSection("Categories").contains(args[1])) {
								if(isNumber(args[2]) == true) {
									if(isNumber(args[3]) == true) {
									if(p.getItemInHand().getType() != Material.AIR) {
									if(p.getItemInHand().getItemMeta().getDisplayName() == null || p.getItemInHand().getItemMeta().getDisplayName().isEmpty()) {
										api.addItem(null, args[1], p.getItemInHand(), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
										sender.sendMessage(Messages.getMessage("ADDED_ITEM").replace("%categorie%", args[2]));
									} else {
										api.addItem(p.getItemInHand().getItemMeta().getDisplayName().toString(), args[1], p.getItemInHand(), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
										sender.sendMessage(Messages.getMessage("ADDED_ITEM").replace("%categorie%", args[2]));
									}
									} else {
									
										sender.sendMessage(Chat.format("&cYou have to hold a item in your hand."));
									}
							
								} else {
									sender.sendMessage(Messages.getMessage("USE_NUMBERS").replace("%number%", args[3]));
								}
								} else {
									sender.sendMessage(Messages.getMessage("USE_NUMBERS").replace("%number%", args[2]));
								}
							} else {
								sender.sendMessage(Messages.getMessage("CATEGORIE_DOES_NOT_EXIST").replace("%categorie%", args[1]));
							}
							
					 } else {
						 sendCommandHelp(sender);
					 }
				} else if(args.length == 5) {
					 if(args[0].equalsIgnoreCase("addItem") || args[0].equalsIgnoreCase("ai")) {
						 if(api.yml.getConfigurationSection("Categories").contains(args[1])) {
								if(isNumber(args[3]) == true) {
									if(isNumber(args[4]) == true) {
									if(p.getItemInHand().getType() != Material.AIR) {
										String name = Chat.format(args[2]);
										api.addItem(Chat.format(args[2]), args[1], p.getItemInHand(), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
										sender.sendMessage(Messages.getMessage("ADDED_ITEM").replace("%categorie%", args[1]));
									} else {
										sender.sendMessage(Chat.format("&cYou have to hold a item in your hand."));
									}
							
								} else {
									sender.sendMessage(Messages.getMessage("USE_NUMBERS").replace("%number%", args[3]));
								}
								} else {
									sender.sendMessage(Messages.getMessage("USE_NUMBERS").replace("%number%", args[2]));
								}
							} else {
								sender.sendMessage(Messages.getMessage("CATEGORIE_DOES_NOT_EXIST").replace("%categorie%", args[1]));
							}
							
					 } else {
						 sendCommandHelp(sender);
					 }
				} else if(args.length == 6) {
					 if(args[0].equalsIgnoreCase("addItem") || args[0].equalsIgnoreCase("ai")) {
						 if(api.yml.getConfigurationSection("Categories").contains(args[1])) {
								if(isNumber(args[4]) == true) {
									if(isNumber(args[5]) == true) {
									if(p.getItemInHand().getType() != Material.AIR) {
										String name = Chat.format(args[2]);
										api.addItem(Chat.format(args[2] + " " + args[3]), args[1], p.getItemInHand(), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
										sender.sendMessage(Messages.getMessage("ADDED_ITEM").replace("%categorie%", args[1]));
									} else {
										sender.sendMessage(Chat.format("&cYou have to hold a item in your hand."));
									}
							
								} else {
									sender.sendMessage(Messages.getMessage("USE_NUMBERS").replace("%number%", args[3]));
								}
								} else {
									sender.sendMessage(Messages.getMessage("USE_NUMBERS").replace("%number%", args[2]));
								}
							} else {
								sender.sendMessage(Messages.getMessage("CATEGORIE_DOES_NOT_EXIST").replace("%categorie%", args[1]));
							}
							
					 } else {
						 sendCommandHelp(sender);
					 }
				} else if(args.length == 7) {
					 if(args[0].equalsIgnoreCase("addItem") || args[0].equalsIgnoreCase("ai")) {
						 if(api.yml.getConfigurationSection("Categories").contains(args[1])) {
								if(isNumber(args[5]) == true) {
									if(isNumber(args[6]) == true) {
									if(p.getItemInHand().getType() != Material.AIR) {
										String name = Chat.format(args[2]);
										api.addItem(Chat.format(args[2]+ " " + args[3]+ " " + args[4]), args[1], p.getItemInHand(), Double.parseDouble(args[5]), Double.parseDouble(args[6]));
										sender.sendMessage(Messages.getMessage("ADDED_ITEM").replace("%categorie%", args[1]));
									} else {
										sender.sendMessage(Chat.format("&cYou have to hold a item in your hand."));
									}
							
								} else {
									sender.sendMessage(Messages.getMessage("USE_NUMBERS").replace("%number%", args[3]));
								}
								} else {
									sender.sendMessage(Messages.getMessage("USE_NUMBERS").replace("%number%", args[2]));
								}
							} else {
								sender.sendMessage(Messages.getMessage("CATEGORIE_DOES_NOT_EXIST").replace("%categorie%", args[1]));
							}
							
					 } else {
						 sendCommandHelp(sender);
					 }
				}
 				
			} else {
				sender.sendMessage(Messages.getMessage("CONSOLE"));
			}
			} else {
				sender.sendMessage(Messages.getMessage("NOPERMISSION"));
			}
		}
		return false;
	}
	
	public boolean isNumber(String number) {
		try {
			Double.parseDouble(number);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void sendHelp(CommandSender sender) {
		sender.sendMessage(Chat.format("&aHelp&7: &f/itemshop help"));
		sender.sendMessage(Chat.format("&aPlugin&7: &f" + plugin.getName()));
		sender.sendMessage(Chat.format("&aAuthor&7: &7" + plugin.getDescription().getAuthors()));
		sender.sendMessage(Chat.format("&aVersion&7: &7" + plugin.getDescription().getVersion()));
	}
	
	public void sendCommandHelp(CommandSender sender) {
		sender.sendMessage(Chat.format("&8&m--------------------------------------------------"));
		sender.sendMessage(Chat.format("&a" + plugin.getName() + " &7Help" ));
		sender.sendMessage(Chat.format("&e&l/itemshop help&7: show the plugin help."));
		sender.sendMessage(Chat.format("&e&l/itemshop createShop [categorie]&7: spawn a villagershop."));
		sender.sendMessage(Chat.format("&e&l/itemshop removeShop [categorie]&7: spawn a villagershop."));
		sender.sendMessage(Chat.format("&e&l/itemshop addCategorie [categorie]&7: add a shop categorie."));
		sender.sendMessage(Chat.format("&e&l/itemshop addItem [categorie] [itemName] [buy_price] [sell_price]&7: add an item to the shop."));
		sender.sendMessage(Chat.format("&e&l/itemshop removeCategorie [categorie]&7: remove a shop categorie."));
		sender.sendMessage(Chat.format("&e&l/itemshop removeItem [categorie] [itemName]&7: remove an item from the shop."));
		sender.sendMessage(Chat.format("&e&l/itemshop editGUI&7: show the plugin help."));
		sender.sendMessage(Chat.format("&8&m--------------------------------------------------"));
	}

}
