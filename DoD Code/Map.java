import java.util.Random;
import java.util.Arrays;
import java.io.*;
import java.util.*;
/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {

	/* Representation of the map */
	private char[][] map;
    //private char[][] newMap;
	
	/* Map name */
	private String mapName;
	
	/* Gold required for the human player to win */
	private int goldRequired;

	// the previous character under player
	private char replaceStr;
    private char botReplaceStr;

	//values for the column and row
	private int row;
	private int column;

    private Random ran;
    private int botRow = 4;
    private int botColumn = 8;

	//boolean value which signals that the player has been added to the game
	private boolean playerIn;
    private boolean botIn;

	private boolean playerFound;

	//private Random ran;

	private char prevString;
    private char botPrevString;
	
    private int i;
    private int j;

    private int newRows;
    private int newColumns;

	/**
	 * Default constructor, creates the default map "Very small Labyrinth of doom".
	 */
	public Map() {
		mapName = "Very small Labyrinth of Doom";
		goldRequired = 2;
		map = new char[][]{
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
		ran = new Random();


	}
	
	/**
	 * Constructor that accepts a map to read in from.
	 *
	 * @param : The filename of the map file.
	 */
	public Map(String fileName) {
        fileName = "example_map.txt";
		readMap(fileName);
	}

    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return goldRequired;
    }

    /**
     * @return : The map as stored in memory.
     */
    protected char[][] getMap() {
        return map;
    }


    /**
     * @return : The name of the current map.
     */
    protected String getMapName() {
        return mapName;
    }


    /**
     * Reads the map from file.
     *
     * @param : Name of the map's file.
     */
    protected void readMap(String fileName) throws FileNotFoundException {
        try{
            StringBuilder mapLine = new StringBuilder();
            String mapStr = "";
    
            File mapFile = new File(fileName);
        
            //Creating Scanner instnace to read File in Java
            Scanner scanner = new Scanner(mapFile);
        
            //Reading each line of file using Scanner class
            int lineNumber = 1;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                //System.out.println("line " + lineNumber + " :" + line);
                lineNumber++;
                if (lineNumber == 3){
                    newColumns = line.length();
                }
    
            }
            newRows = lineNumber;
            char[][] newMap = new char[newRows][newColumns];
    
            lineNumber = 1;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.substring(0,3).equals("name")) {
                    mapName = line.substring(4, line.length());
                }
                else if (line.substring(0, 2).equals("win")) {
                    goldRequired = Integer.parseInt(line.substring(line.length()-1, line.length()));
                }
                else{
                    mapLine.append(line);
                    mapStr = mapLine.toString();
                    for(int i = 0; i < newRows; i++){
                        for(int j = 0; j < newColumns; j++){
                            newMap[i][j] = mapStr.charAt(j);
    
                        }
                    }
                }
            }
            map = newMap;
        }
        catch(Exception e){
            Map();
        }
    }

    protected void addPlayer(){
    	playerIn = false;
    	while(playerIn == false){
    		column = ran.nextInt(map[0].length);
        	row = ran.nextInt(map.length);
    		replaceStr = map[row][column];
    		if (replaceStr != 'G' && replaceStr != '#'){
    			map[row][column] = 'P';
    			playerIn = true;
    		}
    	}
		System.out.println("player added");
    }

	//function to move the player
    protected String movePlayer(int rowOffset, int columnOffset){
        for(i = 0; i < map.length; i ++){
    		for(j = 0; j < (map[i].length); j++){
                if(map[i][j] == 'P'){
                    column = j;
                    row = i;

                }
    		}
    	}
        if(map[row + rowOffset][column + columnOffset] != '#'){
    	   prevString = replaceStr;
    	   map[row][column] = prevString;
    	   prevString = map[row + rowOffset][column + columnOffset];
    	   	map[row + rowOffset][column + columnOffset] = 'P';
    	   	return "SUCCESS";
        }
        else{
            return "FAIL";
        }
    }

	//checks whether the player is standing on gold
    protected boolean goldCheck(){
    	if(prevString == 'G'){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    protected boolean exitGame(int gold){
        if(gold >= goldRequired && prevString == 'E'){
            return true;
        }
        else{
            return false;
        }
    }
	
	protected int findPlayerColumn(){
		for(i = 0; i < map.length; i ++){
    		for(j = 0; j < map[0].length; j++){
    			if(map[i][j] == 'P'){
                    row = i;
                    column = j;
    			}
    		}
    	}
		return column;
	}
	
	protected int findPlayerRow(){
		for(i = 0; i < map.length; i ++){
    		for(j = 0; j < map[0].length; j++){
    			if(map[i][j] == 'P'){
                    row = i;
                    column = j;
    			}
    		}
    	}
		return row;
	}

    protected void addBot(){
        botIn = false;
        while(botIn == false){
            botReplaceStr = map[botRow][botColumn];
            System.out.println(botReplaceStr);
            if (botReplaceStr != 'G' && botReplaceStr != '#'){
                map[botRow][botColumn] = 'B';
                botIn = true;
            }
        }
        System.out.println("bot added");
    }

    protected String moveBot(int rowOffset, int columnOffset){
        if(map[botRow + rowOffset][botColumn + columnOffset] != '#'){
            botPrevString = '.';
            map[botRow][botColumn] = prevString;
            botRow = botRow + rowOffset;
            botColumn = botColumn + columnOffset;
            botPrevString = map[botRow][botColumn];
            map[botRow][botColumn] = 'B';
            return "SUCCESS";
        }
        else{
            return "FAIL";
        }
    }


}
