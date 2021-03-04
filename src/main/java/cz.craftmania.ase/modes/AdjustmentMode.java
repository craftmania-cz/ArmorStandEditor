package cz.craftmania.ase.modes;

public enum AdjustmentMode {
    COARSE("Coarse"), FINE("Fine");

    private final String name;

    AdjustmentMode(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
