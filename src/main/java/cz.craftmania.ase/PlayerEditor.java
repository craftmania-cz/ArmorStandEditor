package cz.craftmania.ase;

import cz.craftmania.ase.menu.EquipmentMenu;
import cz.craftmania.ase.menu.Menu;
import cz.craftmania.ase.modes.AdjustmentMode;
import cz.craftmania.ase.modes.ArmorStandData;
import cz.craftmania.ase.modes.Axis;
import cz.craftmania.ase.modes.CopySlots;
import cz.craftmania.ase.modes.EditMode;
import cz.craftmania.ase.protection.ASEProtection;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerEditor {

    public Main plugin;
    private UUID uuid;
    private EditMode eMode;
    private AdjustmentMode adjMode;
    private CopySlots copySlots;
    private Axis axis;
    private double eulerAngleChange;
    private double degreeAngleChange;
    private double movChange;
    private Menu chestMenu;
    private boolean cancelMenuOpen = false;
    private int uncancelTaskID = 0;
    private ArmorStand target;
    public EquipmentMenu equipMenu;
    private ArrayList<ArmorStand> targetList = null;
    private int targetIndex = 0;

    public PlayerEditor(UUID uuid, Main plugin) {
        this.uuid = uuid;
        this.plugin = plugin;
        eMode = EditMode.NONE;
        adjMode = AdjustmentMode.COARSE;
        axis = Axis.X;
        copySlots = new CopySlots();
        eulerAngleChange = getManager().coarseAdj;
        degreeAngleChange = eulerAngleChange / Math.PI * 180;
        movChange = getManager().coarseMov;
        chestMenu = new Menu(this);
    }

    public void setMode(EditMode editMode) {
        this.eMode = editMode;
        this.getPlayer().sendMessage("§eMod nastaven na: §6" + editMode.toString().toLowerCase());
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
        this.getPlayer().sendMessage("§eOsa nastavena na: §6" + axis.toString().toLowerCase());
    }

    public void setAdjMode(AdjustmentMode adjMode) {
        this.adjMode = adjMode;
        if (adjMode == AdjustmentMode.COARSE) {
            eulerAngleChange = getManager().coarseAdj;
            movChange = getManager().coarseMov;
        } else {
            eulerAngleChange = getManager().fineAdj;
            movChange = getManager().fineMov;
        }
        degreeAngleChange = eulerAngleChange / Math.PI * 180;
        this.getPlayer().sendMessage("§eRozliseni nastaveno na: §6" + adjMode.toString().toLowerCase());
    }

    public void setCopySlot(byte slot) {
        copySlots.changeSlots(slot);
        this.getPlayer().sendMessage("§eVybran kopirovaci slot: §6" + String.valueOf((slot + 1)));
    }

    public void editArmorStand(ArmorStand armorStand) {
        armorStand = attemptTarget(armorStand);
        if (canBuild(armorStand)) {
            switch (eMode) {
                case LEFTARM:
                    armorStand.setLeftArmPose(subEulerAngle(armorStand.getLeftArmPose()));
                    break;
                case RIGHTARM:
                    armorStand.setRightArmPose(subEulerAngle(armorStand.getRightArmPose()));
                    break;
                case BODY:
                    armorStand.setBodyPose(subEulerAngle(armorStand.getBodyPose()));
                    break;
                case HEAD:
                    armorStand.setHeadPose(subEulerAngle(armorStand.getHeadPose()));
                    break;
                case LEFTLEG:
                    armorStand.setLeftLegPose(subEulerAngle(armorStand.getLeftLegPose()));
                    break;
                case RIGHTLEG:
                    armorStand.setRightLegPose(subEulerAngle(armorStand.getRightLegPose()));
                    break;
                case SHOWARMS:
                    toggleArms(armorStand);
                    break;
                case SIZE:
                    toggleSize(armorStand);
                    break;
                case INVISIBLE:
                    toggleVisible(armorStand, getPlayer());
                    break;
                case BASEPLATE:
                    togglePlate(armorStand);
                    break;
               /* case GRAVITY:
                    toggleGravity(armorStand);
                    break;*/
                case COPY:
                    copy(armorStand);
                    break;
                case PASTE:
                    paste(armorStand);
                    break;
                case PLACEMENT:
                    move(armorStand);
                    break;
                case ROTATE:
                    rotate(armorStand);
                    break;
                case DISABLESLOTS:
                    toggleDisableSlots(armorStand);
                    break;
                case RESET:
                    resetPosition(armorStand);
                    break;
                case EQUIPMENT:
                    openEquipment(armorStand);
                    break;
                case GLOWING:
                    toggleGlowing(armorStand);
                    break;
                case DEBUG:
                    debugInfo(armorStand, getPlayer());
                    break;
                case NONE:
                    this.getPlayer().sendMessage("§cTakovy mod neexistuje!");
                    break;
            }
        } else {
            this.getPlayer().sendMessage("§cZde nemas pravo stavet/upravovat armorstandy!");
        }
    }

    ArmorStand attemptTarget(ArmorStand armorStand) {
        if (target == null) return armorStand;
        if (target.getWorld() != getPlayer().getWorld()) return armorStand;
        if (target.getLocation().distanceSquared(getPlayer().getLocation()) > 100) return armorStand;
        armorStand = target;
        glow(armorStand);
        return armorStand;
    }

    private void openEquipment(ArmorStand armorStand) {
        equipMenu = new EquipmentMenu(this, armorStand);
        equipMenu.open();
    }

    private void toggleGlowing(ArmorStand armorStand) {
        if (!armorStand.isGlowing()) {
            armorStand.setGlowing(true);
            return;
        }
        armorStand.setGlowing(false);
    }

    private void debugInfo(ArmorStand armorStand, Player player) {
        DecimalFormat value = new DecimalFormat("#.#");
        player.sendMessage("§a§lArmorStand Debug Info:");
        player.sendMessage("§7Position: §fX: " + value.format(armorStand.getLocation().getX()) + ", Y: " + value.format(armorStand.getLocation().getY()) + ", Z: " + value.format(armorStand.getLocation().getZ()));
        player.sendMessage("§7Head rotation: §fX: " + value.format(armorStand.getHeadPose().getX()) + ", Y: " + value.format(armorStand.getHeadPose().getY()) + ", Z: " + value.format(armorStand.getHeadPose().getZ()));
        player.sendMessage("§7Body rotation: §fX: " + value.format(armorStand.getBodyPose().getX()) + ", Y: " + value.format(armorStand.getBodyPose().getY()) + ", Z: " + value.format(armorStand.getBodyPose().getZ()));
        player.sendMessage("§7Right hand: §fX:" + value.format(armorStand.getRightArmPose().getX()) + ", Y: " + value.format(armorStand.getRightArmPose().getY()) + ", Z: " + value.format(armorStand.getRightArmPose().getZ()));
        player.sendMessage("§7Left hand: §fX: " + value.format(armorStand.getLeftArmPose().getX()) + ", Y: " + value.format(armorStand.getLeftArmPose().getY()) + ", Z: " + value.format(armorStand.getLeftArmPose().getZ()));
        player.sendMessage("§7Right leg: §fX: " + value.format(armorStand.getRightLegPose().getX()) + ", Y: " + value.format(armorStand.getRightLegPose().getY()) + ", Z: " + value.format(armorStand.getRightLegPose().getZ()));
        player.sendMessage("§7Left leg: §fX: " + value.format(armorStand.getLeftLegPose().getX()) + ", Y: " + value.format(armorStand.getLeftLegPose().getY()) + ", Z: " + value.format(armorStand.getLeftLegPose().getZ()));

    }

    private void resetPosition(ArmorStand armorStand) {
        armorStand.setHeadPose(new EulerAngle(0, 0, 0));
        armorStand.setBodyPose(new EulerAngle(0, 0, 0));
        armorStand.setLeftArmPose(new EulerAngle(0, 0, 0));
        armorStand.setRightArmPose(new EulerAngle(0, 0, 0));
        armorStand.setLeftLegPose(new EulerAngle(0, 0, 0));
        armorStand.setRightLegPose(new EulerAngle(0, 0, 0));
    }

    public void reverseEditArmorStand(ArmorStand armorStand) {
        armorStand = attemptTarget(armorStand);
        if (canBuild(armorStand)) {
            switch (eMode) {
                case LEFTARM:
                    armorStand.setLeftArmPose(addEulerAngle(armorStand.getLeftArmPose()));
                    break;
                case RIGHTARM:
                    armorStand.setRightArmPose(addEulerAngle(armorStand.getRightArmPose()));
                    break;
                case BODY:
                    armorStand.setBodyPose(addEulerAngle(armorStand.getBodyPose()));
                    break;
                case HEAD:
                    armorStand.setHeadPose(addEulerAngle(armorStand.getHeadPose()));
                    break;
                case LEFTLEG:
                    armorStand.setLeftLegPose(addEulerAngle(armorStand.getLeftLegPose()));
                    break;
                case RIGHTLEG:
                    armorStand.setRightLegPose(addEulerAngle(armorStand.getRightLegPose()));
                    break;
                case PLACEMENT:
                    reverseMove(armorStand);
                    break;
                case ROTATE:
                    reverseRotate(armorStand);
                    break;
                default:
                    editArmorStand(armorStand);
            }
        } else {
            this.getPlayer().sendMessage("§cZde nemas pravo stavet/upravovat armorstandy!");
        }
    }

    private void move(ArmorStand armorStand) {
        Location loc = armorStand.getLocation();
        switch (axis) {
            case X:
                loc.add(movChange, 0, 0);
                break;
            case Y:
                loc.add(0, movChange, 0);
                break;
            case Z:
                loc.add(0, 0, movChange);
                break;
        }
        armorStand.teleport(loc);
    }

    private void reverseMove(ArmorStand armorStand) {
        Location loc = armorStand.getLocation();
        switch (axis) {
            case X:
                loc.subtract(movChange, 0, 0);
                break;
            case Y:
                loc.subtract(0, movChange, 0);
                break;
            case Z:
                loc.subtract(0, 0, movChange);
                break;
        }
        armorStand.teleport(loc);
    }

    private void rotate(ArmorStand armorStand) {
        Location loc = armorStand.getLocation();
        float yaw = loc.getYaw();
        loc.setYaw((yaw + 180 + (float) degreeAngleChange) % 360 - 180);
        armorStand.teleport(loc);
    }

    private void reverseRotate(ArmorStand armorStand) {
        Location loc = armorStand.getLocation();
        float yaw = loc.getYaw();
        loc.setYaw((yaw + 180 - (float) degreeAngleChange) % 360 - 180);
        armorStand.teleport(loc);
    }

    private void copy(ArmorStand armorStand) {
        copySlots.copyDataToSlot(armorStand);
        this.getPlayer().sendMessage("§eStav armorstandu okopirovan do slotu: §6" + (copySlots.currentSlot + 1));
        setMode(EditMode.PASTE);
    }

    private void paste(ArmorStand armorStand) {
        if (canBuild(armorStand)) {
            ArmorStandData data = copySlots.getDataToPaste();
            if (data == null) return;
            armorStand.setHeadPose(data.headPos);
            armorStand.setBodyPose(data.bodyPos);
            armorStand.setLeftArmPose(data.leftArmPos);
            armorStand.setRightArmPose(data.rightArmPos);
            armorStand.setLeftLegPose(data.leftLegPos);
            armorStand.setRightLegPose(data.rightLegPos);
            armorStand.setSmall(data.size);
            armorStand.setGravity(data.gravity);
            armorStand.setBasePlate(data.basePlate);
            armorStand.setArms(data.showArms);
            armorStand.setVisible(data.visible);
            armorStand.setGlowing(data.isGlowing);
            if (this.getPlayer().getGameMode() == GameMode.CREATIVE) {
                armorStand.setHelmet(data.head);
                armorStand.setChestplate(data.body);
                armorStand.setLeggings(data.legs);
                armorStand.setBoots(data.feetsies);
                armorStand.setItemInHand(data.rightHand);
                armorStand.getEquipment().setItemInOffHand(data.leftHand);
            }
            this.getPlayer().sendMessage("§eNastaveni bylo okopirovano z slotu: §6" + (copySlots.currentSlot + 1));
        } else {
            this.getPlayer().sendMessage("§cZde nemas pravo stavet/upravovat armorstandy!");
        }
    }

    private void toggleDisableSlots(ArmorStand armorStand) {

    }

    private void toggleGravity(ArmorStand armorStand) {
        armorStand.setGravity(Util.toggleFlag(armorStand.hasGravity()));
        String state = armorStand.hasGravity() ? "on" : "off";
        this.getPlayer().sendMessage("§eGravitace nastavena na: §6" + state);
    }

    void togglePlate(ArmorStand armorStand) {
        armorStand.setBasePlate(Util.toggleFlag(armorStand.hasBasePlate()));
    }

    void toggleArms(ArmorStand armorStand) {
        armorStand.setArms(Util.toggleFlag(armorStand.hasArms()));
    }

    void toggleVisible(ArmorStand armorStand, Player player) {
        if (armorStand.isVisible()) {
            armorStand.setMetadata("ase", new FixedMetadataValue(plugin, "invisible"));
            armorStand.setVisible(false);
            return;
        }
        armorStand.removeMetadata("ase", plugin);
        armorStand.setVisible(true);
    }

    void toggleSize(ArmorStand armorStand) {
        armorStand.setSmall(Util.toggleFlag(armorStand.isSmall()));
    }

    void cycleAxis(int i) {
        int index = axis.ordinal();
        index += i;
        index = index % Axis.values().length;
        while (index < 0) {
            index += Axis.values().length;
        }
        setAxis(Axis.values()[index]);
    }

    private EulerAngle addEulerAngle(EulerAngle angle) {
        switch (axis) {
            case X:
                angle = angle.setX(Util.addAngle(angle.getX(), eulerAngleChange));
                break;
            case Y:
                angle = angle.setY(Util.addAngle(angle.getY(), eulerAngleChange));
                break;
            case Z:
                angle = angle.setZ(Util.addAngle(angle.getZ(), eulerAngleChange));
                break;
            default:
                break;
        }
        return angle;
    }

    private EulerAngle subEulerAngle(EulerAngle angle) {
        switch (axis) {
            case X:
                angle = angle.setX(Util.subAngle(angle.getX(), eulerAngleChange));
                break;
            case Y:
                angle = angle.setY(Util.subAngle(angle.getY(), eulerAngleChange));
                break;
            case Z:
                angle = angle.setZ(Util.subAngle(angle.getZ(), eulerAngleChange));
                break;
            default:
                break;
        }
        return angle;
    }

    public void setTarget(ArrayList<ArmorStand> armorStands) {
        if (armorStands == null || armorStands.isEmpty()) {
            target = null;
            targetList = null;
            this.getPlayer().sendMessage("§eVyber armorstandu je odemknut!");
            return;
        }

        if (targetList == null) {
            targetList = armorStands;
            targetIndex = 0;
            this.getPlayer().sendMessage("§eVyber armorstandu je uzamknut Nyni budes upravovat pouze tento!");
        } else {
            boolean same = targetList.size() == armorStands.size();
            if (same) for (ArmorStand as : armorStands) {
                same = targetList.contains(as);
                if (!same) break;
            }

            if (same) {
                targetIndex = ++targetIndex % targetList.size();
            } else {
                targetList = armorStands;
                targetIndex = 0;
                this.getPlayer().sendMessage("§eVyber armorstandu je uzamknut Nyni budes upravovat pouze tento!");
            }
        }
        target = targetList.get(targetIndex);
        glow(target);
    }

    private void glow(ArmorStand armorStand) {
        armorStand.removePotionEffect(PotionEffectType.GLOWING);
        armorStand.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 15, 1, false, false));
    }

    public PlayerEditorManager getManager() {
        return plugin.editorManager;
    }

    public Player getPlayer() {
        return plugin.getServer().getPlayer(getUUID());
    }

    public UUID getUUID() {
        return uuid;
    }

    boolean canBuild(ArmorStand armorstand) {
        for (ASEProtection prot : plugin.getProtections()) {
            if (!prot.canEdit(getPlayer(), armorstand)) return false;
        }
        return true;
    }

    public void openMenu() {
        plugin.getServer().getScheduler().runTaskLater(plugin, new OpenMenuTask(), 1);
    }

    public void cancelOpenMenu() {
        if (cancelMenuOpen) {
            plugin.getServer().getScheduler().cancelTask(uncancelTaskID);
        } else {
            cancelMenuOpen = true;
        }
        uncancelTaskID = plugin.getServer().getScheduler().runTaskLater(plugin, new MenuUncancelTask(), 3).getTaskId();
    }

    class OpenMenuTask implements Runnable {

        @Override
        public void run() {
            if (!cancelMenuOpen) {
                chestMenu.openMenu();
            }
        }
    }

    class MenuUncancelTask implements Runnable {

        @Override
        public void run() {
            cancelMenuOpen = false;
        }
    }
}