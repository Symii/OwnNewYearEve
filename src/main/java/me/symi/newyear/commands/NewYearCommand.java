package me.symi.newyear.commands;

import me.symi.newyear.OwnNewYearEve;
import me.symi.newyear.utils.ChatUtil;
import me.symi.newyear.utils.FireworkUtil;
import me.symi.newyear.utils.XMaterial;
import me.symi.newyear.utils.XSound;
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
                        sender.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear start <time in seconds>"));
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
                                FireworkUtil.spawnFirework(location);
                            }

                            sec--;
                        }
                    }.runTaskTimer(plugin, 20L, 20L);
                    sender.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &eHAPPY NEW YEAR!"));
                    sender.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &eThe fireworks show has started for &c" + seconds + " seconds&e!"));
                }
                else
                {
                    sender.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear start <time in seconds>"));
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
                    player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &aplugin reloaded successfully"));
                    player.playSound(player.getLocation(), XSound.ENTITY_PLAYER_LEVELUP.parseSound(), 1.0f, 1.0f);
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
                    player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear start <time in seconds>"));
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
                    ItemStack rocket_gun = XMaterial.GOLDEN_HORSE_ARMOR.parseItem();
                    ItemMeta rocket_gun_meta = rocket_gun.getItemMeta();
                    rocket_gun_meta.setDisplayName(plugin.getConfigManager().getRocket_gun_name());
                    rocket_gun_meta.setLore(plugin.getConfigManager().getRocket_gun_lore());
                    rocket_gun.setItemMeta(rocket_gun_meta);
                    player.getInventory().addItem(rocket_gun);
                    player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &afirework gun has been added to your inventory."));
                    player.playSound(player.getLocation(), XSound.ENTITY_EXPERIENCE_ORB_PICKUP.parseSound(), 1.0f, 1.0f);
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
                player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear start <time in seconds>"));
                return true;
            }

            if(!player.hasPermission("ownnewyeareve.admin") && seconds > max_time_limit)
            {
                player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &ethe maximum time limit for this command is &c" + max_time_limit + " seconds"));
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
                        FireworkUtil.spawnFirework(location);
                    }

                    sec--;
                }
            }.runTaskTimer(plugin, 20L, 20L);
            player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &eHAPPY NEW YEAR!"));
            player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &eThe fireworks show has started for &c" + seconds + " seconds&e!"));
        }
        else
        {
            sendHelpMessages(player);
        }




        return false;
    }

    private void sendHelpMessages(CommandSender sender)
    {
        sender.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear reload &7- reload config.yml and data.yml file"));
        sender.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear start <time in seconds> &7- start firework event"));
        sender.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear getgun &7- get a firework rocket gun"));
        sender.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/setfirework &7- set location for firework rocket"));
    }

    private void sendNoPermissionMessage(CommandSender sender)
    {
        sender.sendMessage(ChatUtil.fixColors("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is a mistake."));
    }


}
