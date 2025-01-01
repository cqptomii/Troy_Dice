package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Cette classe modélise le controller du menu de jeu
 * 
 * @version 1.0
 *
 * @see MenuController
 * @author Tom FRAISSE
 */
public class MenuController {
    private Interface mainApp;
    private Scene scene;

    
    /** 
	 * Cette méthode permet d'instancier le controller de la scene Menu
	 *  
	 * @param		mainApp : Instance de l'objet qui affiche l'interface graphique
	 * @param		stage : Stage courant
	 * 
	 * @see MenuController#MenuController 
	 * @author Tom FRAISSE
	 */
    public MenuController(Interface mainApp, Stage stage) {
        this.mainApp = mainApp;
        createScene();
    }
    
    
    /** 
	 * Cette méthode permet de créer la scène du menu
	 *  
	 * @see MenuController#createScene 
	 * @author Tom FRAISSE
	 */
    private void createScene() {
    	
    	Image backgroundImage = new Image(getClass().getResource("/images/menu_background.png").toExternalForm());
    	
    	ImageView backgroundView = new ImageView(backgroundImage);
    	backgroundView.setPreserveRatio(false);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600); 
    	
    	
        Button button = new Button("Lancer le jeu");
        button.setOnAction(event -> {
			try {
				mainApp.showNombreJoueursScene();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
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

    /** 
	 * Cette méthode permet d'obtenir la scene du menu
	 *  
	 * @return : Instance de la scene du menu
	 * 
	 * @see MenuController#getScene 
	 * @author Tom FRAISSE
	 */
    public Scene getScene() {
        return this.scene;
    }
}

