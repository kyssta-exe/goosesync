package me.kyssta.goosesync.manager;

import me.kyssta.goosesync.model.PlayerData;
import me.kyssta.goosesync.util.VersionUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {
    private final Map<UUID, PlayerData> playerDataMap;

    public PlayerDataManager() {
        this.playerDataMap = new HashMap<>();
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public PlayerData getOrCreatePlayerData(Player player) {
        PlayerData data = playerDataMap.get(player.getUniqueId());
        if (data == null) {
            data = new PlayerData(player.getName());
            playerDataMap.put(player.getUniqueId(), data);
        }
        return data;
    }

    public void createPlayerData(Player player) {
        getOrCreatePlayerData(player);
    }

    public void removePlayerData(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public void updatePlayerPing(Player player) {
        PlayerData data = getPlayerData(player.getUniqueId());
        getOrCreatePlayerData(player).setPing(VersionUtil.getPing(player));
    }

    public int getTrackedPlayerCount() {
        return playerDataMap.size();
    }
}
