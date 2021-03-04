package cz.craftmania.ase.modes;

import org.bukkit.entity.ArmorStand;

public class CopySlots {
    public byte currentSlot = 0;
    ArmorStandData[] slots = new ArmorStandData[9];

    //returns true if parameters are acceptable, false otherwise.
    public boolean changeSlots(byte slot) {
        if (slot < slots.length && slot >= 0) {
            currentSlot = slot;
            return true;
        } else {
            return false;
        }
    }

    public void copyDataToSlot(ArmorStand armorStand) {
        slots[currentSlot] = new ArmorStandData(armorStand);
    }

    //returns null if there is not data in current slot
    public ArmorStandData getDataToPaste() {
        return slots[currentSlot];
    }
}
