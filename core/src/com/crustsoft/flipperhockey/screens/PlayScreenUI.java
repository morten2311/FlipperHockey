package com.crustsoft.flipperhockey.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.crustsoft.flipperhockey.game.FHGame;

/**
 * Created by Morten on 18.04.2016.
 */
public class PlayScreenUI implements Disposable {
    private Skin skin;
    public Stage stage;
    private  TextButton exitButton, resumeButton;
    TextureAtlas atlas;
    Array<TextButton> textButtons;
    Dialog dialog;
    Button pauseButton;
    Texture pause;
    Button.ButtonStyle buttonStyle;
    Label scoreTopLabel,scoreBottomLabel, goalLabelBottom,goalLabelTop, winnerLabel,loserLabel;
    Label.LabelStyle labelStyle;
    private BitmapFont fontUp, fontDown;
    public Container containerGoalBottom,containerGoalTop, containerLose, containerWin;



    PlayScreen playScreen;
    public PlayScreenUI(SpriteBatch batch, final PlayScreen playScreen) {
        //this.stage = new Stage(new ExtendViewport(FHGame.LOGICAL_V_WIDTH,FHGame.LOGICAL_V_HEIGHT,new OrthographicCamera()));
        this.stage = new Stage(new ExtendViewport(FHGame.LOGICAL_V_WIDTH,FHGame.LOGICAL_V_HEIGHT,new OrthographicCamera()));
        stage.getCamera().position.set(FHGame.LOGICAL_V_WIDTH /2, FHGame.LOGICAL_V_HEIGHT/2,0);
        this.playScreen = playScreen;
        pause = new Texture("buttons/pause.png");
        pause.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.skin = new Skin();
        skin.add("pause", pause);
        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up= skin.getDrawable("pause");
        buttonStyle.down= skin.getDrawable("pause");
        pauseButton = new Button(skin.getDrawable("pause"));

        fontUp = new BitmapFont(Gdx.files.internal("fonts/fontUp-export2x.fnt"),false);

        labelStyle = new Label.LabelStyle();
        labelStyle.font=fontUp;

        goalLabelBottom = new Label("GOAL!",labelStyle);
        goalLabelTop = new Label("GOAL!",labelStyle);
        winnerLabel = new Label("YOU WIN!",labelStyle);
        loserLabel = new Label("YOU LOSE!",labelStyle);

        scoreTopLabel = new Label(playScreen.getScorePlayerTop()+"",labelStyle);
        scoreBottomLabel = new Label(playScreen.getScorePlayerBot()+"", labelStyle);
        Container containerBottom = new Container(scoreBottomLabel);
        Container containerTop = new Container(scoreTopLabel);
        containerGoalTop = new Container(goalLabelTop);
        containerGoalBottom = new Container(goalLabelBottom);
        containerLose= new Container(loserLabel);
        containerWin =new Container(winnerLabel);

        containerWin.setTransform(true);
        containerLose.setTransform(true);
        containerLose.setVisible(false);
        containerWin.setVisible(false);

        containerGoalBottom.setTransform(true);
       // containerGoalBottom.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0),Actions.fadeIn(1),Actions.rotateBy(360,1f)),Actions.parallel(Actions.rotateBy(-360,1f),Actions.fadeOut(1))));
        containerGoalBottom.setVisible(false);
        //containerGoalBottom.addAction(Actions.rotateBy(-360,2f));
        containerGoalTop.setTransform(true);
        containerGoalTop.setVisible(false);
        containerGoalTop.rotateBy(180);

        containerBottom.setTransform(true);
        containerBottom.setPosition(FHGame.LOGICAL_V_WIDTH-100,FHGame.LOGICAL_V_HEIGHT/2-100);
        containerBottom.rotateBy(-90);

        containerTop.setTransform(true);
        containerTop.setPosition(FHGame.LOGICAL_V_WIDTH-100,FHGame.LOGICAL_V_HEIGHT/2+100);
        containerTop.rotateBy(-90);

        stage.addActor(containerBottom);
        stage.addActor(containerTop);
        stage.addActor(containerGoalBottom);
        stage.addActor(containerGoalTop);
        stage.addActor(containerLose);
        stage.addActor(containerWin);

        pauseButton.setPosition((FHGame.LOGICAL_V_WIDTH)-pauseButton.getWidth()*1.5f,(FHGame.LOGICAL_V_HEIGHT/2)-pauseButton.getHeight()/2);

        stage.addActor(pauseButton);
        //stage.addActor(scoreTopLabel);
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
        stage.addActor(exitButton);
        stage.addActor(resumeButton);
        resumeButton.addListener(new ClickListener(){

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                dialog.hide();
                playScreen.resumeGame();


            }
        });


        exitButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                playScreen.getFhGame().setScreen(playScreen.getParent());


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

    public void update(float delta){
        scoreTopLabel.setText(playScreen.getScorePlayerTop()+"");
        scoreBottomLabel.setText(playScreen.getScorePlayerBot()+"");

    }
    public void show(){

        dialog.show(stage);
        //TODO Nasty hack
        dialog.setPosition(65,FHGame.LOGICAL_V_HEIGHT/3+35);
        //dialog.align(Align.top|Align.center);

    }



    public void showGoalBottomPlayer(){
        containerGoalBottom.setPosition(320,360);

        containerGoalBottom.setVisible(true);
        containerGoalBottom.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0),Actions.fadeIn(0.6f),Actions.rotateBy(360,0.5f))
                ,Actions.delay(0.3f),Actions.parallel(Actions.rotateBy(-360,0.5f),Actions.fadeOut(0.5f)),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        playScreen.reset();
                    }
                })));


    }
    public void showLoseBottomPlayer(){
        containerLose.setPosition(320,240);

        containerLose.setVisible(true);
        containerLose.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0),Actions.fadeIn(0.6f),Actions.rotateBy(360,0.5f))));


    }
    public void showWinBottomPlayer(){
        containerWin.setPosition(320,240);

        containerWin.setVisible(true);
        containerWin.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0),Actions.fadeIn(0.6f),Actions.rotateBy(360,0.5f))));


    }

    public void showGoalTopPlayer(){
        containerGoalTop.setPosition(320,720);

        containerGoalTop.setVisible(true);
        containerGoalTop.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0),Actions.fadeIn(0.8f),Actions.rotateBy(360,0.5f))
                ,Actions.delay(0.3f),Actions.parallel(Actions.rotateBy(-360,0.5f),Actions.fadeOut(0.5f)),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        playScreen.reset();
                    }
                })));

    }
    public void showLoseTopPlayer(){
        containerLose.setPosition(320,720);


        containerLose.setVisible(true);
        containerLose.addAction(Actions.rotateBy(180));

        containerLose.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0),Actions.fadeIn(0.6f),Actions.rotateBy(360,0.5f))));
    }
    public void showWinTopPlayer(){
        containerWin.setPosition(320,720);


        containerWin.setVisible(true);
        containerWin.addAction(Actions.rotateBy(180));
        containerWin.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0),Actions.fadeIn(0.6f),Actions.rotateBy(360,0.5f))));

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
