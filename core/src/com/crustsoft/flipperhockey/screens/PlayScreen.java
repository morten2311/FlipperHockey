package com.crustsoft.flipperhockey.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.gameobjects.FieldContainer;
import com.crustsoft.flipperhockey.gameobjects.FlipperLeft;
import com.crustsoft.flipperhockey.gameobjects.FlipperRight;
import com.crustsoft.flipperhockey.gameobjects.Puck;
import com.crustsoft.flipperhockey.gameobjects.ScoreLineSensor;
import com.crustsoft.flipperhockey.helpers.B2DContactListener;
import com.crustsoft.flipperhockey.helpers.InputHandler;

/**
 * Created by Morten on 28.02.2016.
 */
public class PlayScreen implements Screen {
    private int scorePlayerBot = 0;
    private int scorePlayerTop = 0;


    private InputHandler inputHandler;
    public World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FHGame fhGame;
    private OrthographicCamera camera;
    private Viewport viewport;
    private FlipperLeft flipperLeftBottom, flipperLeftTop;
    private FlipperRight flipperRightBottom, flipperRightTop;
    private Puck puck;
    private FieldContainer fieldContainer;
    Rectangle rectangle,rectangle2;
    ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private ScoreLineSensor scoreLineSensorBottom, scoreLineSensorTop;


    public PlayScreen(FHGame fhGame) {
        System.out.println(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
        this.fhGame = fhGame;
        world = new World(new Vector2(0, 0), true);
        spriteBatch = fhGame.spriteBatch;
        box2DDebugRenderer = new Box2DDebugRenderer();

        this.camera = new OrthographicCamera();

        viewport = new ExtendViewport(fhGame.V_WIDTH / FHGame.PPM, fhGame.V_HEIGHT / FHGame.PPM, camera);
        //viewport.setScreenBounds(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());

        viewport.apply();
        camera.position.set(FHGame.V_WIDTH / 2, FHGame.V_HEIGHT / 2, 0);
      camera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());

        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

        scoreLineSensorBottom = new ScoreLineSensor(this,false,360,50);
        scoreLineSensorTop = new ScoreLineSensor(this,true,360,1095);
       // scoreLineSensorTop= new ScoreLineSensor(this,true,400,800);

        flipperLeftBottom = new FlipperLeft(this, 195, 145, 20, -20, -2, true);
        flipperRightBottom = new FlipperRight(this, 495 + 22, 145, 20, -20, 2, false);

        flipperLeftTop = new FlipperLeft(this, 195, 995, 20, -20, 2, true);
        flipperRightTop = new FlipperRight(this, 495 + 22, 995, 20, -20, -2, false);

        puck = new Puck(this);
        fieldContainer = new FieldContainer(this);

        rectangle = new Rectangle(40, 90, 640, 960);
        rectangle2 = new Rectangle(0, 0, 720, 1140);

        shapeRenderer = new ShapeRenderer();
        box2DDebugRenderer.SHAPE_STATIC.add(Color.BLUE);
        world.setContactListener(new B2DContactListener());


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        //camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.position.set((fhGame.V_WIDTH / FHGame.PPM)/2, (fhGame.V_HEIGHT / FHGame.PPM)/2,0);

    }



    public void update(float delta) {
        if (puck.bodyPuck.getPosition().y > (FHGame.V_HEIGHT / 2) / FHGame.PPM) {
            world.setGravity(new Vector2(0, 4f));

        }
        if (puck.bodyPuck.getPosition().y < (FHGame.V_HEIGHT / 2) / FHGame.PPM) {
            world.setGravity(new Vector2(0, -4f));

        }
        camera.update();
        world.step(fhGame.STEP, 6, 2);

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rectangle2.getX() / FHGame.PPM, rectangle2.getY() / FHGame.PPM, rectangle2.getWidth() / FHGame.PPM, rectangle2.getHeight() / FHGame.PPM);

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(rectangle2.getX() / FHGame.PPM, rectangle2.getY() / FHGame.PPM, rectangle2.getWidth() / FHGame.PPM, rectangle2.getHeight() / FHGame.PPM);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(rectangle.getX() / FHGame.PPM, rectangle.getY() / FHGame.PPM, rectangle.getWidth() / FHGame.PPM, rectangle.getHeight() / FHGame.PPM);
        shapeRenderer.end();


        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        //spriteBatch.draw(fieldContainer.texture, 10 /FHGame.PPM, 10/FHGame.PPM,114/FHGame.PPM,814/FHGame.PPM);
        fieldContainer.draw(spriteBatch);
        spriteBatch.end();


        box2DDebugRenderer.render(world, camera.combined);


        //fhGame.spriteBatch.setProjectionMatrix(camera.combined);


    }

    @Override
    public void show() {

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


    public void addScorePlayerBot() {
        scorePlayerBot++;
        System.out.println(scorePlayerBot);

    }

    public void addScorePlayerTop() {
        scorePlayerTop++;
        System.out.println(scorePlayerTop);


    }


    public FlipperLeft getFlipperLeftBottom() {
        return flipperLeftBottom;
    }

    public FlipperLeft getFlipperLeftTop() {
        return flipperLeftTop;
    }

    public FlipperRight getFlipperRightBottom() {
        return flipperRightBottom;
    }

    public FlipperRight getFlipperRightTop() {
        return flipperRightTop;
    }

    public Puck getPuck() {
        return puck;
    }

    public FieldContainer getFieldContainer() {
        return fieldContainer;
    }

    public int getScorePlayerTop() {
        return scorePlayerTop;
    }

    public int getScorePlayerBot() {
        return scorePlayerBot;
    }
}
