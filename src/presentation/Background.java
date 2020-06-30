package presentation;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Contains all images and animations to enable the background to scroll.
 */
public class Background {
	
	private static int BACKGROUND_WIDTH; 
	private static int BACKGROUND_HEIGHT;
	private static int SHIFT_TO_X = 10000; //Distance that the animation will attempt to move the background images to.
	
	//ImageViews for each component of the background. 3 for the front, 2 for the middle, and one for both of the back ones.
	private ArrayList<ImageView> backgroundImageViews = new ArrayList<ImageView>();
	ImageView frontB1, frontB2, frontB3, midB1, midB2, backB1, backB2, backLights;
	
	ParallelTransition scrollBackground; 
	
	public Background(int width, int height) {
		 
		BACKGROUND_WIDTH = width - 40;
		
		frontB1 = new ImageView(new Image("file:assets/backgrounds/parallax-forest-front-trees.png"));
		frontB2 = new ImageView(new Image("file:assets/backgrounds/parallax-forest-front-trees.png"));
		frontB3 = new ImageView(new Image("file:assets/backgrounds/parallax-forest-front-trees.png"));
		midB1 = new ImageView(new Image("file:assets/backgrounds/parallax-forest-middle-trees.png"));
		midB2 = new ImageView(new Image("file:assets/backgrounds/parallax-forest-middle-trees.png"));
		backB1 = new ImageView(new Image("file:assets/backgrounds/parallax-forest-back-trees.png"));
		backB2 = new ImageView(new Image("file:assets/backgrounds/parallax-forest-back-trees.png"));
		backLights = new ImageView(new Image("file:assets/backgrounds/parallax-forest-lights.png"));

		//back ones are added first so that they appear in the back when displayed
		backgroundImageViews.add(backB1);
		backgroundImageViews.add(backB2);
		backgroundImageViews.add(backLights);
		backgroundImageViews.add(midB2);
		backgroundImageViews.add(midB1);
		backgroundImageViews.add(frontB3);
		backgroundImageViews.add(frontB2);
		backgroundImageViews.add(frontB1);

		for (ImageView i : backgroundImageViews) {
			i.setY(0);
			i.setFitWidth(BACKGROUND_WIDTH);
			i.setPreserveRatio(true);
		}
		
		//Set X coordinates of images
		frontB1.setX(0); frontB1.setY(0);
		frontB2.setX(frontB2.getFitWidth()); frontB2.setY(0);
		frontB3.setX(-frontB3.getFitWidth()); frontB3.setY(0);
		midB1.setX(-midB1.getFitWidth() + (midB1.getFitWidth() / 2));
		midB2.setX(midB2.getFitWidth() / 2); 
		backB1.setX(0);
		backB2.setX(backB2.getFitWidth());
		backLights.setX(0);

		int duration = 100;
		
		//Set individual transitions for each imageView
		TranslateTransition frontB1Transition = 
				new TranslateTransition(Duration.seconds(duration), frontB1);
		frontB1Transition.setToX(SHIFT_TO_X);
		frontB1Transition.setCycleCount(TranslateTransition.INDEFINITE);
		
		TranslateTransition frontB2Transition = 
				new TranslateTransition(Duration.seconds(duration), frontB2);
		frontB2Transition.setToX(SHIFT_TO_X);
		frontB2Transition.setCycleCount(TranslateTransition.INDEFINITE);
		
		TranslateTransition frontB3Transition = 
				new TranslateTransition(Duration.seconds(duration), frontB3);
		frontB3Transition.setToX(SHIFT_TO_X);
		frontB3Transition.setCycleCount(TranslateTransition.INDEFINITE);
		
		TranslateTransition midB1Transition = 
				new TranslateTransition(Duration.seconds(duration * 2), midB1);
		midB1Transition.setToX(SHIFT_TO_X);
		midB1Transition.setCycleCount(TranslateTransition.INDEFINITE);
		
		TranslateTransition midB2Transition = 
				new TranslateTransition(Duration.seconds(duration * 2), midB2);
		midB2Transition.setToX(SHIFT_TO_X);
		midB2Transition.setCycleCount(TranslateTransition.INDEFINITE);

		//Add them to the parallel transition
		scrollBackground = new ParallelTransition(frontB1Transition, 
				                                  frontB2Transition,
				                                  frontB3Transition, 
				                                  midB1Transition, 
				                                  midB2Transition);
		
		for (Animation t : scrollBackground.getChildren()) {
			((TranslateTransition) t).setInterpolator(Interpolator.LINEAR);
		}
		
		//Set height of the background so it can be used by other classes and methods 
		BACKGROUND_HEIGHT = (int) (frontB1.getFitWidth() / 
				(frontB1.getImage().getWidth() / frontB1.getImage().getHeight())); 


	}
	
	/**
	 * Return array of background images 
	 */
	public ArrayList<ImageView> getBackgroundImages() {
		return backgroundImageViews;
	}
	
	/**
	 * Stop background from scrolling
	 */
	public void stopAnimation() {
		scrollBackground.stop();
	}
	
	/**
	 * Scroll the background left. Called when player moves right.
	 */
	public void moveBackgroundLeft() {
		
		//Condition prevents method from running over and over because key is held down.
		if ((scrollBackground.getStatus() == Status.STOPPED) || SHIFT_TO_X >= 0) { //Game.getPlayer().state != "MOVE RIGHT"
			
			stopAnimation();	
			
			//Reverse destination of transitions so background moves the opposite direction.
			SHIFT_TO_X = Math.abs(SHIFT_TO_X) * -1;
			
			for (Animation t : scrollBackground.getChildren()) {
				((TranslateTransition) t).setToX(SHIFT_TO_X);
			}
			scrollBackground.play();
		}
	}
	
	/**
	 * Scroll the background Right. Called when player moves left.
	 */
	public void moveBackgroundRight() {
		
		//Condition prevents method from running over and over because key is held down.
		if ((scrollBackground.getStatus() == Status.STOPPED) || SHIFT_TO_X < 0) {
			
			stopAnimation();	
			
			SHIFT_TO_X = Math.abs(SHIFT_TO_X);
			
			for (Animation t : scrollBackground.getChildren()) {
				((TranslateTransition) t).setToX(SHIFT_TO_X);
			}
			scrollBackground.play();
		}
	}

	/**
	 * Return background height
	 */
	public int getBGHeight() {
		return BACKGROUND_HEIGHT;
	}
}
