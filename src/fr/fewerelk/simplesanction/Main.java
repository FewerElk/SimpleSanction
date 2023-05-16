package fr.fewerelk.simplesanction;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getCommand("sanction").setExecutor(new CmdSanction());
		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "SimpleSanction enabled without any issue !");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "SimpleSanction disabled. Goodbye and see you soon !");
	}
	
}
