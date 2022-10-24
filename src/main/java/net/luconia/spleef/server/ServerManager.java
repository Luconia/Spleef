package net.luconia.spleef.server;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.BridgeServiceProperty;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.executor.ServerSelectorType;
import de.dytanic.cloudnet.wrapper.Wrapper;
import lombok.Getter;
import org.bukkit.entity.Player;

public class ServerManager implements IServer {

    @Getter
    private final String service;

    public ServerManager(String service) {
        this.service = service;
    }

    public ServerManager() {
        this.service = null;
    }

    @Override
    public void setInGame(boolean startNewService) {
        if (getServiceInfoSnapshot() == null) return;
        getServiceInfoSnapshot().getProperties().append("State", "INGAME");
        Wrapper.getInstance().publishServiceInfoUpdate(getServiceInfoSnapshot());

        // The code below is from the BridgeServerHelper class :)

        if (!startNewService) {
            return;
        }

        String task = getServiceInfoSnapshot().getServiceId().getTaskName();

        CloudNetDriver.getInstance().getServiceTaskProvider().getServiceTaskAsync(task).onComplete(serviceTask -> {
            if (serviceTask != null) {
                CloudNetDriver.getInstance().getCloudServiceFactory().createCloudServiceAsync(serviceTask).onComplete(serviceInfoSnapshot -> {
                    if (serviceInfoSnapshot != null) {
                        serviceInfoSnapshot.provider().start();
                    }
                });
            }
        });
    }

    @Override
    public void setState(String state) {
        if (getServiceInfoSnapshot() == null) return;
        getServiceInfoSnapshot().getProperties().append("State", state);
        Wrapper.getInstance().publishServiceInfoUpdate(getServiceInfoSnapshot());
    }

    @Override
    public void setMotd(String motd) {
        if (getServiceInfoSnapshot() == null) return;
        getServiceInfoSnapshot().getProperties().append("Motd", motd);
        Wrapper.getInstance().publishServiceInfoUpdate(getServiceInfoSnapshot());
    }

    @Override
    public void sendToGroup(Player player, String task, ServerSelectorType type) {
        CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getPlayerExecutor(player.getUniqueId()).connectToGroup(task, type);
    }

    @Override
    public void sendToService(Player player, String service) {
        CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getPlayerExecutor(player.getUniqueId()).connect(service);
    }

    @Override
    public int getOnlinePlayers(String group) {
        int counter = 0;

        for (ServiceInfoSnapshot serviceInfoSnapshot : CloudNetDriver.getInstance().getCloudServiceProvider().getCloudServices(group)) {
            counter += serviceInfoSnapshot.getProperty(BridgeServiceProperty.ONLINE_COUNT).orElse(0);
        }

        return counter;
    }

    @Override
    public int getOnlinePlayers() {
        return getServiceInfoSnapshot() == null ? 0 : getServiceInfoSnapshot().getProperty(BridgeServiceProperty.ONLINE_COUNT).orElse(0);
    }

    @Override
    public ServiceInfoSnapshot getServiceInfoSnapshot() {
        return service == null ? null : CloudNetDriver.getInstance().getCloudServiceProvider().getCloudServiceByName(service);
    }

    @Override
    public String getState() {
        return getServiceInfoSnapshot() == null ? null : getServiceInfoSnapshot().getProperty(BridgeServiceProperty.STATE).orElse(null);
    }

    @Override
    public String getMotd() {
        return getServiceInfoSnapshot() == null ? null : getServiceInfoSnapshot().getProperty(BridgeServiceProperty.MOTD).orElse(null);
    }

    @Override
    public String getExtra() {
        return getServiceInfoSnapshot() == null ? null : getServiceInfoSnapshot().getProperty(BridgeServiceProperty.EXTRA).orElse(null);
    }

    @Override
    public String getVersion() {
        return getServiceInfoSnapshot() == null ? null : getServiceInfoSnapshot().getProperty(BridgeServiceProperty.VERSION).orElse(null);
    }
}
