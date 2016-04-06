package com.crustsoft.flipperhockey.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 28.03.2016.
 */
public class ScoreLineSensor {
    private World world;
    private Body body;
    private BodyDef bodyDef;
    private PlayScreen playScreen;
    private Float startPosition;
    boolean top;
    float x;
    float y;

    public ScoreLineSensor(PlayScreen playScreen,boolean top,float x, float y) {
        this.top=top;
        this.playScreen = playScreen;
        this.world = playScreen.world;
        this.x=x;
        this.y=y;
        defineScoreLineSensor();
    }


    public void defineScoreLineSensor() {

        bodyDef = new BodyDef();
        bodyDef.type= BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / FHGame.PPM, y/FHGame.PPM);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((400/2)/FHGame.PPM,(100/2)/FHGame.PPM);

        FixtureDef fdefScoreLine = new FixtureDef();
        fdefScoreLine.shape=polygonShape;
        fdefScoreLine.isSensor=true;
        if(top){
            fdefScoreLine.filter.categoryBits=FHGame.BIT_GOAL_TOP;
        }
        else {
            fdefScoreLine.filter.categoryBits=FHGame.BIT_GOAL_BOT;
        }
        fdefScoreLine.filter.maskBits=FHGame.BIT_PUCK;

        body =world.createBody(bodyDef);
        body.createFixture(fdefScoreLine).setUserData(this);

    }

    public PlayScreen getPlayScreen() {
        return playScreen;
    }
}
