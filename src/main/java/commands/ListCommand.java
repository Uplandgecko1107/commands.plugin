package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("list")) {
            if (!sender.hasPermission("clirzcore.list")){
                sender.sendMessage("You don't have permission to use this command!");
                return true;
            }
            StringBuilder onlinePlayers = new StringBuilder("online Players:");
            for (Player player : Bukkit.getOnlinePlayers()) {
                onlinePlayers.append(player.getName()).append(", ");
            }
            onlinePlayers.setLength(onlinePlayers.length() - 2);

            sender.sendMessage(onlinePlayers.toString());
            return true;
        }
        return false;
    }
}
