package de.pixelplayer_studios.xloadbalancer;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import de.pixelplayer_studios.xloadbalancer.listeners.LobbyConnectionListener;
import de.pixelplayer_studios.xloadbalancer.listeners.SpawnConnectionListener;
import org.slf4j.Logger;

@Plugin(
        id = "xloadbalancer",
        name = "xLoadBalancer",
        version = "v0.0.1-RC01",
        description = "A simple load balancer for Velocity proxy",
        authors = {"PixelPlayer Studios"}
)
public class xLoadBalancer {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public xLoadBalancer(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Der Listener 'SpawnConnectionListener' wird initialisert...");
        server.getEventManager().register(this, new SpawnConnectionListener(server, logger));
        logger.info("Der Listener 'SpawnConnectionListener' wurde erfolgreich initialisert.");
        logger.info("Der Listener 'LobbyConnectionListener' wird initialisert...");
        server.getEventManager().register(this, new LobbyConnectionListener(server, logger));
        logger.info("Der Listener 'LobbyConnectionListener' wurde erfolgreich initialisert.");
    }
}