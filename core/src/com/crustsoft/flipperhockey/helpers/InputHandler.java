package com.crustsoft.flipperhockey.helpers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.gameobjects.FlipperRight;
import com.crustsoft.flipperhockey.screens.PlayScreen;
import com.sun.org.apache.bcel.internal.generic.FNEG;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Morten on 30.03.2016.
 */
public class InputHandler implements InputProcessor {
        private int pointerLeftTop, pointerLeftBottom, pointerRightTop, pointerRightBottom;
        public TouchRegions topLeft, topRight, bottomLeft, bottomRight;
        private PlayScreen playScreen ;

        public InputHandler(PlayScreen playScreen, float scaleFactorX,float scaleFactorY) {
            //TouchRegions
        topLeft= new TouchRegions(0,FHGame.V_HEIGHT/2,FHGame.V_WIDTH/2,FHGame.V_HEIGHT/2);
        bottomLeft = new TouchRegions(0,0,FHGame.V_WIDTH/2,FHGame.V_HEIGHT/2);
        topRight = new TouchRegions(FHGame.V_WIDTH/2,FHGame.V_HEIGHT/2, FHGame.V_WIDTH/2,FHGame.V_HEIGHT/2);
        bottomRight = new TouchRegions(FHGame.V_WIDTH/2,0, FHGame.V_WIDTH/2,FHGame.V_HEIGHT/2);
        this.playScreen = playScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.RIGHT){

            playScreen.getFlipperRightBottom().joint.setMotorSpeed(-20);
            AssetLoader.bump.play();

            return true;
        }
        if( keycode== Input.Keys.LEFT) {

            playScreen.getFlipperLeftBottom().joint.setMotorSpeed(20);
            AssetLoader.bump.play();

            return true;
        }
        if( keycode== Input.Keys.D) {

            playScreen.getFlipperRightTop().joint.setMotorSpeed(20);
            AssetLoader.bump.play();

            return true;
        }
        if( keycode== Input.Keys.A) {


            playScreen.getFlipperLeftTop().joint.setMotorSpeed(-20);
            AssetLoader.bump.play();

            return true;
           }

        if(keycode== Input.Keys.SPACE){
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode== Input.Keys.RIGHT){

            playScreen.getFlipperRightBottom().joint.setMotorSpeed(30);

            return true;
        }
        if( keycode== Input.Keys.LEFT) {
            playScreen.getFlipperLeftBottom().joint.setMotorSpeed(-30);
            return true;
        }
        if( keycode== Input.Keys.D) {
            playScreen.getFlipperRightTop().joint.setMotorSpeed(-30);
            return true;
        }
        if( keycode== Input.Keys.A) {
            playScreen.getFlipperLeftTop().joint.setMotorSpeed(30);
            return true;
        }
        if( keycode== Input.Keys.SPACE) {
            playScreen.getPuck().resetPuck();
            playScreen.toggle=true;
            playScreen.counter= 0;
            playScreen.getRandomAngle();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        AssetLoader.bump.play();


        Vector2 newPoints = new Vector2(screenX,screenY);
        newPoints = playScreen.playScreenUI.stage.getViewport().unproject(newPoints);

        if(topRight.isTouchDown(newPoints.x,newPoints.y)){

            playScreen.getFlipperRightTop().joint.setMotorSpeed(20);
            pointerRightTop = pointer;
            return true;

        }
        if(topLeft.isTouchDown(newPoints.x,newPoints.y)){
            playScreen.getFlipperLeftTop().joint.setMotorSpeed(-20);
            pointerLeftTop = pointer;

            return true;

        }
        if(bottomRight.isTouchDown(newPoints.x,newPoints.y)){
            playScreen.getFlipperRightBottom().joint.setMotorSpeed(-20);
            pointerRightBottom = pointer;

            return true;

        }
        if(bottomLeft.isTouchDown(newPoints.x,newPoints.y)){
            playScreen.getFlipperLeftBottom().joint.setMotorSpeed(20);
            pointerLeftBottom = pointer;

            return true;

        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        Vector2 newPoints = new Vector2(screenX,screenY);
//        newPoints = playScreen.playScreenUI.stage.getViewport().unproject(newPoints);


        if(pointer==pointerRightBottom){
            playScreen.getFlipperRightBottom().joint.setMotorSpeed(30);
        }
        if( pointer==pointerLeftBottom) {
            playScreen.getFlipperLeftBottom().joint.setMotorSpeed(-30);
        }
        if( pointer==pointerRightTop) {
            playScreen.getFlipperRightTop().joint.setMotorSpeed(-30);
        }
        if( pointer==pointerLeftTop) {
            playScreen.getFlipperLeftTop().joint.setMotorSpeed(30);
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }



}
