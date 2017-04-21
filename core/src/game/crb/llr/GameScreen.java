package game.crb.llr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private MapRenderer renderer;
    private Actor actor;
    private SpriteBatch batch;

    public GameScreen(OrthographicCamera camera, MapRenderer renderer, Actor player) {
        this.camera = camera;
        this.renderer = renderer;
        this.actor = player;
        this.batch = new SpriteBatch();
        renderer.setView(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        camera.position.x = actor.getX();
        camera.position.y = actor.getY();
        renderer.render();
        batch.begin();
        actor.draw(batch,1);
        batch.end();
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
        batch.dispose();
    }
}
