package handler;

import commands.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import public_command.ChatReportCommand;


import java.io.File;

public class CommandManager {
    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final File dataFolder;

    public CommandManager(JavaPlugin plugin, FileConfiguration config, File dataFolder) {
        this.plugin = plugin;
        this.config = config;
        this.dataFolder = dataFolder;
        registerCommands();
    }

    private void registerCommands() {
        // Register your commands here
        plugin.getCommand("gamemode").setExecutor(new GamemodeCommand());
        plugin.getCommand("clear").setExecutor(new ClearCommand());
        plugin.getCommand("give").setExecutor(new GiveCommand());
        plugin.getCommand("item").setExecutor(new ItemCommand());
        plugin.getCommand("day").setExecutor(new DayCommand());
        plugin.getCommand("night").setExecutor(new NightCommand());
        plugin.getCommand("time").setExecutor(new TimeCommand());
        plugin.getCommand("fly").setExecutor(new FlyCommand());
        plugin.getCommand("ping").setExecutor(new PingCommand());
        plugin.getCommand("heal").setExecutor(new HealCommand());
        plugin.getCommand("clearchat").setExecutor(new ClearChatCommand());
        plugin.getCommand("cc").setExecutor(new ClearChatCommand());
        plugin.getCommand("speed").setExecutor(new SpeedCommand());
        plugin.getCommand("repair").setExecutor(new RepairCommand());
        plugin.getCommand("list").setExecutor(new ListCommand());
        plugin.getCommand("vanish").setExecutor(new VanishCommand());
        plugin.getCommand("build").setExecutor(new BuildCommand());
        plugin.getCommand("inventorysee").setExecutor(new InventorySeeCommand());
        plugin.getCommand("invsee").setExecutor(new InventorySeeCommand());
        plugin.getCommand("teleport").setExecutor(new TeleportCommand());
        plugin.getCommand("tp").setExecutor(new TeleportCommand());
        plugin.getCommand("warp").setExecutor(new WarpCommand(plugin));
        plugin.getCommand("setwarp").setExecutor(new WarpCommand(plugin));
        plugin.getCommand("deletewarp").setExecutor(new WarpCommand(plugin));
        plugin.getCommand("delwarp").setExecutor(new WarpCommand(plugin));
        plugin.getCommand("warps").setExecutor(new WarpCommand(plugin));
        plugin.getCommand("kit").setExecutor(new KitsCommand(plugin));
        plugin.getCommand("createkit").setExecutor(new KitsCommand(plugin));
        plugin.getCommand("deletekit").setExecutor(new KitsCommand(plugin));
        plugin.getCommand("kits").setExecutor(new KitsCommand(plugin));
        plugin.getCommand("tpall").setExecutor(new TeleportAllCommand());
        plugin.getCommand("playtime").setExecutor(new PlaytimeCommand(plugin.getDataFolder()));
        plugin.getCommand("chatreport").setExecutor(new ChatReportCommand());
        plugin.getCommand("report").setExecutor(new ReportCommand(plugin.getDataFolder()));
        plugin.getCommand("website").setExecutor(new WebsiteCommand(config));
        plugin.getCommand("web").setExecutor(new WebsiteCommand(config));
        plugin.getCommand("discord").setExecutor(new DiscordCommand(config));
        plugin.getCommand("dis").setExecutor(new DiscordCommand(config));

        // Register any other commands you have
    }
}