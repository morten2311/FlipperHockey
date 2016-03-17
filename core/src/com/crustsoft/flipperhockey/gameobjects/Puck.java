package com.crustsoft.flipperhockey.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 01.03.2016.
 */
public class Puck   {
    private PlayScreen playScreen;
    private World world;

    Body body;
  public  BodyDef bdefPuck;
    public Body bodyPuck;

    public Puck(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.world=playScreen.world;
        definePuck();
    }

    public void definePuck(){


         bdefPuck = new BodyDef();
        bdefPuck.type= BodyDef.BodyType.DynamicBody;
        bdefPuck.bullet=true;
        bdefPuck.position.set(490 / FHGame.PPM, 400 / FHGame.PPM);

        CircleShape CirclePuck = new CircleShape();
        CirclePuck.setRadius(15 / FHGame.PPM);

        FixtureDef fdefPuck = new FixtureDef();

        fdefPuck.shape=CirclePuck;
        fdefPuck.density=1;
        fdefPuck.restitution=0.1f;

        bodyPuck =world.createBody(bdefPuck);
        bodyPuck.createFixture(fdefPuck);

    }














}
