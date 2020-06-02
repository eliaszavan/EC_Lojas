package net.elicodes.lojas.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.elicodes.lojas.Main;
import net.elicodes.lojas.methods.LojasMethods;
import net.elicodes.lojas.utils.Titles;

public class LojasCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.instance.config.console);
			return true;
		}
		Player p = (Player)sender;
		switch (cmd.getName().toLowerCase()) {
		case "loja":
			if (!sender.hasPermission("loja.use")) {
				sender.sendMessage(Main.instance.config.permission);
				return true;
			}
			new LojasMethods().createInventoryLoja(p);
			break;
		case "lojas":
			if(LojasMethods.lojas.size() == 0) {
				new LojasMethods().createInventoryLojas(p);
				return true;
			}
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
				int a = 4;
				public void run() {
				    if (this.a == 4) {
				    	Titles t = new Titles("", "§eCarregando lojas...", 1, 2, 0);
				    	t.send(p);
				    }
				    if (this.a == 3) {
				    	Titles t = new Titles("", "§eCarregando lojas..", 0, 2, 0);
				    	t.send(p);
				    }
				    if (this.a == 2) {
				    	Titles t = new Titles("", "§eCarregando lojas.", 0, 2, 0);
				    	t.send(p);
				    }
				    if (this.a == 1) {
				    	Titles t = new Titles("", "§eCarregando lojas..", 0, 1, 1);
				    	t.send(p);
				    }
				    if (this.a == 0) {
				    	new LojasMethods().createInventoryLojas(p);
				    }
				    this.a -= 1;
				}
			}, 0L, 10L);
			break;
		}
		return false;
	}
}
