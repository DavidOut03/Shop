package me.shop.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.shop.Core.Main;
import me.shop.GUI.GUI.GuiType;
import me.shop.GUI.GUI_Categories;
import me.shop.Utils.Messages;

public class Command_Shop implements CommandExecutor,TabCompleter{

	Main plugin = Main.getPlugin(Main.class);
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(sender instanceof Player) {
			if(plugin.getConfig().getString("Settings.shop-enabled") != null && plugin.getConfig().getBoolean("Settings.shop-enabled") == true) {
				Player p = (Player) sender;
				GUI_Categories.sendCategorieGUI(p, GuiType.Categories);
			} else {
				sender.sendMessage(Messages.getMessage("SHOP_IS_NOT_ENABLED"));
			}
		} else {
			sender.sendMessage(Messages.getMessage("CONSOLE"));
		}
		return false;
	}

}
