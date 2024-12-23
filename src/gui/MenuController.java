package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MenuController {
    private Interface mainApp;
    private Stage stage;
    private Scene scene;

    public MenuController(Interface mainApp, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        createScene();
    }

    private void createScene() {
    	
    	Image backgroundImage = new Image(getClass().getResource("/images/menu_background.png").toExternalForm());
    	
    	ImageView backgroundView = new ImageView(backgroundImage);
    	backgroundView.setPreserveRatio(false);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600); 
    	
    	
        Button button = new Button("Lancer le jeu");
        button.setOnAction(event -> mainApp.showNombreJoueursScene());
        button.setId("menuButton");
  
        StackPane root = new StackPane(backgroundView,button);
        
        this.scene = new Scene(root, 800, 600);
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        backgroundView.fitWidthProperty().bind(scene.widthProperty());
        backgroundView.fitHeightProperty().bind(scene.heightProperty());
        button.prefWidthProperty().bind(scene.widthProperty().multiply(0.3));
        button.prefHeightProperty().bind(scene.heightProperty().multiply(0.1));

        StackPane.setAlignment(button, Pos.BOTTOM_CENTER);
        button.translateYProperty().bind(scene.heightProperty().multiply(-0.3));
    }

    public Scene getScene() {
        return this.scene;
    }
}

