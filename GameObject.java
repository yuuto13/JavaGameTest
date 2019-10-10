

public interface GameObject {

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom);

	//Call at 60 fps.
	public void update(Game game);

	//Handle mouse click
	public void mouseClicked(Rectangle camera);
}