package commands;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("build")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to use this command!");
                return true;
            }else {
                // If the command is not "/build", return false
                return false;
            }
        }
            Player player = (Player) sender;

        if (!player.hasPermission("clirzcore.build")){
            player.sendMessage("You do not have permission to use this command!");
            return true;
        }
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.ADVENTURE){
            player.setGameMode(GameMode.SURVIVAL);
            sender.sendMessage("You are now in survival mode and cannot build or break blocks!");
        }
             else{
                 player.setGameMode(GameMode.CREATIVE);
                 player.sendMessage("you are now in creative mode and can build or break blocks!");
        }
       return true;
    }

}
