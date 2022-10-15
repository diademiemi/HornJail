package me.diademiemi.hornjail.area;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Area implements ConfigurationSerializable {

    public Map<String, Object> serialize() {
        Map<String, Object> map = new java.util.HashMap<String, Object>();
        map.put("name", name);
        if (region != null) {
            Map<String, Object> regionMap = new HashMap<String, Object>();
            regionMap.put("minX", region.getMinimumPoint().getBlockX());
            regionMap.put("minY", region.getMinimumPoint().getBlockY());
            regionMap.put("minZ", region.getMinimumPoint().getBlockZ());
            regionMap.put("maxX", region.getMaximumPoint().getBlockX());
            regionMap.put("maxY", region.getMaximumPoint().getBlockY());
            regionMap.put("maxZ", region.getMaximumPoint().getBlockZ());
            regionMap.put("world", region.getWorld().getName());
            map.put("region", regionMap);
        } else {
            map.put("region", null);
        }
        return map;
    }

    public Area(Map<String, Object> map) {
        name = (String) map.get("name");
        if (map.get("region") != null) {
            region = new CuboidRegion(
                    BukkitAdapter.adapt(Bukkit.getWorld((String) ((Map<String, Object>) map.get("region")).get("world"))),
                    BlockVector3.at(
                            (int) ((Map<String, Object>) map.get("region")).get("minX"),
                            (int) ((Map<String, Object>) map.get("region")).get("minY"),
                            (int) ((Map<String, Object>) map.get("region")).get("minZ")
                    ),
                    BlockVector3.at(
                            (int) ((Map<String, Object>) map.get("region")).get("maxX"),
                            (int) ((Map<String, Object>) map.get("region")).get("maxY"),
                            (int) ((Map<String, Object>) map.get("region")).get("maxZ")
                    ));
        } else {
            region = null;
        }
    }

    private String name;

    private CuboidRegion region;

    public Area(String name, CuboidRegion region) {
        this.name = name;
        this.region = region;
        AreaList.addArea(this);
    }

    public String getName() {
        return name;
    }

    public CuboidRegion getRegion() {
        return region;
    }

}
