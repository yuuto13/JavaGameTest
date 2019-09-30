import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class KeyBoardListener implements KeyListener, FocusListener {
	public boolean[] keys = new boolean[120];
	public HashMap<String, String[]> keyMap = new HashMap<String, String[]>();

	public KeyBoardListener(File keyMapFile) {
		try {
			Scanner scanner = new Scanner(keyMapFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!line.startsWith("//")) {
					String[] splitStrings = line.split(" ");
					if(splitStrings.length >= 2) {
						String[] keySet = new String[splitStrings.length-1];
						for(int i = 0; i < splitStrings.length-1; i++) {
							keySet[i] = splitStrings[i+1];
						}
						keyMap.put(splitStrings[0], keySet);
					}
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode < keys.length) {
			keys[keyCode] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode < keys.length) {
			keys[keyCode] = false;
		}
	}

	public void focusLost(FocusEvent e) {
		for(int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void focusGained(FocusEvent e) {}

	public void setKeySet(String name, String[] keySet) {
		
	}

	private boolean fetchKeys(String name) {
		boolean isAllKeysPressed = true;
		String[] keySet = keyMap.get(name);
		for(int i = 0; i < keySet.length; i++) {
			String[] strings = keySet[i].split("-");
			for(int j = 0; j < strings.length; j++) {
				int keyCode = Integer.parseInt(strings[j]);
				if(!keys[keyCode]) {
					isAllKeysPressed = false;
				}
			}
			if(isAllKeysPressed) {
				return true;
			} else isAllKeysPressed = true;
		}
		return false;
	}

	private boolean fetchKeysOnce(String name) {
		boolean isAllKeysPressed = true;
		ArrayList<Integer> keyList = new ArrayList<Integer>();
		String[] keySet = keyMap.get(name);
		for(int i = 0; i < keySet.length; i++) {
			String[] strings = keySet[i].split("-");
			for(int j = 0; j < strings.length; j++) {
				int keyCode = Integer.parseInt(strings[j]);
				keyList.add(keyCode);
				if(!keys[keyCode]) {
					isAllKeysPressed = false;
				}
			}
			if(isAllKeysPressed) {
				for(int keyCode : keyList) {
					keys[keyCode] = false;
				}
				return true;
			} else isAllKeysPressed = true;
		}
		return false;
	}

	public boolean up() {
		return fetchKeys("UP");
	}
	public boolean down() {
		return fetchKeys("DOWN");
	}
	public boolean left() {
		return fetchKeys("LEFT");
	}
	public boolean right() {
		return fetchKeys("RIGHT");
	}
	public boolean save_map() {
		return fetchKeysOnce("SAVE_MAP");
	}
	public boolean sprint() {
		return fetchKeys("SPRINT");
	}
	public boolean tool_keys() {
		return fetchKeys("TOOL_KEYS");
	}
}