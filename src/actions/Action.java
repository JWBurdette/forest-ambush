package actions;

import javafx.scene.image.ImageView;
import lifeforms.Lifeform;

/**
 * Class that is extended to make certain repeatable abilities that can be assigned to game character.
 */
public abstract class Action {
	
	ImageView spriteSheet;
	Lifeform lifeform;
		
	/**
	 * Constructor that sets the lifeform this action will belong to.
	 */
	public Action(Lifeform lf) {
		lifeform = lf;
		spriteSheet = lf.getImageView();
	}

	/**
	 * Abstract method for activating the ability.
	 */
	public abstract void run();

}
