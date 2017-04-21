package game.crb.llr;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private MapRenderer renderer;

    public GameScreen(OrthographicCamera camera, MapRenderer renderer) {
        this.camera = camera;
        this.renderer = renderer;
        renderer.setView(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {

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
}
