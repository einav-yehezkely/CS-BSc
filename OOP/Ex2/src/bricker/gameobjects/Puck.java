package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Puck extends Ball{
    private int ballSpeed;

    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound, int ballSpeed) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.ballSpeed = ballSpeed;

        setVelocity(randomVelocity());
    }

    private Vector2 randomVelocity(){
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float velocityX = (float)(Math.cos(angle) * ballSpeed);
        float velocityY = (float)(Math.sin(angle) * ballSpeed);
        return new Vector2(velocityX, velocityY);
    }

}
