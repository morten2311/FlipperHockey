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
import com.crustsoft.flipperhockey.screens.PlayScreen;

import aurelienribon.bodyeditor.BodyEditorLoader;



public class FieldContainer extends Sprite{
    private World world;
    public Texture texture,texture1;
    public TextureRegion textureRegion,textureRegion1;
    public FieldContainer(PlayScreen playScreen) {
        world = playScreen.world;


        setBounds(0, 0, (297 / 2) / FHGame.PPM, (1696 / 2) / FHGame.PPM);


/*
        setRegion(textureRegion1);
        setBounds(0,0,(297/2)/FHGame.PPM,(1696/2)/FHGame.PPM);*/

        createContainer();


    }

    private void createContainer()  {


        // 0. Create a loader for the file saved from the editor.
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("container.json"));

        // 1. Create a BodyDef, as usual.
/*
        BodyDef bdefLeftContainer = new BodyDef();
        bdefLeftContainer.position.set(80/FHGame.PPM,163/ FHGame.PPM );
        bdefLeftContainer.type = BodyDef.BodyType.StaticBody;

        BodyDef bdefRightContainer = new BodyDef();
        bdefRightContainer.position.set(520/FHGame.PPM,163/ FHGame.PPM );
        bdefRightContainer.type = BodyDef.BodyType.StaticBody;
*/


        BodyDef bdefLeftContainer = new BodyDef();
        bdefLeftContainer.position.set(28.5f/FHGame.PPM,(70)/ FHGame.PPM );
        bdefLeftContainer.type = BodyDef.BodyType.StaticBody;

        BodyDef bdefRightContainer = new BodyDef();
        bdefRightContainer.position.set(488/FHGame.PPM,(70)/ FHGame.PPM );
        bdefRightContainer.type = BodyDef.BodyType.StaticBody;


        // 2. Create a FixtureDef, as usual.
        FixtureDef fdLeftContainer = new FixtureDef();



        FixtureDef fdRightContainer = new FixtureDef();


        fdLeftContainer.filter.categoryBits=FHGame.BIT_CONTAINER;
        fdRightContainer.filter.categoryBits=FHGame.BIT_CONTAINER;

        fdLeftContainer.filter.maskBits=FHGame.BIT_PUCK;
        fdRightContainer.filter.maskBits=FHGame.BIT_PUCK;

        // 3. Create a Body, as usual.
        Body bodyRightContainer = world.createBody(bdefRightContainer);
        Body bodyLeftContainer = world.createBody(bdefLeftContainer);




        // 4. Create the body fixture automatically by using the loader.
      // Vector2 bottleModelOrigin= loader.getOrigin("fieldleft", 1.15f);


        loader.attachFixture(bodyLeftContainer, "left", fdLeftContainer, 1.235f, 1, 1);
        loader.attachFixture(bodyRightContainer, "right", fdRightContainer, 1.235f, 1, 1);

       // Vector2 pos = bodyLeftContainer.getPosition().sub(bottleModelOrigin);


        //setPosition(40/FHGame.PPM, 40/FHGame.PPM);
//        setPosition(bodyRightContainer.getPosition().x-(32.5f/2)/FHGame.PPM, bodyRightContainer.getPosition().y-(30f/2)/FHGame.PPM);
       //setOrigin(bottleModelOrigin.x, bottleModelOrigin.y);
    }
}
