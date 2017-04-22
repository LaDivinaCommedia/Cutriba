package game.crb.gss;

import com.badlogic.gdx.InputProcessor;
import game.crb.Utility.Actions;

import java.util.Observable;

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
        setChanged();
        switch (keycode){
            case(21):
            case(29):
                this.notifyObservers(Actions.LEFT);
                break;
            case(20):
            case(47):
                this.notifyObservers(Actions.DOWN);
                break;
            case(22):
            case(32):
                this.notifyObservers(Actions.RIGHT);
                break;
            case(19):
            case(51):
                this.notifyObservers(Actions.UP);
                break;
            case(62):
                this.notifyObservers(Actions.FLIP);
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
//        checkAndNotify(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        //checkAndNotify(character);
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
