package commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
public class WarpCommand implements CommandExecutor {
    private final FileConfiguration config;

    public WarpCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
            if (!config.contains("warps." + warpName)) {
                player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' does not exist.");
                return true;
            }

            List<Double> locationData = config.getDoubleList("warps." + warpName);
            Location warpLocation = new Location(player.getWorld(), locationData.get(0), locationData.get(1), locationData.get(2));
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
                player.sendMessage(ChatColor.RED + "Usage: /setwarp <warp>");
                return true;
            }

            String warpName = args[0].toLowerCase();
            Location warpLocation = player.getLocation();
            config.set("warps." + warpName, warpLocation);
            player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' created successfully.");
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
            if (!config.contains("warps." + warpName)) {
                player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' does not exist.");
                return true;
            }

            config.set("warps." + warpName, null);
            player.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' deleted successfully.");
            return true;
        } else if (command.getName().equalsIgnoreCase("warps")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            Set<String> warpNames = config.getConfigurationSection("warps").getKeys(false);
            if (warpNames.isEmpty()) {
                player.sendMessage(ChatColor.RED + "No warps have been created yet.");
                return true;
            }

            player.sendMessage(ChatColor.GREEN + "Available warps:");
            for (String warpName : warpNames) {
                player.sendMessage("- " + warpName);
            }

            return true;
        }

        return false;

    }
}
