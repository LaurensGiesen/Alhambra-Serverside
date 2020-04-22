package be.howest.ti.alhambra.logic.building;



public enum Buildingtype {
    PAVILION, SERAGLIO, ARCADES, CHAMBERS, GARDEN, TOWER;

    @Override
    public String toString() {
        return Buildingtype.super.toString().toLowerCase();
    }
}
