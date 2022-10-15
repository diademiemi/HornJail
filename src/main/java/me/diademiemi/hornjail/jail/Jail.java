package me.diademiemi.hornjail.jail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.Map;

public class Jail implements ConfigurationSerializable {

    public static Jail jail;

    public Map<String, Object> serialize() {
        Map<String, Object> map = new java.util.HashMap<String, Object>();
        map.put("location", location);
        map.put("jailEnabled", jailEnabled);
        map.put("isWhitelist", isWhitelist);
        return map;
    }

    public Jail(Map<String, Object> map) {
        if (map.get("location") != null) {
            location = (Location) map.get("location");
        } else {
            location = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        }
        if (map.get("jailEnabled") != null) {
            jailEnabled = (boolean) map.get("jailEnabled");
        } else {
            jailEnabled = false;
        }
        if (map.get("isWhitelist") != null) {
            isWhitelist = (boolean) map.get("isWhitelist");
        } else {
            isWhitelist = false;
        }
    }

    public Jail(Location location, boolean jailEnabled, boolean isWhitelist) {
        this.location = location;
        this.jailEnabled = jailEnabled;
        this.isWhitelist = isWhitelist;
    }

    private Location location;

    private boolean jailEnabled;

    private boolean isWhitelist;

    public Location getLocation() {
        return location;
    }

    public static void setJail(Jail jail) {
        Jail.jail = jail;
    }

    public static Jail getJail() {
        return jail;
    }

    public static boolean toggleJail() {
        jail.jailEnabled = !jail.jailEnabled;
        return jail.jailEnabled;
    }

    public static boolean toggleWhitelist() {
        jail.isWhitelist = !jail.isWhitelist;
        return jail.isWhitelist;
    }

    public static void setLocation(Location location) {
        jail.location = location;
    }

    public static void setLocation(Player player) {
        jail.location = player.getLocation();
    }

    public static boolean isJailEnabled() {
        return jail.jailEnabled;
    }

    public static Boolean isWhitelist() {
        return jail.isWhitelist;
    }

}