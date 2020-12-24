package me.symi.newyear;

import org.bukkit.plugin.java.JavaPlugin;

public class OwnNewYearEve extends JavaPlugin {

    private static OwnNewYearEve INSTANCE;

    @Override
    public void onLoad()
    {
        INSTANCE = this;
    }

    @Override
    public void onEnable()
    {

    }

    @Override
    public void onDisable()
    {

    }

    public static OwnNewYearEve getInstance()
    {
        return INSTANCE;
    }

}
