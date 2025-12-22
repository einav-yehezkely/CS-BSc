package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.avatar.Energy.EnergyDisplay;
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

    private int worldBuiltFromX;
    private int worldBuiltUntilX;
    private Terrain terrain;
    private Flora flora;
    private Avatar avatar;
    private WindowController windowController;
    private int deleteDistance;


    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowController = windowController;
        this.deleteDistance = (int) windowController.getWindowDimensions().x() * 2;

        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);

        GameObject night = Night.create(windowController.getWindowDimensions(), NIGHT_CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.UI);

        GameObject sun = Sun.create(windowController.getWindowDimensions(), SUN_CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);

        this.terrain = new Terrain(windowController.getWindowDimensions(), SEED);
        this.flora = new Flora(terrain, SEED);

        worldBuiltUntilX = (int) windowController.getWindowDimensions().x(); // screen width

        buildWorld(0, worldBuiltUntilX);

        float avatarX = windowController.getWindowDimensions().x() / 2f;
        float groundY = terrain.groundHeightAt(avatarX);
        float avatarY = groundY - Avatar.AVATAR_DIMENSIONS.y();
        Vector2 avatarTopLeft = new Vector2(avatarX, avatarY);

        this.avatar = new Avatar(
                avatarTopLeft,
                inputListener,
                imageReader);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);

        EnergyDisplay energyDisplay = new EnergyDisplay(avatar);
        gameObjects().addGameObject(energyDisplay, Layer.UI);

        setCamera(new Camera(avatar, Vector2.ZERO,
                windowController.getWindowDimensions(),windowController.getWindowDimensions()));


    }

    private void buildWorld(int minX, int maxX) {
        List<Block> blocks =
                terrain.createInRange(minX, maxX);
        for(GameObject block : blocks){
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

        List<GameObject> trees = flora.createInRange(minX, maxX);
        for (GameObject tree : trees) {
            gameObjects().addGameObject(tree, Layer.STATIC_OBJECTS);
        }
    }

    private void removeFarObjects() {
        float avatarX = avatar.getCenter().x();

        for (GameObject obj : gameObjects()) {

            String tag = obj.getTag();
            if (tag == null) continue;

            if (tag.equals("avatar") ||
                    tag.equals("energy_display") ||
                    tag.equals("sun") ||
                    tag.equals("sunHalo") ||
                    tag.equals("sky") ||
                    tag.equals("night")) {
                continue;
            }

            float objX = obj.getCenter().x();

            if (Math.abs(objX - avatarX) > deleteDistance) {
                gameObjects().removeGameObject(obj);
            }
        }
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        int avatarX = (int) avatar.getCenter().x();
        int screenWidth = (int) windowController.getWindowDimensions().x();

        if (avatarX + screenWidth > worldBuiltUntilX) {
            int newMinX = worldBuiltUntilX;
            int newMaxX = worldBuiltUntilX + screenWidth;

            buildWorld(newMinX, newMaxX);
            worldBuiltUntilX = newMaxX;
        }

        if (avatarX - screenWidth < worldBuiltFromX) {
            int newMinX = worldBuiltFromX - screenWidth;
            int newMaxX = worldBuiltFromX;

            buildWorld(newMinX, newMaxX);
            worldBuiltFromX = newMinX;
        }

        removeFarObjects();
    }
}
