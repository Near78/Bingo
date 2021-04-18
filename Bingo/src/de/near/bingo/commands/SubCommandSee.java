package de.near.bingo.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.near.bingo.Bingo;
import de.near.bingo.guis.GuiBingo;
import de.near.bingo.utils.Message;
import de.near.subcommand.SubCommand;

public class SubCommandSee extends SubCommand {
    private Bingo plugin;

    SubCommandSee(Bingo plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "see";
    }

    @Override
    public String getDescription() {
        return "Displays player's bingo card";
    }

    @Override
    public String getUsage() {
        return "/bingo see <player>";
    }

    @Override
    public String getPermission() {
        return "bingo.see";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public void handle(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(Message.info("chat.specifyPlayer"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(Message.error("chat.playerNotOnline"));
            return;
        }
        new GuiBingo(plugin, player, target).show();
    }

}
