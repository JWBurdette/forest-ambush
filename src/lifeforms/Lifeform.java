package lifeforms;

import javafx.scene.image.ImageView;

/**
 * Abstract class extended by all of the game characters and enemies.
 */
public abstract class Lifeform {
	
	protected int maxHealth;
	protected int currentHealth;
	protected int attackPower;
	protected ImageView currentSpriteSheet;
	public String state = "IDLE";
	public boolean isJumping = false;

	/**
	 * Returns the current sprite sheet of the life form.
	 */
	public ImageView getImageView() {
		return currentSpriteSheet;
	}
	
	/**
	 * Function to cause life form to stay still.
	 */
	public void idle() {
		state = "IDLE";
		setViewAndStartAnimation(state);
	}
	
	/**
	 * Function to cause life form to move in whatever direction it is currently facing.
	 */
	public void move() {
		state = "MOVE";
		setViewAndStartAnimation(state);
	}	
	
	/**
	 * Function to cause life form to sustain damage and run hurt animation.
	 */
	public void getHurt(int damageAmount) {
		state = "GET HURT";
		setViewAndStartAnimation(state);
	}
	
	/**
	 * Function to cause life form to die and disappear from map..
	 */
	public void die() {
		state = "DIE";
		setViewAndStartAnimation(state);
	}
	
	protected boolean facingRight = true;      //false means it is facing left.
//	public boolean directionChangeFlag = false;
	
	/**
	 * Function flips the life forms image.
	 */
	public void changeDirection() {
		
		if (facingRight) {
			facingRight = false;
            currentSpriteSheet.setScaleX(-1);

		} else {
			facingRight = true;
            currentSpriteSheet.setScaleX(1);

		}
	}
	
	/**
	 * Returns true if facing right, false if facing left.
	 */
	public boolean isFacingRight() {
		if (facingRight) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Will set imageView and corresponding animation for each action.
	 */
	public abstract void setViewAndStartAnimation(String animationName);
}
