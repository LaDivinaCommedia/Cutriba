package game.crb.llr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import game.crb.gf.Actions;
import game.crb.rm.BodyBuilder;
import game.crb.gf.GameEvent;
import game.crb.cp.CutribaContactListener;
import game.crb.gf.Player;
import game.crb.gss.CutribaInputProcessor;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by Iggytoto on 19.04.2017.
 */
public class GameScreen extends BaseScreen implements Observer {
    private Label label;
    private OrthographicCamera camera;
    private MapRenderer renderer;
    private Player player;
    private SpriteBatch batch;
    private World physics;
    private Color clearColor;
    private Music backgroundMusic;
    private boolean rotated;

    public GameScreen(OrthographicCamera camera, MapRenderer renderer, Player player, Map map, Music music) {
        this.camera = camera;
        this.backgroundMusic = music;
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.play();
        this.renderer = renderer;
        this.player = player;
        this.batch = new SpriteBatch();
        this.physics = new World(new Vector2(0, -89), false);
        CutribaContactListener cl = new CutribaContactListener();
        cl.addObserver(this);
        physics.setContactListener(cl);


        BitmapFont font;
        FileHandle fontFile = Gdx.files.internal("fonts/SansPosterBold.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        font = generator.generateFont(parameter);
        font.setColor(0, 0, 0, 1);
        label = new Label(" ", new Label.LabelStyle(font, Color.BLACK));

        clearColor = new Color(1, 1, 1, 1);
        renderer.setView(camera);

        float[] start = BodyBuilder.searchForStart(map);
        player.initBody(start[0], start[1], physics);
        BodyBuilder.buildFinish(map, this.physics);
        BodyBuilder.buildMapShapes(map, 32, this.physics);
        BodyBuilder.buildSpikesObjects(map, this.physics);
        CutribaInputProcessor cutribaInputProcessor = new CutribaInputProcessor();
        cutribaInputProcessor.addObserver(this);
        Gdx.input.setInputProcessor(cutribaInputProcessor);
        Box2D.init();
        createRandomRotationTimer();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        physics.step(1f / 60f, 6, 2);

        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.update();
        camera.position.x = player.getX();
        camera.position.y = player.getY();

        camera.update();

        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        StringBuilder sb = new StringBuilder();
        sb.append("FPS: ").append(Gdx.graphics.getFramesPerSecond());
        label.setText(sb);
        if (rotated) {
            label.setPosition(player.getX() + Gdx.graphics.getWidth() / 2 - 55,
                    player.getY() + Gdx.graphics.getHeight() / 2 - 12);
        } else {
            label.setPosition(player.getX() - Gdx.graphics.getWidth() / 2,
                    player.getY() - Gdx.graphics.getHeight() / 2);
        }


        batch.begin();
        player.draw(batch, 1);
        label.draw(batch, 1);
        batch.end();
        Box2DDebugRenderer dr = new Box2DDebugRenderer();
        dr.render(physics, camera.combined);

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
        int direction = physics.getGravity().y < 0 ? 1 : -1;
        if (arg == Actions.UP) {
            player.move(0, direction);
        }
        if (arg == Actions.LEFT) {
            player.move(-direction, 0);
        }
        if (arg == Actions.RIGHT) {
            player.move(direction, 0);
        }
        if (arg == Actions.FLIP) {
            rotateWorld();
        }

        if (arg == Actions.START_LEFT) {
            player.force(-direction);
        }

        if (arg == Actions.START_RIGHT) {
            player.force(direction);
        }

        if (arg == Actions.STOP_LEFT || arg == Actions.STOP_RIGHT) {
            player.force(0);
        }

        physics.step(1 / 60, 6, 2);

        if (GameEvent.GAME_OVER == arg) {
            backgroundMusic.stop();
            setChanged();
            notifyObservers(GameEvent.GAME_OVER);
        }
        if (GameEvent.LEVEL_FINISHED == arg) {
            setChanged();
            notifyObservers(GameEvent.LEVEL_FINISHED);
        }
    }

    private void createRandomRotationTimer() {
        final Random rand = new Random();
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                // random world rotation probability
                if (rand.nextFloat() < 0.2) {
                    // change the background color before the rotation will appear
                    clearColor.g = 0.96f;
                    clearColor.b = 0.96f;
                    new Timer().scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            // rotate the world and change background color to white
                            rotateWorld();
                            clearColor.g = 1.0f;
                            clearColor.b = 1.0f;
                        }
                    }, 1);
                }
            }
        }, 0, 5);
    }

    private void rotateWorld() {
        final int repeatCount = 30;
        final float intervalSeconds = 0.5f / repeatCount;
        final float angle = 180 / repeatCount;
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                camera.rotate(angle);
            }
        }, 0, intervalSeconds, repeatCount - 1);
        Vector2 gravity = physics.getGravity();
        gravity.y = -gravity.y;
        physics.setGravity(gravity);

        rotated = !rotated;
        label.setRotation(180);
    }

}
