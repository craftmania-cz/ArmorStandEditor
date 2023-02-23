package cz.craftmania.ase.protection;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.island.privilege.IslandPrivileges;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class SuperiorSkyblock2Protection implements ASEProtection {

    @Override
    public boolean canEdit(Player player, ArmorStand armorstand) {
        Island island = SuperiorSkyblockAPI.getIslandAt(player.getLocation());
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);
        return island.hasPermission(superiorPlayer, IslandPrivileges.BREAK);
    }
}
