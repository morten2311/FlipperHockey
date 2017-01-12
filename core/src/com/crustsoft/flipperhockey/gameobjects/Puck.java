package com.crustsoft.flipperhockey.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.helpers.AssetLoader;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 01.03.2016.
 */
public class Puck extends Sprite {
    private PlayScreen playScreen;
    private World world;

    public BodyDef bdefPuck;
    public Body bodyPuck;
    TextureRegion puckreg, puckGlowreg;
    float x, y;

    public Puck(PlayScreen playScreen, float x, float y) {
        this.x = x;
        this.y = y;
        this.playScreen = playScreen;
        this.world = playScreen.world;


        puckGlowreg = AssetLoader.puckGlowreg;
        puckreg = AssetLoader.puckreg;

        definePuck();
    }

    public void definePuck() {


        bdefPuck = new BodyDef();
        bdefPuck.type = BodyDef.BodyType.KinematicBody;
        bdefPuck.bullet = true;
        bdefPuck.position.set(x / FHGame.PPM, y / FHGame.PPM);

        CircleShape CirclePuck = new CircleShape();
        CirclePuck.setRadius((41f) / FHGame.PPM);

        FixtureDef fdefPuck = new FixtureDef();

        fdefPuck.shape = CirclePuck;
        fdefPuck.density = 1f;
        fdefPuck.restitution = 0.4f;
        fdefPuck.friction = 0.0f;
        fdefPuck.filter.categoryBits = FHGame.BIT_PUCK;
        fdefPuck.filter.maskBits = FHGame.BIT_CONTAINER | FHGame.BIT_FLIPPER | FHGame.BIT_GOAL_BOT | FHGame.BIT_GOAL_TOP;


        bodyPuck = world.createBody(bdefPuck);
        // bodyPuck.setFixedRotation(true);
        // bodyPuck.setLinearDamping(0.1f);
        bodyPuck.setUserData(this);
        bodyPuck.createFixture(fdefPuck);

        CirclePuck.dispose();

    }

    public void setDynamic() {
        bdefPuck.type = BodyDef.BodyType.DynamicBody;

    }

    public void resetPuck() {
        bodyPuck.setLinearVelocity(0, 0);
        bodyPuck.setAngularVelocity(0);
        bodyPuck.setTransform(x / FHGame.PPM, y / FHGame.PPM, 0);
    }

    public void setTexturePuck() {
        setRegion(puckreg);


    }

    public void setTextureGlow() {
        setRegion(puckGlowreg);


    }

    public void update(float dt) {
        Vector2 position = bodyPuck.getPosition();
        // Center body is center sprite here
        float hw = getWidth() / 2.0f;

        float hh = getHeight() / 2.0f;
        float a = bodyPuck.getAngle() * MathUtils.radiansToDegrees;
        float x = position.x - hw;
        float y = position.y - hh;

        setBounds(x / FHGame.PPM, y, puckreg.getRegionWidth() / FHGame.PPM, puckreg.getRegionHeight() / FHGame.PPM);

        setPosition(x, y);
        setRotation(a);


    }

    public Body getPuckBody() {
        return bodyPuck;
    }


}
