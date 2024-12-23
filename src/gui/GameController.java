package gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import plateau.De;
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
        StackPane root = new StackPane();
        
        Image backgroundImage = new Image(getClass().getResource("/images/game_background.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);

        root.getChildren().add(backgroundView);

        HBox uiElements = new HBox(20);
        uiElements.setAlignment(Pos.CENTER);

        // Affichage du plateau
        Image plateau = new Image(getClass().getResource("/images/1.png").toExternalForm());
        ImageView imageViewPlateau = new ImageView(plateau);
        imageViewPlateau.setPreserveRatio(true);

        
        //Affichage des places et des dés
        Group plateauGroup = new Group();
        plateauGroup.getChildren().add(imageViewPlateau);

        Place[] places = this.gameMode.getPlateau().getPlace();
        
        int nombreImages = 9;
        if (places.length < nombreImages) {
            System.out.println("Erreur : pas assez de places disponibles !");
            return;
        }
        
        imageViewPlateau.boundsInParentProperty().addListener((obs, oldBounds, newBounds) -> {
        	
            double displayedWidth = newBounds.getWidth();
            double displayedHeight = newBounds.getHeight();
            double rayon = Math.min(displayedWidth, displayedHeight) / 2;
            double centerX = newBounds.getMinX() + displayedWidth / 2;
            double centerY = newBounds.getMinY() + displayedHeight / 2;

            plateauGroup.getChildren().clear();
            plateauGroup.getChildren().add(imageViewPlateau);

            for (int i = 0; i < nombreImages; i++) {
                double angle = Math.toRadians(90) + Math.toRadians(40) * i;
                double imageX = centerX + (rayon + 25) * Math.cos(angle) - 50;
                double imageY = centerY + (rayon + 25) * Math.sin(angle) - 55;

                Place p = places[i];
                Group placeGroup = new Group();

                Button place = this.drawPlace(p, imageX, imageY);
                ImageView de = this.drawDe(p, imageX, imageY);

                if (place != null) placeGroup.getChildren().add(place);
                if (de != null) placeGroup.getChildren().add(de);

                plateauGroup.getChildren().add(placeGroup);
            }
        });

        VBox choisirDe = this.drawToolDe();

        Image feuille = new Image(getClass().getResource("/images/feuille.png").toExternalForm());
        ImageView imageViewFeuille = new ImageView(feuille);
        
        imageViewFeuille.setFitWidth(300);
        imageViewFeuille.setFitHeight(200);
        imageViewFeuille.setPreserveRatio(true);

        
        uiElements.getChildren().addAll(plateauGroup, choisirDe, imageViewFeuille);

        root.getChildren().add(uiElements);

        this.scene = new Scene(root, 1280, 800);

        backgroundView.fitWidthProperty().bind(scene.widthProperty());
        backgroundView.fitHeightProperty().bind(scene.heightProperty());
        imageViewPlateau.fitWidthProperty().bind(scene.widthProperty().multiply(0.30));
        imageViewPlateau.fitHeightProperty().bind(scene.heightProperty().subtract(20));

        choisirDe.prefWidthProperty().bind(scene.widthProperty().multiply(0.15));
        choisirDe.prefHeightProperty().bind(scene.heightProperty().multiply(0.3));

        imageViewFeuille.fitWidthProperty().bind(scene.widthProperty().multiply(0.40));
        imageViewFeuille.fitHeightProperty().bind(scene.heightProperty().subtract(20));
    }

    public Scene getScene() {
    	return this.scene;
    }
    private VBox drawToolDe() {
    	VBox temp = new VBox(5);
    	De de = this.gameMode.getDeChoisi();
    	if(de == null) {
    		Label title = new Label("Aucun dé choisi pour l'instant");
    		
    		temp.getChildren().addAll(title);
    	}
    	
    	return temp;
    	
    }
    private Button drawPlace(Place p, double x, double y) {
        Button placeButton = new Button();
        
        placeButton.setPrefSize(100, 100);
        placeButton.setMaxSize(100, 100);
        placeButton.setMinSize(100, 100);
        
        updateButtonBackground(placeButton, p);
        
        placeButton.setLayoutX(x);
        placeButton.setLayoutY(y);

        placeButton.setOnAction(e -> {
            System.out.println("Bouton cliqué");
        });

        return placeButton;
    }

    private void updateButtonBackground(Button placeButton, Place p) {
        String imageUrl;
        
        int face = p.getFaceVisible();
        
        if (face == 1) {
            imageUrl = "/images/gris.png";
        } else if (face == 2) {
            imageUrl = "/images/orange.png";
        } else {
            imageUrl = "/images/bleu.png";
        }
        
        placeButton.setStyle("-fx-background-image: url('" + getClass().getResource(imageUrl).toExternalForm() + "');" +
                             "-fx-background-size: cover;" + 
                             "-fx-background-radius: 50;" +
                             "-fx-min-width: 100;" + 
                             "-fx-min-height: 100;");
    }
    
    private ImageView drawDe(Place p,double x,double y) {
    	if(p.getDe() == null) {
    		System.out.println("Aucun dé pour la place : " + p);
    		return null;
    	}
    	int color = p.getDe().getCouleur();
    	int valeur = p.getDe().getValeur();
    	ImageView de;
    	
    	try {
	    	if(color == 0) {
	    		String src = "n" + valeur + ".png";
	    		Image deNoir = new Image(getClass().getResource("/images/" + src).toExternalForm());
	    		de = new ImageView(deNoir);
	    	}else {
	    		String src = "t" + valeur + ".png";
	    		Image deTransparent = new Image(getClass().getResource("/images/" + src).toExternalForm());
	    		de = new ImageView(deTransparent);
	    	}
	    	
	    	de.setFitWidth(50);
	    	de.setFitHeight(50);
	    	de.setPreserveRatio(true);
	    	
	    	de.setX(x);
	    	de.setY(y);
	    	return de;
    	}catch(Exception e) {
    		System.err.println("Erreur lors du chargement de l'image pour le dé (couleur: " + color + ", valeur: " + valeur + ")");
            e.printStackTrace();
            return null;
    	}
    	
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
