package presentation;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Contains all images and animations to enable the ground to scroll.
 */
public class Ground {
		
	private static int SHIFT_TO_X = 10000;
	private ArrayList<ImageView> groundImageViews = new ArrayList<ImageView>();
	private int groundLevelY;
	private int blockSize;
	
	ParallelTransition scrollGround;

	/**
	 * Constructor that creates all ground blocks and pairs them with a transition.
	 * 
	 * @param size      length and width of ground block
	 * @param groundY   ground level
	 */
	public Ground(int size, int groundY) {
		
		scrollGround = new ParallelTransition();
		
		groundLevelY = groundY - 5; //cover the patches of white visible through gaps in rocks
		blockSize = size + 10; 
		
		int scrollDuration = 70;
		
		//loop to create 100 ground blocks and their transitions 
		for (int i = -20; i <= 80; i++) {
			
			ImageView groundImage = new ImageView(new Image("file:assets/ground and platforms/ground2.png"));	
			groundImage.setFitWidth(blockSize);
			groundImage.setFitHeight(blockSize);
			groundImage.setX(i * blockSize);
			groundImage.setY(groundLevelY);
			groundImageViews.add(groundImage);
			
			
			TranslateTransition moveGroundBlock = 
					new TranslateTransition(Duration.seconds(scrollDuration), groundImage);
			moveGroundBlock.setToX(SHIFT_TO_X);
			moveGroundBlock.setCycleCount(TranslateTransition.INDEFINITE);
			moveGroundBlock.setInterpolator(Interpolator.LINEAR);
			
			scrollGround.getChildren().add(moveGroundBlock);
		}
	}

	/**
	 * Return ground images
	 */
	public ArrayList<ImageView> getGroundImages() {
		return groundImageViews;
	}
	
	/**
	 * Stop scrolling animation
	 */
	public void stopAnimation() {
		scrollGround.stop();
	}
	
	/**
	 * Scroll the ground left. Called when player moves right.
	 */
	public void moveGroundLeft() {
		
		//Condition prevents method from running over and over because key is held down.
		if ((scrollGround.getStatus() == Status.STOPPED) || SHIFT_TO_X >= 0) { 
			
			stopAnimation();	
			
			SHIFT_TO_X = Math.abs(SHIFT_TO_X) * -1;
			
			for (Animation t : scrollGround.getChildren()) {
				((TranslateTransition) t).setToX(SHIFT_TO_X);
			}
			scrollGround.play();
		}
	}
	
	/**
	 * Scroll the background right. Called when player moves left.
	 */
	public void moveGroundRight() {
		if ((scrollGround.getStatus() == Status.STOPPED) || SHIFT_TO_X < 0) { //Game.getPlayer().state != "MOVE RIGHT"
			
			stopAnimation();	
			
			SHIFT_TO_X = Math.abs(SHIFT_TO_X);
			for (Animation t : scrollGround.getChildren()) {
				((TranslateTransition) t).setToX(SHIFT_TO_X);
			}
			scrollGround.play();
		}
	}
	
	/**
	 * Get groundLevelY 
	 */
	public int getGroundLevelY() {
		return groundLevelY;
	}
	
	
	

}
