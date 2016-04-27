package com.crustsoft.flipperhockey.gameobjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 01.03.2016.
 */
public class FlipperLeft extends  Flipper {


    public FlipperLeft(PlayScreen playScreen, float xPos, float yPos, float motorSpeed, boolean left) {
        super(playScreen, xPos, yPos, motorSpeed, left);
        vertices = new Vector2[4];
        vertices[0]=new Vector2(0/ FHGame.PPM,circleStart_radius/FHGame.PPM);
        vertices[1]=new Vector2(flipperLength/FHGame.PPM,circleEnd_radius/FHGame.PPM);
        vertices[2]=new Vector2(flipperLength/FHGame.PPM,-circleEnd_radius/FHGame.PPM);
        vertices[3]=new Vector2(0/FHGame.PPM,-circleStart_radius/FHGame.PPM);
        defineFlipper();
    }

    @Override
    public void update() {
        Vector2 position = bodyFlipper.getPosition();
        // Center body is center sprite here
        float hw = getWidth() / 2.0f;

        float hh = getHeight() / 2.0f;
        float a = bodyFlipper.getAngle() * MathUtils.radiansToDegrees;
        float x = position.x ;
        float y = position.y - hh;

      /*  System.out.println(circleStart.getPosition().x*FHGame.PPM + " " +circleStart.getPosition().y*FHGame.PPM);

        System.out.println(position.x*FHGame.PPM+ " " + position.y*FHGame.PPM);*/
        setBounds(x/FHGame.PPM,y,flipperRegion.getRegionWidth()/FHGame.PPM,flipperRegion.getRegionHeight()/FHGame.PPM);

        setOrigin((circleStart_radius+20)/FHGame.PPM,(circleStart_radius+20)/FHGame.PPM);
        setPosition(x+(-circleStart_radius-20)/FHGame.PPM,y);
        setRotation(a);
    }
}


