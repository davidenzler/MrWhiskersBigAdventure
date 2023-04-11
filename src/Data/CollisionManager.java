package Data;

import java.util.ArrayList;
import java.util.HashMap;

public class CollisionManager {
    private HashMap<String, ArrayList<RECT>> collisionCollections;

    public CollisionManager() {
        collisionCollections = new HashMap<>();
    }

    public void addCollections(String tag, ArrayList<RECT> rectList) {
        collisionCollections.put(tag, rectList);
    }

    public boolean checkEntityCollision(String tag, int x, int width, int y, int height) {
        ArrayList<RECT> RECTList = collisionCollections.get(tag);
        boolean isCollision = false;
        for (RECT object: RECTList) {
            if(object.isCollision(x, width, y, height)) {
                isCollision = true;
                break;
            }
        }

        return isCollision;
    }
}
