package me.diademiemi.hornjail;

import me.diademiemi.hornjail.area.Area;
import me.diademiemi.hornjail.command.CommandHandler;
import me.diademiemi.hornjail.jail.Jail;
import me.diademiemi.hornjail.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HornJail extends JavaPlugin {

    static {
        ConfigurationSerialization.registerClass(Area.class);
        ConfigurationSerialization.registerClass(Jail.class);
    }

    private static HornJail plugin;

    private static String pluginName = "HornJail";

    private static PluginManager pm;

    private static String[] permissions = new String[]{
        "help",
        "area.create",
        "area.delete",
        "togglejail",
        "setjail",
        "togglewhitelist",
    };

    public void onEnable() {
        plugin = this;


        Jail.setJail(new Jail(new Location(Bukkit.getWorld("world"), 0, 0, 0), false, false));
        pm = getServer().getPluginManager();

        for (String permission : permissions) {
            pm.addPermission(new org.bukkit.permissions.Permission(pluginName.toLowerCase() + "." + permission));
        }

        getCommand("hornjail").setExecutor(new CommandHandler());
        pm.registerEvents(new PlayerListener(), this);
    }

    public void onDisable() {
//        JailIO.writeConfig();
        plugin = null;
    }

    public static HornJail getPlugin() {
        return plugin;
    }

}
