package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;
        if (player.hasPermission("clirzcore.cc")) {
            for (int i=0; i< 100; i++){
                sender.getServer().broadcastMessage("");
            }

        }
        else {
            player.sendMessage("You do not have permission to use this command");
        }
        sender.sendMessage("Chat Has Been Cleared for all players");
        return true;
    }
}
