package Data;

import java.util.ArrayList;

public class Maze {
    public int width;
    public int height;
    public int[] maze;
    public int tileWidth;

    private final int WALL_OPEN = 2;
    private final int WALL_CLOSED = -1;
    private final int PILLAR = 0;
    private final int ROOM = 1;

    public Maze(int tileWidth, int width, int height) {
        this.tileWidth = tileWidth;
        this.width = width;
        this.height = height;
    }
    public void generateMaze() {
        maze = new int[width * height];
        init();

        int rand_x = (int)(Math.random() * width);
        int rand_y = (int)(Math.random() * height);
        while(maze[width * rand_y + rand_x] != ROOM) {
            rand_x = (int)(Math.random() * width);
            rand_y = (int)(Math.random() * height);
        }

        ArrayList<Integer> path = new ArrayList<>();
        path.add(rand_y * width + rand_x);
        ArrayList<Integer> wallList = new ArrayList<>();
        addWalls(width * rand_y + rand_x, wallList);

        while(!wallList.isEmpty()) {
            int randomIndex = (int)(Math.random() * wallList.size());
            int wall = wallList.get(randomIndex);
            wallList.remove(randomIndex);
            ArrayList<Integer> adjacentRooms = new ArrayList<>();
            if(inBounds(wall - 1) && maze[wall - 1] == ROOM) adjacentRooms.add(wall - 1);
            if(inBounds(wall + 1) && maze[wall + 1] == ROOM) adjacentRooms.add(wall + 1);
            if(inBounds(wall - width) && maze[wall - width] == ROOM) adjacentRooms.add(wall - width);
            if(inBounds(wall + width) && maze[wall + width] == ROOM) adjacentRooms.add(wall + width);
            if(adjacentRooms.size() == 2) {
                if(path.contains(adjacentRooms.get(0)) && !path.contains(adjacentRooms.get(1))) {
                    int unvisitedRoom = adjacentRooms.get(1);
                    path.add(unvisitedRoom);
                    maze[wall] = WALL_OPEN;
                    addWalls(unvisitedRoom, wallList);
                } else if(path.contains(adjacentRooms.get(1)) && !path.contains(adjacentRooms.get(0))) {
                    int unvisitedRoom = adjacentRooms.get(0);
                    path.add(unvisitedRoom);
                    maze[wall] = WALL_OPEN;
                    addWalls(unvisitedRoom, wallList);
                }
            }
        }
    }

    private void addWalls(int index, ArrayList<Integer> wallList) {
        int wall1; int wall2; int wall3; int wall4;
        wall1 = index + 1;
        if (wall1 >= 0 && wall1 < width * height && maze[wall1] == WALL_CLOSED) wallList.add(wall1);

        wall3 = index - 1;
        if (wall3 >= 0 && wall3 < width * height && maze[wall3] == WALL_CLOSED) wallList.add(wall3);

        wall2 = index + width;
        if (wall2 >= 0 && wall2 < width * height && maze[wall2] == WALL_CLOSED) wallList.add(wall2);

        wall4 = index - width;
        if (wall4 >= 0 && wall4 < width * height && maze[wall4] == WALL_CLOSED) wallList.add(wall4);
    }

    private void init() {
        /**
         * Changing this initial pattern will affect the types of mazes generated
         * initial pattern is alternating rows and columns of patter:
         * PILLAR WALL PILLAR WALL ...
         * WALL ROOM WALL ROOM ...
         * PILLAR WALL PILLAR WALL ...
         * ...
         * Generates very standard mazes (puzzle book mazes)
         */
        for(int y=0; y < height; y++) {
            for(int x=0; x<width; x++) {
                if(y % 2 == 0) {
                    if(x % 2 == 0) {
                        maze[y * width + x] = PILLAR;
                    } else {
                        maze[y * width + x] = WALL_CLOSED;
                    }
                } else {
                    if(x % 2 == 0) {
                        maze[y * width + x] = WALL_CLOSED;
                    } else {
                        maze[y * width + x] = ROOM;
                    }
                }
            }
        }
    }

    private boolean inBounds(int index) {
        return index >= 0 && index < width * height;
    }

    public void print() {
        for(int i=0; i<maze.length; i++) {
            if(i % width == 0 && i != 0) {
                System.out.printf("\n%3d", maze[i]);
            } else {
                System.out.printf("%3d", maze[i]);
            }
        }
    }
}
