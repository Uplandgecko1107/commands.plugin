package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        int ping = getPing(player);
        player.sendMessage("Your ping: " + ping + "ms");
        return true;
    }

    private int getPing(Player player) {
        int ping = -1; // Declare and initialize the ping variable

        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object connection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            ping = (int) connection.getClass().getMethod("getPlayerPing").invoke(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ping;
    }
}