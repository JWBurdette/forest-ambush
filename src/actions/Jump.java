package actions;

import java.util.Arrays;
import handlers.MovementHandler;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import lifeforms.Lifeform;

/**
 * Class for the jump action.
 */
public class Jump extends Action {
	
	int jumpHeight;                      //the height of the jump
	SequentialTransition jumpAnimation;  //transition will run all components of the jump sequentially.

	/**
	 * Constructor calculates and sets all components of the jump.
	 */
	public Jump(Lifeform lf, int height) {
		
		super(lf);

		jumpHeight = height;		
		Duration jumpTime = Duration.millis(1020.41); //the duration required for the image to return to its previous location
		
		double gravity = 9.8;
		int velocity = 5;
		double time;
		
		int totalJumpComponents = 100;
		
		jumpAnimation = new SequentialTransition();
		
		//loop uses motion formula to calculate how high each component of the jump should send the character.
		for (double i = 1; i <= totalJumpComponents; i++) {
			TranslateTransition jumpComponent = new TranslateTransition(jumpTime.divide(totalJumpComponents), spriteSheet);
			
			time = (jumpTime.toSeconds() / totalJumpComponents) * i;
			
			int multiplier = 200; //multiplier necessary to make jump less realistic and more like a video game.
			double currentHeight = (((velocity * time) - (0.5 * gravity * (time * time)))) * multiplier;

			jumpComponent.setToY(-1 * currentHeight); //must be reversed to account for inconvenient coordinate system.

			jumpAnimation.getChildren().add(jumpComponent);
		}

		jumpAnimation.setAutoReverse(true);
	}

	/**
	 * Runs the jump animation and sets what character will do when the jump is completed depending on what keys are pressed.
	 */
	@Override
	public void run() {
		
		jumpAnimation.setInterpolator(Interpolator.LINEAR);
		jumpAnimation.play();
		jumpAnimation.setOnFinished(e -> {
			lifeform.isJumping = false;
			
			if (MovementHandler.keysPressed.stream().anyMatch((Arrays.asList(KeyCode.RIGHT,
																			 KeyCode.D,
																			 KeyCode.LEFT,
																			 KeyCode.A))::contains)) {
				lifeform.move();

			} else {
				lifeform.idle();
			}
		});
	}
}