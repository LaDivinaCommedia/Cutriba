package game.crb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class GameLauncher extends Game {

    private Screen screen ;
    private InputProcessor inputProcessor;
    private World physics;

    public GameLauncher(){
        this.screen = new GameScreen();
        this.inputProcessor = new CutribaInputProcessor();
        this.physics = new World(new Vector2(0,-98f),true);

        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void create() {
        setScreen(screen);
    }
}
