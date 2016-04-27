package com.crustsoft.flipperhockey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
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

    public boolean pauseGame = false;
    private InputHandler inputHandler;
    public World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private FHGame fhGame;
    private Screen parent;
    private OrthographicCamera camera, cameraStage;
    private Viewport viewport, stageViewport;
    private FlipperLeft flipperLeftBottom, flipperLeftTop;
    private FlipperRight flipperRightBottom, flipperRightTop;
    private Puck puck;
    private FieldContainer fieldContainer;
    Rectangle rectangle,rectangle2;
    ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    Texture laser ;
    Texture laser2;
    Texture markers;
    Texture rect ;
    private ScoreLineSensor scoreLineSensorBottom, scoreLineSensorTop;

    PlayScreenUI playScreenUI;
    InputMultiplexer inputMultiplexer;
    public static Sound cling,flipper;

    public PlayScreen(final FHGame fhGame, Screen parent) {

        this.parent = parent;
        this.fhGame = fhGame;


//        cling = Gdx.audio.newSound(Gdx.files.internal("cling.wav"));
        flipper = Gdx.audio.newSound(Gdx.files.internal("flipp.wav"));
        System.out.println(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
        world = new World(new Vector2(0, 0), true);
        spriteBatch = fhGame.spriteBatch;
        playScreenUI = new PlayScreenUI(spriteBatch,this);

        box2DDebugRenderer = new Box2DDebugRenderer();

        //Camera, Stage and Viewport
        this.camera = new OrthographicCamera();

        viewport = new ExtendViewport(fhGame.LOGICAL_V_WIDTH / FHGame.PPM, fhGame.LOGICAL_V_HEIGHT / FHGame.PPM, camera);
        viewport.apply();
        camera.position.set((fhGame.LOGICAL_V_WIDTH / FHGame.PPM)/2, (fhGame.LOGICAL_V_HEIGHT / FHGame.PPM)/2,0);



        inputHandler = new InputHandler(this);
        inputMultiplexer = new InputMultiplexer(inputHandler,playScreenUI.stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        //Scorelinesensors, positioned top and bottom, not visible on 4:3 only 16:9.
        scoreLineSensorBottom = new ScoreLineSensor(this,false,FHGame.LOGICAL_V_WIDTH/2,-(fhGame.V_HEIGHT-fhGame.LOGICAL_V_HEIGHT)/4);
        scoreLineSensorTop = new ScoreLineSensor(this,true,FHGame.LOGICAL_V_WIDTH/2,(fhGame.V_HEIGHT-fhGame.LOGICAL_V_HEIGHT)/4+fhGame.LOGICAL_V_HEIGHT);


        flipperLeftBottom = new FlipperLeft(this, 150, 145-80, -2, true);
        flipperRightBottom = new FlipperRight(this, 490, 145-80,  2, false);

        flipperLeftTop = new FlipperLeft(this, 150, 995-100,  2, true);
        flipperRightTop = new FlipperRight(this, 490, 995-100,  -2, false);

        puck = new Puck(this,390,571);
        fieldContainer = new FieldContainer(this);

        rectangle = new Rectangle(0, 0, 640, 960);
        rectangle2 = new Rectangle(-40,-90,720,1140);

        shapeRenderer = new ShapeRenderer();
        box2DDebugRenderer.SHAPE_STATIC.add(Color.BLUE);
        world.setContactListener(new B2DContactListener());

        markers = new Texture("markers.png");
        markers.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        laser= new Texture("laser.png");laser2= new Texture("laserOverlayStatic.png");
        laser2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        laser.setFilter(Texture.TextureFilter.Linear , Texture.TextureFilter.Linear);

        rect = new Texture("rect.png");
        rect.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
        camera.position.set((fhGame.LOGICAL_V_WIDTH / FHGame.PPM)/2, (fhGame.LOGICAL_V_HEIGHT / FHGame.PPM)/2,0);
        playScreenUI.stage.getViewport().update(width,height);
      // playScreenUI.stage.getCamera().position.set((fhGame.LOGICAL_V_WIDTH )/2, (fhGame.LOGICAL_V_HEIGHT )/2,0);
    }



    public void update(float delta) {
        if (puck.bodyPuck.getPosition().y > (FHGame.LOGICAL_V_HEIGHT / 2) / FHGame.PPM) {
            world.setGravity(new Vector2(0, 4.5f));

        }
        if (puck.bodyPuck.getPosition().y < (FHGame.LOGICAL_V_HEIGHT / 2) / FHGame.PPM) {
            world.setGravity(new Vector2(0, -4.5f));

        }
        world.step(fhGame.STEP, 6, 2);
        puck.update(delta);
        flipperLeftBottom.update();
        flipperRightBottom.update();
        flipperRightTop.update();
        flipperLeftTop.update();
        scoreLineSensorBottom.update();
        scoreLineSensorTop.update();

    }

    @Override
    public void render(float delta) {

        if(!pauseGame){
        update(delta);


        }
        camera.update();
       // playScreenUI.stage.getCamera().update();


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rectangle2.getX() / FHGame.PPM, rectangle2.getY() / FHGame.PPM, rectangle2.getWidth() / FHGame.PPM, rectangle2.getHeight() / FHGame.PPM);

        // shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(rectangle2.getX() / FHGame.PPM, rectangle2.getY() / FHGame.PPM, rectangle2.getWidth() / FHGame.PPM, rectangle2.getHeight() / FHGame.PPM);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(rectangle.getX() / FHGame.PPM, rectangle.getY() / FHGame.PPM, rectangle.getWidth() / FHGame.PPM, rectangle.getHeight() / FHGame.PPM);
        shapeRenderer.setColor(Color.GREEN);


        shapeRenderer.end();



        spriteBatch.begin();
        //Markers
        spriteBatch.draw(markers,58/FHGame.PPM,151.5f/FHGame.PPM,markers.getWidth()/FHGame.PPM,markers.getHeight()/fhGame.PPM);
        spriteBatch.end();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        spriteBatch.begin();

        //spriteBatch.enableBlending();

        //Glow
        spriteBatch.setColor(Color.BLUE );
        spriteBatch.draw(laser,-36.5f/FHGame.PPM,7.5f/FHGame.PPM,(laser.getWidth())/FHGame.PPM,(laser.getHeight())/FHGame.PPM);

        //Puck
        puck.setTextureGlow();
        puck.setColor(Color.BLUE);
        puck.draw(spriteBatch);

        //Scorelines
        scoreLineSensorBottom.setTextureGlow();
        scoreLineSensorBottom.setColor(Color.GREEN);
        scoreLineSensorBottom.draw(spriteBatch);

        scoreLineSensorTop.setTextureGlow();
        scoreLineSensorTop.setColor(Color.RED);
        scoreLineSensorTop.draw(spriteBatch);

        //Flipper
        flipperLeftBottom.setTextureGlow();
        flipperLeftBottom.setColor(Color.GREEN);
        flipperLeftBottom.draw(spriteBatch);

        flipperRightBottom.setTextureGlow();
        flipperRightBottom.setColor(Color.GREEN);
        flipperRightBottom.draw(spriteBatch);

        flipperLeftTop.setTextureGlow();
        flipperLeftTop.setColor(Color.RED);
        flipperLeftTop.draw(spriteBatch);

        flipperRightTop.setTextureGlow();
        flipperRightTop.setColor(Color.RED);
        flipperRightTop.draw(spriteBatch);
        //Overlay
        spriteBatch.setColor(Color.WHITE);
        spriteBatch.draw(rect,-36.5f/FHGame.PPM,7.5f/FHGame.PPM,(rect.getWidth())/FHGame.PPM,(rect.getHeight())/FHGame.PPM);


        //Flipper
        flipperLeftBottom.setTextureFlipper();
        flipperLeftBottom.setColor(Color.WHITE);
        flipperLeftBottom.draw(spriteBatch);

        flipperRightBottom.setTextureFlipper();
        flipperRightBottom.setColor(Color.WHITE);
        flipperRightBottom.draw(spriteBatch);

        flipperLeftTop.setTextureFlipper();
        flipperLeftTop.setColor(Color.WHITE);
        flipperLeftTop.draw(spriteBatch);

        flipperRightTop.setTextureFlipper();
        flipperRightTop.setColor(Color.WHITE);
        flipperRightTop.draw(spriteBatch);


        //Puck
        puck.setTexturePuck();
        puck.setColor(Color.WHITE);
        puck.draw(spriteBatch);

        //Scorelines
        scoreLineSensorBottom.setTextureScoreLine();
        scoreLineSensorBottom.setColor(Color.WHITE);
        scoreLineSensorBottom.draw(spriteBatch);

        scoreLineSensorTop.setTextureScoreLine();
        scoreLineSensorTop.setColor(Color.WHITE);
        scoreLineSensorTop.draw(spriteBatch);

        //spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.end();

       // box2DDebugRenderer.render(world, camera.combined);


       // fhGame.spriteBatch.setProjectionMatrix(camera.combined);


        spriteBatch.setProjectionMatrix( playScreenUI.stage.getCamera().combined);
        playScreenUI.stage.act();
        playScreenUI.stage.draw();

    }

    public void pauseGame(){
       pauseGame=true;
    }
    public void resumeGame(){

        pauseGame= false;

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

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

    public PlayScreen(Viewport viewport) {
        this.viewport = viewport;
    }

    public Viewport getViewport() {
        return viewport;
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

    public Screen getParent() {
        return parent;
    }

    public FHGame getFhGame() {
        return fhGame;
    }
}
