package com.crustsoft.flipperhockey.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.helpers.AssetLoader;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 01.03.2016.
 */
public abstract class Flipper extends Sprite {
    Vector2[] vertices;
    // BodyDef bdefStartC, bdefEndC, bdefPoly, bdefFlipper;
    Body bodyFlipper, bodyStartC;
    public World world;
    public PlayScreen playScreen;
    public RevoluteJoint joint;
    float restitutions = 0f;
    protected float xPos;
    protected float yPos;
    protected float flipperLength;
    public float upperAngle;
    private float lowerAngle;
    CircleShape circleStart;

    protected float circleStart_radius = 18f;
    protected float circleEnd_radius = 12f;
    private float density;
    float motorSpeed;
    float maxMotorTorque;

    TextureRegion flipperRegion, flipperGlowRegion;


    public Flipper(PlayScreen playScreen, float xPos, float yPos, float motorSpeed, boolean left) {

        flipperGlowRegion = AssetLoader.flipperGlowRegion;
        flipperRegion = AssetLoader.flipperRegion;

        this.xPos = xPos;
        this.yPos = yPos;
        this.upperAngle = 17;
        this.lowerAngle = -17;
        this.playScreen = playScreen;
        this.world = playScreen.world;
        this.density = 3.5f;
        this.maxMotorTorque = 300;
        this.motorSpeed = motorSpeed;
        if (left) {
            flipperLength = 118;
        } else {
            flipperLength = -118;
        }

    }


    public void defineFlipper() {

        //Flipper body
        BodyDef bdefFlipper = new BodyDef();
        bdefFlipper.type = BodyDef.BodyType.DynamicBody;
        bdefFlipper.position.set(xPos / FHGame.PPM, yPos / FHGame.PPM);


        bodyFlipper = world.createBody(bdefFlipper);
        bodyFlipper.setBullet(true);

        //CircleStart
        circleStart = new CircleShape();

        circleStart.setRadius(circleStart_radius / FHGame.PPM);
        BodyDef bdefStartC = new BodyDef();
        bdefStartC.position.set(xPos / FHGame.PPM, yPos / FHGame.PPM);
        bdefStartC.type = BodyDef.BodyType.StaticBody;
        FixtureDef fDefStartC = new FixtureDef();
        fDefStartC.shape = circleStart;
        fDefStartC.restitution = restitutions;

        bodyStartC = world.createBody(bdefStartC);
        bodyStartC.createFixture(fDefStartC);

        //CircleEnd
        CircleShape circleEnd = new CircleShape();
        circleEnd.setRadius(circleEnd_radius / FHGame.PPM);
        circleEnd.setPosition(new Vector2(flipperLength / FHGame.PPM, 0));
        FixtureDef fDefEndC = new FixtureDef();
        fDefEndC.restitution = restitutions;
        fDefEndC.shape = circleEnd;

        //Polygon
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);
        FixtureDef fDefPoly = new FixtureDef();
        fDefPoly.shape = polygonShape;
        bodyFlipper.createFixture(fDefPoly);
        fDefPoly.density = density;
        fDefPoly.restitution = restitutions;


        //Create fixtures
        bodyFlipper.createFixture(fDefEndC);
        bodyFlipper.createFixture(fDefPoly);
        //Set filter
        for (Fixture fixture : bodyFlipper.getFixtureList()) {
            Filter filter = fixture.getFilterData();
            filter.categoryBits = FHGame.BIT_FLIPPER;
            filter.maskBits = FHGame.BIT_PUCK;
            fixture.setFilterData(filter);

        }

        //dispose
        circleStart.dispose();
        polygonShape.dispose();
        circleEnd.dispose();

        //Create joint
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

    public void setTextureFlipper() {
        setRegion(flipperRegion);
    }

    public void setTextureGlow() {
        setRegion(flipperGlowRegion);
    }

    public abstract void update();

}
