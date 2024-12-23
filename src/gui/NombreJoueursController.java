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

public class NombreJoueursController {
    private Interface mainApp;
    private Stage stage;
    private Scene scene;
    private Simulation gameMode;
    
    public NombreJoueursController(Interface mainApp, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.gameMode = Simulation.getInstance();
        createScene();
    }

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
                    mainApp.showSaisieJoueurScene();
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

    public Scene getScene() {
        return this.scene;
    }
}

