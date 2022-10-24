package net.luconia.spleef.game;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameManager implements IGameHandler {

    @Getter
    private final Plugin plugin;

    @Getter
    private int task;

    private final List<Player> players = new ArrayList<>();

    public GameManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void startGame() {
        task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int seconds = 30;

            @Override
            public void run() {
                seconds--;

                if (seconds <= 5 && seconds != 0) {
                    for (Player player : players) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                    }
                }

                if (seconds == 0) {
                    plugin.getServer().getScheduler().cancelTask(task);
                } else {
                    Bukkit.broadcastMessage("§8[§bSpleef§8] §7The game starts in §e" + seconds);
                }
            }
        }, 0L, 20L);
    }

    @Override
    public void stopGame() {

    }

    @Override
    public List<Player> getOnlinePlayers() {
        return players;
    }

    @Override
    public Collection<Player> getPlayersAlive() {
        return null;
    }

    @Override
    public Collection<Player> getDeadPlayers() {
        return null;
    }
}
