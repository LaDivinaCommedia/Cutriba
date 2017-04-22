package game.crb.llr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import game.crb.Utility.Actions;
import game.crb.Utility.BodyBuilder;
import game.crb.cp.CutribaContactListener;
import game.crb.gss.CutribaInputProcessor;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * Created by Iggytoto on 19.04.2017.
 */
public class GameScreen implements Screen, Observer {
    private OrthographicCamera camera;
    private MapRenderer renderer;
    private Actor player;
    private SpriteBatch batch;
    private World physics;
    private Array<Body> mapBodies;
    private Body playerBody;

    public GameScreen(OrthographicCamera camera, MapRenderer renderer, final Actor player, Map map) {
        this.camera = camera;
        this.renderer = renderer;
        this.player = player;
        this.batch = new SpriteBatch();
        this.physics = new World(new Vector2(0,-89),false);
        physics.setContactListener(new CutribaContactListener());

        renderer.setView(camera);

        mapBodies = BodyBuilder.buildMapShapes(map,32,this.physics);
        playerBody  = BodyBuilder.buildPlayerShape(this.physics);
        CutribaInputProcessor cutribaInputProcessor = new CutribaInputProcessor();
        cutribaInputProcessor.addObserver(this);
        Gdx.input.setInputProcessor(cutribaInputProcessor);
        Box2D.init();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        physics.step(1f/60f,6,2);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //updating the player sprite position on the player body
        player.setPosition(playerBody.getPosition().x,playerBody.getPosition().y);
        player.setRotation(playerBody.getAngle());

        camera.position.x = playerBody.getPosition().x;
        camera.position.y = playerBody.getPosition().y;

        camera.update();

        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        player.draw(batch,1);
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

    @Override
    public void update(Observable o, Object arg) {
        if(arg == Actions.UP){
            playerBody.applyLinearImpulse(0,200,playerBody.getPosition().x,playerBody.getPosition().y,true);
        }
        if(arg == Actions.LEFT){
            playerBody.applyForceToCenter(-200,0,true);
        }
        if(arg == Actions.RIGHT){
            playerBody.applyForceToCenter(200,0,true);
        }
        physics.step(1/60,6,2);
    }
}
