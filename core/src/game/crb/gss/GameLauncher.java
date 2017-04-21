package game.crb.gss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import game.crb.gf.GameFlow;
import game.crb.llr.GameScreen;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class GameLauncher extends Game {
    private InputProcessor inputProcessor;
    private World physics;
    private GameFlow gameFlow;

    public GameLauncher(){
        this.inputProcessor = new CutribaInputProcessor();
        this.physics = new World(new Vector2(0,-98f),true);

    }

    @Override
    public void create() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false);
        Gdx.input.setInputProcessor(inputProcessor);
        gameFlow = new GameFlow();


        // instead of 'select level'
        MapRenderer renderer = gameFlow.loadLevel("maps/demo_map.tmx");
        Actor actor = gameFlow.loadPlayer("Circle.png");
        
        this.setScreen(new GameScreen(camera, renderer, actor));
    }
}
