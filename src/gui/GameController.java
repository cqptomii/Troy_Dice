package gui;

import javafx.geometry.Insets;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    
    private int tour;
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
        
        
        
        //
        //	Affichage de l'état du jeu
        //
        this.tour = this.gameMode.getTour();
        Label tour = new Label("Semestre numéro: " + this.tour);

	    tour.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	
	    HBox topLeftContainer = new HBox(tour);
	    topLeftContainer.setAlignment(Pos.TOP_LEFT);
	    topLeftContainer.setPadding(new Insets(10, 0, 0, 10));
        
        root.getChildren().add(topLeftContainer);
        //
        // AFFICHAGE DU PLATEAU
        //
        
        Image plateau = new Image(getClass().getResource("/images/"+this.tour+".png").toExternalForm());
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
        
        //
        //  AFFICHAGE DE LA BOITE DE CHOISI
        //
        
        StackPane choisirDe = this.drawToolDe();
        choisirDe.setAlignment(Pos.CENTER);
        
        //
        // AFFICHAGE DE LA FEUILLE DE JEU
        //
        
        Image feuille_vide = new Image(getClass().getResource("/images/feuille_vide.png").toExternalForm());
        ImageView imageViewFeuille = new ImageView(feuille_vide);
        
        imageViewFeuille.setFitWidth(300);
        imageViewFeuille.setFitHeight(200);
        imageViewFeuille.setPreserveRatio(true);
        
        StackPane feuillePane = new StackPane(imageViewFeuille);
        
        
        //Affichage du nom du joueur 
        
        Label nomJoueur = new Label(this.gameMode.getJoueurTour().getName());
        nomJoueur.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    	
	    HBox nomContainer = new HBox(tour);
	    nomContainer.setAlignment(Pos.TOP_LEFT);
	    nomContainer.setPadding(new Insets(10, 0, 0, 10));
	    feuillePane.getChildren().add(nomContainer);
        
        
        
        VBox feuilleBox = new VBox(0);
        HBox indexBox1 = this.drawIndexSection();
        HBox indexBox2 = this.drawIndexSection();
        HBox indexBox3 = this.drawIndexSection();
        GridPane bat1 = this.drawBatimentButton();
        GridPane bat2 = this.drawBatimentButton();
        GridPane bat3 = this.drawBatimentButton();
        
        
        StackPane quartier1Pane = new StackPane();
        
        Image quartier1 = new Image(getClass().getResource("/images/Q1.png").toExternalForm());
        ImageView imageViewQuartier1 = new ImageView(quartier1);
        imageViewQuartier1.setPreserveRatio(true);
        quartier1Pane.getChildren().addAll(imageViewQuartier1,indexBox1,bat1);
        
        
        StackPane quartier2Pane = new StackPane();
        
        Image quartier2 = new Image(getClass().getResource("/images/Q2.png").toExternalForm());
        ImageView imageViewQuartier2 = new ImageView(quartier2);
        imageViewQuartier2.setPreserveRatio(true);
        quartier2Pane.getChildren().addAll(imageViewQuartier2,indexBox2,bat2);
        
        
        StackPane quartier3Pane = new StackPane();
        
        Image quartier3 = new Image(getClass().getResource("/images/Q3.png").toExternalForm());
        ImageView imageViewQuartier3 = new ImageView(quartier3);
        imageViewQuartier3.setPreserveRatio(true);
        quartier3Pane.getChildren().addAll(imageViewQuartier3,indexBox3,bat3);
        
        
        Image pions = new Image(getClass().getResource("/images/pion.png").toExternalForm());
        ImageView imageViewPion = new ImageView(pions);
        imageViewPion.setPreserveRatio(true);
       
        feuilleBox.getChildren().addAll(quartier1Pane,quartier2Pane,quartier3Pane,imageViewPion);
        
        HBox feuilleUiElements = new HBox();
        
        VBox scoreBox = this.drawScore();
        
        
        feuilleUiElements.getChildren().addAll(feuilleBox,scoreBox);
        
        StackPane.setMargin(indexBox1,new Insets(10,0,0,90));
        StackPane.setMargin(indexBox2,new Insets(10,0,0,90));
        StackPane.setMargin(indexBox3,new Insets(10,0,0,90));
        StackPane.setMargin(bat1,new Insets(35,0,0,80));
        StackPane.setMargin(bat2,new Insets(35,0,0,80));
        StackPane.setMargin(bat3,new Insets(35,0,0,80));
        
        
        imageViewFeuille.boundsInLocalProperty().addListener((obs, oldBounds, newBounds) -> {
            double height = newBounds.getHeight();
            
            HBox.setMargin(nomContainer, new Insets(height *0.2,0,0, height * 0.4));
            HBox.setMargin(scoreBox, new Insets(height * 0.4, 0,0,-height * 0.8));
            VBox.setMargin(quartier1Pane, new Insets(height / 3, 0, -7, 30));
            VBox.setMargin(quartier2Pane, new Insets(0, 0, -7, 30));
            VBox.setMargin(quartier3Pane, new Insets(0, 0, -5, 30));
            VBox.setMargin(imageViewPion, new Insets(0, 15, 0, 30));
           
        });
        
        feuillePane.getChildren().add(feuilleUiElements);
        
        uiElements.getChildren().addAll(plateauGroup, choisirDe, feuillePane);
        
        
        root.getChildren().add(uiElements);

        this.scene = new Scene(root, 720*2, 576*2);
       
        backgroundView.fitWidthProperty().bind(scene.widthProperty());
        backgroundView.fitHeightProperty().bind(scene.heightProperty());
        imageViewPlateau.fitWidthProperty().bind(scene.widthProperty().multiply(0.30));
        imageViewPlateau.fitHeightProperty().bind(scene.heightProperty().subtract(20));

        choisirDe.prefWidthProperty().bind(scene.widthProperty().multiply(0.15));
        choisirDe.prefHeightProperty().bind(scene.heightProperty().multiply(0.3));

        imageViewFeuille.fitWidthProperty().bind(scene.widthProperty().multiply(0.40));
        imageViewFeuille.fitHeightProperty().bind(scene.heightProperty().subtract(20));
        
        imageViewQuartier1.fitWidthProperty().bind(imageViewFeuille.fitWidthProperty().multiply(0.85));
        imageViewQuartier2.fitWidthProperty().bind(imageViewFeuille.fitWidthProperty().multiply(0.85));
        imageViewQuartier3.fitWidthProperty().bind(imageViewFeuille.fitWidthProperty().multiply(0.85));
        imageViewPion.fitWidthProperty().bind(imageViewFeuille.fitWidthProperty().multiply(0.78));
        
        
        this.primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double screenWidth = primaryStage.getWidth();
            primaryStage.setX((screenWidth - newWidth.doubleValue()) / 2);
        });

        this.primaryStage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            double screenHeight = primaryStage.getHeight();
            primaryStage.setY((screenHeight - newHeight.doubleValue()) / 2);
        });
    }

    public Scene getScene() {
    	return this.scene;
    }
    
    private HBox drawIndexSection() {
    	HBox indexBox = new HBox(33);
    	int[] index = this.gameMode.getIndexFeuille();
    	if(index != null) {
    		for(int i : index) {
    			Label sectionLabel = new Label(String.valueOf(i));
                sectionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
                
                sectionLabel.setPrefSize(25, 25);
                sectionLabel.setAlignment(Pos.CENTER);
                sectionLabel.setStyle("-fx-border-color: transparent; -fx-background-color: transparent; -fx-font-size: 14px;");
                
                // Ajouter le label au HBox
                indexBox.getChildren().add(sectionLabel);
    		}
    	}
    	return indexBox;
    }
    private GridPane drawBatimentButton() {
    	GridPane boutonPane = new GridPane();
    	boutonPane.setPadding(new Insets(10, 9, 10, 9));
    	boutonPane.setVgap(5);
    	boutonPane.setHgap(27);
    	
    	Button btn1 = new Button("1");
        Button btn2 = new Button("2");
        Button btn3 = new Button("3");
        Button btn4 = new Button("4");
        Button btn5 = new Button("5");
        Button btn6 = new Button("6");
        Button btn7 = new Button("7");
        Button btn8 = new Button("8");
        Button btn9 = new Button("9");
        Button btn10 = new Button("10");
        Button btn11 = new Button("11");
        Button btn12 = new Button("12");
        
        btn1.setPrefSize(30, 30);
        btn2.setPrefSize(30, 30);
        btn3.setPrefSize(30, 30);
        btn4.setPrefSize(30, 30);
        btn5.setPrefSize(30, 30);
        btn6.setPrefSize(30, 30);
        btn7.setPrefSize(30, 30);
        btn8.setPrefSize(30, 30);
        btn9.setPrefSize(30, 30);
        btn10.setPrefSize(30, 30);
        btn11.setPrefSize(30, 30);
        btn12.setPrefSize(30, 30);
        
        boutonPane.add(btn1, 0, 0);  // Colonne 0, Ligne 0
        boutonPane.add(btn2, 1, 0);  // Colonne 1, Ligne 0
        boutonPane.add(btn3, 2, 0);  // Colonne 2, Ligne 0
        boutonPane.add(btn4, 3, 0);  // Colonne 3, Ligne 0
        boutonPane.add(btn5, 4, 0);  // Colonne 4, Ligne 0
        boutonPane.add(btn6, 5, 0);  // Colonne 5, Ligne 0
        
        boutonPane.add(btn7, 0, 1);  // Colonne 0, Ligne 1
        boutonPane.add(btn8, 1, 1);  // Colonne 1, Ligne 1
        boutonPane.add(btn9, 2, 1);  // Colonne 2, Ligne 1
        boutonPane.add(btn10, 3, 1); // Colonne 3, Ligne 1
        boutonPane.add(btn11, 4, 1); // Colonne 4, Ligne 1
        boutonPane.add(btn12, 5, 1); // Colonne 5, Ligne 1
        
        return boutonPane;
    }
    private VBox drawScore() {
    	VBox scoreBox = new VBox(50);
    	
    	for(int i = 1; i < 2; ++i) {
	    	Label scorePrestige = new Label(String.valueOf(0));
	    	Label scoreTravail = new Label(String.valueOf(0));
	    	
	    	Label total = new Label(String.valueOf(0));
	    	
	    	scoreBox.getChildren().addAll(scorePrestige,scoreTravail,total);
    	}
    	
    	
    	HBox temp = new HBox(20);
    	
    	Label scorePion = new Label(String.valueOf(0));
    	Label scoreQuartier = new Label(String.valueOf(0));
    	
    	temp.getChildren().addAll(scorePion,scoreQuartier);
    	
    	Label scoreFinal = new Label(String.valueOf(0));
    	scoreBox.getChildren().addAll(temp,scoreFinal);
    	
    	return scoreBox;
    }
    private StackPane drawToolDe() {
    	VBox temp = new VBox(10);
    	temp.setAlignment(Pos.CENTER);
    	
    	StackPane stackPane = new StackPane();
    	
    	Rectangle backgroundRectangle = new Rectangle();
        backgroundRectangle.setWidth(200);
        backgroundRectangle.setHeight(120);
        backgroundRectangle.setArcWidth(10);
        backgroundRectangle.setArcHeight(10);
        backgroundRectangle.setFill(Color.WHITE);
        backgroundRectangle.setOpacity(0.7);


        temp.setStyle("-fx-background-size: cover;" +
                      "-fx-background-radius: 10;" +
                      "-fx-padding: 10;" +
                      "-fx-min-width: 200; -fx-max-width: 200;"); 
        
        stackPane.getChildren().add(backgroundRectangle);

        De dechoisi = this.gameMode.getDeChoisi();
        Label title = new Label("Dé choisi :");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
      
        if(dechoisi == null) {
	        Label status = new Label("Aucun dé sélectionné actuellement.");
	        status.setWrapText(true);
	        status.setStyle("-fx-font-size: 14px;");
	
	
	        temp.getChildren().addAll(title, status);
        }else {
        	
        	int value = dechoisi.getValeur();
        	int color = dechoisi.getCouleur();
        	
        	Label colorLabel = new Label("Modifier la couleur :");
        	colorLabel.setWrapText(true);
        	colorLabel.setStyle("-fx-font-size: 14px;");
        	HBox colorBox = new HBox(5);
        	for(int i = 1; i < 4;++i) {
        		Button colorButton = new Button();
        		colorButton.setOnAction(event ->{
            		System.out.println("Couleur");
            	});
        		colorBox.getChildren().add(colorButton);
        	}
        	Label valueLabel = new Label("Modifier la valeur :");
        	valueLabel.setWrapText(true);
        	valueLabel.setStyle("-fx-font-size: 14px;");
        	
        	HBox valueBox = new HBox(5);
        	for(int i = 1; i < 7;++i) {
        		if(i == value) {
        			continue;
        		}
        		Button valueButton = new Button();
        		valueButton.setOnAction(event ->{
            		System.out.println("Valeur");
            	});
        		valueBox.getChildren().add(valueButton);
        	}
        	
        	Button valider = new Button("Valider");
        	Button reset = new Button("Reset");
        	
        	
        	valider.setOnAction(event ->{
        		System.out.println("Valider");
        	});
        	reset.setOnAction(event ->{
        		System.out.println("Reset");
        	});
        	temp.getChildren().addAll(title, colorLabel,colorBox,valueLabel,valueBox,valider,reset);
        }
        
        stackPane.getChildren().add(temp);

        return stackPane;
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
