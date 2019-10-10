import java.awt.geom.Point2D;

public class Player implements GameObject {
	private Rectangle player;
	private Rectangle aimer;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;

	//Speed of Player
	private int speed = 5;
	private int normalSpeed;
	private int sprintSpeed;
	private int sneakSpeed;
	private double sprintMultiplier = 1.75;
	private double sneakMultiplier = 0.5;

	//Size of Player
	private double sizeMultiplierX = 1;
	private double sizeMultiplierY = 1;

	//Initial Location of Player
	private int initX = 0;
	private int initY = 0;

	//Direction of player: 0 - left, 1 - right
	private int direction = 0;

	private int aimerRange = 30;

	public Player(Sprite sprite) {
		player = new Rectangle(initX, initY, 16, 16);
		player.genRecGraphics(3, 0xFFFF00FF);
		// aimer = new Rectangle(0, 0, 5, 5);
		// aimer.genRecGraphics(0xFFFF0010);

		this.sprite = sprite;
		if(sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}

		normalSpeed = speed;
		sprintSpeed = (int)(normalSpeed * sprintMultiplier);
		sneakSpeed = (int)(normalSpeed * sneakMultiplier);
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(sprite != null) {
			renderer.renderSprite(sprite, player.x, player.y, xZoom, yZoom, false);
		} else {
			renderer.renderRectangle(player, xZoom, yZoom, false);
		}
		if(aimer != null) {
			renderer.renderRectangle(aimer, xZoom, yZoom, false);
		}
	}

	public void moveAimer(Game game) {
		if(aimer == null) return;

		Rectangle camera = game.getRenderer().getCamera();
		MouseEventListener mouseListener = game.getMouseListener();
		int mouseX = mouseListener.x + camera.x;
		int mouseY = mouseListener.y + camera.y;
		int playerCX = player.x + player.w / 2 * game.getXZoom();
		int playerCY = player.y + player.h / 2 * game.getYZoom();
		double distance = Point2D.distance(playerCX, playerCY, mouseX, mouseY);
		aimer.x = playerCX - (int)(aimerRange * (playerCX - mouseX) / distance) - aimer.w / 2 * game.getXZoom();
		aimer.y = playerCY - (int)(aimerRange * (playerCY - mouseY) / distance) - aimer.h / 2 * game.getYZoom();
	}

	public void flip() {
		if(sprite != null) {
			sprite.flip();
		} else {
			//Rotate the player rec
			return;
		}
	}

	public void updateCamera(Rectangle camera, int xZoom, int yZoom) {
		camera.x = player.x - camera.w / 2 + player.w * xZoom / 2;
		camera.y = player.y - camera.h / 2 + player.h * yZoom / 2;
	}

	public void update(Game game) {
		KeyBoardListener keyListener = game.getKeyListener();
		Rectangle camera = game.getRenderer().getCamera();

		if(keyListener.up()) {
			player.y -= speed;
		}
		if(keyListener.down()) {
			player.y += speed;
		}
		if(keyListener.left()) {
			player.x -= speed;
			if(direction != 0) {
				flip();
				direction = 0;
			}
		}
		if(keyListener.right()) {
			player.x += speed;
			if(direction != 1) {
				flip();
				direction = 1;
			}
		}
		if(keyListener.sprint()) {
			speed = sprintSpeed;
		} else speed = normalSpeed;

		updateCamera(camera, game.getXZoom(), game.getYZoom());
		moveAimer(game);

		if(animatedSprite != null) {
			animatedSprite.update(game);
		}
	}

	//Handle mouse click
	public void mouseClicked() {
		
	}
}

		// System.out.println(distance);
		// System.out.println(mouseX + ", " + mouseY);
		// System.out.println((aimer.x-playerCX) + ", " + (aimer.y-playerCY));