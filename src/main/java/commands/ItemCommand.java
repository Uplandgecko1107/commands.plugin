package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Usage: /item <item_id> [amount] [data]");
            return false;
        }

        Player player = (Player) sender;
        int itemId;
        int amount = 1;
        short data = 0;

        try {
            itemId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid item ID specified.");
            return false;
        }

        if (args.length >= 2) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid amount specified.");
                return false;
            }
        }

        if (args.length >= 3) {
            try {
                data = Short.parseShort(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid data value specified.");
                return false;
            }
        }

        ItemStack itemStack = new ItemStack(itemId, amount, data);

        if (itemStack.getType() == null) {
            player.sendMessage("Invalid item ID specified.");
            return false;
        }

        player.getInventory().addItem(itemStack);
        player.sendMessage("You have received " + amount + " " + itemStack.getType().name().toLowerCase() + " (data: " + data + ")");
        return true;
    }
}