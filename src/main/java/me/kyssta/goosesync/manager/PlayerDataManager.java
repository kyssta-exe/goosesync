package me.kyssta.goosesync.manager;

import me.kyssta.goosesync.model.PlayerData;
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

    public void createPlayerData(Player player) {
        playerDataMap.put(player.getUniqueId(), new PlayerData(player.getName()));
    }

    public void removePlayerData(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public void updatePlayerPing(Player player) {
        PlayerData data = getPlayerData(player.getUniqueId());
        if (data != null) {
            data.setPing(player.getPing());
        }
    }
}