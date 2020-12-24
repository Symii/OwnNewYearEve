package me.symi.newyear.manager;

import me.symi.newyear.OwnNewYearEve;
import me.symi.newyear.config.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LocationDataManager {

    private final OwnNewYearEve plugin;
    private ArrayList<Location> firework_locations = new ArrayList<>();
    private FileManager fileManager;
    private int counter;

    public LocationDataManager(OwnNewYearEve plugin)
    {
        this.plugin = plugin;
        fileManager = new FileManager(plugin);
        counter = fileManager.getConfig().getInt("counter");
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
        {
            Bukkit.getLogger().info("[OwnNewYearEve] Loading firework locations...");
            try
            {
                reload();
            }
            catch(NullPointerException ignored) { }
        }, 20L);
    }

    public ArrayList<Location> getFirework_locations()
    {
        return firework_locations;
    }

    public void addLocation(Location location)
    {
        firework_locations.add(location);
        save();
    }

    public void save()
    {
        fileManager.getConfig().set("counter", 0);
        fileManager.getConfig().set("fireworks", null);
        counter = 0;
        for(Location location : firework_locations)
        {
            fileManager.getConfig().set("fireworks." + counter + ".x", location.getBlockX());
            fileManager.getConfig().set("fireworks." + counter + ".y", location.getBlockY());
            fileManager.getConfig().set("fireworks." + counter + ".z", location.getBlockZ());
            fileManager.getConfig().set("fireworks." + counter + ".world", location.getWorld().getName());
            counter++;
            fileManager.getConfig().set("counter", counter);
        }
        try {
            fileManager.getConfig().save(new File(plugin.getDataFolder(), "data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLocation(Location location)
    {
        for(Location loc : firework_locations)
        {
            if(loc.getBlockX() == location.getBlockX()
                && loc.getBlockY() == location.getBlockY()
                && loc.getBlockZ() == location.getBlockZ()
                && loc.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()))
            {
                firework_locations.remove(loc);
                break;
            }
        }
    }

    public void onDisable()
    {
        save();
        firework_locations.clear();
    }

    public void reload()
    {
        firework_locations.clear();
        for(String key : fileManager.getConfig().getConfigurationSection("fireworks").getKeys(false))
        {
            Location location = new Location(Bukkit.getWorld(fileManager.getConfig().getString("fireworks." + key + ".world")),
                    fileManager.getConfig().getInt("fireworks." + key + ".x"),
                    fileManager.getConfig().getInt("fireworks." + key + ".y"),
                    fileManager.getConfig().getInt("fireworks." + key + ".z"));
            firework_locations.add(location);
        }
        Bukkit.getLogger().info("[OwnNewYearEve] Fireworks locations loaded successfully!");
    }

}
