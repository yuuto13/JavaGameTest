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

	private void setPixel(int pixel, int x, int y, boolean isFixed) {
		int pixelIndex = 0;
		if(!isFixed) {
			if(x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h) {
				pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
				
			}
		} else {
			if(x >= 0 && y >= 0 && x <= camera.w && y <= camera.h) {
				pixelIndex = x + y * view.getWidth();
			}
		}
		if(pixelIndex < pixels.length && pixel != Game.alpha) {
			pixels[pixelIndex] = pixel;
		}
	}

	public void render(Graphics graphics) {
		// Render array of pixels to the screen
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}

	public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom, boolean isFixed) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(),  xPosition, yPosition, xZoom, yZoom, isFixed);
	}

	public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom, boolean isFixed) {
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom, isFixed);
	}

	public void renderArray(int[] pixels, int width, int height, int xPosition, int yPosition, int xZoom, int yZoom, boolean isFixed) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				for(int j = 0; j < yZoom; j++) {
					for(int i = 0; i < xZoom; i++) {
						setPixel(pixels[x+y*width], x*xZoom+xPosition+i, y*yZoom+yPosition+j, isFixed);
					}
				}
			}
		}
	}

	public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom, boolean isFixed) {
		if(rectangle == null) return;
		int[] recPixels = rectangle.getPixels();
		if(recPixels != null) {
			renderArray(recPixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom, isFixed);
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