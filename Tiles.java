import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Tiles {
	private SpriteSheet spriteSheet;
	private HashMap<Integer, Tile> tilesMap = new HashMap<Integer, Tile>();

	public Tiles(File tilesFile, SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
		try {
			Scanner scanner = new Scanner(tilesFile);
			int currID = 0;
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!line.startsWith("//")) {
					String[] splitStrings = line.split("-");
					if(splitStrings.length < 3) { 
						continue;
					} else if(splitStrings.length == 3) {
						String tileName = splitStrings[0];
						int spriteX = Integer.parseInt(splitStrings[1]);
						int spriteY = Integer.parseInt(splitStrings[2]);
						Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));
						tilesMap.put(currID, tile);
					} else if(splitStrings.length == 4){
						int key = Integer.parseInt(splitStrings[0]);
						if(tilesMap.containsKey(key)) {
							tilesMap.put(currID, tilesMap.get(key));
							tilesMap.remove(key);
						}
						String tileName = splitStrings[1];
						int spriteX = Integer.parseInt(splitStrings[2]);
						int spriteY = Integer.parseInt(splitStrings[3]);
						Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));
						tilesMap.put(key, tile);
					}
					currID++;
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void renderTile(int tileID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom) {
		if(tileID >= 0 && tileID < tilesMap.size()) {
			renderer.renderSprite(tilesMap.get(tileID).sprite, xPosition, yPosition, xZoom, yZoom, false);
		} else {
			System.out.println("TileID " + tileID + " is not within range " + tilesMap.size() + ".");
		}
	}

	class Tile {
		public String tileName;
		public Sprite sprite;

		public Tile(String name, Sprite sprite) {
			this.tileName = name;
			this.sprite = sprite;
		}
	}
}