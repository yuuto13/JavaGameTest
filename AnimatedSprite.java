import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject {

	private Sprite[] sprites;
	private int currentSprite = 0;
	private int fps;
	private int counter = 0;

	public AnimatedSprite(SpriteSheet sheet, int startX, int startY, int width, int height, int frames, int fps) {
		sprites = new Sprite[frames];
		this.fps = fps;
		for(int i = 0; i < frames; i++) {
			sprites[i] = new Sprite(sheet, new Rectangle(startX+width*i, startY, width, height));
		}
	}

	public AnimatedSprite(BufferedImage[] images, int fps) {
		sprites = new Sprite[images.length];
		this.fps = fps;
		for(int i = 0; i < images.length; i++) {
			sprites[i] = new Sprite(images[i]);
		}
	}

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom) {}

	//Call at 60 fps.
	public void update(Game game) {
		counter++;
		if(counter >= game.getFPS() / fps) {
			counter = 0;
			changeSprite(1);
		}
	}

	public void flip() {
		for(int i = 0; i < sprites.length; i++) {
			sprites[i].flip();
		}
	}

	public int getWidth() {
		return sprites[currentSprite].getWidth();
	}

	public int getHeight() {
		return sprites[currentSprite].getHeight();
	}

	public int[] getPixels() {
		return sprites[currentSprite].getPixels();
	}

	public void changeSprite(int increment) {
		currentSprite += increment;
		if(currentSprite >= sprites.length) {
			currentSprite = 0;
		}
	}
}