package GameObjects;

import java.util.ArrayList;

public class GameObjectCollection {

    private static ArrayList<GameObject> gameObjects;

    public static void init() {
        gameObjects = new ArrayList<>();
    }

    public static void spawn(GameObject obj) {
        gameObjects.add(obj);
    }

    public static int numGameObjects() {
        return gameObjects.size();
    }

    public static GameObject getGameObject(int index) {
        return gameObjects.get(index);
    }

}
