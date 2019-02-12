import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * The Level class is responsible for managing all of the objects in your game.
 * The GameEngine creates a new Level object for each level, and then calls that
 * Level object's update() method repeatedly until it returns either "ADVANCE"
 * (to go to the next level), or "QUIT" (to end the entire game).
 * <br/><br/>
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Random randGen;</li>
 * <li>private Hero hero;</li>
 * <li>private Water[] water;</li>
 * <li>private ArrayList&lt;Pant&gt; pants;</li>
 * <li>private ArrayList&lt;Fireball&gt; fireballs;</li>
 * <li>private ArrayList&lt;Fire&gt; fires;</li>
 * </ul></tt>
 * @author Michael Lemus
 */
public class Level
{
	private Hero hero;
	private Graphic graphic;
	private float   speed;
	private int     controlType;
	private Graphic graphicWater;
	private float speed2;
	private float distanceTraveled;
	private Water [] water;
	private ArrayList<Pant> pants;
	private Fireball fireball;
	static ArrayList<Fireball> fireballs= new ArrayList<Fireball>();
	private Fire fire;
	private Fire tempFire;
	private ArrayList<Fire> fires= new ArrayList<Fire>();
	/**
	 * This constructor initializes a new Level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play.  In
	 * the process of this initialization, all of the objects in the current
	 * level should be instantiated and initialized to their beginning states.
	 * @param randGen is the only Random number generator that should be used
	 * throughout this level, by the Level itself and all of the Objects within.
	 * @param level is a string that either contains the word "RANDOM", or the 
	 * contents of a level file that should be loaded and played. 
	 */
	public Level(Random randGen, String level) 
	{ 
		pants = new ArrayList<Pant>(20);
		water = new Water[8];
		fireballs = new ArrayList<Fireball>();
		fires = new ArrayList<Fire>();
		
		// If the Level string says RANDOM then a random level is created
		if (level == "RANDOM"){
			createRandomLevel();
		}
		
		// otherwise run loadLevel method
		else{
			loadLevel(level);
		}
	}

	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of the rules of your game.
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 * @return When this method returns "QUIT" the game will end after a short
	 * 3 second pause and a message indicating that the player has lost.  When
	 * this method returns "ADVANCE", a short pause and win message will be 
	 * followed by the creation of a new level which replaces this one.  When
	 * this method returns anything else (including "CONTINUE"), the GameEngine
	 * will simply continue to call this update() method as usual. 
	 */
	public String update(int time) 
	{
		//Using the length of the array, this updates each element
		for (int i = 0; i < water.length; i++) {
			if(water[i] != null) {
				water[i] = water[i].update(time);
			}
		}
		
		//Updates the hero
		hero.update(time,water);
		
		//Using the length of the array, this updates each element
		for(int i =0; i<pants.size(); i++){
			pants.get(i).update(time);
		}
		//Using the length of the array, this updates each element
		for (int j = 0; j < fireballs.size(); j++) {
			if (fireballs.get(j) != null) {
				fireballs.get(j).update(time);
			} 
		}
		
		//Using the length of the array, this updates each element
		for (int k = 0; k < fires.size(); k++) {
			fires.get(k).update(time);
		}
		
		//This quits the game once the hero is struck with a fireball
		if(hero.handleFireballCollisions(fireballs) == true || pants.size()<=0){
			return "QUIT";
		}
		
		
		//Using the length of the array, this updates each element
		//And checks if the fireballs are colliding with the water
		for (int i=0; i<fireballs.size();i++){
			fireballs.get(i).handleWaterCollisions(water);			
		}
		
		//Using the length of the array, this updates each element
		//And checks if the fires are colliding with the water
		for (int i=0; i<fires.size();i++){
			fires.get(i).handleWaterCollisions(water);
		}
		
		//Using the length of the array, this updates each element
		//And creates new fires once pants are colliding with fireballs
		for(int i=0; i<pants.size();i++){
			tempFire = pants.get(i).handleFireballCollisions(fireballs);
			if(tempFire!=null){
				fires.add(tempFire);
				
				pants.remove(i);
				i--;
			}
		}
		
		//Checks if player removed all fires and allows them to 
		//continue to the next level
		for (int i=0; i<fires.size();i++){
			if(fires.get(i).shouldRemove()==true){
				fires.remove(i);
				if(fires.size()<=0){
					return "ADVANCE";
				}
			}
		}
		
		//Removes the actual fireballs from the arrayList
		for (int i = 0; i<fireballs.size(); i++){
			if(fireballs.get(i).shouldRemove() == true){
				fireballs.remove(i);
			}
		}	
		
		//Continues as long as the hero has not gotten hit by a fireball
		return "CONTINUE"; 
	}	
	
	
	/**
	 * This method returns a string of text that will be displayed in the
	 * upper left hand corner of the game window.  Ultimately this text should 
	 * convey the number of unburned pants and fires remaining in the level.  
	 * However, this may also be useful for temporarily displaying messages that 
	 * help you to debug your game.
	 * @return a string of text to be displayed in the upper-left hand corner
	 * of the screen by the GameEngine.
	 */
	public String getHUDMessage() 
	{
		String x= "Pants Left: "+pants.size();
		String y = "\nFires Left: "+ fires.size();
		return x + y; 
	}

	/**
	 * This method creates a random level consisting of a single Hero centered
	 * in the middle of the screen, along with 6 randomly positioned Fires,
	 * and 20 randomly positioned Pants.
	 */
	public void createRandomLevel() 
	{ 
		Random randgen = new Random();
		hero = new Hero(GameEngine.getWidth()/2, GameEngine.getHeight()/2,
				randgen.nextInt(3) + 1);
		
		speed2 = 0.7f;
		graphicWater = new Graphic("WATER");
		
		water=new Water[8];
		
		//This creates 8 different water objects and turns them to null
		for (int i=0; i<water.length;i++){
			water[i]=null;
		}
		
		//This adds random pants objects in random positions
		pants = new ArrayList<Pant>();
		for(int i =0; i<20; i++){
			int randx= randgen.nextInt(GameEngine.getWidth());
			int randy= randgen.nextInt(GameEngine.getHeight());
			pants.add(new Pant(randx, randy, randgen));
		}
		
		//This adds 6 fire objects at random positions
		for(int i =0; i<6; i++){//changed
			float randx= randgen.nextInt((int)GameEngine.getWidth());
			float randy= randgen.nextInt((int)GameEngine.getHeight());
			fires.add(i,new Fire(randx, randy, randgen));
		}
	}

	/**
	 * This method initializes the current game according to the Object location
	 * descriptions within the level parameter.
	 * @param level is a string containing the contents of a custom level file 
	 * that is read in by the GameEngine.  The contents of this file are then 
	 * passed to Level through its Constructor, and then passed from there to 
	 * here when a custom level is loaded.  You can see the text within these 
	 * level files by dragging them onto the code editing view in Eclipse, or 
	 * by printing out the contents of this level parameter.  Try looking 
	 * through a few of the provided level files to see how they are formatted.
	 * The first line is always the "ControlType: #" where # is either 1, 2, or
	 * 3.  Subsequent lines describe an object TYPE, along with an X and Y 
	 * position, formatted as: "TYPE @ X, Y".  This method should instantiate 
	 * and initialize a new object of the correct type and at the correct 
	 * position for each such line in the level String.
	 */
	public void loadLevel(String level) 
	{ 
		//we need to parse the string level for information
		Random randGen = new Random();	
		Scanner sc = new Scanner(level);
		String lineCompared = sc.nextLine();
		
		//Receives the control type
		String[] controlTypeLine= lineCompared.split(" ");
		int controlTypeNum = Integer.parseInt(controlTypeLine[1]);
		
		//Stating strings so we can compare them to the file
		String pant = "PANT";
		String fire = "FIRE";
		String hero1 = "HERO";
		while (sc.hasNextLine()) {
			lineCompared = sc.nextLine();
			
			// if the line in question of Level starts with PANT
			if ( lineCompared.startsWith("PANT")) {
				
				//the line of level trimmed, trimmed string is called pantsLocation
				String pantsLocation = lineCompared.substring(pant.length() 
						+3).trim();
				
				// Split the pantLocation string, and place it into pantsArray
				String []pantsArray = pantsLocation.split(",");
				
				// Remove the float values from the string and 
				// assign them to float x and float y
				float x = Float.parseFloat( pantsArray[0].trim());
				float y = Float.parseFloat( pantsArray[1].trim());
				pants.add(new Pant(x, y, randGen));
			}
			
			// if the line in question of Level starts with Fire
			else if ( lineCompared.startsWith("FIRE")) {
				
				//the line of level trimmed, trimmed string is called fireLocation
				
				String fireLocation = lineCompared.substring(fire.length()
						+ 3).trim();
				
				// Split the fireLocation string, and place it into fireArray
				String []fireArray = fireLocation.split(",");
				
				// Remove the float values from the string and 
				// assign them to float xFire and float yFire
				float xFire = Float.parseFloat( fireArray[0].trim());
				float yFire = Float.parseFloat( fireArray[1].trim());
				fires.add(new Fire(xFire, yFire, randGen));
			}
			
			// if the line in question of Level starts with HERO
			else if ( lineCompared.startsWith("HERO")) {
				//the line of level trimmed, trimmed string is called heroLocation
				String heroLocation = lineCompared.substring(hero1.length()
						+3).trim();
				// Split the heroLocation string, and place it into heroArray
				String [] heroArray = heroLocation.split(",");
				
				// Remove the float values from the string and 
				// assign them to float xHero and float yHero
				float xHero = Float.parseFloat( heroArray[0].trim());
				float yHero = Float.parseFloat( heroArray[1].trim());
				hero = new Hero(xHero, yHero, controlTypeNum);
			}
		}
		sc.close();
	}

	/**
	 * This method creates and runs a new GameEngine with its first Level.  Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in a particular order.
	 * @param args is the sequence of custom level files to play through.
	 */
	public static void main(String[] args)
	{
		//Just starts the game
		GameEngine.start(null,args);

	}
}
