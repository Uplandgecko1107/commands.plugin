package handler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import commands.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import public_command.*;

public final class features extends JavaPlugin {
private FileConfiguration config;

    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        CommandManager commandManager = new CommandManager(this, config, getDataFolder());
        saveDefaultConfig();

        config = getConfig();
        Bukkit.getLogger().info("plugin started");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Plugin disabled");

    }
}
