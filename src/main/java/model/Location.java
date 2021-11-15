package model;

import lombok.Data;

@Data
public class Location {
    private int x =0;
    private int y=0;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location() {
    }

    @Override
    public String toString() {
        return "(" + x +"," + y + ')';
    }
}
