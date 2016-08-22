package io.github.rypofalem.armorstandeditor.protection;

import com.shynixn.petblocks.api.entities.PetBlock;
import com.shynixn.petblocks.business.bukkit.PetBlocksPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class PetsProtection implements ASEProtection{

    private PetBlocksPlugin petsPlugin;

    public PetsProtection(PetBlocksPlugin pl){ this.petsPlugin = pl; }

    @Override
    public boolean canEdit(Player player, ArmorStand armorstand) {
        if(petsPlugin == null || !petsPlugin.isEnabled()){ return true; }
        if(armorstand instanceof PetBlock){
            return false;
        }
        return true;
    }
}
