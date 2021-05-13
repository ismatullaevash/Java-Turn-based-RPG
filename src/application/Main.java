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

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * 
 * @author Sammy, Mostafa, Shakha
 *
 */
public class Main extends Application 
{
    static BorderPane pane;
    static Scene scene;
    
    /**
     * starts the javaFX
     */
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            pane = new BorderPane();
            scene = new Scene(pane, 800, 400);
            TitleScreen t = new TitleScreen();
            pane.setCenter(t.getPane());
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Slay The Dragon");
            primaryStage.setResizable(false);
            primaryStage.show();
        } 
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * main method for the program
     * @param args does nothing
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    /**
     * gets the scene, which is used later for getting user input
     * @return the scene
     */
    public static Scene getScene()
    {
        return scene;
    }
    /**
     * gets the pane
     * @return the pane
     */
    public static BorderPane getPane()
    {
        return pane;
    }
    /**
     * resets the game, is used when player dies
     */
    public static void reset()
    {
    	TitleScreen t;
		try
		{
			t = new TitleScreen();
	    	pane.setCenter(t.getPane());
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
    }
}