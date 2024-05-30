package commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class KitManager {
    private final File kitsFile;
    private final FileConfiguration kitsConfig;

    public KitManager(JavaPlugin plugin) {
        kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        kitsConfig = YamlConfiguration.loadConfiguration(kitsFile);
    }

    public void saveKits() {
        try {
            kitsConfig.save(kitsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean kitExists(String kitName) {
        return kitsConfig.contains("kits." + kitName);
    }

    public String getKitPermission(String kitName) {
        return kitsConfig.getString("kits." + kitName + ".permission");
    }

    public void giveKit(Player player, String kitName) {
        ItemStack[] kitItems = kitsConfig.getList("kits." + kitName + ".items").toArray(new ItemStack[0]);
        for (ItemStack item : kitItems) {
            if (item != null) {
                player.getInventory().addItem(item);
            }
        }
    }

    public void createKit(String kitName, ItemStack[] kitItems) {
        kitsConfig.set("kits." + kitName + ".items", Arrays.asList(kitItems));
    }
}
