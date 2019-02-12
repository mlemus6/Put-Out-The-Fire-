import java.util.ArrayList;
import java.util.Random;
/**
 * This class sets the position of the pants
 * This class also handles the collisions between pants and fireballs
 */
public class Pant extends java.lang.Object{
	private Graphic graphic;
	private Random randGen;
	private boolean isAlive;
	/**
	 *This method sets the position of a Pant
	 *graphic.setPosition(x,y) sets the location of Pant to float x float y
	 */
	public Pant(float x, float y, Random randGen) {
		//Sets the new pants location and speed
		isAlive=true;
		graphic = new Graphic("PANT");
		// sets the position of graphic pant at x,y
		graphic.setPosition(x, y);	
		
	}
	
	/**
	 *This method handles collisions between pants and fireballs
	 *if a fireball collides with a pant the pant and fireball are removed
	 *A new fire is set at the location where the pant was removed
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		
		//Receives all the fireballs and checks if they are colliding with the pant
		for (int i=0; i<fireballs.size();i++){
			if(graphic.isCollidingWith(fireballs.get(i).getGraphic())){
				//This deletes the pants and also the fireball 
				isAlive=false;
				fireballs.get(i).destroy();
				randGen=new Random();
			
				//Sets a new fire at the location the pans was hit
				return new Fire(graphic.getX(),graphic.getY(),randGen);
			}
			
		}
		return null;
		
		
	}
	/**
	 *Returns the pants graphic
	 *So it can be accessed by other classes
	 */
	public Graphic getGraphic(){
		return this.graphic;
	}
	/**
	 *This method handles the removal of pants
	 * @return if true is returned then pant should be removed
	 * if false is returned then pant should not be removed
	 */
	public boolean shouldRemove(){
		if (isAlive==false){
			return true;
		}
		return false;
	}
	/**
	 *This method draws the object as long as it is alive
	 *Also does not move it since the pants are not supposed to move.
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 */
	public void update(int time) {
		
		if(shouldRemove() == false){
			graphic.draw();
		}
		
	}
}
