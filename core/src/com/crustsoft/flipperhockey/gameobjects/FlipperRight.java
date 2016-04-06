package com.crustsoft.flipperhockey.gameobjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 01.03.2016.
 */
public class FlipperRight extends Flipper{
        public FlipperRight(PlayScreen playScreen, float xPos, float yPos, float upperAngle, float lowerAngle, float motorSpeed, boolean left) {
                super(playScreen, xPos, yPos, upperAngle, lowerAngle, motorSpeed, left);
                vertices = new Vector2[4];
                vertices[0] = new Vector2(0 / FHGame.PPM, 15 / FHGame.PPM);
                vertices[1] = new Vector2(0 / FHGame.PPM, -15 / FHGame.PPM);
                vertices[2] = new Vector2(flipperLength / FHGame.PPM, -10 / FHGame.PPM);
                vertices[3] = new Vector2(flipperLength / FHGame.PPM, 10 / FHGame.PPM);
                defineFlipper();
        }




/*    revoluteJointDef.upperAngle = 20 * MathUtils.degreesToRadians;
        revoluteJointDef.lowerAngle = -20 * MathUtils.degreesToRadians;
             private float xPos=495+22;
        private float yPos=148;

        vertices = new Vector2[4];
        vertices[0] = new Vector2(0 / FHGame.PPM, 15 / FHGame.PPM);
        vertices[1] = new Vector2(0 / FHGame.PPM, -15 / FHGame.PPM);
        vertices[2] = new Vector2(-95 / FHGame.PPM, -10 / FHGame.PPM);
        vertices[3] = new Vector2(-95 / FHGame.PPM, 10 / FHGame.PPM);
        */




}
