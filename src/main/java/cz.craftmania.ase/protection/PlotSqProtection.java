package cz.craftmania.ase.protection;

import com.plotsquared.bukkit.BukkitMain;
import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class PlotSqProtection implements ASEProtection{

	private final BukkitMain plotSqPlugin;

	public PlotSqProtection(BukkitMain plotSPlugin) {
		this.plotSqPlugin = plotSPlugin;
	}
	
	@Override
	public boolean canEdit(Player player, ArmorStand armorstand) {
		if(plotSqPlugin == null || !plotSqPlugin.isEnabled()) return true; 
		//if(!PS.get().hasPlotArea(player.getWorld().getName())) return true; //if the world isn't a plot world
		Location location = BukkitUtil.getLocation(armorstand.getLocation());
		Plot plot = Plot.getPlot(location);
		if(plot == null) return false;
		if(plot.isDenied(player.getUniqueId())) return false;
		if(plot.isOwner(player.getUniqueId()) || plot.isAdded(player.getUniqueId())) return true;
		return false;
	}
}