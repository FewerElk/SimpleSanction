package fr.fewerelk.simplesanction;

//spigot imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.BanList;
import org.bukkit.ChatColor;

//java imports
import java.util.Date;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class CmdSanction implements CommandExecutor {
    //the class for the /sanction command

    public static void kickplayer(Player p, String reason) {
        //kick a player
        p.kickPlayer(reason);
        Bukkit.getServer().broadcastMessage(ChatColor.RED + 
        "The player " + 
        p.getName() + 
        " was kicked : " + 
        ChatColor.YELLOW + 
        reason);

        Bukkit.getLogger().info(ChatColor.RED + 
        "The player " + 
        p.getName() + 
        " was kicked : " + 
        ChatColor.YELLOW + 
        reason);
    }

    public static int findsanction(String player) {
        return 0;
    }

    public static Date getexpires() {
        //give the expires date
        //read the file
        try {
            Scanner diskScanner = new Scanner(new File("bantime.txt"));
            

            //get the expires date
            //days
            int days = diskScanner.nextInt();
            int milli_days = days * 24 * 3600 * 1000;

            //hours
            int hours = diskScanner.nextInt();
            int milli_hours = hours * 3600 * 100;
            //minutes
            int minutes = diskScanner.nextInt();
            int milli_minutes = minutes * 60 * 100;
            //seconds
            int seconds = diskScanner.nextInt();
            int milli_seconds = seconds * 100;
            //total
            int bantime = milli_days + milli_hours + milli_minutes + milli_seconds;
            //creating Date instances
            Date date_now = new Date();
            Date expires = new Date(date_now.getTime() + bantime);
            return expires;
            
        } catch (FileNotFoundException e) {
            Bukkit.getLogger().warning("Error : the file bantime.txt can't be find (FileNot FoundException). Disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("SimpleSanction"));
            return new Date();
        }
    }

    public static void banplayer(String p, String reason, String source) {
        //ban a player

        Date expires = getexpires();
        Bukkit.getBanList(BanList.Type.NAME).addBan(p, reason, expires, source);
        kickplayer(Bukkit.getPlayer(p), reason);
        Bukkit.getServer().broadcastMessage(ChatColor.RED + 
        "The player " + 
        p + 
        " was banned by " + 
        source +
        " : " + 
        ChatColor.YELLOW + 
        reason + 
        ".");

        Bukkit.getLogger().info(ChatColor.RED + 
        "The player " + 
        p + 
        " was banned by " + 
        source +
        " : " + 
        ChatColor.YELLOW + 
        reason + 
        ".");
    }

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
                    
                    //get the reason
                    String reason = ChatColor.DARK_RED + "You are banned by " + source.getDisplayName() + ". Reason : " + args[1] + ".";

                    banplayer(target, reason, source.getName());
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
             
                //get the reason
                String reason = ChatColor.DARK_RED + "You are banned by" + source + ". Reason : " + args[1] + ".";
                
                banplayer(target, reason, source);
            } else {
                Bukkit.getLogger().warning(ChatColor.DARK_RED + "[SimpleSanction] : Failed : bad number of arguments. Usage : /sanction <player> <reason>.");
                return true;
            }
            }
        return true;
    }
}