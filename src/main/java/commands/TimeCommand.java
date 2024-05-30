package commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Usage: /time set <ticks>");
            return false;
        }

        Player player = (Player) sender;
        World world = player.getWorld();
        long ticks;

        try {
            ticks = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid time specified. Please provide the time in ticks.");
            return false;
        }

        world.setTime(ticks);
        player.sendMessage("Time set to " + ticks + " ticks.");
        return true;
    }
}

