package com.crustsoft.flipperhockey.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crustsoft.flipperhockey.game.FHGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new FHGame(), config);
	/*	config.height=FHGame.V_HEIGHT/2;
		config.width=FHGame.V_WIDTH/2;*/
		/*config.height=1920/2;
		config.width=1080/2;*/
		config.height=640;
		config.width=480;
		/*config.height=1280/2;
		config.width=800/2;*/


	}
}
