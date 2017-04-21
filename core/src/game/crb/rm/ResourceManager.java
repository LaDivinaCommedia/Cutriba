package game.crb.rm;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class ResourceManager implements Disposable {
    protected List<Disposable> disposableList;

    public ResourceManager() {
        disposableList = new ArrayList<Disposable>();
    }

    public TiledMap loadMap(String name) {
        TiledMap map = new TmxMapLoader().load(name);
        disposableList.add(map);
        return map;
    }

    @Override
    public void dispose() {
        for (Disposable disp: disposableList) {
            if (disp != null) {
                disp.dispose();
            }
        }
        disposableList.clear();
    }
}
