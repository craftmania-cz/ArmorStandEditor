package cz.craftmania.ase.protection;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.lists.Flags;

import java.util.Optional;

public class BSkyBlockProtection implements ASEProtection {

    @Override
    public boolean canEdit(Player player, ArmorStand armorstand) {
        if (BentoBox.getInstance() == null) return true;

        if (!BentoBox.getInstance().getAddonsManager().getAddonByName("BSkyblock").isPresent()) return true;

        final Location location = armorstand.getLocation();
        final Optional<Island> islandOptional = BentoBox.getInstance().getIslands().getIslandAt(location);

        if (!islandOptional.isPresent()) return false;

        final Island island = islandOptional.get();

        return island.isAllowed(User.getInstance(player), Flags.BREAK_BLOCKS);
    }

}
