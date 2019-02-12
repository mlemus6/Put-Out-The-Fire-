import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * This class sets the position of the hero
 * The class controls the movement and direction of the hero based on the control type
 * The class controls the collisions between the hero and Fireballs
 * The class also is responsible for calling for the creation of water
 * @author Michael Lemus
 */
public class Hero  {
	private Graphic graphic;
	private float   speed;
	private int     controlType;
	private Water water;
	private Water [] water1;
	public ArrayList<Fireball> fireballs;
	public Hero(float x, float y, int controlType) {
		//This sets the hero's  position, control type and speed
		speed = .12f;
		this.controlType=controlType;
		graphic=new Graphic("HERO");
		graphic.setPosition(x, y);	
	}
	
	/**
	 *This method handles collisions between the hero and a fireball
	 *if the hero collides with a fireball then true is returned, else false is returned
	 * @return if true is returned then the level class will return quit which will exit the game
	 * if false is returned then the game will continue
	 */
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		
		//Receives all the fireballs and checks if they are colliding with the hero
		for (int i=0; i<fireballs.size();i++){
			if(graphic.isCollidingWith(fireballs.get(i).getGraphic())){
				return true; 
			}
		}
		
		//Returns false if they are not colliding
		return false;
		
	}
	
	/**
	 *Returns the hero graphic
	 *So it can be accessed by other classes
	 */
	public Graphic getGraphic(){
		return this.graphic;
	}
	
	/**
	 *This method updates the position and direction of the Hero and creates new waters
	 *if space of mouse down is being held a new water is created with the same location
	 *and direction of the hero
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 */
	public void update(int time,Water[]water1){
		float x;
		float y;
		float pi=(float)Math.PI;
		
		if(controlType==1){
			
			//This is all basically the same for WASD except adding and subtracting
			//and setting the direction
			if(GameEngine.isKeyPressed("D") || GameEngine.isKeyHeld("D")){
				x = graphic.getX();
				
				//We add to x here to move the left
				graphic.setX(x + (speed * time));
				x = graphic.getX();
				y = graphic.getY();
				
				//Makes the hero face left
				graphic.setDirection(2 * pi);
				graphic.setPosition(x, y);
			}
			
			if(GameEngine.isKeyHeld("A") || GameEngine.isKeyPressed("A")){
				x = graphic.getX();
				
				//We subtract from x here to move right
				graphic.setX(x - (speed * time));
				x = graphic.getX();
				y = graphic.getY();
				
				//Makes the hero face left
				graphic.setDirection(pi);
				graphic.setPosition(x, y);
			}
			if(GameEngine.isKeyHeld("S") || GameEngine.isKeyPressed("S")){
				y = graphic.getY();
				
				//We add to y here to move down
				graphic.setY(y + (speed * time));
				y = graphic.getY();
				x = graphic.getX();
				
				//Makes the hero face down
				graphic.setDirection(pi / 2);
				graphic.setPosition(x, y);
			}
			if(GameEngine.isKeyHeld("W") || GameEngine.isKeyPressed("W")){
				y = graphic.getY();
				
				//We subtract from y to make it move up
				graphic.setY(y - (speed * time));
				y = graphic.getY();
				x = graphic.getX();
				
				//Makes the hero face up
				graphic.setDirection((3 * pi) / 2);
				graphic.setPosition(x, y);
			}
			
			//Gets the position after keys were pressed and sets the hero there
			x = graphic.getX();
			y = graphic.getY();
			graphic.setPosition(x, y);
			graphic.draw();
			
		}
		
		//This is the same code as before except we change where the hero faces
		else if(controlType==2){
			
			if(GameEngine.isKeyPressed("D") || GameEngine.isKeyHeld("D")){
				x = graphic.getX();
				graphic.setX(x + (speed * time));
				x = graphic.getX();
				y = graphic.getY();
				graphic.setPosition(x, y);
			}
			
			if(GameEngine.isKeyHeld("A") || GameEngine.isKeyPressed("A")){
				x = graphic.getX();
				graphic.setX(x - (speed * time));
				x = graphic.getX();
				y = graphic.getY();
				graphic.setPosition(x, y);
			}
			if(GameEngine.isKeyHeld("S") || GameEngine.isKeyPressed("S")){
				y = graphic.getY();
				graphic.setY(y + (speed * time));
				y = graphic.getY();
				x = graphic.getX();
				graphic.setPosition(x, y);
			}
			if(GameEngine.isKeyHeld("W") || GameEngine.isKeyPressed("W")){
				y = graphic.getY();
				graphic.setY(y - (speed * time));
				y = graphic.getY();
				x = graphic.getX();
				graphic.setPosition(x, y);
			}
			
			//This is the only difference where it gets the mouse's
			//x and y and sets the heros position according to that
			int t = GameEngine.getMouseX();
			int z = GameEngine.getMouseY();
			graphic.setDirection(t, z);
			x = graphic.getX();
			y = graphic.getY();
			graphic.setPosition(x, y);
			graphic.draw();
			
		}
		else if(controlType == 3){
			
			//Gets the mouse's x and y and sets the heros
			//position according to that
			int t = GameEngine.getMouseX();
			int z = GameEngine.getMouseY();
			graphic.setDirection(t, z);
			
			//Gets the direction the hero is facing
			float DirectionX = graphic.getDirectionX();
			float DirectionY = graphic.getDirectionY();
			x = graphic.getX();
			y = graphic.getY();
			
			//This is to provide movement to the hero
			float MovementX = DirectionX * (speed*time);
			float MovementY = DirectionY * (speed*time);
			x = x + MovementX;
			y = y + MovementY;
			
			//This gets the position relative to the mouse
			float c = (float) Math.sqrt(((x-t)*(x-t))+((y-z)*(y-z)));
			
			//Stops  moving the hero once the mouse is within 20 pixels
			//or 20f
			if(c >= 20f){
				graphic.setPosition(x, y);
			}
			graphic.draw();	
			
		}
		
		//This shoots out water from the hero
		if ((GameEngine.isKeyHeld("SPACE")) || (GameEngine.isKeyHeld("MOUSE"))) {
			for (int j = 0; j < water1.length; j++) {
				
				//Only allows 8 waters to come out of the hero
				if (water1[j] == null) {
					water1[j] = new Water(graphic.getX(), graphic.getY(), graphic.getDirection());
					j = 8;
				}
			}
		}

	}
}
