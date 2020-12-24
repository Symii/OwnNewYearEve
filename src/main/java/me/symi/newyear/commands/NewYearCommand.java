package me.symi.newyear.commands;

import me.symi.newyear.OwnNewYearEve;
import me.symi.newyear.utils.ChatUtil;
import me.symi.newyear.utils.FireworkUtil;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
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
            return true;
        }

        final Player player = (Player) sender;
        if(player.hasPermission("ownnewyeareve.admin"))
        {
            if(args.length == 1)
            {
                if(args[0].equalsIgnoreCase("reload"))
                {
                    plugin.getLocationDataManager().reload();
                    player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &aplugin reloaded successfully"));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                }
                else if(args[0].equalsIgnoreCase("start"))
                {
                    player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear start <time in seconds>"));
                }
                else if(args[0].equalsIgnoreCase("getgun"))
                {
                    ItemStack rocket_gun = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
                    ItemMeta rocket_gun_meta = rocket_gun.getItemMeta();
                    rocket_gun_meta.setDisplayName(plugin.getConfigManager().getRocket_gun_name());
                    rocket_gun_meta.setLore(plugin.getConfigManager().getRocket_gun_lore());
                    rocket_gun.setItemMeta(rocket_gun_meta);
                    player.getInventory().addItem(rocket_gun);
                    player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &afirework gun has been added to your inventory."));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear <reload;start;getgun>"));
                }
            }
            else if(args.length == 2 && args[0].equalsIgnoreCase("start"))
            {
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
                player.sendMessage(ChatUtil.fixColors("&6&lOwnNewYearEve &8» &c/newyear <reload;start;getgun>"));
            }
        }
        else
        {
            player.sendMessage(ChatUtil.fixColors("&cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is a mistake."));
        }

        return false;
    }

}
