package pepse.world.avatar.Energy;

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

    private final TextRenderable textRenderable;

    public EnergyDisplay(Avatar avatar) {
        super(POSITION, DIMENSIONS, new TextRenderable("100%"));
        this.textRenderable = (TextRenderable) renderer().getRenderable();

        textRenderable.setColor(Color.BLACK);

        setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        setTag("energy_display");

        avatar.getEnergy().setEnergyChangeListener(
                energy -> textRenderable.setString(energy + "%")
        );
    }

}
