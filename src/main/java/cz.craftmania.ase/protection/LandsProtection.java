package cz.craftmania.ase.protection;

import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.LandArea;
import me.angeschossen.lands.api.role.Role;
import me.angeschossen.lands.api.role.enums.RoleSetting;
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
        final LandArea area = landsIntegration.getArea(location);

        if (area == null) return true;

        final Role role = area.getRole(player.getUniqueId());

        // TEST
        return role.hasSetting(RoleSetting.BLOCK_BREAK);
    }
}
