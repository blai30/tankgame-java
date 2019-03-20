import java.util.ArrayList;

public class Scene {

    private static ArrayList<GameObject> gameObjects = new ArrayList<>();

    public static void add(GameObject newObj) {
        gameObjects.add(newObj);
    }

    public static ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

}
