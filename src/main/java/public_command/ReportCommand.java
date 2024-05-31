package public_command;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportCommand implements CommandExecutor {
    private final File reportsFile;
    private final FileConfiguration reportsConfig;

    public ReportCommand(File dataFolder) {
        reportsFile = new File(dataFolder, "reports.yml");
        reportsConfig = YamlConfiguration.loadConfiguration(reportsFile);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("report")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Usage: /report <player> <reason>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                player.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online.");
                return true;
            }

            String reason = String.join(" ", args).substring(args[0].length() + 1);
            submitReport(player.getUniqueId(), targetPlayer.getUniqueId(), reason);
            player.sendMessage(ChatColor.GREEN + "Your report has been submitted.");

            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                if (onlineStaff.hasPermission("ClizCore.viewreports")) {
                    onlineStaff.sendMessage(ChatColor.YELLOW + player.getName() + " reported " + targetPlayer.getName() + ": " + reason);
                }
            }

            return true;
        }

        return false;
    }

    private void submitReport(UUID reporterId, UUID reportedId, String reason) {
        String reportKey = reporterId.toString() + "-" + reportedId.toString();
        List<String> reports = reportsConfig.getStringList("reports." + reportKey);
        if (reports == null) {
            reports = new ArrayList<>();
        }
        reports.add(reason);
        reportsConfig.set("reports." + reportKey, reports);
        saveReportsConfig();
    }

    private void saveReportsConfig() {
        try {
            reportsConfig.save(reportsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
