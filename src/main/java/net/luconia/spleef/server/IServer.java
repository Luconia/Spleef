package net.luconia.spleef.server;

import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.player.executor.ServerSelectorType;
import org.bukkit.entity.Player;

/**
 * Get multiple things of a CloudNet group or service.
 * It will make it easier to use the CloudNet v3 API in Bukkit plugins.
 * It allows you to send players to a task with the most players online or with the lowest players online.
 */
public interface IServer {

    /**
     * Set the state of the Service to INGAME
     *
     * @param startNewService if it's true, then it will start a new service, if it's false, then it will not do that
     */
    void setInGame(boolean startNewService);

    /**
     * Set the state of the Service
     *
     * @param state The state for the Service. The state can be any String that you like
     */
    void setState(String state);

    /**
     * Change the motd of the Minecraft Server
     *
     * @param motd The motd for the service
     */
    void setMotd(String motd);

    /**
     * Send a player to a specific group
     *
     * @param player The player that will be sent to the group
     * @param group  The group where the player will be sent
     * @param type   The type. The type can be:
     *               <p>
     *               {@link ServerSelectorType#RANDOM},
     *               <p>
     *               {@link ServerSelectorType#HIGHEST_PLAYERS},
     *               <p>
     *               {@link ServerSelectorType#LOWEST_PLAYERS}
     */
    void sendToGroup(Player player, String group, ServerSelectorType type);

    /**
     * Send a player to a specific service
     *
     * @param player  The player that will be sent to the service
     * @param service The service where the player will be sent
     */
    void sendToService(Player player, String service);

    /**
     * Get all online players of a specific group
     *
     * @param group The group that will be used to get the online players
     * @return The online players of the group
     */
    int getOnlinePlayers(String group);

    /**
     * Get the online players of the service
     *
     * @return The online players of the service
     */
    int getOnlinePlayers();

    /**
     * Get the CloudNet {@link ServiceInfoSnapshot} of the service
     *
     * @return The {@link ServiceInfoSnapshot} of the service
     */
    ServiceInfoSnapshot getServiceInfoSnapshot();

    /**
     * Get the state of the service
     *
     * @return The state
     */
    String getState();

    /**
     * Get the motd of the minecraft server
     *
     * @return The motd of the service/minecraft server
     */
    String getMotd();

    /**
     * Get the CloudNet extra. I don't know what the extra is, but I added it here :)
     *
     * @return the extra
     */
    String getExtra();

    /**
     * Get the Minecraft Version of the server
     *
     * @return The Minecraft version
     */
    String getVersion();

}
