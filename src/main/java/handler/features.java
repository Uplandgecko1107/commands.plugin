package handler;


import commands.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class features extends JavaPlugin {
private FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        config = getConfig();
        Bukkit.getLogger().info("plugin started");
getCommand("fly").setExecutor(new FlyCommand());
getCommand("ping").setExecutor(new PingCommand());
getCommand("heal").setExecutor(new HealCommand());
getCommand("clearchat").setExecutor(new ClearChatCommand());
getCommand("cc").setExecutor(new ClearChatCommand());
getCommand("speed").setExecutor(new SpeedCommand());
getCommand("repair").setExecutor(new RepairCommand());
getCommand("list").setExecutor(new ListCommand());
getCommand("vanish").setExecutor(new VanishCommand());
getCommand("build").setExecutor(new BuildCommand());
getCommand("inventorysee").setExecutor(new InventorySeeCommand());
getCommand("invsee").setExecutor(new InventorySeeCommand());
getCommand("teleport").setExecutor(new TeleportCommand());
getCommand("tp").setExecutor(new TeleportCommand());
getCommand("warp").setExecutor(new WarpCommand(config));
getCommand("setwarp").setExecutor(new WarpCommand(config));
getCommand("deletewarp").setExecutor(new WarpCommand(config));
getCommand("delwarp").setExecutor(new WarpCommand(config));
getCommand("warps").setExecutor(new WarpCommand(config));
getCommand("kit").setExecutor(new KitsCommand(config));
getCommand("createkit").setExecutor(new KitsCommand(config));
getCommand("deletekit").setExecutor(new KitsCommand(config));
getCommand("kits").setExecutor(new KitsCommand(config));
        getCommand("tpall").setExecutor(new TeleportAllCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Plugin disabled");

    }
}
