package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import Data.*;
import Data.Frame;
import Test.AnimationManager;
import Test.Maze;
import Test.Entity;
import Test.Vector2D;
import javafx.stage.Screen;
import logic.Control;

public class Main{
	// define graphic window and level bounds
	public static final int ScreenX = 1920;
	public static final int ScreenY = 1080;
	public static final int MapY = 2160;
	public static final int MapX = ScreenX;


	public static Entity player;
	public static int target_x = 0;
	public static int target_y = 0;
	public static int tileWidth = 128;
	public static BufferedImage canvas;
	public static BufferedImage playerHalo;
	public static BufferedImage background;
	public static Sprite canvasSprite;
	public static Maze maze;
	public static BufferedImage mazeImage;
	public static RECT r;
	public static final int dropShadow = 2;
	private static Animation anim;
	private static boolean isScrolling = false;
	private static CollisionManager collisionManager;

	private static int scrollSpeed = 1;
	private static int background_y;
	private static BufferedImage bg;
	private static AnimationManager animationsManager;
	private static final int SCROLL_BUFFER = ScreenY / 3;

	public static void main(String[] args) throws IOException {
		/**
		 * updateArtTxt() will automatically fill out Art.txt file with new entries to ./Art dir
		 */
		// TODO: Modify updateArtTxt() to also remove entries from list which are no longer valid
		ArtFileUpdater.update();
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start(Control ctrl){
		ArrayList<RECT> viewBoundingRECT = new ArrayList<>();
		ArrayList<RECT> mazeWallsBoundingBoxes;
		collisionManager = new CollisionManager();
		maze = new Maze(tileWidth, MapX / tileWidth, MapY / tileWidth);
		maze.generateMaze();

		mazeImage = new BufferedImage(MapX, MapY, BufferedImage.TYPE_INT_ARGB);
		BufferedImage dungeeonWall = ctrl.getSpriteFromBackBuffer("dungeonwall").getSprite();
		Graphics gmaze = mazeImage.getGraphics();
		mazeWallsBoundingBoxes = new ArrayList<>();
		gmaze.setColor(Color.white);
		gmaze.fillRect(0, 0, MapX, MapY);
		background_y = 0;
		boolean player_start = true;
		for(int y=0; y<maze.height; y++) {
			for(int x=0; x < maze.width; x++) {
				if(maze.maze[maze.width * y + x] <= 0) {
					gmaze.drawImage(dungeeonWall, x * tileWidth, y * tileWidth, Color.white, null);
					mazeWallsBoundingBoxes.add(new RECT(x*tileWidth, x*tileWidth + tileWidth, y*tileWidth, y*tileWidth + tileWidth, String.format("mazeWall(%d,%d)", x, y)));
					collisionManager.addCollections("walls", mazeWallsBoundingBoxes);
				} else if(player_start) {
					player = new Entity(3, x * tileWidth, y * tileWidth);
					player_start = false;
				}
			}
		}
		canvas = new BufferedImage(MapX, MapY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = canvas.getGraphics();
		g.drawImage(mazeImage, 0, 0, Color.white, null);
		canvasSprite = new Sprite(0, 0, canvas, "canvas");

		// animation manager init()
		animationsManager = new AnimationManager();
		Animation test = new Animation(85, true, "playerLeft");
		test.addFrame(0,0,"red_dude_left1");
		test.addFrame(0,0,"red_dude_left2");
		test.addFrame(0,0,"red_dude_left3");
		test.addFrame(0,0,"red_dude_left4");
		animationsManager.addAnimation(test);

		viewBoundingRECT.add( new RECT( 0, ScreenX, ScreenY - (ScreenY / 4), ScreenY, "bottomBound"));
		viewBoundingRECT.add( new RECT( 0, ScreenX, 0, ScreenY / 4, "topBound"));
		collisionManager.addCollections("viewBounds", viewBoundingRECT);

	}

	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		if(ctrl.getMouseInput() != null) {
			target_x = ctrl.getMouseInput().getX();
			target_y = ctrl.getMouseInput().getY();
		}

		// calculate the new position of entities on the screen.
		Vector2D playerPosition = player.getPosition();
		Vector2D delta = calcChangePlayerPosition(target_x, target_y, playerPosition, player.getSpeed());
		Vector2D nextPosition = new Vector2D(playerPosition);
		nextPosition.add(delta);

		if(collisionManager.checkEntityCollision("walls", nextPosition.getX(), player.width, playerPosition.getY(), player.height)) {
			delta.setX(0);
		}
		if(collisionManager.checkEntityCollision("walls", playerPosition.getX(), player.width, nextPosition.getY(), player.height)) {
			delta.setY(0);
		}

		/**
		// detect wall collisions for entities
		for (RECT wallPiece: mazeWallsBoundingBoxes) {
			if(wallPiece.isCollision(nextPosition.getX(), player.width, playerPosition.getY(), player.height)) {
				delta.setX(0);
			}
			if(wallPiece.isCollision(playerPosition.getX(), player.width, nextPosition.getY(), player.height)) {
				delta.setY(0);
			}
		}

		 // Detect bounding collisions

		 **/
		// finally update entity position

		// draw background first (current view of maze)
		Frame nextFrame = animationsManager.getCurrentFrame("playerLeft");
		nextFrame.setX(nextPosition.getX());
		nextFrame.setY(nextPosition.getY());

		if(nextPosition.getY() > ScreenY - (SCROLL_BUFFER) && delta.getY() > 0) {
			isScrolling = true;
		}

		if(isScrolling) {
			if (player.getPosition().getY() + (SCROLL_BUFFER) <= MapY) {
				background_y -= delta.getY();
				nextFrame.setY((ScreenY - (SCROLL_BUFFER) ));
			} else {
				nextFrame.setY(nextPosition.getY() % ScreenY + (ScreenY - (SCROLL_BUFFER) ));
				isScrolling = false;
			}
		}
		player.changePosition(delta);
		Graphics g = canvas.getGraphics();
		ctrl.addSpriteToFrontBuffer(0, background_y, canvasSprite);

		// now that entity positions have been updated, draw them to the screen
		// for each entity: first check which direction entity facing
		// then add correct frame and coordinates to queue?
		// for this part, entity and entity information should be tied together using tags:
		// for example: player entity => "player" : animations in manager => "player" + {left, right, idle}
		// so for each entity, get the correct next frame, then add frame to buffer using coordinates for entity
		ctrl.addSpriteToFrontBuffer(nextFrame.getX(), nextFrame.getY(), nextFrame.getSpriteTag());
		ctrl.drawString(600, 600, String.format("(%d, %d) : delta(%d, %d)", player.getPosition().getX(), player.getPosition().getY(), delta.getX(), delta.getY()), Color.GREEN);
	}

	public static Vector2D calcChangePlayerPosition(int target_x, int target_y, Vector2D position, int speed) {
		int difference_x = target_x - (position.getX() % ScreenX);
		int difference_y = target_y - (position.getY() % ScreenY);
		System.out.printf("dif: (%d, %d) target: (%d, %d) player:(%d, %d)\n", difference_x, difference_y, target_x, target_y, position.getX(), position.getY());

		double magnitude = Math.sqrt(Math.pow(target_x, 2) + Math.pow(target_y, 2));

		if(magnitude == 0 || magnitude < speed) {
			return new Vector2D(0,0);
		}
		double delta_x = (difference_x / magnitude) * speed;
		double delta_y = (difference_y / magnitude) * speed;
		if(delta_x > 0)
			delta_x = Math.ceil(delta_x);
		else
			delta_x = Math.floor(delta_x);

		if(delta_y > 0)
			delta_y = Math.ceil(delta_y);
		else
			delta_y = Math.floor(delta_y);

		// Clamp output to stop characters from zooming around at random intervals
		if(delta_x > speed) {
			delta_x = speed;
		}
		if(delta_y > speed) {
			delta_y = speed;
		}

		return new Vector2D((int) delta_x, (int) delta_y);
	}
}