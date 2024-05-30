package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearCommand implements CommandExecutor {
@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
        sender.sendMessage("This command can only be executed by a player");
        return true;
    }
    Player player = (Player) sender;
        player.getInventory().clear();
        player.sendMessage("Your inventory has been cleared");
        return true;
}
}
