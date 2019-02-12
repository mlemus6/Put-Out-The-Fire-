import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * This class sets the position of water
 * This class also handles movement and direction of water
 */
public class Water {
	private Graphic graphic;
	private float speed;
	private float distanceTraveled;
	/**
	 *This method sets the position and direction of Water
	 *graphic.setPosition(x,y) sets the location of Water to float x float y
	 *graphic.setDirection(direction) sets the direction of water to radian direction
	 */
	public Water(float x, float y, float direction) {
		//Sets the new water location and speed
		speed = 0.7f;
		graphic = new Graphic("WATER");
		graphic.setPosition(x, y);
		graphic.setDirection(direction);
	}
	/**
	 *This method returns the water graphic
	 *So it can be accessed by other classes
	 */
	public Graphic getGraphic(){
		return this.graphic;
	}
	/**
	 *This method updates the position of water
	 ** @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 * @return if null is returned then Water is set to null
	 * if water.this is returned then the location of Water is updated
	 */
	public Water update(int time) {
		distanceTraveled+=(speed*time);
		
		//This checks the distance traveled 
		//and once it reaches 200 it changes to null 
		if (distanceTraveled>=200){
			return null;
		}
		
		//Gives the water the movement
		float xPosition = graphic.getX() + ((speed*time)*graphic.getDirectionX());
		graphic.setX(xPosition);
		float yPosition = graphic.getY() + ((speed*time)*graphic.getDirectionY());
		graphic.setY(yPosition);
		graphic.draw();
		
		return Water.this;
	}
	
}
