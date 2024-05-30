package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage("Usage: /gamemode <gamemode> ");
            return true;
        }

        String gamemodeString = args[0].toLowerCase();
        org.bukkit.GameMode gamemode;

        switch(gamemodeString) {
            case "1":
            case "c":
            case "creative":
                gamemode = org.bukkit.GameMode.CREATIVE;
                break;
            case "2":
            case "a":
            case "adventure":
                gamemode = org.bukkit.GameMode.ADVENTURE;
                break;
            case "3":
            case "s":
            case "survival":
                gamemode = org.bukkit.GameMode.SURVIVAL;
                break;
            default:
                player.sendMessage("Invalid gamemode. Use: 1, c, creative, 2, a, adventure, 3, s, or survival");
                return false;
        }
        player.setGameMode(gamemode);
        player.sendMessage("Your gamemode has been updated to " + gamemode.name().toLowerCase() + ".");
        return true;
    }
}
