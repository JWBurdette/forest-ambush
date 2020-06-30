package lifeforms;

import actions.Jump;
import handlers.Animator;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import presentation.Game;

/**
 * Class for the hero.
 */
public class Hero extends Lifeform {
	
	int width, height;
	Jump jump;

	/**
	 * Constructor sets dimensions of character and initial movement animation.
	 */
	public Hero() {
		
		currentSpriteSheet = new ImageView();
		width = Game.windowWidth / 8;
		height = width;
		
		int jumpHeight = width * 3;

		jump = new Jump(this, jumpHeight);
		
		currentSpriteSheet.setFitWidth(width);
		currentSpriteSheet.setFitHeight(height);
		currentSpriteSheet.setX((Game.windowWidth / 2) 
				- currentSpriteSheet.getFitWidth() / 2);
		currentSpriteSheet.setY(Game.getGround().getGroundLevelY() - height);
		
		setViewAndStartAnimation("IDLE");
	}
	
	/**
	 * Method to cause character to jump.
	 */
	public void jump() {
		
		setViewAndStartAnimation("JUMP");
		jump.run();
		isJumping = true;
	}
	
	/**
	 * Method to cause character to use primary attack.
	 */
	public void attack() {
		
		setViewAndStartAnimation("ATTACK");
	}
	
	/**
	 * Method to cause character to use throwing attack.
	 */
	public void throwAttack() {
		
		setViewAndStartAnimation("THROW");
	}

	//loads all sprite sheets from files.
	private Image idleImage = new Image("file:assets/Hero_Sprites/Owlet_Monster_Idle_4.png");
	private Image movingImage = new Image("file:assets/Hero_Sprites/Owlet_Monster_Run_6.png");
	private Image getHurtImage = new Image("file:assets/Hero_Sprites/Owlet_Monster_Hurt_4.png");
	private Image dieImage = new Image("file:assets/Hero_Sprites/Owlet_Monster_Death_8.png");
	private Image jumpImage = new Image("file:assets/Hero_Sprites/Owlet_Monster_Jump_8.png");
	private Image attackImage = new Image("file:assets/Hero_Sprites/Owlet_Monster_Attack2_6.png");
	private Image throwImage = new Image("file:assets/Hero_Sprites/Owlet_Monster_Throw_4.png");

	//animations for each actions.
	private Animator activeAnimation = new Animator();
	private Animator idleAnimation = new Animator(Duration.millis(1000), 4, 4, 0, 0, 
			(int) idleImage.getWidth() / 4, (int) idleImage.getHeight());
	private Animator runningAnimation = new Animator(Duration.millis(500), 6, 6, 0, 0,
			(int) movingImage.getWidth() / 6 /*+ 1*/, (int) movingImage.getHeight());
	private Animator getHurtAnimation = new Animator(Duration.millis(1000), 4, 4, 0, 0,
			(int) getHurtImage.getWidth() / 4, (int) getHurtImage.getHeight());
	private Animator dieAnimation = new Animator(Duration.millis(1000), 8, 8, 0, 0, 
			(int) dieImage.getWidth() / 8, (int) dieImage.getHeight());
//	private Animator jumpAnimation = new Animator(Duration.millis(1000), 8, 8, 0, 0, 
//			(int) jumpImage.getWidth() / 8, (int) jumpImage.getHeight()); //(set aside in favor of static jump)
	private int selectedJumpComponentX = (int)((jumpImage.getWidth() / 8) * 2);
	private Animator jumpAnimation = new Animator(selectedJumpComponentX, (int) jumpImage.getWidth() / 8, (int) jumpImage.getHeight());
	private Animator attackAnimation = new Animator(Duration.millis(500), 6, 6, 0, 0, 
			(int) attackImage.getWidth() / 6, (int) attackImage.getHeight());
	private Animator throwAnimation = new Animator(Duration.millis(700), 4, 4, 0, 0, 
			(int) throwImage.getWidth() / 4, (int) throwImage.getHeight());

	/**
	 * Sets the imageView and corresponding animation for each action.
	 */
	@Override
	public void setViewAndStartAnimation(String animationName) {

		switch (animationName) {
		
		case "IDLE":
			activeAnimation.stop();
			currentSpriteSheet.setImage(idleImage);
			activeAnimation = idleAnimation;
			activeAnimation.setImageView(currentSpriteSheet);
			activeAnimation.setCycleCount(Animation.INDEFINITE);
//			System.out.println("idle");
			break;
			
		case "MOVE":

			if (((currentSpriteSheet.getImage() == movingImage) || isJumping)) {
				break;
			}
			activeAnimation.stop();
			currentSpriteSheet.setImage(movingImage);
			activeAnimation = runningAnimation;
			activeAnimation.setImageView(currentSpriteSheet);
			activeAnimation.setCycleCount(Animation.INDEFINITE);
			break;

		case "GET HURT":
			activeAnimation.stop();
			currentSpriteSheet.setImage(getHurtImage);
			activeAnimation = getHurtAnimation;
			activeAnimation.setImageView(currentSpriteSheet);
			activeAnimation.setCycleCount(1);
			activeAnimation.setOnFinished(e -> idle());
			break;
			
		case "DIE":
			activeAnimation.stop();
			currentSpriteSheet.setImage(dieImage);
			activeAnimation = dieAnimation;						
			activeAnimation.setImageView(currentSpriteSheet);
			activeAnimation.setCycleCount(1);
			break;
			
		case "JUMP":
			
			activeAnimation.stop();
			currentSpriteSheet.setImage(jumpImage);
			activeAnimation = jumpAnimation;						
			activeAnimation.setImageView(currentSpriteSheet);
			activeAnimation.setCycleCount(1);
			break;
			
		case "ATTACK":
			activeAnimation.stop();
			currentSpriteSheet.setImage(attackImage);
			activeAnimation = attackAnimation;						
			activeAnimation.setImageView(currentSpriteSheet);
			activeAnimation.setCycleCount(1);
			activeAnimation.setOnFinished(e -> idle());
			break;
			
		case "THROW":
			activeAnimation.stop();
			currentSpriteSheet.setImage(throwImage);
			activeAnimation = throwAnimation;						
			activeAnimation.setImageView(currentSpriteSheet);
			activeAnimation.setCycleCount(1);
			activeAnimation.setOnFinished(e -> idle());
			break;
			
		default:
			System.out.println("Invalid animation name: " + animationName);		
		}
		
		activeAnimation.play();		
	}
}