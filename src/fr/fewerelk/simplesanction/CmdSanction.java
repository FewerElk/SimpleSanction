package fr.fewerelk.simplesanction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.BanList.Type;

public class CmdSanction implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //if(sender instanceof Player){
    	if(true){
            Player p = (Player) sender;
            if (p.isBanned()) {
            	
            } else {
            	p.kickPlayer("Banned for a good reason");
            	UUID playerId = p.getUniqueId();
            	BanList bl;
            	bl.getBanEntries(Type.NAME).addBan(playerId, "Banned because you are banned", null, p);
            }
        }
        return true;
    }
}