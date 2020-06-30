package presentation;

import handlers.MovementHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lifeforms.Hero;

/**
 * Main class that starts the application.
 */
public class Game extends Application { 

	//Window dimensions
	public static final int windowWidth = 800;//TODO: get real dimensions
	static final int windowHeight = 500;
	
	//JavaFX stuff
	static Stage stage;
	Group root;
	Scene scene;
	
	//Game objects 
	static Background bg;
	static Ground ground;
	static Hero player;
	
	//Handlers
	static MovementHandler moveHandler;

	/**
	 * Main method
	 */
	static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Starts application
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		root = new Group();
		scene = new Scene(root, windowWidth, windowHeight);
		
		bg = new Background(windowWidth, windowHeight);
		ground = new Ground((windowHeight - bg.getBGHeight()), bg.getBGHeight());
		player = new Hero();
		
		moveHandler = new MovementHandler(player);
		
		//Set event handlers 
		scene.setOnKeyPressed(moveHandler);
		scene.setOnKeyReleased(moveHandler);
		
		//Add game objects to root group
		root.getChildren().add(player.getImageView());		
		root.getChildren().addAll(bg.getBackgroundImages());
		root.getChildren().addAll(ground.getGroundImages());
		
		//move images to their appropriate layer if needed.
		layerImages();


		
		stage.setScene(scene);
		stage.setTitle("Forest Ambush");
		stage.show();
		
		
		//Platform.exit();
	}
	
	/**
	 * Move nodes around in root so the right ones appear in front.
	 */
	private void layerImages() {
		root.getChildren().get(0).toFront();		
	}

	/**
	 * Get the background
	 */
	public static Background getBG() {
		return bg;
	}
	
	/**
	 * Get the player
	 */
	public static Hero getPlayer() {
		return player;
	}

	/**
	 * Get the ground
	 */
	public static Ground getGround() {
		return ground;
	}
	
	/**
	 * Get the MovementHandler
	 */
	public static MovementHandler getMovementHandler() {
		return moveHandler;
	}

}