package com.crustsoft.flipperhockey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.gameobjects.ScoreLineSensor;
import com.crustsoft.flipperhockey.helpers.AssetLoader;

/**
 * Created by Morten on 28.02.2016.
 */
public class MenuScreen implements Screen {
    private TextButton twoPlayerButton, onePlayerButton, settingsButton, exitButton, multiplayerButton;
    Skin skin;
    Stage stage;
    TextureAtlas atlas;
    Array<TextButton> textButtons;
    Sound buttonPressedSound;
    FHGame fhGame;
    private Table table;
    private BitmapFont fontUp, fontDown;
    private Texture gameTitle, bg, flippLeft, flippRight, scorelineBot;
    float ratio, pos;
    boolean up = false;
    Camera camera;
    Viewport viewport;
    private ScoreLineSensor scoreLineSensorBottom;

    public MenuScreen(final FHGame fhGame) {
        this.camera = new OrthographicCamera();
        viewport = new ExtendViewport(fhGame.LOGICAL_V_WIDTH, fhGame.LOGICAL_V_HEIGHT, camera);
        viewport.apply();
        camera.position.set((fhGame.LOGICAL_V_WIDTH) / 2, (fhGame.LOGICAL_V_HEIGHT) / 2, 0);

        //Scorelinesensors, positioned top and bottom, not visible on 4:3 only 16:9.

        System.out.println(Gdx.graphics.getWidth());
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        ratio = (height / width);
        if (ratio >= 1.5f) {
            pos = (height / width) - 1.5f;


        }
        this.fhGame = fhGame;
        //this.stage = new Stage(new ExtendViewport(FHGame.LOGICAL_V_WIDTH, FHGame.LOGICAL_V_HEIGHT,new OrthographicCamera()));
        this.stage = new Stage(new ExtendViewport(FHGame.LOGICAL_V_WIDTH, FHGame.LOGICAL_V_HEIGHT, new OrthographicCamera()));
        buttonPressedSound = AssetLoader.buttonPressedSound;

        scorelineBot = new Texture("StartMenu/scoreline.png");
        gameTitle = new Texture("titleMedium.png");
        flippLeft = new Texture("flippgreen.png");
        flippRight = new Texture("flippRight.png");
        gameTitle.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        flippLeft.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        flippRight.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scorelineBot.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        atlas = new TextureAtlas("buttons.pack");
        textButtons = new Array<TextButton>();
        this.skin = new Skin();
        skin.addRegions(atlas);
        //skin.add("default-fontUp",new BitmapFont(Gdx.files.internal("fonts/fontUp-export.fnt")),BitmapFont.class);
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = skin.getDrawable("buttonUp");
        style.down = skin.getDrawable("buttonDown");

        fontDown = new BitmapFont(Gdx.files.internal("fonts/fontDown-export2x.fnt"), false);
        fontUp = new BitmapFont(Gdx.files.internal("fonts/fontUp-export2x.fnt"), false);
        fontUp.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        style.font = fontUp;


//        this.skin= new Skin((Gdx.files.internal("uiskin.json")));


        table = new Table();
        table.padTop(0);
        table.setWidth(stage.getWidth());
        table.setPosition(0, 0);
        table.setFillParent(true);
        twoPlayerButton = new TextButton("PLAY", style);

        exitButton = new TextButton("EXIT", style);



        textButtons.add(twoPlayerButton);
        textButtons.add(exitButton);

        for (final TextButton textButton : textButtons) {
            textButton.addListener(new ClickListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    style.font = fontDown;
                    textButton.setStyle(style);
                    //System.out.println(textButton.getText());

                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("touch");
                    style.font = fontUp;
                    textButton.setStyle(style);
                    buttonPressedSound.play();
                    if (textButton.getText().toString().equals("PLAY")) {
                        System.out.println("2Player");
                        fhGame.setScreen(new PlayScreen(fhGame, MenuScreen.this));
                    } else if (textButton.getText().toString().equals("EXIT")) {
                        Gdx.app.exit();
                    }


                }
            });
            table.add(textButton);
            table.row().padTop(30);
        }

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fhGame.spriteBatch.setProjectionMatrix(camera.combined);

        fhGame.spriteBatch.begin();
        //fhGame.spriteBatch.draw(bg,-124,-90);
        //fhGame.spriteBatch.draw(gameTitle,0,580,gameTitle.getWidth()/3,gameTitle.getHeight()/3);
        fhGame.spriteBatch.draw(gameTitle, 30, 670);
        //fhGame.spriteBatch.draw(flippLeft,0,150, flippLeft.getWidth()/1.5f, flippLeft.getHeight()/1.5f);
        //fhGame.spriteBatch.draw(flippRight,380,150, flippLeft.getWidth()/1.5f, flippLeft.getHeight()/1.5f);


        fhGame.spriteBatch.end();
        stage.act();
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set((fhGame.LOGICAL_V_WIDTH) / 2, (fhGame.LOGICAL_V_HEIGHT) / 2, 0);
        stage.getViewport().update(width, height, true);


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
