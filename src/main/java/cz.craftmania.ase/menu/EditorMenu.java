package cz.craftmania.ase.menu;

import cz.craftmania.ase.Main;
import cz.craftmania.ase.modes.AdjustmentMode;
import cz.craftmania.ase.modes.Axis;
import cz.craftmania.ase.modes.EditMode;
import cz.craftmania.craftcore.spigot.builders.items.ItemBuilder;
import cz.craftmania.craftcore.spigot.inventory.builder.ClickableItem;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryContents;
import cz.craftmania.craftcore.spigot.inventory.builder.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EditorMenu implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.fillRow(0, ClickableItem.empty(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build()));

        // 2. řádek
        contents.set(1, 0, ClickableItem.of(new ItemBuilder(Material.RED_TERRACOTTA).setName("§cOsa X").setLore("§7Kliknuím budeš měnit osu X", "§7Nezapomeň zvolit mód níže.").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setAxis(Axis.X);
            player.closeInventory();
        }));
        contents.set(1,1, ClickableItem.of(new ItemBuilder(Material.GREEN_TERRACOTTA).setName("§aOsa Y").setLore("§7Kliknuím budeš měnit osu Y", "§7Nezapomeň zvolit mód níže.").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setAxis(Axis.Y);
            player.closeInventory();
        }));
        contents.set(1, 2, ClickableItem.of(new ItemBuilder(Material.BLUE_TERRACOTTA).setName("§9Osa Z").setLore("§7Kliknuím budeš měnit osu Z", "§7Nezapomeň zvolit mód níže.").build(), (clickEven) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setAxis(Axis.Z);
            player.closeInventory();
        }));
        contents.set(1, 3, ClickableItem.of(new ItemBuilder(Material.REDSTONE).setName("§cReset").setLore("§7Vyresetování do základní polohy").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.RESET);
            player.closeInventory();
        }));
        contents.set(1, 4, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(1, 5, ClickableItem.of(new ItemBuilder(Material.DIRT).setName("§eHrubé nastavení").setLore("§7Větší rozestup mezi úpravami").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setAdjMode(AdjustmentMode.COARSE);
            player.closeInventory();
        }));
        contents.set(1, 6, ClickableItem.of(new ItemBuilder(Material.SAND).setName("§eJemné nastavení").setLore("§7Menší rozestup mezi úpravami").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setAdjMode(AdjustmentMode.FINE);
            player.closeInventory();
        }));
        contents.set(1, 7, ClickableItem.of(new ItemBuilder(Material.COMPASS).setName("§bRotace").setLore("§7Otáčení armorstandu dokola").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.ROTATE);
            player.closeInventory();
        }));
        contents.set(1, 8, ClickableItem.of(new ItemBuilder(Material.MINECART).setName("§aPozice").setLore("§7Posunutí dopředu nebo dozádu").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.PLACEMENT);
            player.closeInventory();
        }));

        // 3. řádek
        contents.set(2, 1, ClickableItem.of(new ItemBuilder(Material.LEATHER_HELMET).setName("§fNastavení hlavy").hideAllFlags().setLore("§7Mód pro úpravu pozice hlavy").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.HEAD);
            player.closeInventory();
        }));
        contents.set(2, 3, ClickableItem.of(new ItemBuilder(Material.GUNPOWDER).setName("§cDebug info").setLore("§7Debug mód slouží k zobrazení", "§7hodnot Armorstandu k nastavení", "§7v pluginech.").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.DEBUG);
            player.closeInventory();
        }));
        contents.set(2, 4, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.SPECTRAL_ARROW).setName("§eGlowing").setLore("§7Zobrazení ohraničení").build(), (clickEvent -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.GLOWING);
            player.closeInventory();
        })));
        contents.set(2, 6, ClickableItem.of(new ItemBuilder(Material.STICK).setName("§bZobrazení rukou").setLore("§7Armorstand bude nebo nebude mít ruce!").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.SHOWARMS);
            player.closeInventory();
        }));
        contents.set(2, 7, ClickableItem.of(new ItemBuilder(Material.SPLASH_POTION).setName("§bViditelnost").hideAllFlags().setLore("§7Zneviditelníš armorstand.", "§7Takový armorstand může pak", "§7fungovat jako hologram.").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.INVISIBLE);
            player.closeInventory();
        }));
        contents.set(2, 8, ClickableItem.of(new ItemBuilder(Material.TROPICAL_FISH).setName("§6Velikost").setLore("§7Změna velikosti velký/malý").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.SIZE);
            player.closeInventory();
        }));

        // 4. řádek
        contents.set(3, 0, ClickableItem.of(new ItemBuilder(Material.STICK).setName("§fLevá ruka").setLore("§7Mód pro úpravu levé ruky").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.LEFTARM);
            player.closeInventory();
        }));
        contents.set(3, 1, ClickableItem.of(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§fNastavení těla").hideAllFlags().setLore("§7Mód pro úpravu trupu").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.BODY);
            player.closeInventory();
        }));
        contents.set(3, 2, ClickableItem.of(new ItemBuilder(Material.STICK).setName("§fPravá ruka").setLore("§7Mód pro úpravu pravé ruky").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.RIGHTARM);
            player.closeInventory();
        }));
        contents.set(3, 3, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(3, 4, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(3, 5, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(3, 6, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(3, 7, ClickableItem.of(new ItemBuilder(Material.SHULKER_SHELL).setName("§fGravitace").setLore("§7Zapneš nebo vypneš gravitaci", "§cPozor! Armorstand může padat až do voidu.").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.GRAVITY);
            player.closeInventory();
        }));
        contents.set(3, 8, ClickableItem.of(new ItemBuilder(Material.SMOOTH_STONE_SLAB).setName("§fPodstavec").setLore("§7Přepnutí zobrzení podstavce").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.BASEPLATE);
            player.closeInventory();
        }));

        // 5. řádek
        contents.set(4, 0, ClickableItem.of(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("§fLevá noha").hideAllFlags().setLore("§7Mód pro úpravu levé nohy").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.LEFTLEG);
            player.closeInventory();
        }));
        contents.set(4, 2, ClickableItem.of(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("§fPravá noha").hideAllFlags().setLore("§7Mód pro úpravu pravé nohy").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.RIGHTLEG);
            player.closeInventory();
        }));
        contents.set(4, 3, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(4, 4, ClickableItem.of(new ItemBuilder(Material.WRITABLE_BOOK).setName("§aKopírovat").setLore("§7Okopíruješ aktuální nastavení armorstandu").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.COPY);
            player.closeInventory();
        }));
        contents.set(4, 5, ClickableItem.of(new ItemBuilder(Material.ENCHANTED_BOOK).setName("§aVložit").setLore("§7Vložíš okopírované nastavení armorstandu", "§7na jiný armorstand.").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.PASTE);
            player.closeInventory();
        }));
        contents.set(4, 6, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(4, 7, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(4, 8, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));

        // 6. řádek
        contents.set(5, 1, ClickableItem.of(new ItemBuilder(Material.CHEST).setName("§eInventář").setLore("§7Správa inventáře, druhé ruky atd.").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setMode(EditMode.EQUIPMENT);
            player.closeInventory();
        }));
        contents.set(5, 3, ClickableItem.of(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§f ").build(), (clickEvent) -> {}));
        contents.set(5, 4, ClickableItem.of(new ItemBuilder(Material.YELLOW_CONCRETE_POWDER).setName("§aSlot 1").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setCopySlot((byte) 1);
            player.closeInventory();
        }));
        contents.set(5, 5, ClickableItem.of(new ItemBuilder(Material.RED_CONCRETE_POWDER).setAmount(2).setName("§aSlot 2").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setCopySlot((byte) 2);
            player.closeInventory();
        }));
        contents.set(5, 6, ClickableItem.of(new ItemBuilder(Material.BLUE_CONCRETE_POWDER).setAmount(3).setName("§aSlot 3").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setCopySlot((byte) 3);
            player.closeInventory();
        }));
        contents.set(5, 7, ClickableItem.of(new ItemBuilder(Material.WHITE_CONCRETE_POWDER).setAmount(4).setName("§aSlot 4").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setCopySlot((byte) 4);
            player.closeInventory();
        }));
        contents.set(5, 8, ClickableItem.of(new ItemBuilder(Material.BLACK_CONCRETE_POWDER).setAmount(5).setName("§aSlot 5").build(), (clickEvent) -> {
            Main.getInstance().getEditorManager().getPlayerEditor(player.getUniqueId()).setCopySlot((byte) 5);
            player.closeInventory();
        }));






    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
