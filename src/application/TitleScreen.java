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
package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import entity.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * a class for the title screen
 * 
 * @author Sammy, Mostafa, Shakha
 *
 */
public class TitleScreen
{

	private BorderPane bp;

	/**
	 * constructor for the titlescreen
	 * 
	 * @throws FileNotFoundException can't find needed assets
	 */
	TitleScreen() throws FileNotFoundException
	{

		bp = new BorderPane();
		ImageView img1 = new ImageView();
		Image image1 = new Image(new FileInputStream("assets/img/title.png"));
		img1.setImage(image1);
		bp.getChildren().add(img1);

		VBox vbox = new VBox(150);
		Label title = new Label("Slay The Dragon!");
		title.setStyle("-fx-text-fill:green; -fx-font: 24 arial;");

		Button play = new Button("Play Now!");
		play.setPrefWidth(100);
		play.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				try
				{
					Map m = new Map(new Player("Player", 100, 10, 1, 0, "assets/img/player.png", 8,
							"assets/img/playerBattle.png", 6, "assets/img/playerDeath.png", 6, "assets/audio/atk.mp3",
							"assets/audio/death.mp3", 100, 20, 50, 100, 70, "assets/img/playerSpell.png", 7,
							"assets/audio/spell.mp3"), 1);
					Main.getPane().setCenter(m.getPane());
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		});

		vbox.getChildren().addAll(title, play);
		vbox.setAlignment(Pos.CENTER);

		bp.setCenter(vbox);

	}

	/**
	 * gets the pane
	 * 
	 * @return the pane
	 */
	public BorderPane getPane()
	{
		return bp;
	}

}