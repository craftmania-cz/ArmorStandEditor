package io.github.rypofalem.armorstandeditor.protection;

import com.github.shynixn.petblocks.api.business.entity.PetBlock;
import com.github.shynixn.petblocks.bukkit.PetBlocksPlugin;
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
