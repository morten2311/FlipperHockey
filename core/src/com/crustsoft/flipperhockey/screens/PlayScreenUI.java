package com.crustsoft.flipperhockey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.crustsoft.flipperhockey.game.FHGame;

/**
 * Created by Morten on 18.04.2016.
 */
public class PlayScreenUI implements Disposable {
    private Skin skin;
    public Stage stage;
    private Table table;
    private  TextButton exitButton, resumeButton;
    TextureAtlas atlas;
    Array<TextButton> textButtons;
    Dialog dialog;
    Button pauseButton;
    Texture pause;
    Button.ButtonStyle buttonStyle;

    private BitmapFont fontUp, fontDown;
    PlayScreen playScreen;
    public PlayScreenUI(SpriteBatch batch, final PlayScreen playScreen) {
        this.stage = new Stage(new ExtendViewport(FHGame.LOGICAL_V_WIDTH,FHGame.LOGICAL_V_HEIGHT,new OrthographicCamera()));
        this.playScreen = playScreen;
        pause = new Texture("buttons/pause.png");
        pause.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.skin = new Skin();
        skin.add("pause", pause);
        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up= skin.getDrawable("pause");
        buttonStyle.down= skin.getDrawable("pause");
        pauseButton = new Button(skin.getDrawable("pause"));
        //stage.getCamera().position.set((FHGame.LOGICAL_V_WIDTH )/2, (FHGame.LOGICAL_V_HEIGHT )/2,0);
        pauseButton.setPosition((FHGame.LOGICAL_V_WIDTH)-pauseButton.getWidth()*1.5f,(FHGame.LOGICAL_V_HEIGHT/2)-pauseButton.getHeight()/2);

        stage.addActor(pauseButton);

        pauseButton.addListener(new ClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pauseGame();
                show();
                System.out.println("preses");
                super.touchUp(event, x, y, pointer, button);
            }
        });


        atlas = new TextureAtlas("buttons.pack");
        textButtons = new Array<TextButton>();
//        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.addRegions(atlas);
        //skin.add("default-fontUp",new BitmapFont(Gdx.files.internal("fonts/fontUp-export.fnt")),BitmapFont.class);
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); /*//** Button properties **/
        style.up = skin.getDrawable("buttonUp");
        style.down = skin.getDrawable("buttonDown");
        fontDown= new BitmapFont(Gdx.files.internal("fonts/fontDown-export2x.fnt"),false);
        fontUp = new BitmapFont(Gdx.files.internal("fonts/fontUp-export2x.fnt"),false);
        fontUp.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        style.font = fontUp;


//        this.skin= new Skin((Gdx.files.internal("uiskin.json")));




        exitButton = new TextButton("EXIT", style);
        resumeButton = new TextButton("RESUME", style);

        resumeButton.addListener(new ClickListener(){



            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if(isOver()){

                System.out.println("pressed");
                dialog.hide();
                playScreen.resumeGame();
                }

            }
        });
        exitButton.addListener(new ClickListener(){

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if( isOver()){
                playScreen.getFhGame().setScreen(playScreen.getParent());

                }


            }
        });




        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.stageBackground = createDrawable(new Color(0,0,0,0.6f));
        windowStyle.titleFont=fontDown;

        dialog = new Dialog("",windowStyle);
        dialog.setMovable(false);
        dialog.getButtonTable().add(resumeButton).row();
        dialog.getButtonTable().add(exitButton);


    }

    public void show(){

        dialog.show(stage);
        //TODO Nasty hack
        dialog.setPosition(65,FHGame.LOGICAL_V_HEIGHT/3+35);
        //dialog.align(Align.top|Align.center);

    }

    public void pauseGame(){
        playScreen.pauseGame();
    }

    public Drawable createDrawable (Color c) {
        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(c);
        p.drawPixel(0, 0);
        return new SpriteDrawable(new Sprite(new Texture(p)));
    }
    @Override
    public void dispose() {

    }

}
