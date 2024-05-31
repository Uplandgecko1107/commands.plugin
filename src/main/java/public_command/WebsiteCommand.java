package public_command;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WebsiteCommand implements CommandExecutor {
    private final FileConfiguration config;

    public WebsiteCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("website") || command.getName().equalsIgnoreCase("web")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            String websiteUrl = config.getString("website-url", "");
            sender.sendMessage(ChatColor.GREEN + "Website: " + websiteUrl);
            return true;
        }

        return false;
    }
}
