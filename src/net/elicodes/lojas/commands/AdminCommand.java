package net.elicodes.lojas.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.elicodes.lojas.Main;
import net.elicodes.lojas.methods.LojasMethods;

public class AdminCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.instance.config.console);
			return true;
		}
		switch (cmd.getName().toLowerCase()) {
		case "lojaadmin":
			if (!sender.hasPermission("loja.admin")) {
				sender.sendMessage(Main.instance.config.permission);
				return true;
			}
			if (a.length == 0) {
				sender.sendMessage("§cUtilize: /lojaadmin <resetar, creditos>.");
				return true;
			}
			switch (a[0].toLowerCase()) {
			case "resetar":
				for (Player s : Bukkit.getOnlinePlayers()) {
					if (LojasMethods.lojas.size() > 0) {
						LojasMethods.lojas.remove(s.getName());
					}else {
						sender.sendMessage(Main.instance.config.semlojas);
					}
				}
				break;
			case "creditos":
				sender.sendMessage("");
				sender.sendMessage(" §aPlugin: §fEC_Lojas");
				sender.sendMessage(" §aCriador: §fEliCodes");
				sender.sendMessage(" §aVersão: §f" + Main.instance.getDescription().getVersion());
				sender.sendMessage(" §aContato (Discord): §fEliCodes#4536");
				sender.sendMessage("");
				break;
			default:
				break;
			}
		}
		return false;
	}
}
