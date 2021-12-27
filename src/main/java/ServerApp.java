import java.io.IOException;
import java.util.Scanner;

public class ServerApp {
    public static String execute(App app,String input) throws IOException {
            String response="";
            app.history.add(input);

            if (input.equals("pd"))
                response=app.handlePd();
            else if (input.equals("pu"))
                response=app.handlePu();
            else if (input.equals("list steps"))
                response=app.handleListSteps();
            else if  (input.equals("list figures"))
                response=app.handleListFigures();
            else {
                try{
                    //System.out.println(input);
                    String sa[] =input.split(" ");
                    String command = sa[0];
                    String value = sa[1];
                    //System.out.println(command);
                    //System.out.println(value);
                    if (command.equals("color")) response=app.handleColor(value);
                    else if (command.equals("move")) response=app.handleMove(value);
                    else if (command.equals("angle")) response=app.handleAngle(value);
                    else{
                        response=app.handleUnknownCommand();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    response=app.handleUnknownCommand();
                }
            }
            response=response+"\t"+app.print_state();
            return response;

    }
}
