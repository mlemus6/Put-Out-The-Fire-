/**
 * This class sets the position of fireballs
 * The class handles the movement and direction of fireballs
 * The class also handles the collisions between water and fireballs
 */
public class Fireball {
	private Graphic graphic;
	private float speed;
	private boolean isAlive;
	private float distanceTraveled;
	
	/**
	 * This method sets the position and direction of a Fireball
	 * graphic.setPosition(x,y) sets the location of fireball to float x float y
	 * graphic.setDirection(direction) sets the direction of fireball to radian direction
	 */
	public Fireball(float x, float y, float directionAngle) {
		
		//Sets the new fireballs location and speed
		speed = 0.2f;
		graphic = new Graphic("FIREBALL");
		graphic.setPosition(x, y);
		graphic.setDirection(directionAngle);
		isAlive = true;
	}
	
	/**
	 *Returns the fireball graphic
	 *So it can be accessed by other classes
	 */
	public Graphic getGraphic(){
		return this.graphic;
	}
	
	/**
	 * This method handles collisions between water and fireballs
	 * If a fireball collides with a water,
	 * the water and fireball are removed
	 */
	public void handleWaterCollisions(Water[] water) {
		
		//Receives all the water objects
		for (int i = 0; i < water.length;i++){
			if(water[i] != null){
				
				// Checks if each of the objects are colliding with one another
				// And this sets the water to null not making it draw and also
				// deletes the fireballs
				if(graphic.isCollidingWith(water[i].getGraphic())){
					water[i] = null;
					destroy();
				}
			}
		}
	}
	/**
	 *This method handles the removal of fireballs
	 * @return if true is returned then fireball should be removed
	 * if false is returned then fireball should not be removed
	 */
	public boolean shouldRemove(){
		if (isAlive == false){
			return true;
		}
		return false;
	}
	/**
	 *This method sets the isAlive of a fireball to false
	 */
	public void destroy(){
		isAlive = false;
	}
	
	/**
	 * This method updates the position of fireball
	 * if fireball leaves the border of the screen for a distance of greater
	 * than 100 pixels the fireball will be destroyed
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 */
	public void update(int time) {
		if (shouldRemove() == false){
			
			//Draws the fireballs as long as it is alive
			graphic.draw();
			
			//Gives the fireballs the animation
			float changeX = (speed * time) * graphic.getDirectionX();
			float changeY = (speed * time) * graphic.getDirectionY();
			graphic.setPosition(graphic.getX() + changeX, graphic.getY() + changeY);
			
			//Checks the fireballs location and sets it false once it is outside
			//of the gameEngine borders
			if (graphic.getX()>= GameEngine.getWidth()+100
					|| graphic.getY() >= GameEngine.getHeight()+100
					|| graphic.getX() <= -100
					|| graphic.getY() <= -100){
				isAlive = false;
			}

		}
		
	}

}
