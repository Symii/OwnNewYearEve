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
    private int newyear_start_max_time_limit;
    
    private String not_player;
    private String firework_location_added;
    private String no_permission;
    private String start_usage;
    private String happy_new_year;
    private String fireworks_started;
    private String plugin_reloaded;
    private String firework_gun_added;
    private String max_time_limit;
    private String help_reload;
    private String help_start;
    private String help_getgun;
    private String help_setfirework;

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
        newyear_start_max_time_limit = config.getInt("newyear-start-max-time-limit");
        
        not_player = ChatUtil.fixColors(config.getString("messages.not-player"));
        firework_location_added = ChatUtil.fixColors(config.getString("messages.firework-location-added"));
        no_permission = ChatUtil.fixColors(config.getString("messages.no-permission"));
        start_usage = ChatUtil.fixColors(config.getString("messages.start-usage"));
        happy_new_year = ChatUtil.fixColors(config.getString("messages.happy-new-year"));
        fireworks_started = ChatUtil.fixColors(config.getString("messages.fireworks-started"));
        plugin_reloaded = ChatUtil.fixColors(config.getString("messages.plugin-reloaded"));
        firework_gun_added = ChatUtil.fixColors(config.getString("messages.firework-gun-added"));
        max_time_limit = ChatUtil.fixColors(config.getString("messages.max-time-limit"));
        help_reload = ChatUtil.fixColors(config.getString("messages.help-reload"));
        help_start = ChatUtil.fixColors(config.getString("messages.help-start"));
        help_getgun = ChatUtil.fixColors(config.getString("messages.help-getgun"));
        help_setfirework = ChatUtil.fixColors(config.getString("messages.help-setfirework"));
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

    public int getNewyear_start_max_time_limit()
    {
        return newyear_start_max_time_limit;
    }
    
    public String getNot_player()
    {
        return not_player;
    }
    
    public String getFirework_location_added()
    {
        return firework_location_added;
    }
    
    public String getNo_permission()
    {
        return no_permission;
    }
    
    public String getStart_usage()
    {
        return start_usage;
    }
    
    public String getHappy_new_year()
    {
        return happy_new_year;
    }
    
    public String getFireworks_started()
    {
        return fireworks_started;
    }
    
    public String getPlugin_reloaded()
    {
        return plugin_reloaded;
    }
    
    public String getFirework_gun_added()
    {
        return firework_gun_added;
    }
    
    public String getMax_time_limit()
    {
        return max_time_limit;
    }
    
    public String getHelp_reload()
    {
        return help_reload;
    }
    
    public String getHelp_start()
    {
        return help_start;
    }
    
    public String getHelp_getgun()
    {
        return help_getgun;
    }
    
    public String getHelp_setfirework()
    {
        return help_setfirework;
    }
}
