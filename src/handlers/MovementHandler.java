package handlers;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lifeforms.Hero;
import presentation.Game;

/**
 * Handler that handles the movement of the player, ground, and background images.
 */
public class MovementHandler implements EventHandler<Event> {
	
	public static int screenEntryPointInFront; 
	public static int screenEntryPointInBehind;
	//TODO: public static int screenEntryPointAbove;
	//TODO: public static int screenEntryPointBelow;
	private Hero player;

	/**
	 * Constructor.
	 */
	public MovementHandler(Hero hero) {
		player = hero;		
	}
	
	//holds all of the keys that are currently pressed down.
	public static ArrayList<KeyCode> keysPressed = new ArrayList<KeyCode>(); 
	//holds all of the keys for the events that will not stop when the user releases their key.
	private static String[] uninterruptibleEvents = {"Space", "W", "Q", "E"};

	/**
	 * Handle method called when an event occurs.
	 */
	@Override
	public void handle(Event e) {

		if (e.getEventType().getName() == "KEY_PRESSED") {
			
			if (!keysPressed.contains(((KeyEvent) e).getCode())) {
				keysPressed.add(((KeyEvent) e).getCode());
			}
			
			//Do appropriate action for the key that was pressed.
			switch(((KeyEvent) e).getCode()) {
				case RIGHT:
				case D:
					
					if (player.isFacingRight() == false) {
						player.changeDirection();
					}
					
					player.move();
					Game.getBG().moveBackgroundLeft();
					Game.getGround().moveGroundLeft();
					break;
					
				case LEFT:
				case A:					

					if (player.isFacingRight()) {
						player.changeDirection();
					}
					
					player.move();
					Game.getBG().moveBackgroundRight();
					Game.getGround().moveGroundRight();
					break;
					
					//FOR TESTING:
//				case DIGIT1:
//					player.getHurt(100);
//					break;
//					
//				case DIGIT2:
//					player.die();
//					break;
					
				case SPACE:
				case W:
					
					player.jump();
					break;
					
				case Q:
					
					player.attack();
					break;
					
				case E:
					
					player.throwAttack();
					break;

				default:
					System.out.println("This key does not do anything.");
					break;
			}
		}
		
		//Stop animation if the key was released and it is not an un-interruptible event.
		if (e.getEventType().getName() == "KEY_RELEASED") {
			
			keysPressed.remove(((KeyEvent) e).getCode());
			
			if (Arrays.asList(uninterruptibleEvents).contains(((KeyEvent) e).getCode().getName())) {
				return;
			}
			
			Game.getBG().stopAnimation();
			Game.getGround().stopAnimation();
			player.idle();				
		}
	}
}