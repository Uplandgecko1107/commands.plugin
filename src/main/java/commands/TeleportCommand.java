package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("teleport") || command.getName().equalsIgnoreCase("tp")) {
            // Check if the sender is a player
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;
            if (!player.hasPermission("clirzcore.teleport.self")) {
                player.sendMessage("You do not have permission to teleport yourself.");
                return true;
            }
            if (args.length == 0) {
                player.sendMessage("Teleporting to your current location.");
                return true;
            }
            if (!player.hasPermission("yourplugin.teleport.others")) {
                player.sendMessage("You do not have permission to teleport other players.");
                return true;
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                player.sendMessage("Player " + args[0] + " is not online.");
                return true;
            }
            targetPlayer.teleport(player.getLocation());
            player.sendMessage("Teleported " + targetPlayer.getName() + " to your location.");
            targetPlayer.sendMessage("You have been teleported to " + player.getName() + "'s location.");
            return true;
        }
     return false;
    }
}
