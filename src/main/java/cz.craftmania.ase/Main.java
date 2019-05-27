package cz.craftmania.ase;

import com.bekvon.bukkit.residence.Residence;
import com.plotsquared.bukkit.BukkitMain;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.wasteofplastic.askyblock.ASkyBlock;
import cz.craftmania.ase.protection.ASEProtection;
import cz.craftmania.ase.protection.ASkyblockProtection;
import cz.craftmania.ase.protection.PlotSqProtection;
import cz.craftmania.ase.protection.ResidenceProtection;
import cz.craftmania.ase.protection.WGProtection;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    private static Main instance;

    private CommandEx execute;
    public PlayerEditorManager editorManager;
    public Material editTool;
    boolean debug = false; //weather or not to broadcast messages via print(String message)
    double coarseRot;
    double fineRot;
    private ArrayList<ASEProtection> protections;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        coarseRot = getConfig().getDouble("coarse");
        fineRot = getConfig().getDouble("fine");
        String tool = getConfig().getString("tool");
        editTool = Material.getMaterial(tool);

        editorManager = new PlayerEditorManager(this);
        execute = new CommandEx(this);
        getCommand("ase").setExecutor(execute);
        getServer().getPluginManager().registerEvents(editorManager, this);

        protections = new ArrayList<ASEProtection>();
        if (isPluginEnabled("WorldGuard")) {
            WorldGuardPlugin wgPlugin = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
            addProtection(new WGProtection(wgPlugin));
        }
        if (isPluginEnabled("PlotSquared")) {
            Plugin plotSqPlugin = getServer().getPluginManager().getPlugin("PlotSquared");
            if (plotSqPlugin instanceof BukkitMain) addProtection(new PlotSqProtection((BukkitMain) plotSqPlugin));
        }
        if (isPluginEnabled("Residence")) {
            Plugin res = getServer().getPluginManager().getPlugin("Residence");
            if (res instanceof Residence) addProtection(new ResidenceProtection((Residence) res));
        }
        if (isPluginEnabled("ASkyBlock")) {
            Plugin askyblock = getServer().getPluginManager().getPlugin("ASkyBlock");
            if (askyblock instanceof ASkyBlock) addProtection(new ASkyblockProtection((ASkyBlock) askyblock));
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public boolean isPluginEnabled(String plugin) {
        return getServer().getPluginManager().isPluginEnabled(plugin);
    }

    public void addProtection(ASEProtection protection) {
        protections.add(protection);
    }

    public ArrayList<ASEProtection> getProtections() {
        return protections;
    }
}