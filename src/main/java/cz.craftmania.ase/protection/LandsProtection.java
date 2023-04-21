package cz.craftmania.ase.protection;

import me.angeschossen.lands.api.flags.enums.FlagTarget;
import me.angeschossen.lands.api.flags.enums.RoleFlagCategory;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.Area;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class LandsProtection implements ASEProtection {

    private final LandsIntegration landsIntegration;

    public LandsProtection(LandsIntegration landsIntegration) {
        this.landsIntegration = landsIntegration;
    }

    @Override
    public boolean canEdit(Player player, ArmorStand armorstand) {
        if (landsIntegration == null || !landsIntegration.getPlugin().isEnabled()) return true;

        final Location location = armorstand.getLocation();
        if (!landsIntegration.isClaimed(location)) return true;

        final Area area = landsIntegration.getAreaByLoc(location);
        if (area == null) return true;

        return area.isTrusted(player.getUniqueId());
    }
}
