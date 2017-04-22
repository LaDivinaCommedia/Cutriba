package game.crb.gss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import game.crb.gf.GameEvent;
import game.crb.llr.BaseScreen;
import game.crb.llr.MenuScreen;
import game.crb.llr.MessageScreen;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class GameLauncher extends Game implements MenuController.LevelLister, Observer {
    private InputProcessor inputProcessor;
    private MenuScreen menuScreen;
    private MenuController controller;

    public GameLauncher() {
        this.inputProcessor = new CutribaInputProcessor();
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(inputProcessor);
        menuScreen = new MenuScreen();
        controller = new MenuController(menuScreen, this);
        this.setScreen(menuScreen);
    }

    @Override
    public void onSelectedLevel(Screen screen) {
        if (screen != null) {
            if (screen instanceof BaseScreen) {
                ((BaseScreen) screen).addObserver(this);
            }
            this.setScreen(screen);
            if (menuScreen != null) {
                menuScreen.dispose();
                menuScreen = null;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == GameEvent.GAME_OVER) {
            showMessageScreen("GAME OVER!", new Color(1.0f, 0.6f, 0.6f, 1));
        }
        if (arg == GameEvent.LEVEL_FINISHED) {
            showMessageScreen("LEVEL SUCCESSFULLY FINISHED!", new Color(0.6f, 0.6f, 1.0f, 1));
        }
    }

    private void showMessageScreen(String message, Color color) {
        MessageScreen messageScreen = new MessageScreen(message, color, (a) -> {
            Gdx.input.setInputProcessor(inputProcessor);
            menuScreen = new MenuScreen();
            controller = new MenuController(menuScreen, this);
            this.setScreen(menuScreen);
        });
        Gdx.input.setInputProcessor(messageScreen);
        this.setScreen(messageScreen);
    }
}
