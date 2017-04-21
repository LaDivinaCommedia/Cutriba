package game.crb.gss;

import com.badlogic.gdx.InputProcessor;
import game.crb.Utility.Actions;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class CutribaInputProcessor extends Observable implements InputProcessor {
    //A=29,S=47,D=32,W=51,UP=19,Down=20,Left=21,Right=22,Space=62
    private Actions event = null;

    /**
     *  Returns value of changes event. Value is requested by observers in method update().
     * @return type of event
     */
    public Actions getEvent(){
        return event;
    }


    private void checkAndNotify(int keycode){
        switch (keycode){
            case(21):
            case(29):
                event = Actions.LEFT;
                this.notifyObservers();
                break;
            case(20):
            case(47):
                event = Actions.DOWN;
                this.notifyObservers();
                break;
            case(22):
            case(32):
                event = Actions.RIGHT;
                this.notifyObservers();
                break;
            case(19):
            case(51):
                event = Actions.UP;
                this.notifyObservers();
                break;
            case(62):
                event = Actions.JUMP;
                this.notifyObservers();
                break;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        checkAndNotify(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        checkAndNotify(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        checkAndNotify(character);
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
