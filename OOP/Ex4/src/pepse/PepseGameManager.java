package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Energy.EnergyDisplay;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.avatar.Avatar;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;

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

        Flora flora = new Flora(terrain, SEED);
        List<GameObject> trees = flora.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (GameObject tree : trees) {
            gameObjects().addGameObject(tree, Layer.STATIC_OBJECTS);
        }

        float avatarX = windowController.getWindowDimensions().x() / 2f;
        float groundY = terrain.groundHeightAt(avatarX);
        float avatarY = groundY - Avatar.AVATAR_DIMENSIONS.y();
        Vector2 avatarTopLeft = new Vector2(avatarX, avatarY);

        Avatar avatar = new Avatar(
                avatarTopLeft,
                inputListener,
                imageReader);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);

        EnergyDisplay energyDisplay = new EnergyDisplay(avatar);
        gameObjects().addGameObject(energyDisplay, Layer.UI);


    }
}
