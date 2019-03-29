

import GameObjects.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Displays various game information on the screen such as a minimap of the game world.
 */
public class GameHUD {

    private Player[] players;

    private int minimapWidth;
    private int minimapHeight;
    private BufferedImage minimap;

    /**
     * Constructs the game HUD with the minimap being a third of the window size.
     * @param world The game world drawn to the screen
     */
    public GameHUD(BufferedImage world) {
        this.players = new Player[2];

        this.minimapWidth = (int) (((GameWindow.SCREEN_HEIGHT / 3) - 40) * ((float) world.getWidth() / (float) world.getHeight()));
        this.minimapHeight = (GameWindow.SCREEN_HEIGHT / 3) - 40;
        this.minimap = new BufferedImage(minimapWidth, minimapHeight, BufferedImage.TYPE_INT_RGB);
    }

    public void assignPlayer(int player, Player playerObj) {
        this.players[player] = playerObj;
    }

    public int getMinimapWidth() {
        return this.minimapWidth;
    }

    public int getMinimapHeight() {
        return this.minimapHeight;
    }

    /**
     * Called by GamePanel to draw the minimap on the screen.
     * @return The view of the minimap of the game world
     */
    public BufferedImage getMinimap() {
        return minimap;
    }

    /**
     * Continuously redraw the view of the minimap to be based on the game world.
     * @param world The game world drawn to the screen
     */
    public void redraw(BufferedImage world) {
        Graphics g = this.minimap.createGraphics();
        g.drawImage(world, 0, 0, minimapWidth, minimapHeight, null);
        g.dispose();
    }

}
