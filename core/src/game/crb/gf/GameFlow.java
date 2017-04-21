package game.crb.gf;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import game.crb.llr.GameScreen;
import game.crb.rm.ResourceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class GameFlow {
    private ResourceManager resourceManager;
    private TiledMap map;
    private MapRenderer render;
    private Map<String, String> listOfLevels = null;

    public GameFlow() {
        resourceManager = new ResourceManager();
    }

    public MapRenderer loadMap(String levelName) {
        TiledMap map = resourceManager.loadMap(levelName);
        this.render = new OrthogonalTiledMapRenderer(map);
        this.map = map;
        return this.render;
    }

    public Actor loadPlayer(String playerName) {
        Texture texture = resourceManager.loadSprite(playerName);
        Player actor = new Player(texture);
        return actor;
    }

    public List<String> loadListOfLevels() {
        try {
            listOfLevels = resourceManager.loadListOfLevels();
            return new ArrayList<String>(listOfLevels.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    public GameScreen loadLevel(String levelName) {
        String levelLocation = listOfLevels.get(levelName);
        Map<String, String> levelInfo;
        try {
            levelInfo = resourceManager.loadLevelInfo(levelLocation);
            OrthographicCamera camera = new OrthographicCamera();
            camera.setToOrtho(false);
            MapRenderer renderer = this.loadMap(levelInfo.get(ResourceManager.MAP));
            Actor actor = this.loadPlayer(levelInfo.get(ResourceManager.PLAYER));
            GameScreen screen = new GameScreen(camera, renderer, actor);
            return screen;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
