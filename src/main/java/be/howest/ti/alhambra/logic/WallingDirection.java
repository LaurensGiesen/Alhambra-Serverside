package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WallingDirection {

    NORTH, EAST, SOUTH, WEST;

    @JsonValue
    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}
