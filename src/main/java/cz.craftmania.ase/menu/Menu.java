package cz.craftmania.ase.menu;

import cz.craftmania.ase.PlayerEditor;
import cz.craftmania.ase.Util;
import cz.craftmania.craftcore.spigot.builders.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Menu {
    Inventory menuInv;
    private PlayerEditor pe;
    static String name = "Armor Stand Editor Menu";

    public Menu(PlayerEditor pe) {
        this.pe = pe;
        name = "ArmorStandEditor";
        menuInv = Bukkit.createInventory(pe.getManager().getPluginHolder(), 54, name);
        fillInventory();
    }

    private void fillInventory() {
        menuInv.clear();
        ItemStack xAxis = null, yAxis = null, zAxis = null, coarseAdj = null, fineAdj = null, rotate = null, place = null,
                headPos = null,
                rightArmPos = null, bodyPos = null, leftArmPos = null, reset = null, showArms = null, visibility = null, size = null,
                rightLegPos = null, equipment = null, leftLegPos = null, disableSlots = null, gravity = null, plate = null, copy = null, paste = null,
                slot1 = null, slot2 = null, slot3 = null, slot4 = null, slot5 = null, slot6 = null, slot7 = null, slot8 = null, slot9 = null,
                filler = null, navod = null;

        if (pe.getPlayer().hasPermission("asedit.edit")) {

            xAxis = new ItemBuilder(Material.RED_TERRACOTTA)
                    .setName("§c§lX OSA").setLore(cmd("axis x")).build();

            yAxis = new ItemBuilder(Material.GREEN_TERRACOTTA)
                    .setName("§2§lY OSA").setLore(cmd("axis y")).build();

            zAxis = new ItemBuilder(Material.BLUE_TERRACOTTA)
                    .setName("§9§lZ OSA").setLore(cmd("axis z")).build();

            coarseAdj = new ItemBuilder(Material.DIRT).setName("§e§lHrube nastaveni")
                    .setLore(cmd("adj coarse")).build();

            fineAdj = new ItemBuilder(Material.SANDSTONE).setName("§e§lJemne nastaveni")
                    .setLore(cmd("adj fine")).build();

            headPos = new ItemBuilder(Material.LEATHER_HELMET).setName("§f§lNastaveni hlavy")
                    .setLore(cmd("mode head")).hideAllFlags().build();

            bodyPos = new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§f§lNastaveni tela")
                    .setLore(cmd("mode body")).hideAllFlags().build();

            leftLegPos = new ItemBuilder(Material.LEATHER_LEGGINGS).setName("§f§lLeva noha")
                    .setLore(cmd("mode leftleg")).hideAllFlags().build();

            rightLegPos = new ItemBuilder(Material.LEATHER_LEGGINGS).setName("§f§lPrava noha")
                    .setLore(cmd("mode rightleg")).hideAllFlags().build();

            leftArmPos = new ItemBuilder(Material.STICK).setName("§f§lLeva ruka")
                    .setLore(cmd("mode leftarm")).build();

            rightArmPos = new ItemBuilder(Material.STICK).setName("§f§lPrava ruka")
                    .setLore(cmd("mode rightarm")).build();

            reset = new ItemBuilder(Material.LEVER).setName("§c§lReset")
                    .setLore(cmd("mode reset")).build();

            showArms = new ItemBuilder(Material.STICK).setName("§b§lZobrazeni rukou")
                    .setLore(cmd("mode showarms")).build();

            visibility = new ItemBuilder(Material.LINGERING_POTION).setName("§e§lViditelnost")
                    .setLore(cmd("mode invisible")).build();

            size = new ItemBuilder(Material.TROPICAL_FISH).setName("§c§lVelikost")
                    .setLore(cmd("mode size")).build();

            gravity = new ItemBuilder(Material.SAND).setName("§e§lGravitace")
                    .setLore(cmd("mode gravity")).build();

            plate = new ItemBuilder(Material.STONE_SLAB).setName("§f§lPodstavec")
                    .setLore(cmd("mode baseplate")).build();

            place = new ItemBuilder(Material.MINECART).setName("§f§lPozice")
                    .setLore(cmd("mode placement")).build();

            rotate = new ItemBuilder(Material.COMPASS).setName("§c§lRotace")
                    .setLore(cmd("mode rotate")).build();

            equipment = new ItemBuilder(Material.CHEST).setName("§f§lInventar")
                    .setLore(cmd("mode equipment")).build();

            filler = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDurability((short)3).setName("§f").build();
        }

        if (pe.getPlayer().hasPermission("asedit.copy")) {
            copy = new ItemBuilder(Material.WRITABLE_BOOK).setName("§f§lKopirovat")
                    .setLore(cmd("mode copy")).build();

            paste = new ItemBuilder(Material.ENCHANTED_BOOK).setName("§b§lVlozit")
                    .setLore(cmd("mode paste")).build();

            slot1 = new ItemBuilder(Material.DANDELION).setName("§e§lSlot 1")
                    .setLore(cmd("slot 1")).build();

            slot2 = new ItemBuilder(Material.POPPY, 2).setName("§e§lSlot 2").setDurability((short)3)
                    .setLore(cmd("slot 2")).build();

            slot3 = new ItemBuilder(Material.BLUE_ORCHID, 3).setName("§e§lSlot 3").setDurability((short)1)
                    .setLore(cmd("slot 3")).build();

            slot4 = new ItemBuilder(Material.WHITE_TULIP, 4).setName("§e§lSlot 4").setDurability((short)5)
                    .setLore(cmd("slot 4")).build();

            slot5 = new ItemBuilder(Material.WITHER_ROSE, 5).setName("§e§lSlot 4").setDurability((short)6)
                    .setLore(cmd("slot 5")).build();
        }
        ItemStack[] items =
                {       filler, filler, filler, filler, null, filler, filler, filler, filler,
                        xAxis, yAxis, zAxis, reset, filler, coarseAdj, fineAdj, rotate, place,
                        null, headPos, null, null, filler, null, showArms, visibility, size,
                        rightArmPos, bodyPos, leftArmPos, filler, filler, filler, filler, gravity, plate,
                        rightLegPos, null, leftLegPos, filler, copy, paste, filler, filler, filler,
                        null, equipment, null, filler, slot1, slot2, slot3, slot4, slot5
                };
        menuInv.setContents(items);
    }

    private ItemStack createIcon(ItemStack icon, String name, String command) {
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> loreList = new ArrayList<String>();
        loreList.add(Util.encodeHiddenLore("ase " + command));
        loreList.add("null");
        meta.setLore(loreList);
        icon.setItemMeta(meta);
        return icon;
    }

    private String cmd(String cmd) {
        return Util.encodeHiddenLore("ase " + cmd);
    }

    private ItemStack createIcon(ItemStack icon, String name, String command, String option) {
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> loreList = new ArrayList<String>();
        loreList.add(Util.encodeHiddenLore("ase " + command));
        loreList.add("null");
        meta.setLore(loreList);
        icon.setItemMeta(meta);
        return icon;
    }

    public void openMenu() {
        if (pe.getPlayer().hasPermission("asedit.basic")) {
            fillInventory();
            pe.getPlayer().openInventory(menuInv);
        }
    }

    public static String getName() {
        return name;
    }
}