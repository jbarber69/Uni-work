import java.util.Random;

public class Bot{

	private Map map;
	private char[] directionArray;
	private Random rand;
	private int randInt;
	private char direction;


	public Bot(){
		map = new Map();
		directionArray = new char[]{'n','e','s','w'};
		rand = new Random();
	}

	//move the bot
	public void moveBot(){
		randInt = rand.nextInt(3);
		direction = directionArray[randInt];
		if (direction == 'n'){
            System.out.println(map.moveBot(-1, 0));
        }
        else if (direction == 'e'){
            System.out.println(map.moveBot(0, 1));		
        }
        else if (direction == 's'){
            System.out.println(map.moveBot(1, 0));   				
        }
   	    else if (direction == 'w'){
            System.out.println(map.moveBot(0, -1));				
        }
	}


	//look for the player
}