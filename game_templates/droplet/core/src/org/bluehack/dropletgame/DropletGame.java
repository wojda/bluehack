package org.bluehack.dropletgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class DropletGame extends Game {
	
	private Screen menuScreen;
	
	@Override
	public void create() {
		menuScreen = new MenuScreen(this);
		this.setScreen(menuScreen);
		
	}
	
	@Override
	public void dispose() {
		menuScreen.dispose();
	}

}
