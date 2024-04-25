package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class KitsCommand implements CommandExecutor {
    private final FileConfiguration config;

    public KitsCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("kit")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /kit <kit>");
                return true;
            }

            String kitName = args[0].toLowerCase();
            if (!config.contains("kits." + kitName)) {
                player.sendMessage(ChatColor.RED + "Kit '" + kitName + "' does not exist.");
                return true;
            }

            List<ItemStack> kitItems = (List<ItemStack>) config.getList("kits." + kitName);
            for (ItemStack item : kitItems) {
                player.getInventory().addItem(item);
            }
            player.sendMessage(ChatColor.GREEN + "You have received the '" + kitName + "' kit.");
            return true;
        } else if (command.getName().equalsIgnoreCase("createkit")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("yourplugin.createkit")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to create kits.");
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /createkit <kit>");
                return true;
            }

            String kitName = args[0].toLowerCase();
            List<ItemStack> kitItems = new ArrayList<>(Arrays.asList(player.getInventory().getContents()));
            config.set("kits." + kitName, kitItems);
            player.sendMessage(ChatColor.GREEN + "Kit '" + kitName + "' created successfully.");
            return true;
        } else if (command.getName().equalsIgnoreCase("deletekit")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("yourplugin.deletekit")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to delete kits.");
                return true;
            }

            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "Usage: /deletekit <kit>");
                return true;
            }

            String kitName = args[0].toLowerCase();
            if (!config.contains("kits." + kitName)) {
                player.sendMessage(ChatColor.RED + "Kit '" + kitName + "' does not exist.");
                return true;
            }

            config.set("kits." + kitName, null);
            player.sendMessage(ChatColor.GREEN + "Kit '" + kitName + "' deleted successfully.");
            return true;
        } else if (command.getName().equalsIgnoreCase("kits")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            Set<String> kitNames = config.getConfigurationSection("kits").getKeys(false);
            if (kitNames.isEmpty()) {
                player.sendMessage(ChatColor.RED + "No kits have been created yet.");
                return true;
            }

            player.sendMessage(ChatColor.GREEN + "Available kits:");
            for (String kitName : kitNames) {
                player.sendMessage("- " + kitName);
            }

            return true;
        }

        return false;
    }
}
