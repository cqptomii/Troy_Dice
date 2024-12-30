package gui;

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

public class EndController {
	private Stage primaryStage;
	private Simulation gameMode;
	private Scene scene;
	public EndController(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.gameMode = Simulation.getInstance();
		this.createScene();
	}
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
		
		summaryBox.getChildren().add(winnerBox);
		
		System.out.println(this.gameMode.getJoueurs().size());
		for(Joueur j : this.gameMode.getJoueurs()) {
			HBox playerBox = this.createPlayerField(j,false);
			summaryBox.getChildren().add(playerBox);
		}
		
		root.getChildren().add(summaryBox);
		this.scene = new Scene(root, 720*2, 576*2);
		
		VBox.setMargin(winnerBox, new Insets(0,0,20,0));
		backgroundImageView.fitWidthProperty().bind(scene.widthProperty());
		backgroundImageView.fitHeightProperty().bind(scene.heightProperty());
		
		this.primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double screenWidth = primaryStage.getWidth();
            primaryStage.setX((screenWidth - newWidth.doubleValue()) / 2);
        });

        this.primaryStage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            double screenHeight = primaryStage.getHeight();
            primaryStage.setY((screenHeight - newHeight.doubleValue()) / 2);
        });
	}
	public Scene getScene() {
		return this.scene;
	}
	private HBox createPlayerField(Joueur j,boolean winner) {
		HBox playerBox = new HBox();
		playerBox.setSpacing(10);
		
		Label nameLabel = new Label(j.getName());
		Label scoreLabel = new Label(String.valueOf(j.getScore()));
		
		if(winner) {
			nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: red;");
			scoreLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: red;");
		}else {
			nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
			scoreLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		}
		playerBox.getChildren().addAll(nameLabel,scoreLabel);
		
		return playerBox;
	}
}
