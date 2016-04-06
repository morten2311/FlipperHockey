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
public class Flipper {
    Vector2[] vertices;
    // BodyDef bdefStartC, bdefEndC, bdefPoly, bdefFlipper;
    Body bodyFlipper, bodyStartC;
    public World world;
    public PlayScreen playScreen;
    public RevoluteJoint joint;

    private float xPos;
    private float yPos;
    protected float flipperLength;
    public float upperAngle;
    private float lowerAngle;

    private float circleStart_radius=15;
    private float circleEnd_radius=10;
    private float density;
    float motorSpeed;
    float maxMotorTorque;


    public Flipper(PlayScreen playScreen, float xPos, float yPos, float upperAngle, float lowerAngle,float motorSpeed,boolean left) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.upperAngle = upperAngle;
        this.lowerAngle = lowerAngle;
        this.playScreen = playScreen;
        this.world = playScreen.world;
        this.density=10f;
        this.maxMotorTorque=400;
        this.motorSpeed=motorSpeed;
        if(left){
            flipperLength=114;
        }
        else {
            flipperLength=-114;
        }

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
        circleStart.setRadius(circleStart_radius / FHGame.PPM);
        BodyDef bdefStartC = new BodyDef();
        bdefStartC.position.set(xPos / FHGame.PPM, yPos / FHGame.PPM);
        bdefStartC.type = BodyDef.BodyType.StaticBody;

        FixtureDef fDefStartC = new FixtureDef();
        fDefStartC.shape = circleStart;

        bodyStartC = world.createBody(bdefStartC);

        bodyStartC.createFixture(fDefStartC);
        circleStart.dispose();


        //CircleEnd
        CircleShape circleEnd = new CircleShape();
        circleEnd.setRadius(circleEnd_radius / FHGame.PPM);
        circleEnd.setPosition(new Vector2(flipperLength / FHGame.PPM, 0));
        FixtureDef fDefEndC = new FixtureDef();
        fDefEndC.shape = circleEnd;



        //Polygon
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        FixtureDef fDefPoly = new FixtureDef();
        fDefPoly.shape = polygonShape;
        bodyFlipper.createFixture(fDefPoly);

        fDefPoly.density = density;



        bodyFlipper.createFixture(fDefEndC);
        bodyFlipper.createFixture(fDefPoly);
        polygonShape.dispose();
        circleEnd.dispose();


        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(bodyStartC, bodyFlipper, new Vector2(xPos / FHGame.PPM, yPos / FHGame.PPM));

        revoluteJointDef.collideConnected = false;




        revoluteJointDef.enableLimit = true;
        revoluteJointDef.upperAngle = upperAngle * MathUtils.degreesToRadians;
        revoluteJointDef.lowerAngle = lowerAngle * MathUtils.degreesToRadians;

        revoluteJointDef.enableMotor = true;
        revoluteJointDef.motorSpeed = motorSpeed;
        revoluteJointDef.maxMotorTorque = maxMotorTorque;
        joint = (RevoluteJoint) world.createJoint(revoluteJointDef);


    }
}
