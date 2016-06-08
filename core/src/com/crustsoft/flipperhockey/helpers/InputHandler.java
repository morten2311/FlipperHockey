package com.crustsoft.flipperhockey.helpers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.gameobjects.FlipperRight;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 30.03.2016.
 */
public class InputHandler implements InputProcessor {

    PlayScreen playScreen ;
    public InputHandler(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }


    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.RIGHT){

            playScreen.getFlipperRightBottom().joint.setMotorSpeed(-20);
            return true;
        }
        if( keycode== Input.Keys.LEFT) {

            playScreen.getFlipperLeftBottom().joint.setMotorSpeed(20);
            return true;
        }
        if( keycode== Input.Keys.D) {

            playScreen.getFlipperRightTop().joint.setMotorSpeed(20);
            return true;
        }
        if( keycode== Input.Keys.A) {

            playScreen.getFlipperLeftTop().joint.setMotorSpeed(-20);
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
