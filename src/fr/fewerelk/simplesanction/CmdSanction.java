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
import java.io.IOException;
import java.io.PrintStream;

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
        File file = new File(player + ".txt");
        if (file.exists()) {
            try{
            Scanner scan = new Scanner(file);
            int sanc = scan.nextInt();
            scan.close();
            return sanc;} catch(IOException e) {}
        } else {
            return 0;
        }
        return 99999;
    }

    public static void upgradesanction(String player) {
        int level = findsanction(player);
        File file = new File(player + ".txt");
        level += 1;
        if (level == 1) {
            try {
                file.createNewFile();
            } catch (IOException e) {}
        }
        try {
            PrintStream out = new PrintStream(player + ".txt");
            out.print(level);
            out.close();
        } catch (FileNotFoundException e) {}
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
            diskScanner.close();

            return expires;
            
        } catch (FileNotFoundException e) {
            File f = new File("bantime.txt");
            try {
                f.createNewFile();
                PrintStream errorwriter = new PrintStream("bantime.txt");
                errorwriter.print("7 0 0 0");
                errorwriter.close();
            } catch(IOException e2) {
                Bukkit.getLogger().warning("Error : we met a weird exception. The plugin is disabled.");
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("SimpleSanction"));
                return new Date();
            }
            return getexpires();
        }
    }

    public static void banplayer(String p, String reason, String source) {
        //ban a player

        String r = ChatColor.DARK_RED + "You are banned by " + source + ". Reason : " + reason + ".";

        Date expires = getexpires();
        Bukkit.getBanList(BanList.Type.NAME).addBan(p, r, expires, source);
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

    public static void warn(Player p, String reason) {
        //warn a player
        p.sendMessage(ChatColor.RED + 
        "YOU RECEVEID A WARNING !\nPLEASE DON'T IGNORE IT !\nSOME SANCTION CAN BE USED !\nReason of the warning : " + 
        reason);
        Bukkit.broadcastMessage(ChatColor.RED + 
        "The player " + 
        p.getName() + 
        " received a warning ! Reason : " +
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

                    int nbsanc = findsanction(target);
                    
                    switch (nbsanc) {
                        case 0:
                            warn(Bukkit.getPlayer(target), args[1]);
                            upgradesanction(target);
                        case 1:
                            kickplayer(Bukkit.getPlayer(target), args[1]);
                            upgradesanction(target);
                        case 2:
                            banplayer(target, args[1], source.getName());
                            upgradesanction(target);
                        default:
                            Bukkit.getLogger().warning(ChatColor.RED + "This player is boring ! He have too many sanctions. Please use /ban <player> instead.");
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
                             
                banplayer(target, args[1], source);
            } else {
                Bukkit.getLogger().warning(ChatColor.DARK_RED + "[SimpleSanction] : Failed : bad number of arguments. Usage : /sanction <player> <reason>.");
                return true;
            }
            }
        return true;
    }
}