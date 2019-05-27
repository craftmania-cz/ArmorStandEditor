package cz.craftmania.ase;

import cz.craftmania.ase.modes.AdjustmentMode;
import cz.craftmania.ase.modes.Axis;
import cz.craftmania.ase.modes.EditMode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEx implements CommandExecutor {
    Main plugin;
    final String LISTMODE = ChatColor.GREEN + "/ase mode <" + Util.getEnumList(EditMode.class) + ">";
    final String LISTAXIS = ChatColor.GREEN + "/ase axis <" + Util.getEnumList(Axis.class) + ">";
    final String LISTADJUSTMENT = ChatColor.GREEN + "/ase adj <" + Util.getEnumList(AdjustmentMode.class) + ">";
    final String LISTSLOT = ChatColor.GREEN + "/ase slot <1-9>";

    public CommandEx(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    private boolean checkPermission(Player player, String permName, boolean sendMessageOnInvalidation) {
        if (permName.toLowerCase().equals("paste")) {
            permName = "copy";
        }
        if (player.hasPermission("asedit." + permName.toLowerCase())) {
            return true;
        } else {
            if (sendMessageOnInvalidation) {
                player.sendMessage("§cNa toto nemas dostatecna prava!");
            }
            return false;
        }
    }
}