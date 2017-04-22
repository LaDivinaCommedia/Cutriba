package game.crb.gss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.compression.lzma.Base;
import game.crb.gf.GameEvent;
import game.crb.llr.BaseScreen;
import game.crb.llr.MenuScreen;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class GameLauncher extends Game implements MenuController.LevelLister, Observer {
    private InputProcessor inputProcessor;
    private MenuScreen menuScreen;
    private MenuController controller;

    public GameLauncher(){
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
            if(screen instanceof BaseScreen){
                ((BaseScreen) screen).addObserver(this);
            }
            this.setScreen(screen);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == GameEvent.GAMEOVER){
            menuScreen = new MenuScreen();
            controller = new MenuController(menuScreen,this);
            this.setScreen(menuScreen);
        }
    }
}
