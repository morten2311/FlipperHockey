package com.crustsoft.flipperhockey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.gameobjects.FieldContainer;
import com.crustsoft.flipperhockey.gameobjects.FlipperLeft;
import com.crustsoft.flipperhockey.gameobjects.FlipperRight;
import com.crustsoft.flipperhockey.gameobjects.Puck;

/**
 * Created by Morten on 28.02.2016.
 */
public class PlayScreen implements Screen{
    Matrix4 debugMatrix;
    public World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FHGame fhGame;
    private Texture texture;
    private OrthographicCamera camera;
    private Viewport viewport;

    FlipperLeft flipperLeft;
    FlipperRight flipperRight;
    Puck puck;
    FieldContainer fieldContainer;
    Rectangle rectangle;
    ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;




    public PlayScreen(FHGame fhGame) {
        this.fhGame=fhGame;
        world = new World(new Vector2(0,-9.81f),true);
        spriteBatch= fhGame.spriteBatch;
        box2DDebugRenderer = new Box2DDebugRenderer();

        this.camera=new OrthographicCamera();

        viewport= new FitViewport(fhGame.V_WIDTH/FHGame.PPM,fhGame.V_HEIGHT/FHGame.PPM,camera);
        viewport.apply();
        //camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.setToOrtho(false, viewport.getWorldWidth(),viewport.getWorldHeight());
        flipperLeft= new FlipperLeft(this);
        flipperRight= new FlipperRight(this);
        puck = new Puck(this);
        fieldContainer=new FieldContainer(this);
        rectangle= new Rectangle(40,90,640,960);
         shapeRenderer= new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);


        box2DDebugRenderer.SHAPE_STATIC.add(Color.BLUE);



/*


        BodyDef rectDef  = new BodyDef();
        rectDef.position.set(0/FHGame.PPM,0/FHGame.PPM);
        rectDef.type= BodyDef.BodyType.StaticBody;
       Body rectBod= world.createBody(rectDef);


        PolygonShape rect=new PolygonShape();


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape= rect;
        rect.setAsBox(630/FHGame.PPM,10/FHGame.PPM,new Vector2(0,0),0);
        rectBod.createFixture(fixtureDef);

        rect.setAsBox(630/FHGame.PPM,10/FHGame.PPM,new Vector2(0,900/FHGame.PPM),0);
        rectBod.createFixture(fixtureDef);

        rect.setAsBox(10 / FHGame.PPM, 900 / FHGame.PPM, new Vector2(20/FHGame.PPM, 0 / FHGame.PPM), 0);
        rectBod.createFixture(fixtureDef);


        rect.setAsBox(10/FHGame.PPM,900/FHGame.PPM,new Vector2(400/FHGame.PPM,0),0);
        rectBod.createFixture(fixtureDef);

*/








    }

    public void handleInput() {
        if( Gdx.input.isTouched()) {

            flipperRight.joint.setMotorSpeed(-30);
        }
        if( !Gdx.input.isTouched()) {

            flipperRight.joint.setMotorSpeed(30);
        }




    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        handleInput();
        if(puck.bodyPuck.getPosition().y>480/FHGame.PPM){
            //world.setGravity(new Vector2(0,9.81f));

        }
        camera.update();
        world.step(fhGame.STEP, 6, 2);
       /* if(flipperLeft.joint.getJointAngle()>=25* MathUtils.degreesToRadians){
            flipperLeft.joint.setMotorSpeed(0);
        }
*/

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

/*
       shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(rectangle.getX()/FHGame.PPM,rectangle.getY()/FHGame.PPM,rectangle.getWidth()/FHGame.PPM,rectangle.getHeight()/FHGame.PPM);
        shapeRenderer.end();*/

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        //spriteBatch.draw(fieldContainer.texture, 10 /FHGame.PPM, 10/FHGame.PPM,114/FHGame.PPM,814/FHGame.PPM);
        fieldContainer.draw(spriteBatch);
        spriteBatch.end();

        box2DDebugRenderer.render(world,camera.combined);




        //fhGame.spriteBatch.setProjectionMatrix(camera.combined);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
       // camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);



    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
