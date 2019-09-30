import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
	private Rectangle camera;
	private BufferedImage view;
	private int[] pixels;

	public RenderHandler(int width, int height) {
		camera = new Rectangle(0, 0, width, height);
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}

	private void setPixel(int pixel, int x, int y) {
		if(x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h) {
			int pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
			if(pixelIndex < pixels.length && pixel != Game.alpha) {
				pixels[pixelIndex] = pixel;
			}
		}
	}

	public void render(Graphics graphics) {
		// Render array of pixels to the screen
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}

	public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(),  xPosition, yPosition, xZoom, yZoom);
	}

	public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom);
	}

	public void renderArray(int[] pixels, int width, int height, int xPosition, int yPosition, int xZoom, int yZoom) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				for(int j = 0; j < yZoom; j++) {
					for(int i = 0; i < xZoom; i++) {
						setPixel(pixels[x+y*width], x*xZoom+xPosition+i, y*yZoom+yPosition+j);
					}
				}
			}
		}
	}

	public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom) {
		int[] recPixels = rectangle.getPixels();
		if(recPixels != null) {
			renderArray(recPixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom);
		}
	}

	public Rectangle getCamera() {
		return camera;
	}

	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
}


// Random Pixels
// for(int heightIndex = 0; heightIndex < height; heightIndex++) {
// 	int randomPixel = (int)(Math.random() * 0xFFFFFF);	
// 	for(int widthIndex = 0; widthIndex < width; widthIndex++) {
// 		pixels[heightIndex*width+widthIndex] = randomPixel;
// 	}
// }