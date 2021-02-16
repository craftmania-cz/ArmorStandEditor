package cz.craftmania.ase.protection;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public interface ASEProtection {

    /**
     * Determines whether a protection plugin should
     * allow editing the given {@link ArmorStand} by given {@link Player}.
     * @param player Target Player
     * @param armorstand Target ArmorStand
     * @return True if editing should be allowed, false if not.
     */
	boolean canEdit(Player player, ArmorStand armorstand);
}
