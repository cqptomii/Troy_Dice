package gui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        Label title = new Label("Bienvenue sur le jeu UTBM Dice");
        Button button = new Button("Lancer le jeu");
        button.setOnAction(event -> mainApp.showNombreJoueursScene());

        VBox layout = new VBox(10, title, button);
        layout.setAlignment(Pos.CENTER);
        this.scene = new Scene(layout, 400, 400);
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
    }

    public Scene getScene() {
        return this.scene;
    }
}

