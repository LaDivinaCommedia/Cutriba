package game.crb.llr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.function.Consumer;

/**
 * author:  Vladyslav Vasyliev
 * date:    22.04.2017
 * version: 1.0
 */
public class MessageScreen extends BaseScreen implements InputProcessor {
    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private int width;
    private int height;
    private Consumer<Object> consumer;
    private Color clearColor;

    public MessageScreen(String text, Color clearColor, Consumer<Object> supplier) {
        batch = new SpriteBatch();
        font = new BitmapFont();

        FileHandle fontFile = Gdx.files.internal("fonts/SansPosterBold.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        font = generator.generateFont(parameter);

        font.setColor(0, 0, 0, 1);
        font.getData().setScale(1);
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
        this.clearColor = clearColor;
        this.consumer = supplier;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        float x = width / 2 - glyphLayout.width / 2;
        float y = height / 2 + glyphLayout.height / 2;

        if (clearColor != null) {
            Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

        batch.begin();
        font.draw(batch, glyphLayout, x, y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
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
        if (!isDisposed) {
            super.dispose();
            batch.dispose();
            font.dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        consumer.accept(this);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
