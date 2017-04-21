package game.crb.gss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import game.crb.gf.GameFlow;
import game.crb.llr.MenuScreen;
import game.crb.rm.ResourceManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class MenuController implements MenuScreen.MenuListener {
    private MenuScreen menuScreen;
    private LevelLister levelLister;
    private Map<String, String> items;
    private GameFlow gameFlow;
    private boolean isLevelName;

    MenuController(MenuScreen menuScreen, LevelLister levelLister) {
        this.menuScreen = menuScreen;
        this.levelLister = levelLister;
        gameFlow = new GameFlow();
        menuScreen.addMenuListener(this);
        ResourceManager resourceManager = ResourceManager.getInstance();
        items = resourceManager.loadMenu();
        menuScreen.initialize(items.keySet());
        isLevelName = false;
    }

    public void exitGame() {
        Gdx.app.exit();
    }

    public void loadLevels() {
        Collection<String> levels = gameFlow.loadListOfLevels();
        menuScreen.initialize(levels);
        isLevelName = true;
    }

    @Override
    public void onItemSelected(String item) {
        if (isLevelName) {
            Screen screen = gameFlow.loadLevel(item);
            levelLister.onSelectedLevel(screen);
        }
        else {
            try {
                Method method = this.getClass().getMethod(items.get(item));
                method.invoke(this);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public interface LevelLister {
        void onSelectedLevel(Screen screen);
    }
}
