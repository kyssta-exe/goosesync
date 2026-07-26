package me.kyssta.goosesync.util;

import org.bukkit.util.Vector;

public final class KnockbackUtil {

    private KnockbackUtil() {
    }

    public static Vector reduceHorizontal(Vector velocity, double multiplier) {
        Vector adjusted = velocity.clone();
        adjusted.setX(adjusted.getX() * multiplier);
        adjusted.setZ(adjusted.getZ() * multiplier);
        return adjusted;
    }
}
