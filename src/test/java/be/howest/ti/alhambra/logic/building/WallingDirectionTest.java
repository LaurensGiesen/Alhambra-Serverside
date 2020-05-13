package be.howest.ti.alhambra.logic.building;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WallingDirectionTest {

    @Test
    void testToString() {

        String example = "north";
        assertEquals(example, WallingDirection.NORTH.toString());

    }
}