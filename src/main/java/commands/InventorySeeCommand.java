package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventorySeeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
          if(cmd.getName().equalsIgnoreCase("inventorysee")) {
              if(!(sender instanceof Player)) {
                  sender.sendMessage("You must be a player to use this command!");
                  return true;
              }
              Player player = (Player) sender;

              if(!(player.hasPermission("clirzcore.invsee"))) {
                 player.sendMessage("You don't have permission to use this command!");
                 return true;
              }
              if (args.length <1) {
                  player.sendMessage("Usage: /inventorysee <player>");
                  return true;
              }
                 Player targetPlayer = Bukkit.getPlayer(args[0]);
              if (targetPlayer == null) {
                  player.sendMessage("Player not found!");
                  return true;
              }
                 Inventory targetinventory = targetPlayer.getInventory();
                 player.openInventory(targetinventory);
                 player.sendMessage("viewing" + targetPlayer.getName() + "'s inventory.");

                 return true;
          }
          return false;
    }
}
