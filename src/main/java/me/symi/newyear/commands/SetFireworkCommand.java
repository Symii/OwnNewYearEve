package me.symi.newyear.commands;

import me.symi.newyear.OwnNewYearEve;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetFireworkCommand implements CommandExecutor {

    private final OwnNewYearEve plugin;

    public SetFireworkCommand(OwnNewYearEve plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage(plugin.getConfigManager().getNot_player());
            return true;
        }

        final Player player = (Player) sender;
        if(player.hasPermission("ownnewyeareve.admin") || player.hasPermission("ownnewyeareve.setfirework"))
        {
            plugin.getLocationDataManager().addLocation(player.getLocation());
            player.sendMessage(plugin.getConfigManager().getFirework_location_added());
        }
        else
        {
            player.sendMessage(plugin.getConfigManager().getNo_permission());
        }

        return true;
    }
}
