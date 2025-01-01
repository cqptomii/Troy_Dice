package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import core.Crieur;
import core.Simulation;


/**
 * Cette classe modélise le controller de la saisi des joueurs
 * 
 * @version 1.0
 *
 * @see SaisiJoueurController
 * @author Tom FRAISSE
 */
public class SaisiJoueurController {
    private Interface mainApp;
    private Simulation gameMode;
    private Scene scene;

    /** 
	 * Cette méthode permet d'instancier le controller de la scene de saisi des joueurs
	 *  
	 * @param		mainApp : Instance de l'objet qui affiche l'interface graphique
	 * @param		stage : Stage courant
	 * 
	 * @see SaisiJoueurController#SaisiJoueurController 
	 * @author Tom FRAISSE
	 */
    public SaisiJoueurController(Interface mainApp, Stage stage) {
        this.mainApp = mainApp;
        this.gameMode = Simulation.getInstance();
        createScene();
    }
    
    /** 
	 * Cette méthode permet de créer la scène de saisi des joueurs
	 *  
	 * @see SaisiJoueurController#createScene 
	 * @author Tom FRAISSE
	 */
    private void createScene() {
    	Label error = new Label("");
    	error.setStyle("-fx-text-fill: red;");
        Label nameLabel = new Label("Entrez le nom du joueur :");
        TextField nameField = new TextField();
        Label gpaLabel = new Label("Entrez le GPA du joueur :");
        TextField gpaField = new TextField();
        Button validateButton = new Button("Créer");

        validateButton.setOnAction(event -> {
            try {
            	int gpa = Integer.parseInt(gpaField.getText());
    			String name = nameField.getText();
            	if(name.length() < 2) {
    				error.setText("Veuillez entrer un nom valide.");
    				return;
    			}else if(gpa >100 || gpa <0) {
    				error.setText("Veuillez entrer un GPA valide.");
    				return;
    			}else {
    				this.gameMode.addJoueur(name, gpa);
    			}
                if (gameMode.getNbJoueurs() < gameMode.getNombreJoueur()) {
                    mainApp.showSaisieJoueurScene();
                } else {
                    Crieur crieur = gameMode.choisirCrieur();
                    mainApp.showModeJeuScene(crieur);
                }
            } catch (Exception e) {
                error.setText("Erreur : données invalides !");
            }
        });

        VBox layout = new VBox(10, nameLabel,
        		nameField, gpaLabel, gpaField, 
        		error, validateButton
            );
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));

        this.scene = new Scene(layout, 400, 400);
    }

    /** 
	 * Cette méthode permet d'obtenir la scene de saisi des joueurs
	 *  
	 * @return : Instance de la scene de saisi des joueurs
	 * 
	 * @see SaisiJoueurController#getScene 
	 * @author Tom FRAISSE
	 */
    public Scene getScene() {
        return this.scene;
    }
}