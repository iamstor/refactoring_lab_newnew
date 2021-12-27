package model;

import lombok.Data;

@Data
public class Turtle {
    private Location location = new Location();
    private int direction = 0; //0 - up,1 - right,2 - down,3 left//
    private boolean pen = false;
    private String color = "green";
    private String user;




    @Override
    public String toString() {
        String pen_value="";
        if (pen) pen_value="down";
        else pen_value="up";



        return "Turtle state is {" +
                "location=" + location +
                ", direction=" + direction +
                ", pen=" + pen_value +
                ", color='" + color +
                ", user='" + user + '\'' +
                '}';
    }
}
