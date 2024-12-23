package gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import plateau.Place;


import core.*;

public class GameController {

    private Interface mainApp;
    private Stage primaryStage;
    private Scene scene;
    private Simulation gameMode;
    private Crieur crieur;
    public GameController(Interface app,Stage primaryStage,Crieur crieur) {
        this.mainApp = app;
        this.primaryStage = primaryStage;
        this.gameMode = Simulation.getInstance();
        this.crieur = crieur;
        createScene();
    }
    private void createScene() {
    	Label gameLabel = new Label("Le jeu commence");
    	
        HBox root = new HBox();
		root.setAlignment(Pos.CENTER);
		
		//Affichage du plateau
		Image plateau = new Image("/images/1.png");
        ImageView imageViewPlateau = new ImageView(plateau);

        imageViewPlateau.setFitWidth(300); 
        imageViewPlateau.setFitHeight(200); 
        imageViewPlateau.setPreserveRatio(true);
        
        Group plateauGroup = new Group();
        plateauGroup.getChildren().add(imageViewPlateau);	
        
        //Affichage des places et des dés sur le plateau
        
        Place[] places = this.gameMode.getPlateau().getPlace();
        
        for( Place p : places) {
        	
        	Group placeGroup = new Group();
        	
        	ImageView place = this.drawPlace(p);
        	ImageView de = this.drawDe(p);
        	
        	placeGroup.getChildren().addAll(place,de);
        	plateauGroup.getChildren().add(placeGroup);
        }
        
        // Affichage de la feuille de jeu
        Image feuille = new Image("/images/feuille.png");

        ImageView imageViewFeuille = new ImageView(feuille);

        imageViewFeuille.setFitWidth(300); 
        imageViewFeuille.setFitHeight(200); 
        imageViewFeuille.setPreserveRatio(true);
        
        root.getChildren().addAll(imageViewPlateau,imageViewFeuille);
		
		this.scene = new Scene(root,400,400);		
    }
    public Scene getScene() {
    	return this.scene;
    }
    private ImageView drawPlace(Place p) {
    	ImageView place;
    	int face = p.getFaceVisible();
    	if(face == 1) {
    		//Ajouter la place grise comme descendant du groupe
    		Image placeGrise = new Image("/images/gris.png");
    		place = new ImageView(placeGrise);
    	}else if(face==2) {
    		//Ajouter la place orange comme descendant du groupe
    		Image placeOrange = new Image("/images/orange.png");
    		place = new ImageView(placeOrange);
    	}else{
    		//Ajouter la place bleu comme descendant du groupe
    		Image placeBleu = new Image("/images/bleu.png");
    		place = new ImageView(placeBleu);
    	}
    	
    	place.setFitWidth(100);
    	place.setFitHeight(100);
    	place.setPreserveRatio(true);

    	place.setX(250);
    	place.setY(300);
    	
    	return place;
    }
    
    private ImageView drawDe(Place p) {
    	if(p.getDe() == null)
    		return null;
    	int color = p.getDe().getCouleur();
    	int valeur = p.getDe().getValeur();
    	ImageView de;
    	if(color == 0) {
    		String src = "n" + valeur + ".png";
    		Image deNoir = new Image("/images/" + src);
    		de = new ImageView(deNoir);
    	}else {
    		String src = "t" + valeur + ".png";
    		Image deTransparent = new Image("/images/" + src);
    		de = new ImageView(deTransparent);
    	}
    	
    	return de;
    }
    private Scene createNombreJoueursScene(Stage primaryStage) {
    	
    	
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Demande du nombre de joueurs ici");
        alert.showAndWait();

        // Créez et retournez la scène nombreJoueurs comme dans votre code d'origine
        return null;  // Retournez la scène appropriée ici
    }
}
