package org.bluehack.dropletgame.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Asset {

	public static final Texture player = new Texture(Gdx.files.internal("player.png"));
    public static final Skin skin = new Skin(Gdx.files.internal("UI/Holo.json"));
	public static final Texture drop = new Texture(Gdx.files.internal("drop.png"));
    
}
