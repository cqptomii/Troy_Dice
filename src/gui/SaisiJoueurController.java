package gui;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import core.Simulation;

public class SaisiJoueurController {
    private Interface mainApp;
    private Stage stage;
    private Simulation gameMode;
    private Scene scene;

    public SaisiJoueurController(Interface mainApp, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.gameMode = Simulation.getInstance();
        createScene();
    }

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
                    String crieur = gameMode.choisirCrieur();
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

    public Scene getScene() {
        return this.scene;
    }
}