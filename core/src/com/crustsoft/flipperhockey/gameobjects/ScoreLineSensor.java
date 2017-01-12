package com.crustsoft.flipperhockey.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.helpers.AssetLoader;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 28.03.2016.
 */
public class ScoreLineSensor extends Sprite {
    private World world;
    private Body body;
    private BodyDef bodyDef;
    private PlayScreen playScreen;
    private Float startPosition;
    boolean top;
    float x;
    float y;

    TextureRegion scorelineRegion, scorelineGlowRegion;

    public ScoreLineSensor(PlayScreen playScreen, boolean top, float x, float y) {

        scorelineGlowRegion = AssetLoader.scorelineGlowRegion;
        scorelineRegion = AssetLoader.scorelineRegion;

        this.top = top;
        this.playScreen = playScreen;
        this.world = playScreen.world;
        this.x = x;
        this.y = y;
        defineScoreLineSensor();
    }


    public void defineScoreLineSensor() {

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / FHGame.PPM, y / FHGame.PPM);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((200) / FHGame.PPM, (10) / FHGame.PPM);

        FixtureDef fdefScoreLine = new FixtureDef();
        fdefScoreLine.shape = polygonShape;
        fdefScoreLine.isSensor = true;
        if (top) {
            fdefScoreLine.filter.categoryBits = FHGame.BIT_GOAL_TOP;
        } else {
            fdefScoreLine.filter.categoryBits = FHGame.BIT_GOAL_BOT;
        }
        fdefScoreLine.filter.maskBits = FHGame.BIT_PUCK;

        body = world.createBody(bodyDef);
        body.createFixture(fdefScoreLine).setUserData(this);

    }

    public PlayScreen getPlayScreen() {
        return playScreen;
    }

    public void setTextureScoreLine() {
        setRegion(scorelineRegion);


    }

    public void setTextureGlow() {
        setRegion(scorelineGlowRegion);


    }

    public void update() {
        Vector2 position = body.getPosition();
        // Center body is center sprite here
        float hw = getWidth() / 2.0f;

        float hh = getHeight() / 2.0f;
        float x = position.x - hw;
        float y = position.y - hh;

        setBounds(x / FHGame.PPM, x, scorelineRegion.getRegionWidth() / FHGame.PPM, scorelineRegion.getRegionHeight() / FHGame.PPM);

        setPosition(x, y);
    }
}
