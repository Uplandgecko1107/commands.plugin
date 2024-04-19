package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fly  implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("clirzcore.fly")) {
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage("Flying Disabled");
            } else {
                player.setAllowFlight(true);
                player.sendMessage("Flying Enabled");
            }

            return true;

        }
        else {
            player.sendMessage("You do not have permission to use this command");
        }

    }
}
