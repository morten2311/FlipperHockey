package com.crustsoft.flipperhockey.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
/*import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;*/
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by Morten on 10.09.2015.
 */
public class AssetLoader {

    private static TextureAtlas atlas;
    public static Preferences prefs;
    public static Sound cling, button, goal, win, bump, airhorn, buttonPressedSound, music;
    public static Texture flipper, flipperGlow, puck, puckGlow, scoreline, scorelineGlow;
    public static TextureRegion flipperRegion, flipperGlowRegion, puckreg, puckGlowreg, scorelineRegion,scorelineGlowRegion;
    public static Texture gameTitle, bg, flippLeft,flippRight, scorelineBot;


    public static void load() {

        //Json


        //Sound files
        bump = Gdx.audio.newSound(Gdx.files.internal("sounds/bump.wav"));
        airhorn = Gdx.audio.newSound(Gdx.files.internal("sounds/airhorn2.wav"));
        buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button.wav"));
        music = Gdx.audio.newSound(Gdx.files.internal("sounds/music.mp3"));



        //textures
        flipper = new Texture("flipper.png");
        flipperGlow = new Texture("flipperGlow.png");
        flipperGlowRegion = new TextureRegion(flipperGlow, 0, 0, flipper.getWidth(), flipper.getHeight());
        flipperRegion = new TextureRegion(flipper, 0, 0, flipper.getWidth(), flipper.getHeight());
        flipperGlow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        flipper.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //Puck
        puck = new Texture("puck.png");
        puckGlow = new Texture("puckGlow.png");
        puckGlow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        puck.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        puckGlowreg = new TextureRegion(puckGlow,0,0,puckGlow.getWidth(),puckGlow.getHeight());
        puckreg = new TextureRegion(puck,0,0,puck.getWidth(),puck.getHeight());



        //ScoreLine
        scoreline= new Texture("scoreline.png");
        scorelineGlow = new Texture("scorelineGlow.png");
        scoreline.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scorelineGlow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scorelineGlowRegion = new TextureRegion(scorelineGlow,0,0,scorelineGlow.getWidth(),scorelineGlow.getHeight());
        scorelineRegion = new TextureRegion(scoreline,0,0,scoreline.getWidth(),scoreline.getHeight());

        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("ZombieBird");
        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.

    }


}
