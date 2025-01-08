package gui;

import core.Crieur;
import core.Simulation;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Cette classe modélise le controller de la scene de mode jeu
 * 
 * @version 1.0
 *
 * @see ModeJeuController
 * @author Tom FRAISSE
 */
public class ModeJeuController {
	private Interface mainApp;
    private Stage stage;
    private Scene scene;
    private Simulation gameMode;
    private Crieur nomCrieur;
    
    
    /** 
	 * Cette méthode permet d'instancier le controller de la scene de choix de mode de jeu
	 *  
	 * @param		mainApp : Instance de l'objet qui affiche l'interface graphique
	 * @param		stage : Stage courant
	 * 
	 * @see ModeJeuController#ModeJeuController 
	 * @author Tom FRAISSE
	 */
    public ModeJeuController(Interface mainApp, Stage stage,Crieur nomCrieur) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.gameMode = Simulation.getInstance();
        this.nomCrieur = nomCrieur;
        createSceneMode();
    }
    
    /** 
	 * Cette méthode permet de créer la scène du choix du mode de jeu de la partie
	 *  
	 * @see ModeJeuController#createSceneMode 
	 * @author Tom FRAISSE
	 */
    private void createSceneMode() {
    	
    	Image backgroundImage = new Image(getClass().getResource("/images/menu_background2.png").toExternalForm());
    	
    	ImageView backgroundView = new ImageView(backgroundImage);
    	backgroundView.setPreserveRatio(false);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600); 
    	
		Label crieurBanner = new Label("Le crieur est :" + nomCrieur.getName());
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
	    
	    BorderPane border = new BorderPane();
	    
	    border.setCenter(gLayout);
		
		StackPane root = new StackPane(backgroundView,border);
        
        this.scene = new Scene(root, 800, 600);
    }
    
    /** 
	 * Cette méthode permet de creer la scène du choix de l'ordre des sections des feuilles de jeu
	 *  
	 * @see ModeJeuController#createSceneFeuille
	 * @author Tom FRAISSE
	 */
    private void createSceneFeuille() {
    	
    	Image backgroundImage = new Image(getClass().getResource("/images/menu_background2.png").toExternalForm());
    	
    	ImageView backgroundView = new ImageView(backgroundImage);
    	backgroundView.setPreserveRatio(false);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600); 
    	
    	Label title = new Label(this.nomCrieur.getName() + " choisis l'ordre des sections des feuilles de jeu");
    	title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10;");
		
		
		Button bDeLancer = new Button("Lancer le dé");
		
		Label lDe = new Label("0");
		 lDe.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10;");
		bDeLancer.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		bDeLancer.setOnAction(event -> onLancerDe(event, lDe));
		
		VBox deLayout = new VBox(10, lDe, bDeLancer);
	    deLayout.setAlignment(Pos.CENTER);
		
		Button bDecroissant = new Button("Ordre décroissant");
		bDecroissant.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		bDecroissant.setOnAction(event -> {
			int deValue = Integer.parseInt(lDe.getText());
			if(deValue > 0 && deValue <=6)
				onChoisirOrdre(event, 1,deValue);
			});
		
		Button bCroissant = new Button("Ordre croissant");
		bCroissant.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
		bCroissant.setOnAction(event ->{
			int deValue = Integer.parseInt(lDe.getText());
			if(deValue > 0 && deValue <=6)
				onChoisirOrdre(event, 0,deValue);
			});
		
		HBox bLayout = new HBox(20, bDecroissant, bCroissant);
	    bLayout.setAlignment(Pos.CENTER);

	    VBox vLayout = new VBox(20, title, deLayout, bLayout);
	    vLayout.setAlignment(Pos.CENTER);
	    VBox.setMargin(title, new Insets(10));
	    VBox.setMargin(deLayout, new Insets(10));
	    VBox.setMargin(bLayout, new Insets(10));
		
	    BorderPane border = new BorderPane();
	    
		border.setCenter(vLayout);
		
		StackPane root = new StackPane(backgroundView,border);
        
        this.scene = new Scene(root, 800, 600);
    }
    
    /** 
	 * Cette méthode permet de choisir le mode de jeu de la partie
	 * 
	 * @param		event : evenement
	 * @param 		mode : mode de jeu choisi
	 * 
	 * @see ModeJeuController#onChoisirMode 
	 * @author Tom FRAISSE
	 */
    public void onChoisirMode(ActionEvent event, int mode) {
    	if(mode==0) { // ordonnée
    		this.onChoisirOrdre(event, 2, 1);
    	}
    	else if(mode == 1) { // croissant / décroissant
    		this.createSceneFeuille();
    		this.stage.setScene(this.scene);
    	}else { // aléatoir
    		this.onChoisirOrdre(event, -1, 1);
    	}
    }
    
    /** 
	 * Cette méthode permet de choisir l'ordre des sections des feuilles de jeu
	 * 
	 * @param 		event : evenement
	 * @param 		ordre : odre de classement des sections
	 * @param 		value : valeur du dé tiré
	 *  
	 * @see ModeJeuController#onChoisirOrdre 
	 * @author Tom FRAISSE
	 */
    public void onChoisirOrdre(ActionEvent event, int ordre,int value) {
    	
    	/*
		 *  Altération du modèle
		 */
    	this.gameMode.setIndexFeuille(value, ordre);
    	this.gameMode.initPartie();
    	// Lancer la fenêtre du jeu
    	try {
			this.mainApp.showGameScene();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /** 
	 * Cette méthode permet de lancer un dé et de l'afficher dans la fenetre
	 * 
	 * @param		event : evenement
	 * @param 		de : label contenant la valeu du dé
	 *  
	 * @see ModeJeuController#onLancerDe 
	 * @author Tom FRAISSE
	 */
    public void onLancerDe(ActionEvent event, Label de) {
    	int value = nomCrieur.lancerDe();
    	de.setText(""+value);
    }
    
    /** 
	 * Cette méthode permet d'obtenir la scene du controller
	 *  
	 * @return : Instance de la scene du choix du controller
	 * 
	 * @see ModeJeuController#getScene 
	 * @author Tom FRAISSE
	 */
    public Scene getScene() {
        return this.scene;
    }
}
