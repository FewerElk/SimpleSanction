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
                    String target = args[0];

                    //create the Date object that define when the ban expires (here 7 days)
                    //days
                    int days = 7;
                    int milli_days = days * 24 * 3600 * 1000;

                    //hours
                    int hours = 0;
                    int milli_hours = hours * 3600 * 1000;

                    //minutes
                    int minutes = 0;
                    int milli_minutes = minutes * 60 * 1000;

                    //seconds
                    int seconds = 0;
                    int milli_seconds = seconds * 1000;

                    //total
                    int bantime = milli_days + milli_hours + milli_minutes + milli_seconds;

                    //creating Date instances
                    Date date_now = new Date();
                    Date expires = new Date(date_now.getTime() + bantime);

                    
                    //get the reason
                    String reason = ChatColor.DARK_RED + "You are banned by " + source.getDisplayName() + ". Reason : " + args[1] + ". Expires on : " + expires;

                    //add the ban to the blacklist (or banlist)
                    Bukkit.getBanList(BanList.Type.NAME).addBan(target, reason, expires, source.getName());
                    Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "The player " + target + " has been banned !" + ChatColor.YELLOW + " Be carefull !");
                    Bukkit.getLogger().info(ChatColor.DARK_RED + "The player " + target + " has been banned by " + source.getName() + " !");

                    //kick the player if he is online
                    //check if he is online
                    Player t = Bukkit.getPlayer(target);
                    if (t == null){
                        //do nothing because he isn't online.
                        //kick the player isn't needed.
                    } else {
                        //the player is online. Kick him is necessary.
                        t.kickPlayer(reason);
                    }
                } else {
                    source.sendMessage(ChatColor.DARK_RED + "[SimpleSanction] : Failed : bad number of arguments. Usage : /sanction <player> <reason>.");
                    return true;
                }
            }
        } else {
            String source = "Console";
            if(args.length == 2) {
                //get the target
                String target = args[0];


                //create the Date object that define when the ban expires (here 7 days)
                    //days
                    int days = 7;
                    int milli_days = days * 24 * 3600 * 1000;

                    //hours
                    int hours = 0;
                    int milli_hours = hours * 3600 * 1000;

                    //minutes
                    int minutes = 0;
                    int milli_minutes = minutes * 60 * 1000;

                    //seconds
                    int seconds = 0;
                    int milli_seconds = seconds * 1000;

                    //total
                    int bantime = milli_days + milli_hours + milli_minutes + milli_seconds;

                    //creating Date instances
                    Date date_now = new Date();
                    Date expires = new Date(date_now.getTime() + bantime);

                
                //get the reason
                String reason = ChatColor.DARK_RED + "You are banned by" + source + ". Reason : " + args[1] + ".Expires on : " + expires;

                //add the ban to the blacklist (or banlist)
                Bukkit.getBanList(BanList.Type.NAME).addBan(target, reason, expires, source);
                Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "The player " + target + " has been banned !" + ChatColor.YELLOW + " Be carefull !");
                Bukkit.getLogger().info(ChatColor.DARK_RED + "You have succesfully banned " + target + " !");
                //kick the player (todo)
            } else {
                Bukkit.getLogger().warning(ChatColor.DARK_RED + "[SimpleSanction] : Failed : bad number of arguments. Usage : /sanction <player> <reason>.");
                return true;
            }
            }
        return true;
    }
}