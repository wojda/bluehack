package org.bluehack.dropletgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class DropletGame extends Game {
	
	private Screen gameScreen;
	
	@Override
	public void create() {
		gameScreen = new GameScreen();
		this.setScreen(gameScreen);
		
	}
	
	@Override
	public void dispose() {
		gameScreen.dispose();
	}

}
