package commands;

import handler.msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class heal implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
        msg.send(CommandSender,"&cOnly Player Can Use This Command");

        return true;
    }
    Player player = (Player) CommandSender;
        if (player.hasPermission("clirzcore.fly")) {
            if (args.length < 1) {
                player.sendMessage("usage: /heal <amount>");
                return true;
            }
            int amount;
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid amount! Please enter a valid number.");
                return true;
            }

            double maxHealth = player.getMaxHealth();
            if (maxHealth <= 0) {
                player.sendMessage("Invalid amount! Please enter a valid number.");
            } else if (amount > maxHealth) {
                player.sendMessage("You cannot  heal more than your maximum health.");
            } else {
                player.setHealth(amount);
                player.setFoodLevel(20);
            }

            return true;
        }
    }
}
