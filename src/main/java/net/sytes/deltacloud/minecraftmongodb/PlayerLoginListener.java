package net.sytes.deltacloud.minecraftmongodb;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLoginListener implements Listener {

    MongoDBHandler mongoDBHandler;
    public PlayerLoginListener(MongoDBHandler dbHandlerInstance) {
        mongoDBHandler = dbHandlerInstance;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        mongoDBHandler.updateOnlinePlayerCount(1);
    }
    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        mongoDBHandler.updateOnlinePlayerCount(-1);
    }
}
