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
import java.util.Collection;
import java.util.Map;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class GameFlow {
    private ResourceManager resourceManager;
    private MapRenderer renderer;
    private Map<String, String> listOfLevels = null;

    public GameFlow() {
        resourceManager = ResourceManager.getInstance();
    }

    private TiledMap loadMap(String levelName) {
        return resourceManager.loadMap(levelName);
    }

    private Actor loadPlayer(String playerName) {
        Texture texture = resourceManager.loadSprite(playerName);
        return new Player(texture);
    }

    public Collection<String> loadListOfLevels() {
        try {
            listOfLevels = resourceManager.loadListOfLevels();
            return new ArrayList<>(listOfLevels.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public GameScreen loadLevel(String levelName) {
        String levelLocation = listOfLevels.get(levelName);
        Map<String, String> levelInfo;
        try {
            levelInfo = resourceManager.loadLevelInfo(levelLocation);
            OrthographicCamera camera = new OrthographicCamera();
            camera.setToOrtho(false);
            TiledMap map = this.loadMap(levelInfo.get(ResourceManager.MAP));
            this.renderer = new OrthogonalTiledMapRenderer(map);
            Actor actor = this.loadPlayer(levelInfo.get(ResourceManager.PLAYER));
            return new GameScreen(camera, renderer, actor, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
