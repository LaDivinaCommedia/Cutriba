package game.crb.Utility;

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

    public static Body buildPlayerShape(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(500,500);

        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(32f);

        FixtureDef fixtureDef = new FixtureDef();   //IDK WTF is this
        fixtureDef.shape = shape;
        fixtureDef.density =1f;

        body.createFixture(fixtureDef);
        shape.dispose();

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
            /*else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
            }*/
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
        bd.position.set(rectangle.x,rectangle.y);

        FixtureDef fd = new FixtureDef();
        fd.filter.categoryBits = 0x0010;
        fd.filter.maskBits = 0x0001;

        EdgeShape shape = new EdgeShape();

        shape.set(-rectangle.width/2,-rectangle.height/2,rectangle.width/2,rectangle.height/2);
        fd.shape = shape;

        result = world.createBody(bd);
        result.createFixture(fd);
        shape.dispose();

        return result;

    }

    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / ppt;
            worldVertices[i].y = vertices[i * 2 + 1] / ppt;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}
