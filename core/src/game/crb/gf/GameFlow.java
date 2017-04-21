package game.crb.gf;

import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.crb.rm.ResourceManager;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class GameFlow {
    private ResourceManager resourceManager;
    private TiledMap map;
    private MapRenderer render;

    public GameFlow() {
        resourceManager = new ResourceManager();
    }

    public MapRenderer initLevel(String levelName) {
        TiledMap map = resourceManager.loadMap(levelName);
        this.render = new OrthogonalTiledMapRenderer(map);
        this.map = map;
        return this.render;
    }
}
