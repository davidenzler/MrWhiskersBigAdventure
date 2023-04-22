package Main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Iterator;

import Data.Sprite;
import Input.Mouse;
import Data.Vector2D;
import Data.CommandAdapter;
import javafx.stage.Screen;
import logic.Control;
import particles.ParticleSystem;
import particles.Rain;
import Data.Frame;
import particles.Sparkle;

public class Main{
	// define graphic window and level bounds
	public static final int ScreenX = 1280;
	public static final int ScreenY = 720;
	public static final int MapY = 2160;
	public static final int MapX = ScreenX;
	public static Sparkle sparkle;
	public static Rain rain;



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
	public static void start(Control ctrl) {
		sparkle = new Sparkle(
				795,
				480,
				25,
				25,
				10,
				30,
				5,
				2
		);
		rain = new Rain(
				-50,
				0,
				1200,
				90,
				25,
				60,
				150
		);
	}

	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		ctrl.addSpriteToFrontBuffer(0, 0, "forest_1280");
		ctrl.addSpriteToFrontBuffer(800, 500, "catnip");
		ParticleSystem pm2 = sparkle.getParticleSystem();
		ParticleSystem itsRaining = rain.getParticleSystem();
		Iterator<Data.Frame> rainIterator = itsRaining.getParticles();
		Iterator<Data.Frame> it2 = pm2.getParticles();

		while(it2.hasNext()) {
			Data.Frame par2 = it2.next();
			BufferedImage particle = ctrl.getSpriteFromBackBuffer(par2.getSpriteTag()).getSprite().getSubimage(0,0,16,16);
			Sprite particleSprite = new Sprite(16, 14, particle, "sparkleParticle");
			ctrl.addSpriteToFrontBuffer(par2.getX(), par2.getY(), particleSprite);
		}
		while(rainIterator.hasNext()) {
			Data.Frame rainFrame = rainIterator.next();
			BufferedImage rainImg = ctrl.getSpriteFromBackBuffer(rainFrame.getSpriteTag()).getSprite();
			BufferedImage subrainImg = rainImg.getSubimage(0,0,rainImg.getWidth(),rainImg.getHeight());
			Sprite particleSprite = new Sprite(16, 16, rainImg, "rainParticle");
			ctrl.addSpriteToFrontBuffer(rainFrame.getX(), rainFrame.getY(), particleSprite);
		}

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
