package com.crustsoft.flipperhockey.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by morte on 7/6/2016.
 */
public class TouchRegions {

    private float x, y, width, height;


    public Rectangle bounds;


     boolean isPressed = false;

    public TouchRegions(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;


        bounds = new Rectangle(x, y, width, height);


    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }



    public boolean isTouchDown(float screenX, float
            screenY) {

        if (bounds.contains(screenX, screenY)) {
            isPressed = true;

            return true;
        }

        return false;
    }

    public boolean isTouchUp(float screenX, float screenY) {

        // It only counts as a touchUp if the button is in a pressed state.
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }

        // Whenever a finger is released, we will cancel any presses.
        //isPressed = false;
        return false;
    }
}
