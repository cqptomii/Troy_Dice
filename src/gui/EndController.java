package gui;

import java.util.ArrayList;

import core.Joueur;
import core.Simulation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Cette classe modélise le controller de la scene de fin
 * 
 * @version 1.0
 *
 * @see EndController
 * @author Tom FRAISSE
 */
public class EndController {
	private Stage primaryStage;
	private Simulation gameMode;
	private Scene scene;
	
	
	/** 
	 * Cette méthode permet d'instancier le controller de la scene de fin
	 *  
	 * @param		stage : Stage courant
	 * 
	 * @see EndController#EndController 
	 * @author Tom FRAISSE
	 */
	public EndController(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.gameMode = Simulation.getInstance();
		this.createScene();
	}
	
	/** 
	 * Cette méthode permet de créer la scène de fin
	 *  
	 * @see EndController#createScene 
	 * @author Tom FRAISSE
	 */
	private void createScene() {
		StackPane root = new StackPane();
		
		Image backgroundImage = new Image(getClass().getResource("/images/Fin.png").toExternalForm());
		ImageView backgroundImageView = new ImageView(backgroundImage);
		
		root.getChildren().add(backgroundImageView);
		
		VBox summaryBox = new VBox();
		summaryBox.setSpacing(5); // Ajoute un espacement vertical
		summaryBox.setAlignment(Pos.CENTER);
		summaryBox.setPadding(new Insets(0,0,15,0));
		
		HBox winnerBox = this.createPlayerField(this.gameMode.getWinner(),true);
		winnerBox.setAlignment(Pos.CENTER);
		summaryBox.getChildren().addAll(winnerBox);
		
		HBox playerBox = new HBox(30);
		playerBox.setAlignment(Pos.CENTER);
		playerBox.setPadding(new Insets(20,0,0,0));
		System.out.println(this.gameMode.getJoueurs().size());
		
		VBox playerGraduate = this.createPlayerSummary(this.gameMode.getJoueurs(), true);
		VBox playerUnGraduate = this.createPlayerSummary(this.gameMode.getJoueurs(), false);
		playerBox.getChildren().addAll(
				playerGraduate,
				playerUnGraduate
				);
		
		summaryBox.getChildren().add(playerBox);
		
		root.getChildren().add(summaryBox);
		this.scene = new Scene(root, 720*2, 576*2);
		
		VBox.setMargin(winnerBox, new Insets(75,0,20,0));
		HBox.setMargin(playerUnGraduate, new Insets(125,0,0,250));
		HBox.setMargin(playerGraduate, new Insets(125,350,0,0));
		backgroundImageView.fitWidthProperty().bind(scene.widthProperty());
		backgroundImageView.fitHeightProperty().bind(scene.heightProperty());
	}
	
	/** 
	 * Cette méthode permet d'obtenir la scene de fin de partie
	 *  
	 * @return : Instance de la scene de fin de partie
	 * 
	 * @see EndController#getScene 
	 * @author Tom FRAISSE
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	private VBox createPlayerSummary(ArrayList<Joueur> players, boolean graduated) {
		VBox boxPlayers = new VBox();
		for(Joueur j : players) {
			if( (j.getScore() >= 20 && graduated) || graduated == false && j.getScore() < 20) {
				HBox playerBox = this.createPlayerField(j,false);
				boxPlayers.getChildren().add(playerBox);
			}
		}
		return boxPlayers;
	}
	
	/** 
	 * Cette méthode permet de creer la boite d'affiche du score d'un joueur
	 *  
	 * @return : Boite d'affichage des score
	 * @param		j : Joueur à utiliser
	 * @param		winner : état si le joueur est le gagnant ou pas
	 * 
	 * @see EndController#createPlayerField 
	 * @author Tom FRAISSE
	 */
	private HBox createPlayerField(Joueur j,boolean winner) {
		HBox playerBox = new HBox();
		playerBox.setSpacing(10);
		
		Label nameLabel = new Label(j.getName());
		Label scoreLabel = new Label(String.valueOf(j.getScore()));
		
		if(winner) {
			nameLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;-fx-text-fill: red");
			scoreLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;-fx-text-fill: red");
		}else {
			nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
			scoreLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		}
		playerBox.getChildren().addAll(nameLabel,scoreLabel);
		
		return playerBox;
	}
}
