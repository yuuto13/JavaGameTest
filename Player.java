import java.awt.geom.Point2D;

public class Player implements GameObject {
	Rectangle player;
	Rectangle aimer;

	int speed = 5;
	int normalSpeed;
	int sprintSpeed;
	int sneakSpeed;
	double sprintMultiplier = 1.75;
	double sneakMultiplier = 0.5;

	int aimerRange = 30;

	double sizeMultiplierX = 1;
	double sizeMultiplierY = 1;

	public Player() {
		player = new Rectangle(0, 0, 10, 16);
		player.genRecGraphics(3, 0xFFFF00FF);
		aimer = new Rectangle(0, 0, 5, 5);
		aimer.genRecGraphics(0xFFFF0010);

		normalSpeed = speed;
		sprintSpeed = (int)(normalSpeed * sprintMultiplier);
		sneakSpeed = (int)(normalSpeed * sneakMultiplier);
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderRectangle(player, xZoom, yZoom);
		renderer.renderRectangle(aimer, xZoom, yZoom);
	}

	public void moveAimer(Game game) {
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
		}
		if(keyListener.right()) {
			player.x += speed;
		}
		if(keyListener.sprint()) {
			speed = sprintSpeed;
		} else speed = normalSpeed;

		updateCamera(camera, game.getXZoom(), game.getYZoom());
		moveAimer(game);
	}
}

		// System.out.println(distance);
		// System.out.println(mouseX + ", " + mouseY);
		// System.out.println((aimer.x-playerCX) + ", " + (aimer.y-playerCY));