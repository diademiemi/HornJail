package me.diademiemi.hornjail.area;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;

import java.util.HashMap;

public class AreaList {

    public static HashMap<String, Area> areas = new HashMap<String, Area>();

    public static void addArea(Area area) {
        if (!areas.containsKey(area.getName())) {
            areas.put(area.getName(), area);
        } else {
            areas.replace(area.getName(), area);
        }
    }

    public static void removeArea(Area area) {
        areas.remove(area.getName());
    }

    public static void removeArea(String areaName) {
        areas.remove(areaName);
    }


    public static Area getArea(String name) {
        return areas.get(name);
    }

    public static HashMap<String, Area> getAreas() {
        return areas;
    }

    public static Area getAreaByRegion(CuboidRegion region, Area exclude) {
        // Check for overlapping regions
        for (Area area : areas.values()) {
            if ( area.getRegion() != null &&
                    area.getRegion().getMinimumPoint().getX() <= region.getMaximumPoint().getX() &&
                    area.getRegion().getMaximumPoint().getX() >= region.getMinimumPoint().getX() &&
                    area.getRegion().getMinimumPoint().getY() <= region.getMaximumPoint().getY() &&
                    area.getRegion().getMaximumPoint().getY() >= region.getMinimumPoint().getY() &&
                    area.getRegion().getMinimumPoint().getZ() <= region.getMaximumPoint().getZ() &&
                    area.getRegion().getMaximumPoint().getZ() >= region.getMinimumPoint().getZ()
            ) {
                if (exclude != null && area != exclude) {
                    return area;
                }
            }
        }
        return null;
    }

    public static Area getAreaByRegion(CuboidRegion region) {
        return getAreaByRegion(region, null);
    }

    public static Area getAreaByLocation(Location location) {
        // Check for overlapping regions
        for (Area area : areas.values()) {
            if (area.getRegion() != null && area.getRegion().contains(BukkitAdapter.asBlockVector(location))) {
                return area;
            }
        }
        return null;
    }
}
