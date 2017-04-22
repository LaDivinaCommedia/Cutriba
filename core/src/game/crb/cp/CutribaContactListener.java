package game.crb.cp;

import com.badlogic.gdx.physics.box2d.*;
import game.crb.gf.GameEvent;

import java.util.Observable;

/**
 * Created by Iggytoto on 21.04.2017.
 */
public class CutribaContactListener extends Observable implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() !=null && a.getUserData().equals("player")){
            if(b.getUserData() !=null && b.getUserData().equals("spike")){
                setChanged();
                notifyObservers(GameEvent.GAMEOVER);
            }
        }
        if(b.getUserData() !=null && b.getUserData().equals("player")){
            if(a.getUserData() !=null && a.getUserData().equals("spike")){
                setChanged();
                notifyObservers(GameEvent.GAMEOVER);
            }
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
