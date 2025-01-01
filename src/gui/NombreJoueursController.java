package gui;
import core.Simulation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Cette classe modélise le controller gérant le nombre du joueurs
 * 
 * @version 1.0
 *
 * @see NombreJoueursController
 * @author Tom FRAISSE
 */
public class NombreJoueursController {
    private Interface mainApp;
    private Scene scene;
    private Simulation gameMode;
    
    /** 
	 * Cette méthode permet d'instancier le controller de la scene NombreJoueur
	 *  
	 * @param		mainApp : Instance de l'objet qui affiche l'interface graphique
	 * @param		stage : Stage courant
	 * 
	 * @see NombreJoueursController#NombreJoueursController 
	 * @author Tom FRAISSE
	 */
    public NombreJoueursController(Interface mainApp, Stage stage) {
        this.mainApp = mainApp;
        this.gameMode = Simulation.getInstance();
        createScene();
    }

    /** 
	 * Cette méthode permet de créer la scène du choix du nombre de joueur
	 *  
	 * @see NombreJoueursController#createScene 
	 * @author Tom FRAISSE
	 */
    private void createScene() {
        Label instruction = new Label("Entrez le nombre de joueurs (2-6) :");
        Label error = new Label();
        TextField input = new TextField();
        Button validateButton = new Button("Valider");

        validateButton.setOnAction(event -> {
            try {
                int value = Integer.parseInt(input.getText());
                if (value >= 2 && value <= 6) {
                    this.gameMode.setNombreJoueur(value);
                    try {
						mainApp.showSaisieJoueurScene();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                } else {
                    error.setText("Entrez un nombre valide entre 2 et 6");
                }
            } catch (NumberFormatException e) {
                error.setText("Entrez un nombre valide entre 2 et 6");
            }
        });

        VBox layout = new VBox(10, instruction, input, validateButton, error);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
 
        this.scene = new Scene(layout, 400, 400);
    }

    /** 
	 * Cette méthode permet d'obtenir la scene du choix du nombre de joueur
	 *  
	 * @return : Instance de la scene du choix du nombre de joueur
	 * 
	 * @see NombreJoueursController#getScene 
	 * @author Tom FRAISSE
	 */
    public Scene getScene() {
        return this.scene;
    }
}

