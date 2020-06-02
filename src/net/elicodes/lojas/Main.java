package net.elicodes.lojas;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.elicodes.lojas.commands.AdminCommand;
import net.elicodes.lojas.commands.LojasCommand;
import net.elicodes.lojas.connection.LojasConnection;
import net.elicodes.lojas.connection.LojasManager;
import net.elicodes.lojas.listeners.LojasListener;
import net.elicodes.lojas.methods.LojasConfig;
import net.elicodes.lojas.methods.LojasMethods;

public class Main extends JavaPlugin {

	public static Main instance;
	private LojasConnection lojasConnection;
	private LojasManager lojasManager;
	public LojasConfig config = new LojasConfig();

	@Override
	public void onEnable() {
		instance = this;
		if(!getDescription().getName().equals("EC_Lojas")) {
			getLogger().info("§cNão tente modificar a plugin.yml!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		saveDefaultConfig();
		setup();
	}
	
	@Override
	public void onDisable() {
		saveDefaultConfig();
		setupDisable();
	}

	public void setup() {
		config.setup(this);
		getServer().getPluginManager().registerEvents(new LojasListener(), this);
		getCommand("loja").setExecutor(new LojasCommand());
		getCommand("lojas").setExecutor(new LojasCommand());
		getCommand("lojaadmin").setExecutor(new AdminCommand());
		this.lojasConnection = new LojasConnection(getConfig().getString("MySQL.host"),
				getConfig().getString("MySQL.usuario"),
				getConfig().getString("MySQL.database"),
				getConfig().getString("MySQL.senha"));
		this.setLojasManager(new LojasManager(this, this.lojasConnection));
		 if (getLojasManager().getPlayers().size() > 0) {
	        	for (String s : getLojasManager().getPlayers()) {
	            	if (!LojasMethods.lojas.containsKey(s)) {
	            		getLojasManager().get(s);
	            	}
	            }
	        }
		setupLater();
	}

	public void setupLater() {
		if (getLojasManager().getPlayers().size() > 0) {
        	LojasMethods.lojas.clear();
        	for (String s : getLojasManager().getPlayers()) {
            	getLojasManager().get(s);
            }
        }
	}

	public void setupDisable() {
		if (LojasMethods.lojas.size() > 0) {
			for (String s : LojasMethods.lojas.keySet()) {
				Map<String, Boolean> map = LojasMethods.lojas.get(s);
				for (Entry<String, Boolean> lojas : map.entrySet()) {
					if (!getLojasManager().exists(s)) {
						getLojasManager().set(s, lojas.getKey(), lojas.getValue());
					} else {
						getLojasManager().updatePlayer(s, lojas.getKey(), lojas.getValue());
					}

				}
			}
		}
	}

	public LojasManager getLojasManager() {
		return lojasManager;
	}

	public void setLojasManager(LojasManager lojasManager) {
		this.lojasManager = lojasManager;
	}
}