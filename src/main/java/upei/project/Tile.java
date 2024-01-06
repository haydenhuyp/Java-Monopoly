package upei.project;

import java.util.ArrayList;

public abstract class Tile {
    private String name;

    public Tile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
