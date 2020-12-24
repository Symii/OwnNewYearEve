package me.symi.newyear.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class ChatUtil {

    public static String fixColors(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> fixColors(List<String> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            list.set(i, fixColors(list.get(i)));
        }
        return list;
    }

}
