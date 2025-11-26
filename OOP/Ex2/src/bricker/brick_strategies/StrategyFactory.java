package bricker.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class StrategyFactory {

    private Sound collisionSound;
    private Vector2 ballDimensions;
    private GameObjectCollection gameObjects;
    private Counter brickCounter;
    private Random rand;
    private Renderable puckImage;
    private Vector2 windowDimensions;

    public StrategyFactory(GameObjectCollection gameObjects, Counter brickCounter, ImageReader imageReader,
                           Sound collisionSound, Vector2 ballDimensions, Vector2 windowDimensions) {
        this.collisionSound = collisionSound;
        this.ballDimensions = ballDimensions;
        this.rand = new Random();
        this.gameObjects = gameObjects;
        this.brickCounter = brickCounter;
        this.windowDimensions = windowDimensions;
        this.puckImage =
                imageReader.readImage("assets/mockBall.png", true);
    }

    public CollisionStrategy generateStrategy(int depthOfDouble){
        float p = rand.nextFloat();

        if (p<0.5f){
            return new BasicCollisionStrategy(gameObjects, brickCounter);
        }

        if(p<0.6f){
            return new MoreBallsStrategy(
                    gameObjects, brickCounter, puckImage, collisionSound, ballDimensions, windowDimensions
            );
        }

        if(p<0.7f){
            return new ExtraPaddleStrategy();
        }

        if(p<0.8f){
            return new ExplosionStrategy();
        }

        if(p<0.9f){
            return new RestoreLifeStrategy();
        }

        if(depthOfDouble < 2){
            return new DoubleBehaviourStrategy(
                    generateSpecialStrategy(depthOfDouble + 1),
                    generateSpecialStrategy(depthOfDouble + 1)
            );
        }

        return generateSpecialStrategyNotDouble();
    }

    private CollisionStrategy generateSpecialStrategy(int depthOfDouble){
        float p = rand.nextFloat();

        if(p<0.2f){
            return new MoreBallsStrategy(
                    gameObjects, brickCounter, puckImage, collisionSound, ballDimensions, windowDimensions
            );
        }

        if(p<0.4f){
            return new ExtraPaddleStrategy();
        }

        if(p<0.6f){
            return new ExplosionStrategy();
        }

        if (p<0.8f){
            return new RestoreLifeStrategy();
        }

        if(depthOfDouble < 2){
            return new DoubleBehaviourStrategy(
                    generateSpecialStrategy(depthOfDouble + 1),
                    generateSpecialStrategy(depthOfDouble + 1)
            );
        }

        return generateSpecialStrategyNotDouble();
    }

    private CollisionStrategy generateSpecialStrategyNotDouble(){
        float p = rand.nextFloat();

        if(p<0.25f){
            return new MoreBallsStrategy(
                    gameObjects, brickCounter, puckImage, collisionSound, ballDimensions, windowDimensions
            );
        }

        if(p<0.5f){
            return new ExtraPaddleStrategy();
        }

        if(p<0.75f){
            return new ExplosionStrategy();
        }

        return new RestoreLifeStrategy();
    }
}
