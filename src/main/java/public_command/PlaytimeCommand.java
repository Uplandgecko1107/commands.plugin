package public_command;



import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlaytimeCommand implements CommandExecutor {
    private final File playtimeFile;
    private final FileConfiguration playtimeConfig;

    public PlaytimeCommand(File dataFolder) {
        playtimeFile = new File(dataFolder, "playtime.yml");
        playtimeConfig = YamlConfiguration.loadConfiguration(playtimeFile);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("playtime")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length == 0) {
                long playtime = getPlaytime(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Your playtime is " + formatPlaytime(playtime));
            } else if (args.length == 1 && player.hasPermission("ClizCore.playtime.others")) {
                Player targetPlayer = player.getServer().getPlayer(args[0]);
                if (targetPlayer == null) {
                    player.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online.");
                    return true;
                }

                long playtime = getPlaytime(targetPlayer.getUniqueId());
                player.sendMessage(ChatColor.GREEN + targetPlayer.getName() + "'s playtime is " + formatPlaytime(playtime));
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /playtime [player]");
            }

            return true;
        }

        return false;
    }

    private long getPlaytime(UUID uuid) {
        return playtimeConfig.getLong("playtime." + uuid.toString(), 0);
    }

    private void setPlaytime(UUID uuid, long playtime) {
        playtimeConfig.set("playtime." + uuid.toString(), playtime);
        savePlaytimeConfig();
    }

    private void savePlaytimeConfig() {
        try {
            playtimeConfig.save(playtimeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatPlaytime(long playtime) {
        long seconds = playtime / 1000;
        long hours = seconds / 3600;
        seconds %= 3600;
        long minutes = seconds / 60;
        seconds %= 60;

        return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
    }
}
