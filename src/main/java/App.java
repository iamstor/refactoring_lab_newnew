import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    Turtle turtle = new Turtle();
    User user = new User();

    public ArrayList<String> history = new ArrayList<String>();
    private ArrayList<Location> locations = new ArrayList<Location>();
    private ArrayList<Figure> figures = new ArrayList<Figure>();
    private int turnCount = 0;



    public void dump_login() throws IOException {
        Gson dump_gson_log = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer=new FileWriter("login.json");
        dump_gson_log.toJson(this.user, writer);
        writer.close();

    }

    public void dump() throws IOException {
        Gson dump_gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer=new FileWriter("turtle.json");
        dump_gson.toJson(this.turtle, writer);
        writer.close();

    }

    public void load() throws IOException {
        FileReader reader =new FileReader("turtle.json");
        Gson gson = new Gson();
        try{
            this.turtle = gson.fromJson(reader, Turtle.class);
        } catch (Exception e){
            this.turtle = new Turtle();
            System.out.println("not dump file found - creating new turtle");
        }
        reader.close();

    }


    public void load_login() throws IOException {
        FileReader reader =new FileReader("login.json");
        Gson gson1 = new Gson();
        try{
            this.user = gson1.fromJson(reader, User.class);
        } catch (Exception e){
            this.user = new User();
            System.out.println("not dump file found - creating new user");
        }
        reader.close();

    }


    public String print_help() {
        System.out.println("move X - move on X steps," + "\nangle X - turn on X degrees" +
                "\npd - pen down" +
                "\npu - pen up" +
                "\nlist steps - show history" +
                "\nlist figures - show painted figures" +
                "\nexit - exit"
        );
        return "move X - move on X steps," + "\nangle X - turn on X degrees" +
                "\npd - pen down" +
                "\npu - pen up" +
                "\nlist steps - show history" +
                "\nlist figures - show painted figures" +
                "\nexit - exit";
    }

    public void handle() {
        System.out.println("handle");
    }

    public String handleLogin(String login){
        turtle.setUser(login);
        System.out.println("Login accepted");
        return "login";
    }

    public String handlePd() {
        turtle.setPen(true);
        System.out.println("pen down");
        return "pen down";

    }

    public String handlePu() {
        turtle.setPen(false);
        System.out.println("pen up");
        this.locations=new ArrayList<Location>();
        this.turnCount=0;
        return "pen up";
    }

    public String handleListSteps() {
        System.out.println("steps:");
        String response="steps:\n";
        for (String s : this.history) {
            System.out.println(s);
            response += s + "\n";
        }
        return response;
    }


    public String handleListFigures() {
        System.out.println("figures:");
        String response="figures:\n";
        for (Figure f : this.figures) {
            System.out.println(f);
            response+=f+"\n";
        }
        return response;
    }


    public void handleExit() {
        System.exit(0);
    }

    public String handleAngle(String value) throws Exception {
        String response="";
        if (value.equals("90")) {
            int dir = this.turtle.getDirection();
            if (dir == 3) {
                dir = -1;
            }
            this.turtle.setDirection(dir + 1);
        } else if (value.equals("-90")) {
            int dir = this.turtle.getDirection();
            if (dir == 0) {
                dir = 4;
            }
            this.turtle.setDirection(dir - 1);
        } else if (value.equals("-180") || value.equals("180")) {
            int dir = this.turtle.getDirection();
            if (dir == 0) this.turtle.setDirection(2);
            else if (dir == 1) this.turtle.setDirection(3);
            else if (dir == 2) this.turtle.setDirection(0);
            else if (dir == 3) this.turtle.setDirection(1);
            else {
                throw new Exception("invalid direction");
            }

        } else if (value.equals("0")) {

        } else {
            System.out.println("invalid angle");
            response="invalid angle";
            //throw new AssertionError("invalid angle");
        }
        if (this.turtle.isPen()) this.turnCount+=1;

        System.out.print("Turned current direction:");
        System.out.println(this.turtle.getDirection());
        response="Turned current direction:"+Integer.toString(this.turtle.getDirection());

        return response;

    }

    public String handleColor(String value) {
        String response =  "";
        if (value.equals("green") || value.equals("black")) {
            System.out.print("color set: ");
            System.out.println(value);

            response="color set:"+value;

            this.turtle.setColor(value);
        }
        else {
            System.out.println("Only green and black is possible");
            response="Only green and black is possible";
        }
        return response;
    }

    private int checkCross() {
        for (int i = 0; i < this.locations.size()-1; i++) {
            for (int j = 0; j < this.locations.size()-1; j++) {
                Location l0=this.locations.get(i);
                Location l1=this.locations.get(i+1);
                Location l_0=this.locations.get(j);
                Location l_1=this.locations.get(j+1);

//                System.out.println("checking... (i/j) (xy,xy)");
//                System.out.println(i+" "+j);
//                System.out.println(l0+" "+l1+" | "+l_0+" "+l_1);


                if ((l0.getX()==l1.getX()) && (l_0.getX()==l_1.getX())) continue;
                if ((l0.getY()==l1.getY()) && (l_0.getY()==l_1.getY())) continue;

                if (l0.getX()==l1.getX() && l_0.getY()==l_1.getY()) {
                    int x=l0.getX();
                    if ((x<l_0.getX() && x>l_1.getX()) || (x>l_0.getX() && x<l_1.getX())){

                        int y=l_0.getY();

                        if ((y<l0.getY() && y>l1.getY()) || (y>l0.getY() && y<l1.getY())) return 1;
                    }

                }





            }
        }
        return 0;
    }


    public String handleMove(String value) throws Exception {
        String response="";
        int step;

        try {
            step = Integer.parseInt(value);
        } catch (Exception e) {
            step = 0;
            System.out.println("invalid move input");

        }

        int x = this.turtle.getLocation().getX();
        int y = this.turtle.getLocation().getY();

        if ((this.locations.size() == 0) &&(this.turtle.isPen())) this.locations.add(new Location(x, y));

        if (this.turtle.getDirection() == 0) y += step;
        else if (this.turtle.getDirection() == 1) x += step;
        else if (this.turtle.getDirection() == 2) y -= step;
        else if (this.turtle.getDirection() == 3) x -= step;
        else throw new Exception("invalid direction");

        this.turtle.setLocation(new Location(x, y));


        if (this.turtle.isPen()){
            this.locations.add(new Location(x, y));
        }

        if (this.checkCross()==1) {
            System.out.println("cross found!");
            this.figures.add(new Figure(this.locations,this.turnCount));
            this.locations=new ArrayList<Location>();
            this.turnCount=0;
        }


    return response;

    }

    public String handleUnknownCommand() {
        System.out.println("unknown command");
        return "unknown command";
    }

    public String print_state() throws IOException {
        System.out.println(this.turtle);
        this.dump();
        return this.turtle.toString();

    }

    public String get_state() throws IOException {
        return this.turtle.toString();
    }
}
