

public class GUI implements GameObject {
	private Sprite bg;
	private GUIButton[] buttons;
	private Rectangle rect = new Rectangle();
	private boolean isFixed;

	public GUI(Sprite bg, GUIButton[] buttons, int x, int y, boolean isFixed) {
		this.bg = bg;
		this.buttons = buttons;
		this.isFixed = isFixed;
		rect.x = x;
		rect.y = y;
		if(bg == null) return;
		rect.w = bg.getWidth();
		rect.h = bg.getHeight();
	}

	public GUI(GUIButton[] buttons, int x, int y, boolean isFixed) {
		this(null, buttons, x, y, isFixed);
	}

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		if(bg != null) {
			renderer.renderSprite(bg, rect.x, rect.y, xZoom, yZoom);
		}
		if(buttons != null) {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].render(renderer, xZoom, yZoom);
			}
		}
	}

	//Call at 60 fps.
	public void update(Game game) {
		if(buttons != null) {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].update(game);
			}
		}
	}

	//Handle mouse click
	public void mouseClicked(Rectangle camera) {

	}


}