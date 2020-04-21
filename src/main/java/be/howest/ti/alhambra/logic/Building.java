package be.howest.ti.alhambra.logic;

public class Building {
    Buildingtype type;
    private int cost;
    private Walling walls;

    public Building(Buildingtype type, int cost, Walling walls) {
        this.type = type;
        this.cost = cost;
        this.walls = walls;
    }

}
