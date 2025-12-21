package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import java.util.List;

public class PepseGameManager extends GameManager {
    private static final int SEED = 12345;
    private static final float NIGHT_CYCLE_LENGTH = 30f;
    private static final float SUN_CYCLE_LENGTH = 30f;


    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);

        Terrain terrain = new Terrain(windowController.getWindowDimensions(), SEED);
        List<Block> blocks =
                terrain.createInRange(0, (int)windowController.getWindowDimensions().x());
        for(GameObject block : blocks){
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

        GameObject night = Night.create(windowController.getWindowDimensions(), NIGHT_CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.UI);

        GameObject sun = Sun.create(windowController.getWindowDimensions(), SUN_CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
    }
}
