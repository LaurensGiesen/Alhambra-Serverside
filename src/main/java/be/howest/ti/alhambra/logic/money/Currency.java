package be.howest.ti.alhambra.logic.money;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {

    BLUE, GREEN, ORANGE, YELLOW;

    @JsonValue
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
