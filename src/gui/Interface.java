package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import core.Simulation;

public class Interface extends Application{
	private Stage primaryStage;
	private GameController controller;
	private String nomCrieur;
	
	public void init() throws Exception{
		super.init();
	
	}
	public void stop() throws Exception{
		System.out.println("stop");
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Troy Dice");
		primaryStage.setResizable(true);
		
		// Création du contrôleur avec l'instance de primaryStage
		controller = new GameController(this,primaryStage);
		
		showMenuScene();
//		new Thread(()-> {
//			try {
//				Thread.sleep(5000);
//				
//				Platform.runLater(()->{
//					primaryStage.hide();
//				});
//					
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}).start();
	}
	private void showMenuScene() {
		MenuController menuController = new MenuController(this,primaryStage);
		primaryStage.setScene(menuController.getScene());
		primaryStage.setTitle("UTBM Dice - Menu");
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
	
	protected void showNombreJoueursScene() {
		NombreJoueursController jController = new  NombreJoueursController(this,primaryStage);
		primaryStage.setScene(jController.getScene());
		primaryStage.setTitle("UTBM Dice - Joueurs");
		primaryStage.show();
	}
	
	protected void showSaisieJoueurScene() {
		SaisiJoueurController sController = new SaisiJoueurController(this,primaryStage);
		primaryStage.setScene(sController.getScene());
		primaryStage.setTitle("UTBM Dice - créer joueur");
		primaryStage.show();
	}
	protected void showModeJeuScene(String nomCrieur) {
		this.nomCrieur = nomCrieur;
		ModeJeuController mController = new ModeJeuController(this,primaryStage,nomCrieur);
		primaryStage.setScene(mController.getScene());
		primaryStage.show();
	}	
	protected void showGameScene() {
		primaryStage.setScene(this.controller.getScene());
		primaryStage.setTitle("UTBM Dice - Game");
		primaryStage.show();
	}
	public void run() {
		launch();
	}
	public static void main(String[] args) {
		launch();
		//Platform.exit()
	}
}
