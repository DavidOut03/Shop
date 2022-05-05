package me.shop.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.coins.Utils.Chat;
import me.shop.API.ShopAPI;
import me.shop.Utils.Messages;

public class Command_Sell implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		if(sender instanceof Player) {
		
			if(args.length == 2) {
				if(isInteger(args[1])) {
					ShopAPI api = new ShopAPI();
					String d = null;
					for(String items : api.yml.getConfigurationSection("Shop.Blocks").getKeys(false)) {
						 d += items + ",";
					}
					sender.sendMessage(d);
				} else {
					sender.sendMessage(Messages.getMessage("USE_NUMBERS"));
				}
			}
		}
		return false;
	}

	public static boolean isInteger(String message) {
		try {
			Double.parseDouble(message);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
}
