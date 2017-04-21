package game.crb.rm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    public Texture loadSprite(String name) {
        Texture texture = new Texture(Gdx.files.internal(name));
        disposableList.add(texture);
        return texture;
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
