import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

import java.lang.Runnable;
import java.lang.Thread;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Game extends JFrame implements Runnable{

	public static int alpha = 0xFFFF00DC;

	private int gridSize = 16;
	private int xZoom = 2;
	private int yZoom = 2;

	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private KeyBoardListener keyListener;
	private MouseEventListener mouseListener;

	private GameObject[] objects;
	private Player player;
	
	private SpriteSheet sheet;
	private Tiles tiles;
	private Map map;

	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 800);
		setLocationRelativeTo(null); //Move the frame to the center
		add(canvas);
		setVisible(true);
		canvas.createBufferStrategy(3);
		renderer = new RenderHandler(getWidth(), getHeight());

		// Load Assets
		BufferedImage sheetImage = loadImage("resources/roguelike.png");
		sheet = new SpriteSheet(sheetImage, 16, 16, 1);
		tiles = new Tiles(new File("Tiles.txt"), sheet);
		map = new Map(new File("Map.txt"), tiles);

		// testImage = loadImage("resources/test_sprite_single.png");
		// testSprite = new Sprite(testImage);

		// Load Objects
		objects = new GameObject[1];
		player = new Player();
		objects[0] = player;

		// Add Listeners
		keyListener = new KeyBoardListener(new File("KeyMap.ini"));
		mouseListener = new MouseEventListener(this);
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
	}

	public void render() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);

		map.renderMap(renderer, xZoom, yZoom, gridSize);
		for(int i = 0; i < objects.length; i++) {
			objects[i].render(renderer, xZoom, yZoom);
		}
		renderer.render(graphics);

		graphics.dispose();
		bufferStrategy.show();
		renderer.clear();
	}

	public void update() {
		if(!keyListener.tool_keys()) {
			for(int i = 0; i < objects.length; i++) {
				objects[i].update(this);
			}
		}
		if(keyListener.save_map()) {
			map.saveMap(new File("testMap.txt"));
		}
	}

	public void run() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		long lastTime = System.nanoTime();
		double nanoToOneSecond = 1000000000.0 / 60;
		double deltaSeconds = 0;

		while(true) {
			long now = System.nanoTime();

			deltaSeconds += (now - lastTime) / nanoToOneSecond;

			if(deltaSeconds >= 1) {
				update();
				deltaSeconds = 0;
			}
			render();

			lastTime = now;
		}
	}

	private BufferedImage loadImage(String path) {
		try {
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

			return formattedImage;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public KeyBoardListener getKeyListener() {
		return keyListener;
	}

	public MouseEventListener getMouseListener() {
		return mouseListener;
	}

	public RenderHandler getRenderer() {
		return renderer;
	}

	public void leftClick(int x, int y) {
		// System.out.println("(" + x + ", " + y + ")");
		x = (int) Math.floor((x + renderer.getCamera().x) / ((double) gridSize * xZoom));
		y = (int) Math.floor((y + renderer.getCamera().y) / ((double) gridSize * yZoom));
		map.setTile(6, x, y);
	}

	public void mouseMove(int x, int y) {
		player.moveAimer(this);
	}

	public int getXZoom() { return xZoom; }
	public int getYZoom() { return yZoom; }

	public static void main(String[] args) {
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
}


		// renderer.renderImage(testImage, 0, 0, 10, 10);
		// renderer.renderSprite(sheet.getSprite(2, 0), 0, 0, 15, 15);
		// tiles.renderTile(5, renderer, 10, 10, 5, 10);
		// renderer.renderRectangle(testRec, 1, 1);