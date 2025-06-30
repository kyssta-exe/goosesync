package me.kyssta.goosesync.util;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class KnockbackUtil {
    private static final double BASE_HORIZONTAL = 0.4;
    private static final double BASE_VERTICAL = 0.4;
    private static final double SPRINT_BONUS = 0.8;
    private static final double MAX_HORIZONTAL = 2.0;
    private static final double MIN_HORIZONTAL = 0.2;

    public static Vector calculateBaseKnockback(Player attacker, Player victim) {
        Vector direction = victim.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
        double horizontal = BASE_HORIZONTAL;

        if (attacker.isSprinting()) {
            horizontal += SPRINT_BONUS;
        }

        return direction.multiply(horizontal).setY(BASE_VERTICAL);
    }

    public static Vector calculateAdjustedKnockback(Vector originalVelocity, Vector baseKnockback, double multiplier, int ping) {
        if (baseKnockback == null) {
            return null;
        }

        double pingMultiplier = 1.0 + ((ping - 100) / 1000.0);
        double finalMultiplier = multiplier * pingMultiplier;

        Vector adjusted = baseKnockback.clone().multiply(finalMultiplier);

        double horizontalMagnitude = Math.sqrt(adjusted.getX() * adjusted.getX() + adjusted.getZ() * adjusted.getZ());
        if (horizontalMagnitude > 0) {
            double newMagnitude = Math.min(MAX_HORIZONTAL, Math.max(MIN_HORIZONTAL, horizontalMagnitude));
            double scale = newMagnitude / horizontalMagnitude;
            adjusted.setX(adjusted.getX() * scale);
            adjusted.setZ(adjusted.getZ() * scale);
        }

        // Add any additional velocity (e.g., from previous motion)
        adjusted.add(new Vector(0, originalVelocity.getY(), 0));

        return adjusted;
    }
}