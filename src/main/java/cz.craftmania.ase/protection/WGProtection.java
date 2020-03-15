package cz.craftmania.ase.protection;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class WGProtection implements ASEProtection {
	private WorldGuardPlugin wgPlugin;
	
	public WGProtection(WorldGuardPlugin wgplugin){
		this.wgPlugin = wgplugin;
	}

	@Override
	public boolean canEdit(Player player, ArmorStand armorstand) {
	    if(player.hasPermission("ase.fullbypass")){
	        return true;
        }
		RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
		com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(armorstand.getLocation());
		return query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(player), Flags.BUILD);
	}

}
