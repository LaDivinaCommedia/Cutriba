package game.crb.gf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class Player extends Actor {
    private Texture texture;

    public Player(Texture texture){
        this.texture = texture;
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY());
    }
}
