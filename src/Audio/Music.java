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
package Audio;

import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
/**
 * class to play music and sounds
 * @author Sammy, Mostafa, Shakha
 *
 */
public class Music
{
	private MediaPlayer music;
	/**
	 * constructor for music
	 * @param file the file containing the sound
	 */
	public Music(String file)
	{
		String song = file;
        Media play = new Media(Paths.get(song).toUri().toString());
        music = new MediaPlayer(play);
	}
	/**
	 * sets teh sound to loop
	 */
	public void loop()
	{
		music.setOnEndOfMedia(new Runnable() {
            public void run()
            {
                music.seek(Duration.ZERO);
            }
        });
	}
	/**
	 * starts playing the sound
	 */
	public void play()
	{
		music.play();
	}
	/**
	 * pauses the sound
	 */
	public void pause()
	{
		music.pause();
	}
	/**
	 * stops the sound
	 */
	public void stop()
	{
		music.stop();
	}
	/**
	 * resets the sound so that it can be played again
	 */
	public void reset()
	{
		music.seek(music.getStartTime());
	}

}
