package fr.fewerelk.simplesanction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.BanList;
import org.bukkit.ChatColor;

import java.util.Date;

public class CmdSanction implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            //get sender
            Player source = (Player) sender;

            //does the sender allowed to use the command ?
            if(source.hasPermission("simplesanction.sanction")) {
                //good number of args ?
                if(args.length == 2) {
                    //get the target
                    Player target = Bukkit.getPlayer(args[0]);


                    //create the Date object that define when the ban expires (here 7 days)
                    int bantime = 604800000;
                    Date date_now = new Date();
                    Date expires = new Date(date_now.getTime() + bantime);

                    
                    //get the reason
                    String reason = ChatColor.DARK_RED + "You are banned by" + source.getDisplayName() + ". Reason : " + args[1] + ".Expires on : " + expires;

                    //add the ban to the blacklist (or banlist)
                    Bukkit.getBanList(BanList.Type.IP).addBan(target.getAddress().getHostString(), reason, expires, source.getName());
                    Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "The player " + target.getDisplayName() + " has been banned !" + ChatColor.YELLOW + " Be carefull !");
                    Bukkit.getLogger().info(ChatColor.DARK_RED + "The player " + target.getDisplayName() + " has been banned by " + source.getName() + " !");
                    //kick the player (todo)
                } else {
                    source.sendMessage(ChatColor.DARK_RED + "[SimpleSanction] : Failed : bad number of arguments. Usage : /sanction <player> <reason>.");
                    return true;
                }
            }
        } else {
            String source = "Console";
            if(args.length == 2) {
                //get the target
                Player target = Bukkit.getPlayer(args[0]);


                //create the Date object that define when the ban expires (here 7 days)
                int bantime = 604800000;
                Date date_now = new Date();
                Date expires = new Date(date_now.getTime() + bantime);

                
                //get the reason
                String reason = ChatColor.DARK_RED + "You are banned by" + source + ". Reason : " + args[1] + ".Expires on : " + expires;

                //add the ban to the blacklist (or banlist)
                Bukkit.getBanList(BanList.Type.IP).addBan(target.getAddress().getHostString(), reason, expires, source);
                Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "The player " + target.getDisplayName() + " has been banned !" + ChatColor.YELLOW + " Be carefull !");
                Bukkit.getLogger().info(ChatColor.DARK_RED + "You have succesfully banned " + target.getName() + " !");
                //kick the player (todo)
            } else {
                Bukkit.getLogger().warning(ChatColor.DARK_RED + "[SimpleSanction] : Failed : bad number of arguments. Usage : /sanction <player> <reason>.");
                return true;
            }
            }
        return true;
    }
}