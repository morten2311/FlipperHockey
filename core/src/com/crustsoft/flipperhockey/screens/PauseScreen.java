package com.crustsoft.flipperhockey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
 * Created by Morten on 18.04.2016.
 */
public class PauseScreen implements Screen {
    Skin skin;
    Stage stage;
    private Table table;
    private final TextButton exitButton, resumeButton;
    TextureAtlas atlas;
    Array<TextButton> textButtons;
    private Screen parent;
    private FHGame fhGame;

    private BitmapFont fontUp, fontDown;

    public PauseScreen(final FHGame fhGame, final Screen parent) {
        this.parent = parent;
        this.fhGame = fhGame;
        this.stage = new Stage(new ExtendViewport(FHGame.LOGICAL_V_WIDTH,FHGame.LOGICAL_V_HEIGHT));

        atlas = new TextureAtlas("buttons.pack");
        textButtons = new Array<TextButton>();
        this.skin = new Skin();
        skin.addRegions(atlas);
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = skin.getDrawable("buttonUp");
        style.down = skin.getDrawable("buttonDown");
        fontDown= new BitmapFont(Gdx.files.internal("fonts/fontDown-export2x.fnt"),false);
        fontUp = new BitmapFont(Gdx.files.internal("fonts/fontUp-export2x.fnt"),false);
        fontUp.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        style.font = fontUp;


//        this.skin= new Skin((Gdx.files.internal("uiskin.json")));


        System.out.println(stage.getWidth());
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center| Align.top);
        table.setPosition(0,FHGame.LOGICAL_V_HEIGHT);

        exitButton = new TextButton("RESUME", style);
        resumeButton = new TextButton("EXIT", style);


        textButtons.add(resumeButton);
        textButtons.add(exitButton);

        table.padTop(FHGame.LOGICAL_V_HEIGHT/8);
        for (final TextButton textButton: textButtons){
            textButton.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    style.font=fontDown;
                    textButton.setStyle(style);
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    style.font=fontUp;
                    textButton.setStyle(style);
                    if(isOver()){
                    if(textButton.getText().toString().equals("RESUME")){


                        fhGame.setScreen(parent);
                        dispose();
                    }

                    }

                }
            });

            table.add(textButton);
            table.row();


        }


        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);



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
}
