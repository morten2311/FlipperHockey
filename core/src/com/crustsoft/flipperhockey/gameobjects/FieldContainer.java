package com.crustsoft.flipperhockey.gameobjects;

/**
 * Created by Morten on 13.03.2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.helpers.AssetLoader;
import com.crustsoft.flipperhockey.screens.PlayScreen;

import aurelienribon.bodyeditor.BodyEditorLoader;


public class FieldContainer extends Sprite {
    private World world;
    private float restitution = 0.0f;

    public FieldContainer(PlayScreen playScreen) {
        world = playScreen.world;
        setBounds(0, 0, (297 / 2) / FHGame.PPM, (1696 / 2) / FHGame.PPM);

        createContainer();

    }

    private void createContainer() {


        // 0. Create a loader for the file saved from the editor.
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("container.json"));

        // 1. Create a BodyDef, as usual.
        BodyDef bdefLeftContainer = new BodyDef();
        bdefLeftContainer.position.set(28.5f / FHGame.PPM, (70) / FHGame.PPM);
        bdefLeftContainer.type = BodyDef.BodyType.StaticBody;

        BodyDef bdefRightContainer = new BodyDef();
        bdefRightContainer.position.set(488 / FHGame.PPM, (70) / FHGame.PPM);
        bdefRightContainer.type = BodyDef.BodyType.StaticBody;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fdLeftContainer = new FixtureDef();
        fdLeftContainer.restitution = restitution;
        fdLeftContainer.friction = 0f;

        FixtureDef fdRightContainer = new FixtureDef();
        fdRightContainer.restitution = restitution;
        fdRightContainer.friction = 0.0f;

        fdLeftContainer.filter.categoryBits = FHGame.BIT_CONTAINER;
        fdRightContainer.filter.categoryBits = FHGame.BIT_CONTAINER;

        fdLeftContainer.filter.maskBits = FHGame.BIT_PUCK;
        fdRightContainer.filter.maskBits = FHGame.BIT_PUCK;

        // 3. Create a Body, as usual.
        Body bodyRightContainer = world.createBody(bdefRightContainer);
        Body bodyLeftContainer = world.createBody(bdefLeftContainer);
        // 4. Create the body fixture automatically by using the loader.

        loader.attachFixture(bodyLeftContainer, "left", fdLeftContainer, 1.235f, 1, 1);
        loader.attachFixture(bodyRightContainer, "right", fdRightContainer, 1.235f, 1, 1);




    }
}
