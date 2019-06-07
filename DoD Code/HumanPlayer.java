import java.util.Scanner;
/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */
public class HumanPlayer {

    private String command;
    private Scanner input;
    private String output;
    private GameLogic game;
    private String moveString;
    private String testCommand;
    private char direction;

    //constructor
    public HumanPlayer(){
        input = new Scanner(System.in);
        //game = new GameLogic();
    }

    /**
     * Reads player's input from the console.
     * <p>
     * return : A string containing the input the player entered.
     */
    protected String getInputFromConsole() {
        command = input.nextLine();
        return command;
    }

    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     *
     * @param command : Input entered by the user.
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    protected String getNextAction(String command) {
		//System.out.println(command);
        testCommand = command.toLowerCase();
        //MOVE - check if move, check if NSWE, call move method
        try{
            moveString = testCommand.substring(0,4);
        

        if (moveString.equals("move")){
            direction = testCommand.charAt(testCommand.length()-1);
            return testCommand;
        }
        //PICKUP - check if gold in position, call gold method
        else if (testCommand.equals("pickup")){
			return testCommand;
        }
        //LOOK - output map
        else if(testCommand.equals("look")){
			return testCommand;
        }
        //HELLO - output gold needed to exit
        else if(testCommand.equals("hello")){
			return testCommand;
        }
        //QUIT - quit game, put gamerunning to false
        else if(testCommand.equals("quit")){
			return testCommand;
        }
        else if(testCommand.equals("exit")){
            return testCommand;
        }
		else{
			return "Invalid Command";
		}
        }
        catch (Exception e) {
            return testCommand;
        }

    }




}