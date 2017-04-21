package game.crb.gss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import game.crb.llr.MenuScreen;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class GameLauncher extends Game implements MenuController.LevelLister {
    private InputProcessor inputProcessor;
    private MenuScreen menuScreen;

    public GameLauncher(){
        this.inputProcessor = new CutribaInputProcessor();
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(inputProcessor);
        menuScreen = new MenuScreen();
        new MenuController(menuScreen, this);
        this.setScreen(menuScreen);
    }

    @Override
    public void onSelectedLevel(Screen screen) {
        if (screen != null) {
            this.setScreen(screen);
        }
    }
}
