package me.symi.newyear.commands;

import me.symi.newyear.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetFireworkCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage("Hey! Sorry, but you can't execute this command.");
            return true;
        }

        final Player player = (Player) sender;
        if(player.hasPermission("ownnewyeareve.admin"))
        {

        }
        else
        {
            player.sendMessage(ChatUtil.fixColors("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is a mistake."));
        }

        return true;
    }
}
