package net.elicodes.lojas.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.elicodes.lojas.Main;
import net.elicodes.lojas.utils.NBTTag;
import net.elicodes.lojas.utils.Scroller;
import net.elicodes.lojas.utils.Scroller.ScrollerBuilder;

public class LojasMethods {

	public static HashMap<String, Map<String, Boolean>> lojas = new HashMap<String, Map<String, Boolean>>();

	public void createInventoryLoja(Player p) {
		Inventory inv;
		ItemStack item;
		ItemMeta m;
		if (lojas.containsKey(p.getName())) {
			inv = Bukkit.createInventory(null, 3 * 9, Main.instance.config.configurarName);
			item = new ItemStack(Material.ENDER_PEARL);
			m = item.getItemMeta();
			m.setDisplayName(Main.instance.config.configurarTeleportarName);
			m.setLore(Main.instance.config.configurarTeleportarLore);
			item.setItemMeta(m);
			inv.setItem(10, item);
			item = new ItemStack(Material.BARRIER);
			m = item.getItemMeta();
			m.setDisplayName(Main.instance.config.configurarDeletarName);
			m.setLore(Main.instance.config.configurarDeletarLore);
			item.setItemMeta(m);
			inv.setItem(12, item);
			lojas.get(p.getName()).keySet().forEach(loja -> {
				boolean value = lojas.get(p.getName()).get(loja);
				if (value) {
					ItemStack s = new ItemStack(Material.REDSTONE_BLOCK);
					ItemMeta ms = s.getItemMeta();
					ms.setDisplayName(Main.instance.config.configurarFecharName);
					ms.setLore(Main.instance.config.configurarFecharLore);
					s.setItemMeta(ms);
					inv.setItem(14, s);
				} else {
					ItemStack s = new ItemStack(Material.EMERALD_BLOCK);
					ItemMeta ms = s.getItemMeta();
					ms.setDisplayName(Main.instance.config.configurarAbrirName);
					ms.setLore(Main.instance.config.configurarAbrirLore);
					s.setItemMeta(ms);
					inv.setItem(14, s);
				}
			});
			item = new ItemStack(Material.FEATHER);
			m = item.getItemMeta();
			m.setDisplayName(Main.instance.config.configurarSetarName);
			m.setLore(Main.instance.config.configurarSetarLore);
			item.setItemMeta(m);
			inv.setItem(16, item);
			p.openInventory(inv);
			return;
		}
		inv = Bukkit.createInventory(null, 3 * 9, Main.instance.config.criarName);
		item = new ItemStack(Material.SIGN);
		m = item.getItemMeta();
		m.setDisplayName(Main.instance.config.criarCriarName);
		m.setLore(Main.instance.config.criarCriarLore);
		item.setItemMeta(m);
		inv.setItem(13, item);
		p.openInventory(inv);
		return;
	}

	public void createInventoryLojas(Player p) {
		ItemStack item;
		SkullMeta m;
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		if (lojas.size() == 0) {
			Inventory inv = Bukkit.createInventory(null, 54, Main.instance.config.lojasVaziasName);
			item = new ItemStack(Material.MINECART);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Main.instance.config.lojasVaziasVaziaName);
			meta.setLore(Main.instance.config.lojasVaziasVaziaLore);
			item.setItemMeta(meta);
			inv.setItem(22, item);
			p.openInventory(inv);
			return;
		}
		for (String s : lojas.keySet()) {
			item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			m = (SkullMeta) item.getItemMeta();
			m.setOwner(s);
			m.setDisplayName(Main.instance.config.lojaShopName.replace("{player}", s));
			m.setLore(Main.instance.config.lojaShopLore);
			item.setItemMeta(m);
			NBTTag.setNBTString(item.getItemMeta(), "EC_Lojas", s);
			items.add(item);
		}
		Scroller scroller = new ScrollerBuilder().withItems(items).withName(Main.instance.config.lojaName).build();
		scroller.open(p);
	}
}
