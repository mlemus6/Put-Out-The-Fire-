import java.util.Random;
/**
 * This class sets the location of the fires
 * This class calls for the creation of fireballs after the fireballcountdown
 * This class also handles the collisions between fire and water
 * @author Michael Lemus
 */
public class Fire {
	private Graphic graphic;
	private Random randGen;
	private int fireballCountdown;
	private int heat=40;
	
	/**
	 *This method sets the position of fires
	 *graphic.setPosition(x,y) sets the location of fire to float x float y
	 */
	public Fire(float x, float y, Random randGen) {
		//Draws a fire object at the position set in level
		graphic = new Graphic("FIRE");
		graphic.setPosition(x, y);
		fireballCountdown = randGen.nextInt(3001)+3000;
		
		
	}
	
	/**
	 *Returns the fire graphic
	 *So it can be accessed by other classes
	 */
	public Graphic getGraphic(){
		return this.graphic;
	}
	
	/**
	 *This method handles collisions between fire and water
	 *if a water collides with a fire the water is set to null and heat decreases
	 */
	public void handleWaterCollisions(Water[] water) {
		//Receives all the water objects
		for (int i=0; i<water.length; i++){
			if(water[i] != null){
				
				//Checks if each of the objects are colliding with one another
				//And once heat reaches zero the fires are deleted
				if(graphic.isCollidingWith(water[i].getGraphic())){
					if(heat>=1){
						heat=heat-1;
						water[i]=null;
					}
				}
			}
		}
		
	}
	
	/**
	 *This method handles the removal of Fire
	 * @return if true is returned then fire should be removed
	 * if false is returned then pant fire not be removed
	 */
	public boolean shouldRemove(){
		if (heat<=0){
			return true;
		}
		return false;
	}
	
	/**
	 *This method updates fire to create fireballs once the countdown hits zero or negative
	 ** @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 * @return This method returns the new fireball that is created
	 */
	public Fireball update(int time) {
		if(shouldRemove()==false){
			graphic.draw();
		}
		
		randGen=new Random();
		
		//Decrements the countdown by time
		fireballCountdown-=time;
		
		//As long as fireballcountdown<=0 it prints out a new fireball
		if(fireballCountdown <=0){
			
			//Sets a new number in the coundown starting the process again
			fireballCountdown = randGen.nextInt(3001)+3000;
			
			//Makes the fires produce fireballs
			Fireball fire= new Fireball(graphic.getX(),graphic.getY(),
					(float)(randGen.nextFloat()*(2*Math.PI)));
			if(shouldRemove()==false){
				Level.fireballs.add(fire);
			}
			return fire;
			
		}
		else return null;
		
	}	

}
