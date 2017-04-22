package game.crb.rm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.XmlReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class ResourceManager implements Disposable {
    private static String FILE_LEVELS = "levels.xml";
    private static ResourceManager resourceManager;

    public static String MAP = "map";
    public static String PLAYER = "player";
    public static String BACKGRROUND_SOUND = "background_sound";


    private List<Disposable> disposableList;

    private ResourceManager() {
        disposableList = new ArrayList<>();
    }

    public static ResourceManager getInstance() {
        if (resourceManager == null) {
            resourceManager = new ResourceManager();
        }
        return resourceManager;
    }

    public Map<String, String> loadListOfLevels() throws IOException {
        Map<String, String> levels = new HashMap<>();
        Json json = new Json();
        levels = json.fromJson(levels.getClass(), Gdx.files.internal("levels.json"));
        return levels;
    }

    public Map<String, String> loadLevelInfo(String location) throws IOException {
        Map<String, String> levelInfo = new HashMap<>();
        Json json = new Json();
        levelInfo = json.fromJson(levelInfo.getClass(), Gdx.files.internal(location));
        return levelInfo;
    }

    public Map<String, String> loadMenu() {
        Map<String, String> menu = new HashMap<>();
        Json json = new Json();
        menu = json.fromJson(menu.getClass(), Gdx.files.internal("menu.json"));
        return menu;
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

    public Music loadMusic(String name) {
        return Gdx.audio.newMusic(Gdx.files.internal(name));
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
