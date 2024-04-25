package commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class KitsCommand implements CommandExecutor, Listener {
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

            String kitPermission = config.getString("kits." + kitName + ".permission");
            if (kitPermission != null && !player.hasPermission(kitPermission)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use the '" + kitName + "' kit.");
                return true;
            }

            List<ItemStack> kitItems = (List<ItemStack>) config.getList("kits." + kitName + ".items");
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
            openKitCreationGUI(player, kitName);
            return true;
        }
        // ... (other command handling code)
        return false;
    }

    private void openKitCreationGUI(Player player, String kitName) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Create Kit: " + kitName);

        // Set armor slots
        for (int i = 0; i < 4; i++) {
            inventory.setItem(i, player.getInventory().getArmorContents()[i]);
        }

        // Set inventory slots
        for (int i = 9; i < 54; i++) {
            inventory.setItem(i, player.getInventory().getContents()[i - 9]);
        }

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().startsWith("Create Kit: ")) {
            return;
        }

        event.setCancelled(true);

        if (event.getCurrentItem() == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String kitName = event.getView().getTitle().replace("Create Kit: ", "");

        List<ItemStack> kitItems = new ArrayList<>();

        // Get armor items
        for (int i = 0; i < 4; i++) {
            ItemStack item = event.getInventory().getItem(i);
            if (item != null) {
                kitItems.add(item);
            }
        }

        // Get inventory items
        for (int i = 9; i < 54; i++) {
            ItemStack item = event.getInventory().getItem(i);
            if (item != null) {
                kitItems.add(item);
            }
        }

        config.set("kits." + kitName + ".items", kitItems);
        player.sendMessage(ChatColor.GREEN + "Kit '" + kitName + "' created successfully.");
        player.closeInventory();
    }
}