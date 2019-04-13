package gameobjects;

import java.util.ArrayList;

/**
 * Holds a static array list of game objects present in the game world.
 */
public class GameObjectCollection {

    private static ArrayList<GameObject> gameObjects;
    public static ArrayList<Spawn> spawnPoints; // Spawn points for tanks to respawn

    /**
     * Called when the game is initialized with a map and starts running.
     */
    public static void init() {
        gameObjects = new ArrayList<>();
        spawnPoints = new ArrayList<>();
    }

    /**
     * Create a game object in the game world.
     * @param obj Game object to be created
     */
    public static void spawn(GameObject obj) {
        gameObjects.add(obj);
    }

    /**
     * Removes a game object from the game world.
     * @param obj Game object to be removed
     */
    public static void destroy(GameObject obj) {
        gameObjects.remove(obj);
    }

    /**
     * Counts the number of game objects present in the game world.
     * @return Number of game objects in the array list
     */
    public static int numGameObjects() {
        return gameObjects.size();
    }

    /**
     * Retrieve a specific game object from the array list at a specified index.
     * @param index Index of the array list
     * @return The game object in the array list at index
     */
    public static GameObject getGameObject(int index) {
        return gameObjects.get(index);
    }

}
