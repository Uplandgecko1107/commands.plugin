package public_command;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatReportCommand implements CommandExecutor {
    private static final List<String> chatLog = new ArrayList<>();
    private static final int MAX_LOG_SIZE = 100; // Adjust this value as needed

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("chatreport")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("yourplugin.chatreport")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }

            if (chatLog.isEmpty()) {
                player.sendMessage(ChatColor.RED + "No chat messages to report.");
                return true;
            }

            player.sendMessage(ChatColor.GREEN + "Recent chat messages:");
            for (String message : chatLog) {
                player.sendMessage(message);
            }

            return true;
        }

        return false;
    }

    public static void logChatMessage(String message) {
        chatLog.add(message);
        if (chatLog.size() > MAX_LOG_SIZE) {
            chatLog.remove(0); // Remove the oldest message
        }
    }
}
