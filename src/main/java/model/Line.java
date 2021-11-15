package model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Line {
    private ArrayList<Location> locationsList = new ArrayList<Location>();
    private String color;

    @Override
    public String toString() {
        String result="[";
        for (Location l: locationsList)
            result+=l.toString();
        result+="]";
        return result;
    }
}
