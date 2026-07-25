package me.symi.newyear.commands;

import me.symi.newyear.OwnNewYearEve;
import me.symi.newyear.utils.FireworkUtil;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class NewYearCommand implements CommandExecutor {

    private final OwnNewYearEve plugin;

    public NewYearCommand(OwnNewYearEve plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player))
        {
            if(sender.hasPermission("ownnewyeareve.admin"))
            {
                if(args.length == 2 && args[0].equalsIgnoreCase("start"))
                {
                    final int seconds;
                    try
                    {
                        seconds = Integer.parseInt(args[1]);
                    }
                    catch(NumberFormatException exception)
                    {
                        sender.sendMessage(plugin.getConfigManager().getStart_usage());
                        return true;
                    }
                    new BukkitRunnable()
                    {
                        int sec = seconds;
                        @Override
                        public void run()
                        {
                            if(sec <= 0)
                            {
                                this.cancel();
                                return;
                            }

                            for(Location location : plugin.getLocationDataManager().getFirework_locations())
                            {
                                FireworkUtil.spawnFirework(location, plugin);
                            }

                            sec--;
                        }
                    }.runTaskTimer(plugin, 20L, 20L);
                    sender.sendMessage(plugin.getConfigManager().getHappy_new_year());
                    sender.sendMessage(plugin.getConfigManager().getFireworks_started().replace("{seconds}", String.valueOf(seconds)));
                }
                else
                {
                    sender.sendMessage(plugin.getConfigManager().getStart_usage());
                }

            }
            return true;
        }

        final Player player = (Player) sender;

        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("reload"))
            {
                if(player.hasPermission("ownnewyeareve.reload") || player.hasPermission("ownnewyeareve.admin"))
                {
                    plugin.getLocationDataManager().reload();
                    plugin.getConfigManager().loadConfig();
                    player.sendMessage(plugin.getConfigManager().getPlugin_reloaded());
                }
                else
                {
                    sendNoPermissionMessage(player);
                }
            }
            else if(args[0].equalsIgnoreCase("start"))
            {
                if(player.hasPermission("ownnewyeareve.start") || player.hasPermission("ownnewyeareve.admin"))
                {
                    player.sendMessage(plugin.getConfigManager().getStart_usage());
                }
                else
                {
                    sendNoPermissionMessage(player);
                }

            }
            else if(args[0].equalsIgnoreCase("getgun"))
            {
                if(player.hasPermission("ownnewyeareve.getgun") || player.hasPermission("ownnewyeareve.admin"))
                {
                    ItemStack rocket_gun = new ItemStack(plugin.getGoldenHorseArmor());
                    ItemMeta rocket_gun_meta = rocket_gun.getItemMeta();
                    rocket_gun_meta.setDisplayName(plugin.getConfigManager().getRocket_gun_name());
                    rocket_gun_meta.setLore(plugin.getConfigManager().getRocket_gun_lore());
                    rocket_gun.setItemMeta(rocket_gun_meta);
                    player.getInventory().addItem(rocket_gun);
                    player.sendMessage(plugin.getConfigManager().getFirework_gun_added());
                }
                else
                {
                    sendNoPermissionMessage(player);
                }

            }
            else
            {
                sendHelpMessages(player);
            }
        }
        else if(args.length == 2 && args[0].equalsIgnoreCase("start"))
        {
            if(!player.hasPermission("ownnewyeareve.start") && !player.hasPermission("ownnewyeareve.admin"))
            {
                sendNoPermissionMessage(player);
                return true;
            }
            final int max_time_limit = plugin.getConfigManager().getNewyear_start_max_time_limit();
            final int seconds;
            try
            {
                seconds = Integer.parseInt(args[1]);
            }
            catch(NumberFormatException exception)
            {
                player.sendMessage(plugin.getConfigManager().getStart_usage());
                return true;
            }

            if(!player.hasPermission("ownnewyeareve.admin") && seconds > max_time_limit)
            {
                player.sendMessage(plugin.getConfigManager().getMax_time_limit().replace("{limit}", String.valueOf(max_time_limit)));
                return true;
            }

            new BukkitRunnable()
            {
                int sec = seconds;
                @Override
                public void run()
                {
                    if(sec <= 0)
                    {
                        this.cancel();
                        return;
                    }

                    for(Location location : plugin.getLocationDataManager().getFirework_locations())
                    {
                        FireworkUtil.spawnFirework(location, plugin);
                    }

                    sec--;
                }
            }.runTaskTimer(plugin, 20L, 20L);
            player.sendMessage(plugin.getConfigManager().getHappy_new_year());
            player.sendMessage(plugin.getConfigManager().getFireworks_started().replace("{seconds}", String.valueOf(seconds)));
        }
        else
        {
            sendHelpMessages(player);
        }




        return false;
    }

    private void sendHelpMessages(CommandSender sender)
    {
        sender.sendMessage(plugin.getConfigManager().getHelp_reload());
        sender.sendMessage(plugin.getConfigManager().getHelp_start());
        sender.sendMessage(plugin.getConfigManager().getHelp_getgun());
        sender.sendMessage(plugin.getConfigManager().getHelp_setfirework());
    }

    private void sendNoPermissionMessage(CommandSender sender)
    {
        sender.sendMessage(plugin.getConfigManager().getNo_permission());
    }


}
