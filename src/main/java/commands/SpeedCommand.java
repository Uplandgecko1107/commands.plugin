package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fly")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to use this command!");
                return true;
            }
            Player player = (Player) sender;
            if (player.hasPermission("clirzcore.speed")) {
                if (args.length != 1) {
                    player.sendMessage("usage /speed <value>");
                    return true;
                }
                float speed;
                try {
                    speed = Float.parseFloat(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage("Invalid speed value! Please enter a valid number.");
                    return true;

                }
                player.setWalkSpeed(speed);
                player.sendMessage("your speed is now " + speed);
            } else {
                player.sendMessage("You do not have permission to use this command");
                return true;
            }
            return true;
        }
       return false;
    }

}