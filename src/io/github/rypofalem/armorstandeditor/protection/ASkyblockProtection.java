package io.github.rypofalem.armorstandeditor.protection;

import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class ASkyblockProtection implements ASEProtection{

    private ASkyBlock aSkyBlock;

    public ASkyblockProtection(ASkyBlock aSkyBlock){
        this.aSkyBlock = aSkyBlock;
    }

    @Override
    public boolean canEdit(Player p, ArmorStand a){
        if(aSkyBlock == null || !aSkyBlock.isEnabled()){
            return true;
        }
        if(ASkyBlockAPI.getInstance().playerIsOnIsland(p) || p.hasPermission("ase.fullbypass")){
            return true;
        }
        return false;

    }
}
