package game.crb.rm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
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

    public static String MAP = "MAP";
    public static String PLAYER = "PLAYER";

    protected List<Disposable> disposableList;

    public ResourceManager() {
        disposableList = new ArrayList<Disposable>();
    }

    public Map<String, String> loadListOfLevels() throws IOException {
        Map<String, String> levels = new HashMap<String, String>();
        XmlReader xml = new XmlReader();
        XmlReader.Element root = xml.parse(new FileReader(FILE_LEVELS));

        int childCount = root.getChildCount();
        XmlReader.Element child;
        String name, location;
        for (int i = 0; i < childCount; ++i) {
            child = root.getChild(i);

            name = child.getChildByName("name").getText();
            location = child.getChildByName("location").getText();
            levels.put(name, location);
        }
        return levels;
    }

    public Map<String, String> loadLevelInfo(String location) throws IOException {
        Map<String, String> levelInfo = new HashMap<String, String>();

        XmlReader xml = new XmlReader();
        XmlReader.Element root = xml.parse(new FileReader(location));

        levelInfo.put(MAP, root.getChildByName(MAP.toLowerCase()).getText());
        levelInfo.put(PLAYER, root.getChildByName(PLAYER.toLowerCase()).getText());
        return levelInfo;
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
