package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Puck extends Ball{
    private int ballSpeed;
    private Vector2 windowDimensions;
    private GameObjectCollection gameObjects;

    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound, int ballSpeed,
                Vector2 windowDimensions, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.ballSpeed = ballSpeed;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;

        setVelocity(randomVelocity());
    }

    private Vector2 randomVelocity(){
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float velocityX = (float)(Math.cos(angle) * ballSpeed);
        float velocityY = (float)(Math.sin(angle) * ballSpeed);
        return new Vector2(velocityX, velocityY);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float y = getCenter().y();

        if(y>windowDimensions.y()){
            gameObjects.removeGameObject(this);
        }
    }
}
