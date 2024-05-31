package public_command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {
    private final FileConfiguration config;

    public DiscordCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("discord") || command.getName().equalsIgnoreCase("dis")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            String discordInviteLink = config.getString("discord-invite-link", "");
            sender.sendMessage(ChatColor.GREEN + "Join our Discord server: " + discordInviteLink);
            return true;
        }

        return false;
    }
}
