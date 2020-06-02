package net.elicodes.lojas.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.intellectualcrafters.plot.api.PlotAPI;

import net.elicodes.lojas.Main;
import net.elicodes.lojas.methods.LojasMethods;
import net.elicodes.lojas.utils.NBTTag;

public class LojasListener implements Listener {
	
	PlotAPI plot = new PlotAPI();
	
	public Location getDeserializedLocation(String paramString) {
		if (paramString.equalsIgnoreCase("null"))
			return null;
		String[] arrayOfString = paramString.split(";");
		World world = Bukkit.getServer().getWorld(arrayOfString[0]);
		double d1 = Double.parseDouble(arrayOfString[1]);
		double d2 = Double.parseDouble(arrayOfString[2]);
		double d3 = Double.parseDouble(arrayOfString[3]);
		return new Location(world, d1, d2, d3);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory() == null) return;
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equals(Main.instance.config.configurarName)) {
			e.setCancelled(true);
			switch (e.getRawSlot()) {
			case 10:
				Map<String, Boolean> hash = LojasMethods.lojas.get(p.getName());
				for (Entry<String, Boolean> locs : hash.entrySet()) {
					Location loc = getDeserializedLocation(locs.getKey());
					try {
						p.teleport(loc);
					}catch (NullPointerException es) {
						p.sendMessage(Main.instance.config.error);
						if (Main.instance.getLojasManager().getPlayers().size() > 0) {
			            	LojasMethods.lojas.clear();
			            	for (String s : Main.instance.getLojasManager().getPlayers()) {
			            		Main.instance.getLojasManager().get(s);
			                }
			            }
						return;
					}
					p.closeInventory();
					p.sendMessage(Main.instance.config.teleported);
					return;
				}
				break;
			case 12:
				LojasMethods.lojas.remove(p.getName());
				Main.instance.getLojasManager().delete(p.getName());
				p.sendMessage(Main.instance.config.deleted);
				p.closeInventory();
				break;
			case 14:
				hash = LojasMethods.lojas.get(p.getName());
				hash.entrySet().forEach(locs -> {
					if (locs.getValue()) {
						Map<String, Boolean> map = new HashMap<String, Boolean>();
						map.put(locs.getKey(), false);
						LojasMethods.lojas.remove(p.getName());
						LojasMethods.lojas.put(p.getName(), map);
						p.sendMessage(Main.instance.config.closed);
						p.closeInventory();
						new LojasMethods().createInventoryLoja(p);
						return;
					}
					Map<String, Boolean> map = new HashMap<String, Boolean>();
					map.put(locs.getKey(), true);
					LojasMethods.lojas.remove(p.getName());
					LojasMethods.lojas.put(p.getName(), map);
					p.sendMessage(Main.instance.config.opened);
					p.closeInventory();
					new LojasMethods().createInventoryLoja(p);
					return;
				});
				break;
			case 16:
				Location loc2 = p.getLocation();
				String loc = loc2.getWorld().getName()+";"+loc2.getX()+";"+loc2.getY()+";"+loc2.getZ();
				if (!loc2.getWorld().getName().equals(Main.instance.config.world)) {
					p.sendMessage(Main.instance.config.worldError);
					return;
				}
				if (!plot.isInPlot(p) || !plot.getPlot(loc2).hasOwner()) {
					p.sendMessage(Main.instance.config.plotOwner);
					return;
				}
				Map<String, Boolean> map = new HashMap<String, Boolean>();
				map.put(loc, false);
				LojasMethods.lojas.remove(p.getName());
				LojasMethods.lojas.put(p.getName(), map);
				p.sendMessage(Main.instance.config.changed);
				p.closeInventory();
				new LojasMethods().createInventoryLoja(p);
				break;
			}
		}
        if (e.getInventory().getName().equals(Main.instance.config.criarName)) {
        	e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.SIGN) {
				Location loc2 = p.getLocation();
				String loc = loc2.getWorld().getName()+";"+loc2.getX()+";"+loc2.getY()+";"+loc2.getZ();
				if (!loc2.getWorld().getName().equals(Main.instance.config.world)) {
					p.sendMessage(Main.instance.config.worldError);
					return;
				}
				if (!plot.isInPlot(p) || !plot.getPlot(loc2).hasOwner()) {
					p.sendMessage(Main.instance.config.plotOwner);
					return;
				}
				Map<String, Boolean> hash = new HashMap<String, Boolean>();
				hash.put(loc, false);
				LojasMethods.lojas.put(p.getName(), hash);
				p.closeInventory();
				p.sendMessage(Main.instance.config.created);
				new LojasMethods().createInventoryLoja(p);
			}
		}
        if (e.getInventory().getName().equals(Main.instance.config.lojaName)) {
        	e.setCancelled(true);
        	if (e.getCurrentItem().getType() != Material.SKULL_ITEM) {
        		return;
        	}
        	String nome = NBTTag.getNBTString(e.getCurrentItem().getItemMeta(), "EC_Lojas");
        	LojasMethods.lojas.get(nome).entrySet().forEach(loja -> {
        		if (loja.getValue()) {
        			p.teleport(getDeserializedLocation(loja.getKey()));
        			p.closeInventory();
        			p.sendMessage(Main.instance.config.teleportedO.replace("{player}", nome));
        			return;
        		}
        		p.closeInventory();
        		p.sendMessage(Main.instance.config.closedO);
        		return;
        	});
        }
        if (e.getInventory().getName().equals(Main.instance.config.lojasVaziasName)) {
        	e.setCancelled(true);
        }
	}
}
