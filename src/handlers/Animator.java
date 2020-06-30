package handlers;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Class handles cycling through portions of sprite sheets to create the appearance of movement.
 * Much of this class was copied from: https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
 */ 
public class Animator extends Transition {

    private boolean staticImageFlag; //boolean to signify if the animation should only focus on one image
	private ImageView imageView;     //ImageView that holds the sprite sheet
    private int count;      //how many sprites there are in the sprite sheet
    private int columns;    //how many columns the sprite are arranged in
    private int offsetX;    //set if there is unneeded space in between the sprites
    private int offsetY;    //set if there is unneeded space in between the sprites
    private int width;      //width of sprite
    private int height;     //height of sprite
    private int lastIndex;  //index of the last sprite that was shown

    /**
     * Constructor to use for moving image.
     */
    public Animator(Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height) {
        staticImageFlag = false;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    /**
     * Default Constructor.
     */
    public Animator() {}

    /**
     * Constructor to use for static image.
     */
	public Animator(int offsetX, int width, int height) {
		staticImageFlag = true;
		
        this.count     = 1;
        this.columns   = 1;
        this.offsetX   = offsetX;
        this.offsetY   = 0;
        this.width     = width;
        this.height    = height;
        setCycleDuration(Duration.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
	}

	/**
	 * Determines how the animation will cycle through the sprites.
	 */
	protected void interpolate(double k) {
		
		if (staticImageFlag) {
			imageView.setViewport(new Rectangle2D(offsetX, 0, width, height));
		} else {
	        final int index = Math.min((int) Math.floor(k * count), count - 1);
	        if (index != lastIndex) {
	            final int x = (index % columns) * width  + offsetX;
	            final int y = (index / columns) * height + offsetY;
	            imageView.setViewport(new Rectangle2D(x + 1, y, width - 1, height));//ones were added
	            lastIndex = index;
	        }
		}
    }

	/**
	 * Setter for imageView.
	 */
	public void setImageView(ImageView imageView) {

		this.imageView = imageView;		
	}
}