package commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportToggleCommand implements CommandExecutor {
    private static final Map<UUID, Boolean> tpToggleMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tptoggle")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            boolean canToggle = player.hasPermission("clirzcore.tptoggle");
            boolean isBypassAllowed = player.hasPermission("yourplugin.tptoggle.bypass");

            if (!canToggle && !isBypassAllowed) {
                player.sendMessage("You do not have permission to use this command.");
                return true;
            }

            boolean isAllowed = toggleTpToggle(player.getUniqueId(), isBypassAllowed);

            if (isAllowed) {
                player.sendMessage(ChatColor.GREEN + "You can now receive teleport requests from other players.");
            } else {
                player.sendMessage(ChatColor.RED + "You will not receive teleport requests from other players.");
            }

            return true;
        }

        return false;
    }

    private boolean toggleTpToggle(UUID playerId, boolean bypassAllowed) {
        boolean currentState = tpToggleMap.getOrDefault(playerId, true);
        boolean newState;

        if (bypassAllowed) {
            newState = true;
        } else {
            newState = !currentState;
        }

        tpToggleMap.put(playerId, newState);
        return newState;
    }

    public static boolean canReceiveTpRequest(Player player, Player requester) {
        boolean isRequesterStaff = requester.hasPermission("yourplugin.tptoggle.bypass");
        boolean isReceiversAllowed = tpToggleMap.getOrDefault(player.getUniqueId(), true);

        return isReceiversAllowed || isRequesterStaff;
    }
}