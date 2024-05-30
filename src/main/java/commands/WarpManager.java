package commands;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WarpManager {
    private final File warpsFile;
    private final FileConfiguration warpsConfig;

    public WarpManager(JavaPlugin plugin) {
        warpsFile = new File(plugin.getDataFolder(), "warps.yml");
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);
    }

    public void saveWarps() {
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getWarpsConfig() {
        return warpsConfig;
    }

    public Location getWarpLocation(String warpName) {
        List<Double> locationData = warpsConfig.getDoubleList("warps." + warpName + ".location");
        return new Location(null, locationData.get(0), locationData.get(1), locationData.get(2));
    }

    public void setWarp(String warpName, Location warpLocation, String warpPermission) {
        warpsConfig.set("warps." + warpName + ".location", Arrays.asList(warpLocation.getX(), warpLocation.getY(), warpLocation.getZ()));
        if (warpPermission != null) {
            warpsConfig.set("warps." + warpName + ".permission", warpPermission);
        } else {
            warpsConfig.set("warps." + warpName + ".permission", null);
        }
    }

    public void deleteWarp(String warpName) {
        warpsConfig.set("warps." + warpName, null);
    }
}
