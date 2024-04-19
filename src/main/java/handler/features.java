package handler;


import commands.fly;
import commands.heal;
import commands.ping;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class features extends JavaPlugin {
    private static features instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
instance = this;
        Bukkit.getLogger().info("plugin started");
getCommand("fly").setExecutor(new fly());
getCommand("ping").setExecutor(new ping());
getCommand("heal").setExecutor(new heal());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Plugin disabled");
        public static features getInstance(){
            return  instance;
        }
    }
}
