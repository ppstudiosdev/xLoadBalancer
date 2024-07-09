package de.pixelplayer_studios.xloadbalancer.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@SuppressWarnings({"SimplifyStreamApiCallChains", "unused"})
public class SpawnConnectionListener {

    private final ProxyServer server;
    private final Logger logger;
    private static final int MAX_PLAYERS_PER_SERVER = 150;
    private final List<String> spawnServers = List.of("spawn1", "spawn2", "spawn3", "spawn4", "spawn5", "spawn6", "spawn7", "spawn8", "spawn9", "spawn10");

    public SpawnConnectionListener(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();

        List<RegisteredServer> availableServers = server.getAllServers().stream()
                .filter(s -> spawnServers.contains(s.getServerInfo().getName()) && s.getPlayersConnected().size() < MAX_PLAYERS_PER_SERVER)
                .filter(this::isServerOnline)
                .collect(Collectors.toList());

        if (availableServers.isEmpty()) {
            logger.warn("No available spawn servers with less than {} players found", MAX_PLAYERS_PER_SERVER);
            return;
        }

        RegisteredServer selectedServer = availableServers.get(ThreadLocalRandom.current().nextInt(availableServers.size()));

        player.createConnectionRequest(selectedServer).connect().thenAccept(result -> {
            if (result.isSuccessful()) {
                logger.info("Player {} connected to {}", player.getUsername(), selectedServer.getServerInfo().getName());
            } else {
                logger.warn("Player {} failed to connect to {}", player.getUsername(), selectedServer.getServerInfo().getName());
            }
        });
    }

    private boolean isServerOnline(RegisteredServer server) {
        try {
            server.ping().join();
            return true;
        } catch (Exception e) {
            logger.warn("Server {} is offline", server.getServerInfo().getName());
            return false;
        }
    }
}