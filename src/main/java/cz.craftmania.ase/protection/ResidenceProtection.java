package cz.craftmania.ase.protection;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class ResidenceProtection implements ASEProtection {
    Residence residencePlugin;

    public ResidenceProtection(Residence res) {
        residencePlugin = res;
    }

    @Override
    public boolean canEdit(Player player, ArmorStand armorstand) {
        if (residencePlugin == null || !residencePlugin.isEnabled()) return true;
        if (Residence.getInstance().getResidenceManager() == null) return true;
        Location loc = armorstand.getLocation();
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);
        FlagPermissions perms = Residence.getInstance().getPermsByLoc(loc);
        if (perms == null) return true;
        if (res == null) return true;
        return res.getPermissions().getOwnerUUID().equals(player.getUniqueId()) ||
                (perms.playerHas(player.getName(), loc.getWorld().getName(), "destroy", false) && perms.playerHas(player.getName(), loc.getWorld().getName(), "destroy", false));
    }
}