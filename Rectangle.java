

public class Rectangle {
	public int x, y, w, h;
	private int[] pixels;

	Rectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	Rectangle() {
		this(0, 0, 0, 0);
	}

	public void genRecGraphics(int color) {
		pixels = new int[w*h];
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				pixels[x + y * w] = color;
			}
		}
	}

	public void genRecGraphics(int bolderWidth, int color) {
		pixels = new int[w*h];

		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = Game.alpha;
		}

		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				if(y <= bolderWidth || y >= h - bolderWidth - 1 || x <= bolderWidth || x >= w - bolderWidth - 1) {
					pixels[x + y * w] = color;
				}
			}
		}
	}

	public int[] getPixels() {
		if(pixels != null) {
			return pixels;
		} else {
			System.out.println("Attempted to retrive pixels from a Rectangle object without generating graphics.");
		}
		return null;
	}
}