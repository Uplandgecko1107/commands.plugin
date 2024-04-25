package commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand implements CommandExecutor {
    private static final List<Player> vanishedPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.getName().equalsIgnoreCase("vanish")) {
            // Check if the sender is a player
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("clirzcore.vanish")) {
                player.sendMessage("You do not have permission to use this command.");
                return true;
            }
            if (!vanishedPlayers.contains(player)) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.showPlayer(player);
                }
                      vanishedPlayers.remove(player);
                      player.sendMessage("You are now visible to other players.");

            }
            else {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.hidePlayer(player);
                }
                    vanishedPlayers.add(player);
                    player.sendMessage("You are now vanished and invisible to other players.");

            }
            return true;
        }
        return false;
    }
}
