package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WallingDirection {

    NORTH, EAST, WEST, SOUTH;

    @JsonValue
    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}
