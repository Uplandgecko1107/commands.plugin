package commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;
        for (World world : player.getServer().getWorlds()) {
            long time = world.getTime();
            long newTime = (time / 24000) * 24000;
            world.setTime(newTime);
        }

        player.sendMessage("Time set to day in all worlds.");
        return true;
    }
    }

