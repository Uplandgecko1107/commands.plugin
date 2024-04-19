package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ping implements CommandExecutor {
@Override

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
        sender.sendMessage("This command can only be executed by a player");
        return true;
    }
    Player player = (Player) sender;

      int ping = getPing(player)
    player.sendMessage("your ping:"+ ping + "ms");
      return true;
}
private int getPing(Player player) {
    try{
        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
        Object connection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
        ping = (int) connection.getClass().getMethod("getPlayerPing").invoke(connection);

    } catch (Exception e){
         e.printStackTrace();
    }
    return ping;
}
}
