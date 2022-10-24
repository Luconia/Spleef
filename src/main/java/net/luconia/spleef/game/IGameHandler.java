package net.luconia.spleef.game;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

/**
 * The Spleef game handler.
 * It is used to get the alive players, the dead players and the online players.
 * It is also used to start the game and stop the game
 */
public interface IGameHandler {

    /**
     * Starts the countdown of the Game.
     * If the countdown is finished then all online players will be teleported to the
     * spleef arena
     */
    void startGame();

    /**
     * Stops the game and stops the minecraft server.
     * The server will be automatically restarted because of CloudNet.
     * CloudNet is restarting the server and resets the Arena that got destroyed.
     */
    void stopGame();

    /**
     * Get all players that are on the minecraft server.
     *
     * @return THe online players
     */
    List<Player> getOnlinePlayers();

    /**
     * Get all players that are alive.
     * If a player dies then he will be removed from the Collection.
     *
     * @return The players that are alive
     */
    Collection<Player> getPlayersAlive();

    /**
     * Get all players that already died.
     *
     * @return The players that are dead
     */
    Collection<Player> getDeadPlayers();

}
