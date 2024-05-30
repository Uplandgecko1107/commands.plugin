package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAllCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("tpall")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to use this command!");
                return true;
            }
            Player player = (Player) sender;

            if (!player.hasPermission("clirzcore.tpall")){
                player.sendMessage("You do not have permission to use this command!");
                return true;
            }
            for (Player target : player.getServer().getOnlinePlayers()) {
                if (target != player) {
                    target.teleport(player.getLocation());
                }
            }
            player.sendMessage("Teleported all players to your location.");
          return true;
        }
     return false;
    }
}
