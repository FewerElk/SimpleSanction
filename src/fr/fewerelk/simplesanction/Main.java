package fr.fewerelk.simplesanction;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Main extends JavaPlugin {
	
	@Override
	public void OnEnable() {
		this.getCommand("manger").setExecutor(new CmdSanction());
		System.out.println("SimpleSanction enabled succesfully !");
	}
	
	@Override
	public void OnDisable() {
		System.out.println("SimpleSanction disabled succesfully. Goodbye ! :-(");
	}
	
}
