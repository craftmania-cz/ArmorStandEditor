package cz.craftmania.ase.protection;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public interface ASEProtection {

    //determines if a protection plugin should allow editing for the given ArmorStand by the given Player
    // return true if editing should be allowed, return false if not
	boolean canEdit(Player player, ArmorStand armorstand);
}
