

import GameObjects.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Displays various game information on the screen such as a minimap of the game world.
 */
public class GameHUD {

    private Player[] players;

    private int HUDHeight;

    private int minimapWidth;
    private BufferedImage minimap;

    private int infoWidth;
    private BufferedImage p1info;
    private BufferedImage p2info;

    /**
     * Constructs the game HUD with the minimap being a third of the window size.
     * @param world The game world drawn to the screen
     */
    public GameHUD(BufferedImage world) {
        this.players = new Player[2];

        this.HUDHeight = (GameWindow.SCREEN_HEIGHT / 3) - 40;

        this.minimapWidth = (int) (((GameWindow.SCREEN_HEIGHT / 3) - 40) * ((float) world.getWidth() / (float) world.getHeight()));
        this.minimap = new BufferedImage(this.minimapWidth, this.HUDHeight, BufferedImage.TYPE_INT_RGB);

        this.infoWidth = (GameWindow.SCREEN_WIDTH / 2) - (this.minimapWidth / 2);
        this.p1info = new BufferedImage(this.infoWidth, this.HUDHeight, BufferedImage.TYPE_INT_RGB);
        this.p2info = new BufferedImage(this.infoWidth, this.HUDHeight, BufferedImage.TYPE_INT_RGB);
    }

    public void assignPlayer(int player, Player playerObj) {
        this.players[player] = playerObj;
    }

    public int getHUDHeight() {
        return this.HUDHeight;
    }

    public int getMinimapWidth() {
        return this.minimapWidth;
    }

    public BufferedImage getP1info() {
        return this.p1info;
    }

    public BufferedImage getP2info() {
        return this.p2info;
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
     * Continuously redraw player information such as stats and health bar.
     * @param world The game world drawn to the screen
     */
    public void redraw(BufferedImage world) {
        Graphics p1graphics = this.p1info.createGraphics();
        Graphics p2graphics = this.p2info.createGraphics();
        Graphics map = this.minimap.createGraphics();

        p1graphics.clearRect(0, 0, p1info.getWidth(), p1info.getHeight());
        p2graphics.clearRect(0, 0, p2info.getWidth(), p2info.getHeight());
        map.clearRect(0, 0, minimap.getWidth(), minimap.getHeight());

        Font font = new Font("Courier New", Font.PLAIN,18);

        // Draw Player 1 information
        p1graphics.setColor(Color.RED);
        p1graphics.drawRect(4, 2, this.p1info.getWidth() - 8, this.p1info.getHeight() - 6);
        p1graphics.drawImage(this.players[0].getSprite(), 32, 32, null);
        // Draw health bar
        p1graphics.setColor(Color.WHITE);
        p1graphics.drawRect(150, 32, 240, 16);
        p1graphics.setColor((this.players[0].getHP() > 3) ? Color.GREEN : Color.RED);
        p1graphics.fillRect(150, 32, this.players[0].getHP() * 24, 16);
        // Draw stats
        p1graphics.setColor(Color.WHITE);
        p1graphics.setFont(font);
        int separator = 0;
        for (Map.Entry<String, Number> entry : this.players[0].getStats().entrySet()) {
            p1graphics.drawString(entry.getKey(), 150, 96 + separator);
            p1graphics.drawString(":", 320, 96 + separator);
            p1graphics.drawString(entry.getValue().toString(), 350, 96 + separator);
            separator += 24;
        }

        // Draw Player 2 information
        p2graphics.setColor(Color.BLUE);
        p2graphics.drawRect(4, 2, this.p2info.getWidth() - 26, this.p2info.getHeight() - 6);
        p2graphics.drawImage(this.players[1].getSprite(), 32, 32, null);
        // Draw health bar
        p2graphics.setColor(Color.WHITE);
        p2graphics.drawRect(150, 32, 240, 16);
        p2graphics.setColor((this.players[1].getHP() > 3) ? Color.GREEN : Color.RED);
        p2graphics.fillRect(150, 32, this.players[1].getHP() * 24, 16);
        // Draw stats
        p2graphics.setColor(Color.WHITE);
        p2graphics.setFont(font);
        separator = 0;
        for (Map.Entry<String, Number> entry : this.players[1].getStats().entrySet()) {
            p2graphics.drawString(entry.getKey(), 150, 96 + separator);
            p2graphics.drawString(":", 320, 96 + separator);
            p2graphics.drawString(entry.getValue().toString(), 350, 96 + separator);
            separator += 24;
        }

        map.drawImage(world, 0, 0, this.minimapWidth, this.HUDHeight, null);

        p1graphics.dispose();
        p2graphics.dispose();
        map.dispose();
    }

}
