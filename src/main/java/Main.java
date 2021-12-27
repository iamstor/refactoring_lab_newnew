import model.Turtle;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        App app = new App();
        app.load();
        Scanner scanner = new Scanner(System.in);
        app.print_help();
        System.out.println("Введите логин");
        String input2=scanner.nextLine();
        app.history.add(input2);
        app.handleLogin(input2);

        while (scanner.hasNextLine()) {
            String input=scanner.nextLine();
            app.history.add(input);
            if (input.equals("pd"))
                app.handlePd();
            else if (input.equals("pu"))
                app.handlePu();
            else if (input.equals("list steps"))
                app.handleListSteps();
            else if  (input.equals("list figures"))
                app.handleListFigures();
            else if  (input.equals("exit"))
                app.handleExit();
            else {
                try{
                    //System.out.println(input);
                    String sa[] =input.split(" ");
                    String command = sa[0];
                    String value = sa[1];
                    //System.out.println(command);
                    //System.out.println(value);
                    if (command.equals("color")) app.handleColor(value);
                    else if (command.equals("move")) app.handleMove(value);
                    else if (command.equals("angle")) app.handleAngle(value);
                    else{
                        app.handleUnknownCommand();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    app.handleUnknownCommand();
                }
            }
            app.print_state();
        }
    }
}
