import java.io.File;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Scanner;

public class Map {
	private Tiles tileSet;
	private int fillTileID = -1;
	private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();

	public Map(File mapFile, Tiles tileSet) {
		this.tileSet = tileSet;
		try {
			Scanner scanner = new Scanner(mapFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!line.startsWith("//")) {
					if(line.contains(":")) {
						String[] splitStrings = line.split(":");
						if(splitStrings.length > 1 && splitStrings[0].equalsIgnoreCase("Fill")) {
							fillTileID = Integer.parseInt(splitStrings[1]);
							continue;
						}
					}
					String[] splitStrings = line.split(",");
					if(splitStrings.length >= 3) {
						MappedTile mappedTile = new MappedTile(Integer.parseInt(splitStrings[0]),
															   Integer.parseInt(splitStrings[1]),
															   Integer.parseInt(splitStrings[2]));
						mappedTiles.add(mappedTile);
					}
				}
			}
		} catch(java.io.FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void renderMap(RenderHandler renderer, int xZoom, int yZoom, int gridSize) {
		int gridSizeX = gridSize * xZoom;
		int gridSizeY = gridSize * yZoom;
		if(fillTileID >= 0) {
			Rectangle camera = renderer.getCamera();
			for(int y = camera.y - gridSizeY - (camera.y % gridSizeY); y < camera.y + camera.h; y += gridSizeY) {
				for(int x = camera.x - gridSizeX - (camera.x % gridSizeX); x < camera.x + camera.w; x += gridSizeX) {
					tileSet.renderTile(fillTileID, renderer, x, y, xZoom, yZoom);
				}
			}
		}
		for(int i = 0; i < mappedTiles.size(); i++) {
			MappedTile tile = mappedTiles.get(i);
			tileSet.renderTile(tile.id, renderer, tile.x * gridSizeX, tile.y * gridSizeY, xZoom, yZoom);
		}
	}

	public void setTile(int id, int x, int y) {
		boolean foundTile = false;
		for(int i = 0; i < mappedTiles.size(); i++) {
			MappedTile tile = mappedTiles.get(i);
			if(tile.x == x && tile.y == y) {
				tile.id = id;
				foundTile = true;
				break;
			}
		}
		if(!foundTile) {
			mappedTiles.add(new MappedTile(id, x, y));
		}
	}

	public void removeTile(int x, int y) {
		for(int i = 0; i < mappedTiles.size(); i++) {
			MappedTile tile = mappedTiles.get(i);
			if(tile.x == x && tile.y == y) {
				mappedTiles.remove(i);
				return;
			}
		}
	}

	public void saveMap(File mapFile) {
		try {
			PrintWriter printWriter = new PrintWriter(mapFile);
			if(fillTileID >= 0) {
				printWriter.println("//Fill tile ID");
				printWriter.println("Fill:" + fillTileID);
			}
			printWriter.println("//TileID,TileX,TileY");
			for(int i = 0; i < mappedTiles.size(); i++) {
				MappedTile tile = mappedTiles.get(i);
				printWriter.println(tile.id + "," + tile.x + "," + tile.y);
			}
			printWriter.close();
		} catch(java.io.IOException e) {
			e.printStackTrace();
		}
		System.out.println("Map Saved!");
	}

	class MappedTile {
		public int id, x, y;
		public MappedTile(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
}