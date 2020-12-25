package me.symi.newyear.config;

import me.symi.newyear.OwnNewYearEve;
import me.symi.newyear.utils.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private final OwnNewYearEve plugin;
    private String rocket_gun_name;
    private List<String> rocket_gun_lore;
    private boolean metrics;

    public ConfigManager(OwnNewYearEve plugin)
    {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        loadConfig();
    }

    public void loadConfig()
    {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        rocket_gun_name = ChatUtil.fixColors(config.getString("firework-rocket-name"));
        rocket_gun_lore = ChatUtil.fixColors(config.getStringList("firework-rocket-lore"));
        metrics = config.getBoolean("plugin-metrics");
    }

    public String getRocket_gun_name()
    {
        return rocket_gun_name;
    }

    public List<String> getRocket_gun_lore()
    {
        return rocket_gun_lore;
    }

    public boolean isMetrics()
    {
        return metrics;
    }

}
