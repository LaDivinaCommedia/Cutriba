package game.crb.rm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Iggytoto on 21.04.2017.
 */
public class BodyBuilder {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float ppt = 32f;

    public static Body buildPlayerShape(float x,float y,World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(x,y);

        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(16f);
        shape.setPosition(new Vector2(16,16));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.0f;


        body.createFixture(fixtureDef);
        shape.dispose();

        body.getFixtureList().get(0).setUserData("player");
        body.setAngularDamping(10000f);

        return body;
    }

    public static Array<Body> buildMapShapes(Map map, float pixels, World world) {
        ppt = pixels;
        MapObjects objects = map.getLayers().get("obstacles").getObjects();

        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Body body;
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rect = (RectangleMapObject)object;
                body = getRectangle(rect, world);
            }
            else {
                continue;
            }

            bodies.add(body);

            }
        return bodies;
    }

    private static Body getRectangle(RectangleMapObject rectangleObject,World world) {
        Rectangle rectangle = rectangleObject.getRectangle();
        Body result;

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(rectangle.x + rectangle.width/2,rectangle.y + rectangle.height/2);

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;


        PolygonShape shape = new PolygonShape();

        shape.setAsBox(rectangle.width/2,rectangle.height/2);
        fd.shape = shape;
        result = world.createBody(bd);
        result.createFixture(fd);
        shape.dispose();

        return result;

    }

    public static float[] searchForStart(Map map) {
        MapObjects objects = map.getLayers().get("gameObjects").getObjects();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            if(object.getName().equals("Start")){

                Rectangle r = ((RectangleMapObject)object).getRectangle();
                return new float[]{r.x + r.width/2,r.y + r.height/2};
            }
        }
        return new float[2];
    }

    public static void buildSpikesObjects(Map map, World physics) {
        MapObjects objects = map.getLayers().get("gameObjects").getObjects();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            if(object.getName().equals("spikes")){

                RectangleMapObject rect = (RectangleMapObject)object;
                Body body = getRectangle(rect, physics);

                body.getFixtureList().get(0).setUserData("spike");
            }
        }
    }

    public static void buildFinish(Map map,World physics){
        MapObjects objects = map.getLayers().get("gameObjects").getObjects();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            if(object.getName().equals("Finish")){

                RectangleMapObject rect = (RectangleMapObject)object;
                Body body = getRectangle(rect, physics);

                body.getFixtureList().get(0).setUserData("finish");

            }
        }
    }
}
