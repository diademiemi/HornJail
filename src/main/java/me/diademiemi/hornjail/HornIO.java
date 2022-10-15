package me.diademiemi.hornjail;

import me.diademiemi.hornjail.area.Area;
import me.diademiemi.hornjail.area.AreaList;
import me.diademiemi.hornjail.jail.Jail;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HornIO {

    private static File configFile;

    private static YamlConfiguration config;

    public static void init() {
        configFile = new File(HornJail.getPlugin().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            HornJail.getPlugin().saveResource("config.yml", false);
        }
    }

    public static void writeConfig() {
        config.set("areas", AreaList.getAreas());
        config.set("jail", Jail.getJail());
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        try {
            for (String key : config.getConfigurationSection("areas").getKeys(false)) {
                AreaList.addArea((Area) config.get("areas." + key));
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error loading areas from config.yml");
        }
        if (config.get("jail") != null) {
            Jail.setJail((Jail) config.get("jail"));
        } else {
            Jail.setJail(new Jail(new Location(Bukkit.getWorld("world"), 0, 0, 0), false, false));
        }

    }

}
