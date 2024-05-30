package commands;



import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class WarpCommand implements CommandExecutor {
    private final WarpManager warpManager;

    public WarpCommand(JavaPlugin plugin) {
        this.warpManager = new WarpManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration warpsConfig = warpManager.getWarpsConfig();

        if (command.getName().equalsIgnoreCase("warp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /warp <warp>");
                return true;
            }

            String warpName = args[0].toLowerCase();
            if (!warpsConfig.contains("warps." + warpName)) {
                player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' does not exist.");
                return true;
            }

            String warpPermission = warpsConfig.getString("warps." + warpName + ".permission");
            if (warpPermission != null && !player.hasPermission(warpPermission)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use the '" + warpName + "' warp.");
                return true;
            }

            Location warpLocation = warpManager.getWarpLocation(warpName);
            player.teleport(warpLocation);
            player.sendMessage(ChatColor.GREEN + "Teleported to warp '" + warpName + "'.");
            return true;
        } else if (command.getName().equalsIgnoreCase("setwarp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("yourplugin.setwarp")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to create warps.");
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /setwarp <warp> [permission]");
                return true;
            }

            String warpName = args[0].toLowerCase();
            Location warpLocation = player.getLocation();

            String warpPermission = null;
            if (args.length > 1) {
                warpPermission = args[1];
            }

            warpManager.setWarp(warpName, warpLocation, warpPermission);
            player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' created successfully.");
            warpManager.saveWarps();
            return true;
        } else if (command.getName().equalsIgnoreCase("deletewarp") || command.getName().equalsIgnoreCase("delwarp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("yourplugin.deletewarp")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to delete warps.");
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /deletewarp <warp> or /delwarp <warp>");
                return true;
            }

            String warpName = args[0].toLowerCase();
            if (!warpsConfig.contains("warps." + warpName)) {
                player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' does not exist.");
                return true;
            }

            warpManager.deleteWarp(warpName);
            player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' deleted successfully.");
            warpManager.saveWarps();
            return true;
        } else if (command.getName().equalsIgnoreCase("warps")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            Set<String> warpNames = warpsConfig.getConfigurationSection("warps").getKeys(false);
            if (warpNames.isEmpty()) {
                player.sendMessage(ChatColor.RED + "No warps have been created yet.");
                return true;
            }

            player.sendMessage(ChatColor.GREEN + "Available warps:");
            for (String warpName : warpNames) {
                String warpPermission = warpsConfig.getString("warps." + warpName + ".permission");
                if (warpPermission != null) {
                    player.sendMessage("- " + warpName + " (Permission: " + warpPermission + ")");
                } else {
                    player.sendMessage("- " + warpName);
                }
            }

            return true;
        }

        return false;
    }
}