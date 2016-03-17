package com.crustsoft.flipperhockey.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crustsoft.flipperhockey.game.FHGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new FHGame(), config);
		config.height=FHGame.V_HEIGHT;
		config.width=FHGame.V_WIDTH;
	}
}
