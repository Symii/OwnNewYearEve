package me.symi.newyear.config;

import me.symi.newyear.OwnNewYearEve;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private final OwnNewYearEve plugin;
    private FileConfiguration config;
    private File config_file;

    public FileManager(OwnNewYearEve plugin)
    {
        this.plugin = plugin;
        createConfigs();
    }

    private void createConfigs()
    {

        config_file = new File(plugin.getDataFolder(), "data.yml");
        if(!config_file.exists())
        {
            config_file.getParentFile().mkdir();
            plugin.saveResource("data.yml", false);
        }

        config = new YamlConfiguration();
        reloadConfig();
    }

    public void reloadConfig()
    {
        try
        {
            config.load(config_file);
        }
        catch(IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig()
    {
        return config;
    }


}
