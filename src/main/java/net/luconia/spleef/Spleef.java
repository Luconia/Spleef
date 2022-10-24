package net.luconia.spleef;

import lombok.Getter;
import net.luconia.spleef.server.IServer;
import net.luconia.spleef.server.ServerManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Plugin main class that Bukkit needs to load the Spigot plugin.
 * It is used for initializing new instances of classes, and it is used
 * to register commands and listeners
 */
@Getter
public final class Spleef extends JavaPlugin {

    private static Spleef instance;

    /**
     * Initializes the {@link Spleef} instance and register all commands and listeners
     */
    @Override
    public void onEnable() {
        instance = this;
    }

    /**
     * Creates a new instance of the {@link ServerManager} class.
     *
     * @param service The service name that the ServerManager need to get the online player count and to get other things.
     *                You can set the service to null if you don't want to use the service things,
     *                but you can send players to groups and services
     * @return A new instance of the {@link ServerManager} class.
     */
    public IServer getServer(String service) {
        return new ServerManager(service);
    }


}
