package cz.craftmania.ase.protection;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class UltraMinionsProtection implements ASEProtection {

    Main ultraMinionsPlugin;

    public UltraMinionsProtection(Main plugin) {
        this.ultraMinionsPlugin = plugin;
    }

    @Override
    public boolean canEdit(Player player, ArmorStand armorstand) {
        PlayerMinion playerMinion = this.ultraMinionsPlugin.getMm().getActiveMinions().get(armorstand.getUniqueId());
        if (playerMinion == null) {
            return true;
        }
        return false;
    }
}
