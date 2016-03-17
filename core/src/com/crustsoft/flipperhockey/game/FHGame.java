package com.crustsoft.flipperhockey.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crustsoft.flipperhockey.screens.PlayScreen;

public class FHGame extends Game {

	public static final String TITLE="Flipper Hockey";
	public static final int V_WIDTH=720;
	public static final int V_HEIGHT=1140;
	public static final int SCALE=2;
	public static final float STEP = 1 / 60f;
	public static final float PPM = 100;




	public SpriteBatch spriteBatch;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		setScreen(new PlayScreen(this));





	}

	@Override
	public void render () {
		super.render();


	}




}
