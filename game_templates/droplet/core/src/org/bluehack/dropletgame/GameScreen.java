package org.bluehack.dropletgame;

import java.util.Random;

import org.bluehack.dropletgame.asset.Asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	
	private Stage stage;
	private Image player;
	private Label livesLabel;
	private float nextDropletTime;
	private int level = 1;
	private int count = 0;
	private int lives = 3;
	private Label scoreLabel;
	private Random random = new Random();
	private Array<Image> dropletList  = new Array<Image>(); 

	@Override
	public void show() {
		stage = new Stage();
		player = new Image(Asset.player);
		
		initGUI();
		
		player.setPosition(Gdx.graphics.getWidth()/2 -player.getWidth()/2, 10);
		stage.addActor(player);
		InputMultiplexer inputMixer = new InputMultiplexer();
		
		InputProcessor input = new Controller(player);
		inputMixer.addProcessor(stage);
		inputMixer.addProcessor(input);
		Gdx.input.setInputProcessor(inputMixer);
	}

	private void initGUI() {
		Table table = new Table(Asset.skin);
		table.setFillParent(true);
		table.align(Align.topRight);
		scoreLabel = new Label(String.valueOf(count), Asset.skin);
		livesLabel = new Label("lives left: " + String.valueOf(lives), Asset.skin);
		livesLabel.setAlignment(Align.left, Align.left);
		table.add(livesLabel).prefWidth(999);
		table.add(scoreLabel);
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateDroplet(delta);
        stage.act();
        stage.draw();

	}

	private void updateDroplet(float delta) {
		nextDropletTime = nextDropletTime - delta;
		if (nextDropletTime < 0f) {
			spawnDroplet();
			nextDropletTime = timeForNextDroplet();
		}
		moveDroplets();
		checkCollisions();
	}

	private void checkCollisions() {
		Rectangle playerBound = new Rectangle(player.getX(),player.getY(), player.getWidth(), player.getHeight());
		for (Image drop : dropletList) {
			Rectangle dropBound = new Rectangle(drop.getX(), drop.getY(), drop.getWidth(), drop.getHeight());
			if (playerBound.overlaps(dropBound)) {
				dropletList.removeValue(drop, true);
				drop.remove();
				count++;
				scoreLabel.setText(String.valueOf(count));
			}
		}
	}

	private void moveDroplets() {
		for (Image drop : dropletList) {
			drop.moveBy(0, -2);
		}
		
	}

	private float timeForNextDroplet() {
		float time = 2f + random.nextFloat() - level * 0.1f; 
		return time;
	}

	private void spawnDroplet() {
		Image droplet = new Image(Asset.drop);
		droplet.setPosition(random.nextInt(450)+10, 470);
		dropletList.add(droplet);
		stage.addActor(droplet);
		
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
		// TODO Auto-generated method stub

	}

}
