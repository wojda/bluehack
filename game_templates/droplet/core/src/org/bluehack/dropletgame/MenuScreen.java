package org.bluehack.dropletgame;


import org.bluehack.dropletgame.asset.Asset;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen {
	
	Stage stage;
	Game game;
	
	public MenuScreen(Game dropletGame) {
		this.game = dropletGame;
	}

	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		initStage();
		
	}

	private void initStage() {
		final Table table = new Table(Asset.skin);
		final Table higscoreTable = new Table(Asset.skin);
		table.setFillParent(true);
		higscoreTable.setFillParent(true);
		
		Preferences prefs = Asset.prefs;
		
		if (!prefs.contains("highScore1")) {
			 prefs.putString("highScoreName1", "-------");
			 prefs.putInteger("highScore1", 0);
		}
		if (!prefs.contains("highScore2")) {
			 prefs.putString("highScoreName2", "-------");
			 prefs.putInteger("highScore2", 0);
		}
		if (!prefs.contains("highScore3")) {
			 prefs.putString("highScoreName3", "-------");
			 prefs.putInteger("highScore3", 0);
		}
		if (!prefs.contains("highScore4")) {
			 prefs.putString("highScoreName4", "-------");
			 prefs.putInteger("highScore4", 0);
		}
		if (!prefs.contains("highScore5")) {
			 prefs.putString("highScoreName5", "-------");
			 prefs.putInteger("highScore5", 0);
		}
		TextButton startButton = new TextButton("START", Asset.skin);
		
		startButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new GameScreen(game));
				
			}
		});
		
		TextButton highScoreButton = new TextButton("HIGHSCORE", Asset.skin);
		
		highScoreButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				table.remove();
				stage.addActor(higscoreTable);
				
			}
		});
		
		TextButton backButton = new TextButton("BACK", Asset.skin);
		
		backButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				higscoreTable.remove();
				stage.addActor(table);
			}
		});
		
		higscoreTable.add(new Label(prefs.getString("highScore1") + " " + prefs.getString("highScoreName1"), Asset.skin)).row();
		higscoreTable.add(new Label(prefs.getString("highScore2") + " " + prefs.getString("highScoreName2"), Asset.skin)).row();
		higscoreTable.add(new Label(prefs.getString("highScore3") + " " + prefs.getString("highScoreName3"), Asset.skin)).row();
		higscoreTable.add(new Label(prefs.getString("highScore4") + " " + prefs.getString("highScoreName4"), Asset.skin)).row();
		higscoreTable.add(new Label(prefs.getString("highScore5") + " " + prefs.getString("highScoreName5"), Asset.skin)).row();
		higscoreTable.add(backButton).prefWidth(Gdx.graphics.getWidth()/4);
		
		table.add(startButton).prefWidth(Gdx.graphics.getWidth()/4).row();
		table.add(highScoreButton).prefWidth(Gdx.graphics.getWidth()/4);
		stage.addActor(table);
		
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}

}
