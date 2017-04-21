package game.crb.gf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import game.crb.Utility.Actions;
import game.crb.gss.CutribaInputProcessor;

import java.util.Observable;
import java.util.Observer;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class Player extends Actor implements Observer{
    private Texture texture;

    public Player(Texture texture){
        this.texture = texture;
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
    }



    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY());
    }

    @Override
    public void update(Observable o, Object arg) {
        Actions value = ((CutribaInputProcessor)o).getEvent();
        System.out.println(value);
    }
}
