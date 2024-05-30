package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RepairCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        Player player = (Player) sender;

        // Check if the player has the required permission
        if (!player.hasPermission("clirzcore.repair")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        // Check if the player provided any arguments
        if (args.length == 0) {
            repairHeldItem(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
            repairAllItems(player);
        } else {
            player.sendMessage("Usage: /repair [all]");
        }

        return true;
    }

    private void repairHeldItem(Player player) {
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem != null) {
            heldItem.setDurability((short) 0);
            player.sendMessage("Your held item has been repaired.");
        } else {
            player.sendMessage("You are not holding any item.");
        }
    }

    private void repairAllItems(Player player) {
        PlayerInventory inventory = player.getInventory();
        int repairedItems = 0;

        // Repair items in main inventory
        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                item.setDurability((short)0);
                repairedItems++;
            }
        }

        // Repair items in armor slots
        for (ItemStack item : inventory.getArmorContents()) {
            if (item != null) {
                item.setDurability((short)0);
                repairedItems++;
            }
        }

        player.sendMessage("Repaired " + repairedItems + " items.");
    }
}