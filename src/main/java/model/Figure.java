package model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Figure {

    private ArrayList<Location> locationsList;
    private String type;

    public Figure(ArrayList<Location> locationsList, int turnCount) {
        this.locationsList = locationsList;
        if (turnCount == 3) {
            this.type = "triangle";
        } else if (turnCount == 4) {
            this.type = "rectangle";
        } else if (turnCount == 5) {
            this.type = "pentagon";
        }else if (turnCount == 6) {
            this.type = "hexagon";
        } else {
            this.type = "unknown figure";
        }
    }

    @Override
    public String toString() {
        String result = "[";
        for (Location l : locationsList)
            result += l.toString();
        result += "] - "+this.type;
        return result;
//        "Figure{" +
//                "coordinates=" + locationsList +
//                ", type='" + type + '\'' +
//                '}';
    }
}
