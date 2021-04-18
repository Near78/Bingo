package de.near.bingo.commands;

import org.bukkit.entity.Player;

import de.near.bingo.Bingo;
import de.near.bingo.managers.GamerManager;
import de.near.bingo.utils.Message;
import de.near.subcommand.SubCommand;

public class SubCommandCheatMode extends SubCommand {
    private Bingo plugin;
    private GamerManager gameManager;

    SubCommandCheatMode(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
    }

    @Override
    public String getName() {
        return "cheatMode";
    }

    @Override
    public String getDescription() {
        return "Toggles the cheat mode";
    }

    @Override
    public String getUsage() {
        return "/bingo cheatMode";
    }

    @Override
    public String getPermission() {
        return "bingo.cheatMode";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"cM"};
    }

    @Override
    public void handle(Player player, String[] args) {
        if (!gameManager.isGameInProgress()) {
            player.sendMessage(Message.info("chat.gameNotStarted"));
            return;
        }
        if (plugin.getPlayer(player.getUniqueId()).isCheatMode()) {
            plugin.getPlayer(player.getUniqueId()).setCheatMode(false);
            player.sendMessage(Message.success("chat.cheatModeDisabled").replace("$state", "disabled"));
            return;
        }
        plugin.getPlayer(player.getUniqueId()).setCheatMode(true);
        player.sendMessage(Message.success("chat.cheatModeEnabled").replace("$state", "enabled"));
    }


}
