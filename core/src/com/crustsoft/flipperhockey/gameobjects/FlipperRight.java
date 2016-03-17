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
public class FlipperRight {
    Vector2[] vertices;
   // BodyDef bdefStartC, bdefEndC, bdefPoly, bdefFlipper;
    Body bodyFlipper, bodyStartC;
    public World world;
    public PlayScreen playScreen;
    public RevoluteJoint joint;
    private float xPos=495;
    private float yPos=137;



    public FlipperRight(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.world = playScreen.world;

        vertices = new Vector2[4];
        vertices[0] = new Vector2(0 / FHGame.PPM, 15 / FHGame.PPM);
        vertices[1] = new Vector2(0 / FHGame.PPM, -15 / FHGame.PPM);
        vertices[2] = new Vector2(-150 / FHGame.PPM, -10 / FHGame.PPM);
        vertices[3] = new Vector2(-150 / FHGame.PPM, 10 / FHGame.PPM);

        defineFlipper();

    }

    public void defineFlipper() {

        //Main body
        BodyDef bdefFlipper = new BodyDef();
        bdefFlipper.type = BodyDef.BodyType.DynamicBody;
        bdefFlipper.position.set(xPos / FHGame.PPM, yPos / FHGame.PPM);

        bodyFlipper = world.createBody(bdefFlipper);
        bodyFlipper.setBullet(true);

        //CircleStart
        CircleShape circleStart = new CircleShape();
        circleStart.setRadius(15 / FHGame.PPM);
        BodyDef bdefStartC = new BodyDef();
        bdefStartC.position.set(xPos / FHGame.PPM, yPos / FHGame.PPM);
        bdefStartC.type = BodyDef.BodyType.StaticBody;

        FixtureDef fDefStartC = new FixtureDef();
        fDefStartC.shape = circleStart;
        fDefStartC.density = 1;

        bodyStartC = world.createBody(bdefStartC);

        bodyStartC.createFixture(fDefStartC);
        circleStart.dispose();


        //CircleEnd
        CircleShape circleEnd = new CircleShape();
        circleEnd.setRadius(10 / FHGame.PPM);
        circleEnd.setPosition(new Vector2(-150 / FHGame.PPM, 0));
        FixtureDef fDefEndC = new FixtureDef();
        fDefEndC.shape = circleEnd;
        fDefEndC.density = 1f;



        //Polygon
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        FixtureDef fDefPoly = new FixtureDef();
        fDefPoly.shape = polygonShape;
        bodyFlipper.createFixture(fDefPoly);

        fDefPoly.density = 1f;


        bodyFlipper.createFixture(fDefEndC);
        bodyFlipper.createFixture(fDefPoly);
        polygonShape.dispose();
        circleEnd.dispose();


        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(bodyStartC, bodyFlipper, new Vector2(xPos / FHGame.PPM, yPos / FHGame.PPM));
       /* revoluteJointDef.bodyB=bodyStartC;
        revoluteJointDef.bodyA=bodyFlipper;*/
        revoluteJointDef.collideConnected = false;


        //revoluteJointDef.localAnchorB.set(0, 0);
        //revoluteJointDef.localAnchorA.set(0/FHGame.PPM, 0);


        revoluteJointDef.enableLimit = true;
        revoluteJointDef.upperAngle = 25 * MathUtils.degreesToRadians;
        revoluteJointDef.lowerAngle = -25 * MathUtils.degreesToRadians;

        revoluteJointDef.enableMotor = true;
        revoluteJointDef.motorSpeed = 2;
        revoluteJointDef.maxMotorTorque = 50;
      joint = (RevoluteJoint) world.createJoint(revoluteJointDef);


    }
}
