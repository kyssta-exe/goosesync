package me.kyssta.goosesync.model;

public class PlayerData {
    private final String playerName;
    private int ping;
    private Integer lastDamageTicks;
    private Integer lastPearlTicks;
    private Integer lastConsumeTicks;
    private Integer lastPotionTicks;
    private Double verticalVelocity;
    private boolean compensationEnabled;

    public PlayerData(String playerName) {
        this.playerName = playerName;
        this.compensationEnabled = true;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public Integer getLastDamageTicks() {
        return lastDamageTicks;
    }

    public void setLastDamageTicks(Integer lastDamageTicks) {
        this.lastDamageTicks = lastDamageTicks;
    }

    public Integer getLastPearlTicks() {
        return lastPearlTicks;
    }

    public void setLastPearlTicks(Integer lastPearlTicks) {
        this.lastPearlTicks = lastPearlTicks;
    }

    public Integer getLastConsumeTicks() {
        return lastConsumeTicks;
    }

    public void setLastConsumeTicks(Integer lastConsumeTicks) {
        this.lastConsumeTicks = lastConsumeTicks;
    }

    public Integer getLastPotionTicks() {
        return lastPotionTicks;
    }

    public void setLastPotionTicks(Integer lastPotionTicks) {
        this.lastPotionTicks = lastPotionTicks;
    }

    public Double getVerticalVelocity() {
        return verticalVelocity;
    }

    public void setVerticalVelocity(Double verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
    }

    public boolean isCompensationEnabled() {
        return compensationEnabled;
    }

    public void setCompensationEnabled(boolean compensationEnabled) {
        this.compensationEnabled = compensationEnabled;
    }

    public boolean isOnGround(double y) {
        return Math.abs(y) < 0.005;
    }

    public boolean hasElapsed(Integer previousTick, int currentTick, int requiredTicks) {
        return previousTick == null || currentTick - previousTick >= requiredTicks;
    }

    public boolean isWithinTicks(Integer previousTick, int currentTick, int maxTicks) {
        return previousTick != null && currentTick - previousTick <= maxTicks;
    }

    public boolean shouldCompensate(int threshold) {
        return compensationEnabled && ping >= threshold;
    }
}
