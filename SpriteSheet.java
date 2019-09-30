import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	private Sprite[] loadedSprites = null;
	private int[] pixels;
	private int gridSizeX;
	private int padding;
	private boolean spritesLoaded = false;
	public final int SIZE_X;
	public final int SIZE_Y;


	public SpriteSheet(BufferedImage image, int spriteSizeX, int spriteSizeY, int padding) {
		this.image = image;
		this.gridSizeX = spriteSizeX + padding;
		this.padding = padding;
		SIZE_X = image.getWidth();
		SIZE_Y = image.getHeight();
		pixels = new int[SIZE_X*SIZE_Y];
		pixels = image.getRGB(0, 0, SIZE_X, SIZE_Y, pixels, 0, SIZE_X);
		loadSprites(spriteSizeX, spriteSizeY, padding);
	}

	public void loadSprites(int spriteSizeX, int spriteSizeY, int padding) {
		int gridSizeX = spriteSizeX + padding;
		int gridSizeY = spriteSizeY + padding;
		loadedSprites = new Sprite[((SIZE_X + padding) / gridSizeX) * ((SIZE_Y + padding) / gridSizeY)];
		int spriteID = 0;
		for(int y = 0; y < SIZE_Y; y += gridSizeY) {
			for(int x = 0; x < SIZE_X; x += gridSizeX) {
				loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
				spriteID++;
			}
		}
		spritesLoaded = true;
	}

	public Sprite getSprite(int spriteX, int spriteY) {
		if(spritesLoaded) {
			int spriteID = spriteX + spriteY * ((SIZE_X + padding) / gridSizeX);
			if(spriteID < loadedSprites.length) {
				return loadedSprites[spriteID];
			} else {
				System.out.println("SpriteID " + spriteID + " out of range of " + loadedSprites.length + ".");
			}
		} else {
			System.out.println("Sprite sheet could not get a sprite with no loaded sprites.");
		}
		return null;
	}

	public int[] getPixels() {
		return pixels;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setPixels(int[] pixels) {

	}

} 