package pepse.world.Energy;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.avatar.Avatar;

import java.awt.*;

/**
 * Displays the avatar's current energy level on screen.
 */
public class EnergyDisplay extends GameObject {

    private static final Vector2 POSITION = new Vector2(20, 20);
    private static final Vector2 DIMENSIONS = new Vector2(200, 50);

    private final Avatar avatar;
    private final TextRenderable textRenderable;

    public EnergyDisplay(Avatar avatar) {
        super(POSITION, DIMENSIONS, new TextRenderable("100%"));

        this.avatar = avatar;
        this.textRenderable = (TextRenderable) renderer().getRenderable();

        textRenderable.setColor(Color.BLACK);

        setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        setTag("energy_display");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        int energyValue = avatar.getEnergy().getEnergy();
        textRenderable.setString(energyValue + "%");
    }
}
