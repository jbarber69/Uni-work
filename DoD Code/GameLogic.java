import java.util.Random;
import java.util.Scanner;

/**
 * Contains the main logic part of the game, as it processes.
 *
 */
public class GameLogic {
	
	private static Map map;
    private char movement;
    private static HumanPlayer player1;
    private static boolean gameOn = true;
    private int column;
    private int row;
    private int goldTotal = 0;
    private boolean isGold;
    //values of the column and row of the player
	private int playerRow;
	private int playerColumn;
	private String mapStr;
	private static char[][] playerMap;
	private static GameLogic game;
    private static char direction;	
    private static String moveString;
    private static Bot bot1;
    private boolean gameWon;

	/**
	 * Default constructor
	 */
	public GameLogic() {
		map = new Map();
        player1 = new HumanPlayer();
	}

    /**
     * @return if the game is running.
     */
    protected boolean gameRunning() {
        if (gameOn == true){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return : Returns back gold player requires to exit the Dungeon.
     */
    protected String hello() {
        return "gold to win:" + Integer.toString(goldTotal);
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @return : Protocol if success or not.
     */
    protected String move(char direction) {
            movement = direction;
            if (movement == 'n'){
                return map.movePlayer(-1, 0);
				
            }
            else if (movement == 'e'){
                return map.movePlayer(0, 1);
				
            }
            else if (movement == 's'){
                return map.movePlayer(1, 0);   
				
            }
            else if (movement == 'w'){
                return map.movePlayer(0, -1); 
				
            }
            else{
			 return "Illegal movement"; 
            }  
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    protected String look() {
        mapStr = "";
		playerMap = map.getMap();
		playerRow = map.findPlayerRow();
		playerColumn = map.findPlayerColumn();
        //make sure not of of bounds
        if (((playerRow - 2) > 0 && (playerRow + 2) < playerMap.length) && ((playerColumn - 2)>0 && (playerColumn + 2) < playerMap[0].length)){
            for(int i = (playerRow-2); i < (playerRow+3); i++){
                for(int j = (playerColumn-2); j < (playerColumn+3); j++){
                    mapStr = mapStr + playerMap[i][j];
                }
                mapStr = mapStr + System.lineSeparator();
            } 
            return mapStr;
        }
        else{
            for(int i = (playerRow-1); i < (playerRow+2); i++){
                for(int j = (playerColumn-1); j < (playerColumn+2); j++){
                    mapStr = mapStr + playerMap[i][j];
                }
              mapStr = mapStr + System.lineSeparator();
            } 
            return mapStr;
        }
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @return If the player successfully picked-up gold or not.
     */
    protected String pickup() {
        isGold = map.goldCheck();
        if(isGold == true){
            goldTotal = goldTotal + 1;
			return "SUCCESS";
        }
        else{
            return "FAIL";
        }
    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame() {
        gameOn = false;
    }

    protected String exit(){
        gameWon = map.exitGame(goldTotal);
        if (gameWon == true){
            gameOn = false;
            return "you win!";
            
        }
        else{
            return "try again!";
        }

    }
	
    //processes the commands from the HumanPlayer class
	protected static void processCommand(String command){
        moveString = command.substring(0,4);
		if (moveString.equals("move")){
            if (command.length() > 4){
                direction = command.charAt(command.length()-1);
                game.move(direction);
            }
            else{
                System.out.println("No direction");
            }
        }
        //PICKUP - check if gold in position, call gold method
        else if (command.equals("pickup")){
            System.out.println(game.pickup());
        }
        //LOOK - output map
        else if(command.equals("look")){
            System.out.println(game.look());
        }
        //HELLO - output gold needed to exit
        else if(command.equals("hello")){
            System.out.println(game.hello());
        }
        //QUIT - quit game, put gamerunning to false
        else if(command.equals("quit")){
            game.quitGame();
        }
        //EXIT - if the required amount of gold has been aquired and player is on an exit square, quitgame
        else if(command.equals("exit")){
            System.out.println(game.exit());
        }
        else{
            System.err.println(command);
        }
	}

	public static void main(String[] args) {
        player1 = new HumanPlayer();
        map = new Map();
		game = new GameLogic();
        bot1 = new Bot();

        map.addPlayer();
        map.addBot();
        System.out.print("Map name: ");
        System.out.println(map.getMapName());
        //map.readMap("example_map.txt");
        playerMap = map.getMap();

        for(int i = 0; i < playerMap.length; i++){
            for(int j = 0; j < playerMap[0].length; j++){
                System.out.print(playerMap[i][j]);
            }
            System.out.println();
        }


        while(gameOn == true){
               processCommand( player1.getNextAction(player1.getInputFromConsole()) );
               bot1.moveBot();
                for(int i = 0; i < playerMap.length; i++){
                    for(int j = 0; j < playerMap[0].length; j++){
                        System.out.print(playerMap[i][j]);
                    }
                    System.out.println();
                }
        }
		
    }
}