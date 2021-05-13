/**********************************************
Assignment 2
Course: btp400 - winter 2021
Author: Sammy, Mostafa, Shakha
ID: 161334180
Section: NAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date: 2020-04-21
**********************************************/
package entity;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Rectangle2D;

/**
 * class for the sprite
 * @author Sammy, Mostafa, Shakha
 *
 */
public class Sprite
{
    private Image image;
    private double posX;
    private double posY;    
    private double velX;
    private double velY;
    private double width;
	private double height;

	/**
	 * Constructor for sprite
	 * @param Filename file for the sprite
	 */
    public Sprite(String Filename)
    {
    	setImage(Filename);
        posX = 0;
        posY = 0;    
        velX = 0;
        velY = 0;
    }
    /**
     * sets the image
     * @param img image
     */
    public void setImage(Image img)
    {
    	image = img;
    	width = img.getWidth();
    	height = img.getHeight();
    }
    /**
     * sets the image
     * @param filename file of image
     */
    public void setImage(String filename)
    {
		try
		{
			Image i = new Image(new FileInputStream("./" + filename));
	        image = i;
	        width = i.getWidth();
	        height = i.getHeight();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
    }

    /**
     * sets the position
     * @param x x coord
     * @param y y coord
     */
    public void setPosition(double x, double y)
    {
        posX = x;
        posY = y;
    }

    /**
     * sets velocity
     * @param x x velocity
     * @param y y velocity
     */
    public void setVelocity(double x, double y)
    {
        velX = x;
        velY = y;
    }

    /**
     * adds the velocity
     * @param x x velocity to add
     * @param y y velocity to add
     */
    public void addVelocity(double x, double y)
    {
        velX += x;
        velY += y;
    }

    /**
     * changes position based on time passed
     * @param time time passed
     */
    public void update(double time)
    {
        posX += velX * time;
        posY += velY * time;
    }

    /**
     * renders the image to screen
     * @param gc used to render
     */
    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, posX, posY);
    }
    /**
     * renders the image to screen
     * @param gc used to render
     * @param width width to render
     * @param height height to render
     */
    public void render(GraphicsContext gc, int width, int height)
    {
    	setSize(width,height);
        gc.drawImage( image, posX, posY, width, height);
    }
    /**
     * sets size
     * @param width2 new width
     * @param height2 new height
     */
    public void setSize(double width2, double height2)
    {
    	this.width=width2;
    	this.height=height2;
    }

    /**
     * gets boundary of image
     * @return a rectangle2D of the boundaries
     */
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(posX,posY,width,height);
    }

    /**
     * checks for collisions
     * @param s sprite to compare to
     * @return if they collide
     */
    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    /**
     * gets x velocity
     * @return x velocity
     */
	public double getVelX()
	{
		return velX;
	}

	/**
	 * sets x velocity
	 * @param velX new x velocity
	 */
	public void setVelX(double velX)
	{
		this.velX = velX;
	}

	/**
	 * gets y velocity
	 * @return y velocity
	 */
	public double getVelY()
	{
		return velY;
	}

	/**
	 * sets y velocity
	 * @param velY new y velocity
	 */
	public void setVelY(double velY)
	{
		this.velY = velY;
	}

	/**
	 * gets x pos
	 * @return x pos
	 */
	public double getPosX()
	{
		return posX;
	}

	/**
	 * sets x pos
	 * @param posX new x pos
	 */
	public void setPosX(double posX)
	{
		this.posX = posX;
	}

	/**
	 * gets y pos
	 * @return y pos
	 */
	public double getPosY()
	{
		return posY;
	}

	/**
	 * sets y pos
	 * @param posY new y pos
	 */
	public void setPosY(double posY)
	{
		this.posY = posY;
	}

	/**
	 * gets image
	 * @return returns image
	 */
	public Image getImage()
	{
		return image;
	}
	/**
	 * gets width of image
	 * @return width
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * sets width
	 * @param width new width
	 */
	public void setWidth(double width)
	{
		this.width = width;
	}

	/**
	 * gets height of image
	 * @return height
	 */
	public double getHeight()
	{
		return height;
	}

	/**
	 * sets height
	 * @param height new height
	 */
	public void setHeight(double height)
	{
		this.height = height;
	}
	
}