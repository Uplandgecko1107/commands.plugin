package commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaCommand implements CommandExecutor {
    private static final Map<UUID, Long> cooldownMap = new HashMap<>();
    private static final long COOLDOWN_DURATION = 60000; // 60 seconds in milliseconds

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpa")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("clirzcore.tpa")) {
                player.sendMessage("You do not have permission to use this command.");
                return true;
            }

            if (args.length < 1) {
                player.sendMessage("Usage: /tpa <player>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null) {
                player.sendMessage("Player " + args[0] + " is not online.");
                return true;
            }

            long remainingCooldown = getRemainingCooldown(player.getUniqueId());
            if (remainingCooldown > 0) {
                player.sendMessage(ChatColor.RED + "You must wait " + formatTimeRemaining(remainingCooldown) + " before sending another teleport request.");
                return true;
            }

            sendTeleportRequest(player, targetPlayer);

            return true;
        }

        return false;
    }

    private void sendTeleportRequest(Player sender, Player target) {
        sender.sendMessage(ChatColor.YELLOW + "Teleport request sent to " + target.getName() + ".");

        TextComponent message = new TextComponent(ChatColor.GREEN + sender.getName() + " has requested to teleport to you.\n" +
                ChatColor.GREEN + "Click here to " + ChatColor.UNDERLINE + "[Accept]" + ChatColor.RESET + ChatColor.GREEN + " or " + ChatColor.UNDERLINE + "[Deny]" + ChatColor.RESET + ChatColor.GREEN + " the request.");

        message.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Click to accept or deny the teleport request").create()
        ));

        TextComponent acceptComponent = new TextComponent(ChatColor.UNDERLINE + "[Accept]");
        acceptComponent.setClickEvent(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                "/tpaccept " + sender.getName()
        ));

        TextComponent denyComponent = new TextComponent(ChatColor.UNDERLINE + "[Deny]");
        denyComponent.setClickEvent(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                "/tpdeny " + sender.getName()
        ));

        message.addExtra(acceptComponent);
        message.addExtra(new TextComponent(ChatColor.GREEN + " or "));
        message.addExtra(denyComponent);

        target.spigot().sendMessage(message);
        cooldownMap.put(sender.getUniqueId(), System.currentTimeMillis() + COOLDOWN_DURATION);
    }

    private long getRemainingCooldown(UUID playerId) {
        Long cooldownEnd = cooldownMap.get(playerId);
        if (cooldownEnd == null) {
            return 0;
        }
        long remainingCooldown = cooldownEnd - System.currentTimeMillis();
        return Math.max(remainingCooldown, 0);
    }

    private String formatTimeRemaining(long timeRemaining) {
        long seconds = timeRemaining / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return minutes + " minute" + (minutes != 1 ? "s" : "") + " " + seconds + " second" + (seconds != 1 ? "s" : "");
    }
}