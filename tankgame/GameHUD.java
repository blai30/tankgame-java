

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
    private BufferedImage playerInfo[];

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

        this.playerInfo = new BufferedImage[2];
        this.playerInfo[0] = new BufferedImage(this.infoWidth, this.HUDHeight, BufferedImage.TYPE_INT_RGB);
        this.playerInfo[1] = new BufferedImage(this.infoWidth, this.HUDHeight, BufferedImage.TYPE_INT_RGB);
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
        return this.playerInfo[0];
    }

    public BufferedImage getP2info() {
        return this.playerInfo[1];
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
        Graphics[] playerGraphics = { this.playerInfo[0].createGraphics(), this.playerInfo[1].createGraphics() };
        Graphics map = this.minimap.createGraphics();

        playerGraphics[0].clearRect(0, 0, playerInfo[0].getWidth(), playerInfo[0].getHeight());
        playerGraphics[1].clearRect(0, 0, playerInfo[1].getWidth(), playerInfo[1].getHeight());
        map.clearRect(0, 0, minimap.getWidth(), minimap.getHeight());

        Font font = new Font("Courier New", Font.PLAIN,16);

        playerGraphics[0].setColor(Color.RED);      // Player 1 info box border color
        playerGraphics[1].setColor(Color.BLUE);     // Player 2 info box border color
        // Iterate loop for each player
        for (int i = 0; i < playerGraphics.length; i++) {
            // Draw player info box
            playerGraphics[i].drawRect(4, 2, this.playerInfo[i].getWidth() - 8, this.playerInfo[i].getHeight() - 6);
            playerGraphics[i].drawImage(this.players[i].getSprite(), 32, 32, null);
            playerGraphics[i].setFont(font);
            playerGraphics[i].setColor(Color.WHITE);
            playerGraphics[i].drawString("Weapon:", 32, 120);
            playerGraphics[i].drawString("[" + this.players[i].getWeapon() + "]", 32, 144);
            // Draw health bar
            playerGraphics[i].setColor((this.players[i].getHP() > 5) ? Color.GREEN : (this.players[i].getHP() > 2) ? Color.YELLOW : Color.RED);
            playerGraphics[i].fillRect(170, 32, this.players[i].getHP() * 24, 16);
            playerGraphics[i].setColor(Color.WHITE);
            playerGraphics[i].drawRect(170, 32, 240, 16);
            // Draw lives
            for (int j = 0; j < this.players[i].getLives(); j++) {
                playerGraphics[i].setColor(Color.GREEN);
                playerGraphics[i].fillOval(170 + (j * 16), 52, 12, 12);
                playerGraphics[i].setColor(Color.WHITE);
                playerGraphics[i].drawOval(170 + (j * 16), 52, 12, 12);
            }
            // Draw stats
            playerGraphics[i].setColor(Color.WHITE);
            int separator = 0;
            for (Map.Entry<String, Integer> entry : this.players[i].getStats().entrySet()) {
                playerGraphics[i].drawString(entry.getKey(), 170, 96 + separator);
                playerGraphics[i].drawString(":", 340, 96 + separator);
                playerGraphics[i].drawString(entry.getValue().toString(), 370, 96 + separator);
                separator += 24;
            }
        }

        // Draw minimap
        map.drawImage(world, 0, 0, this.minimapWidth, this.HUDHeight, null);

        playerGraphics[0].dispose();
        playerGraphics[1].dispose();
        map.dispose();
    }

}
