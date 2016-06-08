package com.crustsoft.flipperhockey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.crustsoft.flipperhockey.game.FHGame;

/**
 * Created by Morten on 28.02.2016.
 */
public class MenuScreen implements Screen {
    private final TextButton twoPlayerButton, onePlayerButton, settingsButton, exitButton, multiplayerButton;
    Skin skin;
    Stage stage;
    TextureAtlas atlas;
    Array<TextButton> textButtons;
    Sound buttonPressedSound;
    FHGame fhGame;
    private Table table;
    private BitmapFont fontUp, fontDown;
    private Texture gameTitle;

    public MenuScreen(final FHGame fhGame) {
        this.fhGame = fhGame;
        this.stage = new Stage(new ExtendViewport(FHGame.LOGICAL_V_WIDTH, FHGame.LOGICAL_V_HEIGHT));
        buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button.wav"));
        gameTitle = new Texture("FlipperHockeyTitle.png");
        gameTitle.setFilter(Texture.TextureFilter.Linear , Texture.TextureFilter.Linear);

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


        System.out.println(stage.getWidth());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, FHGame.LOGICAL_V_HEIGHT);
        twoPlayerButton = new TextButton("TWO PLAYER", style);
        onePlayerButton = new TextButton("ONE PLAYER", style);
        settingsButton = new TextButton("SETTINGS", style);
        exitButton = new TextButton("EXIT", style);
        multiplayerButton = new TextButton("MULTIPLAYER", style);

        textButtons.add(multiplayerButton);
        textButtons.add(onePlayerButton);
        textButtons.add(twoPlayerButton);
        textButtons.add(settingsButton);
        textButtons.add(exitButton);

        table.padTop(FHGame.LOGICAL_V_HEIGHT / 4);
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
                    if (textButton.getText().toString().equals("TWO PLAYER")) {
                        System.out.println("2Player");
                        fhGame.setScreen(new PlayScreen(fhGame, MenuScreen.this));
                    } else if (textButton.getText().toString().equals("EXIT")) {
                        Gdx.app.exit();
                    }


                }
            });
            table.add(textButton);
            table.row();
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fhGame.spriteBatch.begin();
        fhGame.spriteBatch.draw(gameTitle,40,650);
        fhGame.spriteBatch.end();
        stage.act();
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);


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
