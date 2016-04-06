package com.crustsoft.flipperhockey.gameobjects;

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
        bdefPuck.position.set(380 / FHGame.PPM, 571 / FHGame.PPM);

        CircleShape CirclePuck = new CircleShape();
        CirclePuck.setRadius((38f) / FHGame.PPM);

        FixtureDef fdefPuck = new FixtureDef();

        fdefPuck.shape=CirclePuck;
        fdefPuck.density=1f;
        fdefPuck.restitution=0.4f;
        fdefPuck.friction=0.0f;
        fdefPuck.filter.categoryBits= FHGame.BIT_PUCK;
        fdefPuck.filter.maskBits=FHGame.BIT_CONTAINER|FHGame.BIT_FLIPPER|FHGame.BIT_GOAL_BOT |FHGame.BIT_GOAL_TOP;


        bodyPuck =world.createBody(bdefPuck);
        bodyPuck.setFixedRotation(true);
        bodyPuck.setLinearDamping(0.3f);
        bodyPuck.setUserData(this);
        bodyPuck.createFixture(fdefPuck);

    }














}
