package com.crustsoft.flipperhockey.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.crustsoft.flipperhockey.game.FHGame;
import com.crustsoft.flipperhockey.gameobjects.ScoreLineSensor;
import com.crustsoft.flipperhockey.screens.PlayScreen;

/**
 * Created by Morten on 02.04.2016.
 */
public class B2DContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef){
            case FHGame.BIT_PUCK|FHGame.BIT_GOAL_BOT:
                if(fixA.getFilterData().categoryBits == FHGame.BIT_GOAL_BOT){
                    ((ScoreLineSensor) fixA.getUserData()).getPlayScreen().addScorePlayerTop();
                }
                else{
                    ((ScoreLineSensor) fixB.getUserData()).getPlayScreen().addScorePlayerTop();

                }
                break;
            case FHGame.BIT_PUCK|FHGame.BIT_GOAL_TOP:
                if(fixA.getFilterData().categoryBits == FHGame.BIT_GOAL_TOP){
                    ((ScoreLineSensor) fixA.getUserData()).getPlayScreen().addScorePlayerBot();
                }
                else{
                    ((ScoreLineSensor) fixB.getUserData()).getPlayScreen().addScorePlayerBot();

                }
                break;
            case FHGame.BIT_PUCK|FHGame.BIT_CONTAINER:

                break;


            case FHGame.BIT_PUCK|FHGame.BIT_FLIPPER:



                break;
        }

    }

    @Override
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
