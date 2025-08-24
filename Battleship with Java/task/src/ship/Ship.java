package ship;

import java.util.LinkedHashMap;

public class Ship {
    String name;
    int length;
    LinkedHashMap<String, Boolean> parts;
    boolean sunk;
    String ID;

    public Ship(String name, int length, String[] partsArr, String ID) {
        this.name = name;
        this.length = length;
        this.parts = addParts(partsArr);
        this.sunk = false;
        this.ID = ID;
    }

    public LinkedHashMap<String, Boolean> getParts() {
        return parts;
    }

    public boolean isSunk() {
        int hitCount = 0;
        for (boolean hit : parts.values()) {
            if (hit) {
                hitCount++;
            }
        }

        if (hitCount == parts.size()) {
            sunk = true;
        }

        return sunk;
    }

    public String getID() {
        return ID;
    }

    private LinkedHashMap<String, Boolean> addParts(String[] partsArr) {
        parts = new LinkedHashMap<>();

        for (String part : partsArr) {
            parts.put(part, false);
        }

        return parts;
    }
}
