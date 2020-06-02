package net.elicodes.lojas.methods;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.google.common.collect.Lists;

import net.elicodes.lojas.Main;

public class LojasConfig {
	
	public String world;
	public String console, permission, error, teleported, deleted, closed, opened, worldError,
	plotOwner, changed, created, teleportedO, closedO, resetar, reload, semlojas;
	
	public String criarName;
	public String criarCriarName;
	public List<String> criarCriarLore = Lists.newArrayList();
	
	public String configurarName;
	public String configurarTeleportarName;
	public List<String> configurarTeleportarLore = Lists.newArrayList();
	public String configurarDeletarName;
	public List<String> configurarDeletarLore = Lists.newArrayList();
	public String configurarAbrirName;
	public List<String> configurarAbrirLore = Lists.newArrayList();
	public String configurarFecharName;
	public List<String> configurarFecharLore = Lists.newArrayList();
	public String configurarSetarName;
	public List<String> configurarSetarLore = Lists.newArrayList();
	
	public String lojasVaziasName;
	public String lojasVaziasVaziaName;
	public List<String> lojasVaziasVaziaLore = Lists.newArrayList();
	
	public String lojaName;
	public String lojaShopName;
	public List<String> lojaShopLore = Lists.newArrayList(); 
	
	public LojasConfig setup(Main main) {
		LojasConfig config = main.config;
		
		config.world = main.getConfig().getString("MundoPermitido");
		
		ConfigurationSection messages = main.getConfig().getConfigurationSection("Messages");
		config.semlojas = messages.getString("ResetError").replace('&', '§');
		config.reload = messages.getString("Reload").replace('&', '§');
		config.resetar = messages.getString("Resetar").replace('&', '§');
		config.console = messages.getString("Console").replace('&', '§');
		config.permission = messages.getString("Permission").replace('&', '§');
		config.error = messages.getString("Error").replace('&', '§');
		config.teleported = messages.getString("Teleported").replace('&', '§');
		config.deleted = messages.getString("Deleted").replace('&', '§');
		config.closed = messages.getString("Closed").replace('&', '§');
		config.opened = messages.getString("Opened").replace('&', '§');
		config.worldError = messages.getString("World Error").replace('&', '§');
		config.plotOwner = messages.getString("Plot Owner").replace('&', '§');
		config.changed = messages.getString("Changed").replace('&', '§');
		config.created = messages.getString("Created").replace('&', '§');
		config.teleportedO = messages.getString("Teleported Other").replace('&', '§');
		config.closedO = messages.getString("Closed Other").replace('&', '§');
		
		ConfigurationSection menu = main.getConfig().getConfigurationSection("Menu");
		ConfigurationSection loja = menu.getConfigurationSection("Loja");
		ConfigurationSection criar = loja.getConfigurationSection("Criar");
		config.criarName = criar.getString("Name").replace('&', '§');
		config.criarCriarName = criar.getString("Items.Criar.Name").replace('&', '§');
		criar.getStringList("Items.Criar.Lore").forEach(msg -> config.criarCriarLore.add(msg.replace('&', '§')));
		
		ConfigurationSection configurar = loja.getConfigurationSection("Configurar");
		config.configurarName = configurar.getString("Name").replace('&', '§');
		ConfigurationSection items = configurar.getConfigurationSection("Items");
		
		ConfigurationSection teleportar = items.getConfigurationSection("Teleportar");
		config.configurarTeleportarName = teleportar.getString("Name").replace('&', '§');
		teleportar.getStringList("Lore").forEach(msg -> config.configurarTeleportarLore.add(msg.replace('&', '§')));
		
		ConfigurationSection deletar = items.getConfigurationSection("Deletar");
		config.configurarDeletarName = deletar.getString("Name").replace('&', '§');
		deletar.getStringList("Lore").forEach(msg -> config.configurarDeletarLore.add(msg.replace('&', '§')));
		
		ConfigurationSection abrir = items.getConfigurationSection("Abrir");
		config.configurarAbrirName = abrir.getString("Name").replace('&', '§');
		abrir.getStringList("Lore").forEach(msg -> config.configurarAbrirLore.add(msg.replace('&', '§')));
		
		ConfigurationSection fechar = items.getConfigurationSection("Fechar");
		config.configurarFecharName = fechar.getString("Name").replace('&', '§');
		fechar.getStringList("Lore").forEach(msg -> config.configurarFecharLore.add(msg.replace('&', '§')));
		
		ConfigurationSection setar = items.getConfigurationSection("Setar Novo Local");
		config.configurarSetarName = setar.getString("Name").replace('&', '§');
		setar.getStringList("Lore").forEach(msg -> config.configurarSetarLore.add(msg.replace('&', '§')));
		
		ConfigurationSection vazias = menu.getConfigurationSection("Lojas Vazias");
		config.lojasVaziasName = vazias.getString("Name").replace('&', '§');
		config.lojasVaziasVaziaName = vazias.getString("Items.Vazia.Name").replace('&', '§');
		vazias.getStringList("Items.Vazia.Lore").forEach(msg -> config.lojasVaziasVaziaLore.add(msg.replace('&', '§')));
		
		ConfigurationSection lojas = menu.getConfigurationSection("Lojas");
		config.lojaName = lojas.getString("Name").replace('&', '§');
		config.lojaShopName = lojas.getString("Items.Shop.Name").replace('&', '§');
		lojas.getStringList("Items.Shop.Lore").forEach(msg -> config.lojaShopLore.add(msg.replace('&', '§')));
		return this;
	}
}
