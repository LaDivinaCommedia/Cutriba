package game.crb.llr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.*;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class MenuScreen extends BaseScreen implements Screen {
    private List<TextButton> buttons;
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Set<MenuListener> menuListeners;

    public MenuScreen() {
        stage = new Stage();
        batch = new SpriteBatch();
        menuListeners = new HashSet<>();
        skin = getDefaultSkin();
        buttons = new ArrayList<>();
        Gdx.input.setInputProcessor(stage);
    }

    public MenuScreen(Collection<String> items) {
        this();
        initialize(items);
    }

    public void initialize(Collection<String> items) {
        TextButton button;
        buttons.clear();
        stage.clear();
        for (String item : items) {
            button = new TextButton(item, skin);
            button.addListener(new ChangeListener() {
                public void changed(ChangeEvent event, Actor actor) {
                    onMenuItemSelected(((TextButton) event.getTarget()).getText());
                }
            });
            buttons.add(button);
            stage.addActor(button);
        }
        resize((int) stage.getWidth(), (int) stage.getHeight());
    }

    public void addMenuListener(MenuListener listener) {
        menuListeners.add(listener);
    }

    public void removeMenuListener(MenuListener listener) {
        if (menuListeners.contains(listener)) {
            menuListeners.remove(listener);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        int count = buttons.size();
        if (count > 0) {
            int x;
            int y = 20;
            int buttonWidth = width > 250 ? 250 : width;
            int buttonHeight = (height - 2 * y - count * 10) / count;
            buttonHeight = buttonHeight > 30 ? 30 : buttonHeight;
            x = (width - buttonWidth) / 2;
            for (TextButton button : buttons) {
                button.setWidth(buttonWidth);
                button.setHeight(buttonHeight);
                button.setPosition(x, y);
                y += buttonHeight + 10;
            }
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    private void onMenuItemSelected(CharSequence item) {
        if (menuListeners != null) {
            String str = item.toString();
            for (MenuListener listener : menuListeners) {
                listener.onItemSelected(str);
            }
        }
    }

    private static Skin getDefaultSkin() {
        Skin skin = new Skin();
        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        // Store the default libgdx font under the name "default".
        skin.add("default", new BitmapFont());
        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }

    public interface MenuListener {
        void onItemSelected(String item);
    }
}
