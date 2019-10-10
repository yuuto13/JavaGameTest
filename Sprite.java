import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Sprite {
	protected int width, height;
	protected int[] pixels;
	protected boolean isRotatable = false;

	// private BufferedImage image = null;

	public Sprite(SpriteSheet sheet, Rectangle rec) {
		this.width = rec.w;
		this.height = rec.h;
		pixels = new int[width*height];
		this.isRotatable = isRotatable;
		// image = sheet.getImage().getSubimage(rec.x, rec.y, rec.w, rec.h);
		sheet.getImage().getRGB(rec.x, rec.y, rec.w, rec.h, pixels, 0, rec.w);
	}
	public Sprite(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();

		pixels = new int[width*height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}
	public Sprite() {}

	public void rotate(double rads) {
		// if(rads % 360 == 0) {
		// 	return;
		// } else {
		// 	double sin = Math.abs(Math.sin(rads));
		// 	double cos = Math.abs(Math.cos(rads));
		// 	width = (int) Math.floor(width * cos + height * sin);
		// 	height = (int) Math.floor(height * cos + width * sin);
		// 	BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
		// 	AffineTransform at = new AffineTransform();
		// 	at.translate(width / 2, height / 2);
		// 	at.rotate(rads, 0, 0);
		// 	at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
		// 	final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		// 	rotatedImage = rotateOp.filter(image, null);
		// 	image = rotatedImage;

		// 	System.out.println(image.getWidth() + ", " + image.getHeight());
			
		// 	pixels = new int[width*height];
		// 	image.getRGB(0, 0, width, height, pixels, 0, width);
		// }
	}

	public void flip() {
		int pixelIndex = 0;
		for(int y = 0; y < height; y++) {
			for(int x = width; x > width / 2; x--) {
				int temp = pixels[pixelIndex];
				pixels[pixelIndex] = pixels[x+y*width-1];
				pixels[x+y*width-1] = temp;
				pixelIndex++;
			}
			pixelIndex = (y + 1) * width;
		}
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int[] getPixels() {
		return pixels;
	}
}