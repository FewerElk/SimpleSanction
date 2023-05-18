package fr.fewerelk.simplesanction;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

public class SimpleSanction extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Bukkit.getLogger().info(ChatColor.GREEN + "Enabling SimpleSanction...");
		this.getCommand("sanction").setExecutor(new CmdSanction());
		Bukkit.getLogger().info(ChatColor.GREEN + "SimpleSanction enabled !");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info(ChatColor.BLUE + "SimpleSanction disabled. Goodbye and see you soon !");
	}
	
}
