package be.howest.ti.alhambra.logic.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingtypeTest {

    @Test
    void testToString() {
        String example = "pavilion";
        assertEquals(example, Buildingtype.PAVILION.toString());
    }
}