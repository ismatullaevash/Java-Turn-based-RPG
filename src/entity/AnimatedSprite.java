
package entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
/**
 * a class for an animated sprite
 * 
 *
 */
public class AnimatedSprite extends Sprite
{
	private final int duration = 8;
	private Image[] frames;
	private int frameCount;
	private double totalTime;
	private int frame;
	/**
	 * constructor for animated sprite
	 * @param fileName the file of the base sprite
	 * @param frames the amount of frames in the animation
	 */
	public AnimatedSprite(String fileName, int frames)
	{
		super(fileName);
		this.frames = new Image[frames];
		for (int i = 0; i < frames; i++)
		{
			try
			{
				this.frames[i] = new Image(new FileInputStream("./" + fileName.substring(0, fileName.lastIndexOf('.')) + i + ".png"));
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		totalTime = 0;
		frameCount = frames;
	}
	/**
	 * changes the "active" frame based on the time passed
	 * @param time the time passed
	 */
	public void update(double time)
	{
		super.update(time);
		totalTime+=time;
		frame = (int)((totalTime % (frameCount * duration)) / duration);
		super.setImage(frames[frame]);
	}
	/**
	 * changes the base file
	 * @param filename the new image file
	 */
	public void setImage(String filename)
	{
		for (int i = 0; i < frameCount; i++)
		{
			try
			{
				this.frames[i] = new Image(new FileInputStream("./" + filename.substring(0, filename.lastIndexOf('.')) + i + ".png"));
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		super.setImage(filename);
	}
	/**
	 * pauses the animations
	 */
	public void pause()
	{
		frameCount = 1;
	}
	/**
	 * re-enables animation
	 */
	public void play()
	{
		frameCount = frames.length;
	}
	/**
	 * gets the next frame
	 * @return the next frame, as an image
	 */
	public Image getNextFrame()
	{
		frame++;
		if (frame >= frames.length)
		{
			frame = 0;
		}
		return frames[frame];
	}
	/**
	 * gets the amount of frames
	 * @return amount of frames
	 */
	public int getAmount()
	{
		return frames.length;
	}
	/**
	 * resets the animation to frame 0
	 */
	public void reset()
	{
		frame = 0;
		super.setImage(frames[0]);
	}

}
