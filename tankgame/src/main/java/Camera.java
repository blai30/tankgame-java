import gameobjects.GameObject;

import java.awt.image.BufferedImage;

/**
 * main.java.Camera objects provide a screen for each player which follows the assigned tank.
 */
public class Camera {

    private static final int WIDTH = (GameWindow.SCREEN_WIDTH / 2) - 2;
    private static final int HEIGHT = (GameWindow.SCREEN_HEIGHT * 2 / 3) - 2;

    private GameObject trackObject;
    private BufferedImage view;

    /**
     * Assigns a game object to this camera that it will follow.
     * @param obj The game object that the camera will follow
     */
    public Camera(GameObject obj) {
        this.trackObject = obj;
    }

    /**
     * Continuously crops the game world to the dimensions of the camera.
     * @param world The game world passed in by main.java.GamePanel that this camera will crop
     */
    public void redraw(BufferedImage world) {
        float x = this.trackObject.getTransform().getPositionX() + this.trackObject.getOriginOffset().getX() - ((float) WIDTH / 2);
        float y = this.trackObject.getTransform().getPositionY() + this.trackObject.getOriginOffset().getY() - ((float) HEIGHT / 2);

        // Stop scrolling when left or right edge of the map is reached
        if (x <= 0) {
            x = 0;
        } else if (x >= world.getWidth() - WIDTH) {
            x = world.getWidth() - WIDTH;
        }

        // Stop scrolling when top or bottom edge of the map is reached
        if (y <= 0) {
            y = 0;
        } else if (y >= world.getHeight() - HEIGHT) {
            y = world.getHeight() - HEIGHT;
        }

        this.view = world.getSubimage((int) x, (int) y, WIDTH, HEIGHT);
    }

    /**
     * Called by main.java.GamePanel to draw the camera to the screen.
     * @return This view of this camera object
     */
    public BufferedImage getScreen() {
        return this.view;
    }

}
