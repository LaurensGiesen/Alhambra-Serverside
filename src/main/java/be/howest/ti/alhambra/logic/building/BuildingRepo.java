package be.howest.ti.alhambra.logic.building;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildingRepo {

    private static List<Building> allBuildings = null;

    private BuildingRepo() {
    }

    public static List<Building> getAllBuildings() {
        if (allBuildings == null) {
            allBuildings = loadFromFile();
        }
        return Collections.unmodifiableList(allBuildings);
    }

    private static List<Building> loadFromFile() {
        try (InputStream in = BuildingRepo.class.getResourceAsStream("/buildings.json")) {
            return Arrays.asList(
                    Json.decodeValue(Buffer.buffer(in.readAllBytes()),
                            Building[].class)
            );
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to load buildings", ex);
            return Collections.emptyList();
        }
    }

}