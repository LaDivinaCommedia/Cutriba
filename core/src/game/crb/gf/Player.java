package game.crb.gf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import game.crb.rm.BodyBuilder;

/**
 * author:  Vladyslav Vasyliev
 * date:    21.04.2017
 * version: 1.0
 */
public class Player extends Actor {
    private Texture texture;
    private Body body;

    public Player(Texture texture){
        this.texture = texture;
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
    }

    public void initBody(float x,float y,World world) {
        body = BodyBuilder.buildPlayerShape(x,y,world);
    }

    public void update() {
        if (body == null) {
            return;
        }
        //updating the player sprite position on the player body
        this.setPosition(body.getWorldCenter().x - 16, body.getWorldCenter().y - 16);
        this.setRotation(body.getAngle());
    }

    public void move(float dx, float dy) {
        move(dx, dy, 100.0f);
    }

    public void move(float dx, float dy, float velocity) {
        if (body == null) {
            return;
        }

        Vector2 linearVelocity = body.getLinearVelocity();
        if (Math.abs(linearVelocity.y) > 0.1) {
            return;
        }

        float mass = body.getMass();
        Vector2 targetPosition = new Vector2(body.getPosition());
        targetPosition.add(dx, dy);
        // Now calculate the impulse magnitude and use it to scale
        // a direction (because its 2D movement)
        float impulseMag = mass * velocity;
        // Point the cannon towards the touch point
        Vector2 impulse = new Vector2();
        // Point the impulse from the cannon ball to the target
        impulse.set(targetPosition).sub(body.getPosition());
        // Normalize the direction (to get a constant speed)
        impulse.nor();
        // Scale by the calculated magnitude
        impulse.scl(impulseMag);
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }

   @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY());
    }

}
