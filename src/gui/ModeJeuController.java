package gui;

import java.util.Random;

import core.Simulation;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModeJeuController {
	private Interface mainApp;
    private Stage stage;
    private Scene scene;
    private Simulation gameMode;
    private String nomCrieur;
    
    public ModeJeuController(Interface mainApp, Stage stage,String nomCrieur) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.gameMode = Simulation.getInstance();
        this.nomCrieur = nomCrieur;
        createSceneMode();
    }

    private void createSceneMode() {	
		Label crieurBanner = new Label("Le crieur est :" + nomCrieur);
		crieurBanner.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");

		Label title = new Label("Choisissez le mode de jeu");
		title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10;");

		
		Button modeUn = new Button("Mode classique");
		modeUn.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		modeUn.setOnAction( event -> onChoisirMode(event, 0));
		
		Button modeDeux = new Button("Mode avancé");
		modeDeux.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		modeDeux.setOnAction( event -> onChoisirMode(event, 1));
		
		Button modeTrois = new Button("Mode aléatoire");
		modeTrois.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		modeTrois.setOnAction( event -> onChoisirMode(event, 2));
		
		HBox bLayout = new HBox(modeUn,modeDeux,modeTrois);
		bLayout.setAlignment(Pos.CENTER);
		VBox gLayout = new VBox(crieurBanner,title,bLayout);
		gLayout.setAlignment(Pos.CENTER);
	    VBox.setMargin(crieurBanner, new Insets(10));
	    VBox.setMargin(title, new Insets(10));
	    VBox.setMargin(bLayout, new Insets(10));
	    
	    BorderPane root = new BorderPane();
	    
		root.setCenter(gLayout);
		this.scene = new Scene(root,400,400);
    }
    private void createSceneFeuille() {
    	Label title = new Label(this.nomCrieur+ " choisis l'ordre des sections des feuilles de jeu");
    	title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");
		
		
		Button bDeLancer = new Button("Lancer le dé");
		
		Label lDe = new Label("0");
		 lDe.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10;");
		int deValue = Integer.parseInt(lDe.getText());
		bDeLancer.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		bDeLancer.setOnAction(event -> onLancerDe(event, lDe));
		
		VBox deLayout = new VBox(10, lDe, bDeLancer);
	    deLayout.setAlignment(Pos.CENTER);
		
		Button bDecroissant = new Button("Ordre décroissant");
		bDecroissant.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		bDecroissant.setOnAction(event -> onChoisirOrdre(event, 1,deValue));
		
		Button bCroissant = new Button("Ordre croissant");
		bCroissant.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		bCroissant.setOnAction(event -> onChoisirOrdre(event, 0,deValue));
		
		HBox bLayout = new HBox(20, bDecroissant, bCroissant);
	    bLayout.setAlignment(Pos.CENTER);

	    VBox vLayout = new VBox(20, title, deLayout, bLayout);
	    vLayout.setAlignment(Pos.CENTER);
	    VBox.setMargin(title, new Insets(10));
	    VBox.setMargin(deLayout, new Insets(10));
	    VBox.setMargin(bLayout, new Insets(10));
		
	    BorderPane root = new BorderPane();
	    
		root.setCenter(vLayout);
		
		this.scene = new Scene(root,400,400);
    }
    public void onChoisirMode(ActionEvent event, int mode) {
    	if(mode==0) {
    		this.onChoisirOrdre(event, 2, 1);
    	}
    	else if(mode == 1) {
    		this.createSceneFeuille();
    		this.stage.setScene(this.scene);
    	}else {
    		this.onChoisirOrdre(event, -1, 1);
    	}
    }
    public void onChoisirOrdre(ActionEvent event, int ordre,int value) {
    	
    	/*
		 *  Altération du modèle
		 */
    	this.gameMode.setIndexFeuille(value, ordre);
    	// Lancer la fenêtre du jeu
    	this.mainApp.showGameScene();
    }
    public void onLancerDe(ActionEvent event, Label de) {
    	Random randomEngine = new Random();
    	int value = randomEngine.nextInt(1,7);
    	de.setText(""+value);
    }
    public Scene getScene() {
        return this.scene;
    }
}
