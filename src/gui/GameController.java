package gui;

import javafx.application.Platform;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
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
import javafx.scene.layout.Region;
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
import feuille.FeuilleDeJeu;
import feuille.Quartier;


public class GameController {

    private Interface mainApp;
    private Stage primaryStage;
    private Scene scene;
    private Simulation gameMode;
    private Crieur crieur;
    
    
    private VBox choisirDe;
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

        HBox uiElements = new HBox(10);
        uiElements.setAlignment(Pos.CENTER);
        
        
        
        //
        //	Affichage de l'état du jeu
        //
        Label tour = new Label();
	    tour.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    
	    tour.textProperty().bind(this.gameMode.tourProperty().asString("Année numéro: %d"));
	    Label demiTour = new Label(" - Automne");
	    demiTour.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    
	    this.gameMode.getPlateau().demiTourProperty().addListener((obs,lastValue,newValue) -> {
	    	if(newValue) {
	    		demiTour.setText(" - Printemps");
	    	}else {
	    		demiTour .setText(" - Automne");
	    	}
	    });
	    
	    HBox topLeftContainer = new HBox(tour,demiTour);
	    topLeftContainer.setAlignment(Pos.TOP_LEFT);
	    topLeftContainer.setPadding(new Insets(10, 0, 0, 10));
        
        root.getChildren().add(topLeftContainer);
        
        
        //
        // AFFICHAGE DU PLATEAU
        //
        Image plateau = new Image(getClass().getResource("/images/" + this.gameMode.getTour() + ".png").toExternalForm());
        ImageView imageViewPlateau = new ImageView(plateau);
        imageViewPlateau.setPreserveRatio(true);
          
        this.gameMode.tourProperty().addListener((obs, oldTour, newTour) -> {
        	Platform.runLater(() -> {
                System.out.println(newTour.intValue() + " tour ");
                String imagePath = getClass().getResource("/images/" + newTour.intValue() + ".png").toExternalForm();
           
                if (imagePath == null) {
                    System.err.println("Image introuvable : " + imagePath);
                    return;
                }
                Image temp = new Image(imagePath);
                imageViewPlateau.setImage(temp);
                
                imageViewPlateau.getParent().layout();
            });
        });
        
        //Affichage des places et des dés
        Group plateauGroup = new Group();
        
        Place[] places = this.gameMode.getPlateau().getPlace();
        
        int nombreImages = 9;
        if (places.length < nombreImages) {
            System.out.println("Erreur : pas assez de places disponibles !");
            return;
        }
        
        imageViewPlateau.boundsInParentProperty().addListener((obs, oldBounds, newBounds) -> {
            if (newBounds != null) {
                updatePlateauGroup(newBounds, plateauGroup, imageViewPlateau, places, nombreImages);
            }
        });
        this.gameMode.getPlateau().demiTourProperty().addListener((obs, oldPlayer, newPlayer) -> {
        	Platform.runLater(() -> {
                Bounds plateauBounds = imageViewPlateau.getLayoutBounds();
                updatePlateauGroup(plateauBounds, plateauGroup, imageViewPlateau, places, nombreImages);
            });
        });
        plateauGroup.getChildren().add(imageViewPlateau); 
        
        //
        //  AFFICHAGE DE LA BOITE DE CHOIW DE Dé
        //
        
        choisirDe = this.drawToolDe();
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
        Label nomJoueur = new Label(this.gameMode.getTourJoueur().getName());
        nomJoueur.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        feuillePane.getChildren().add(nomJoueur);
        
        VBox feuilleBox = this.drawFeuille(imageViewFeuille);
        
        this.gameMode.tourJoueurProperty().addListener((obs, oldJoueur, newJoueur) -> {
        	Platform.runLater(() -> {
	            System.out.println("Changement de joueur : ancien = " + (oldJoueur != null ? oldJoueur.getName() : "null")
	                + ", nouveau = " + (newJoueur != null ? newJoueur.getName() : "null"));
	
	            if (newJoueur != null) {
	            	// Modification nom de joueur
	                nomJoueur.setText(newJoueur.getName());
	                
	                // Rafraichissment de la feuille de jeu 
	                VBox newFeuilleBox = this.drawFeuille(imageViewFeuille);
	                feuillePane.getChildren().setAll(imageViewFeuille, newFeuilleBox,nomJoueur);
	                
	                // Placement des box dans la fenêtre
	                
	                Bounds  newBounds = imageViewFeuille.getLayoutBounds();
	                double height = newBounds.getHeight();
	                
	                StackPane.setMargin(newFeuilleBox, new Insets(height /6 + 20,0,0,20));
	            } else {
	                nomJoueur.setText("Aucun joueur");
	            }
        	});
        });
        
	    feuillePane.getChildren().add(feuilleBox);
	    
        uiElements.getChildren().addAll(plateauGroup, choisirDe, feuillePane);
        
        
        root.getChildren().add(uiElements);
        
        this.scene = new Scene(root, 720*2, 576*2);
        
        
        //
        //	Mise à jour dynamique
        //
        
        imageViewFeuille.boundsInLocalProperty().addListener((obs, oldBounds, newBounds) -> {
            double height = newBounds.getHeight();
            
            StackPane.setMargin(feuilleBox, new Insets(height /6 + 20,0,0,20));
            StackPane.setMargin(nomJoueur, new Insets(-height*0.6 - 20,0,0,+height*0.05));
        });
       
        backgroundView.fitWidthProperty().bind(scene.widthProperty());
        backgroundView.fitHeightProperty().bind(scene.heightProperty());
        
        imageViewPlateau.fitWidthProperty().bind(scene.widthProperty().multiply(0.30));
        imageViewPlateau.fitHeightProperty().bind(scene.heightProperty().subtract(10));

        choisirDe.prefWidthProperty().bind(scene.widthProperty().multiply(0.15));
        choisirDe.prefHeightProperty().bind(scene.heightProperty().multiply(0.3));

        imageViewFeuille.fitWidthProperty().bind(scene.widthProperty().multiply(0.5));
        imageViewFeuille.fitHeightProperty().bind(scene.heightProperty());
        
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
    private VBox drawToolDe() {
    	VBox temp = new VBox(10);
    	temp.setAlignment(Pos.CENTER);
    	
    	StackPane stackPane = new StackPane();
    	
    	Rectangle backgroundRectangle = new Rectangle();
        backgroundRectangle.setArcWidth(10);
        backgroundRectangle.setArcHeight(10);
        backgroundRectangle.setFill(Color.WHITE);
        backgroundRectangle.setOpacity(0.7);


        temp.setStyle("-fx-background-size: cover;" +
                      "-fx-background-radius: 10;" +
                      "-fx-padding: 10;" +
                      "-fx-min-width: 200; -fx-max-width: 200;"); 
        stackPane.getChildren().add(backgroundRectangle);
        
        // GAIN de ressource
        
        Button gainButton = new Button("Gagner les ressources");
        
        gainButton.setOnAction(event ->{        	
        	this.gameMode.gagnerRessource();
        });
        
        temp.getChildren().add(gainButton); 
        
        VBox modificationContainer = new VBox(10);
        modificationContainer.setAlignment(Pos.CENTER);

        Label title = new Label("Dé choisi :");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
      
        modificationContainer.getChildren().add(title);

        Label status = new Label();
        status.setWrapText(true);
        status.setStyle("-fx-font-size: 14px;");
        modificationContainer.getChildren().add(status);
        
        temp.getChildren().add(modificationContainer);
        this.gameMode.deChoisiProperty().addListener((obs, oldDe, newDe) -> {
            modificationContainer.getChildren().clear();
            modificationContainer.getChildren().add(title);
            modificationContainer.getChildren().add(status);
            
            if (newDe == null) {
                status.setText("Aucun dé sélectionné actuellement.");
            } else {
            	String [] colorArray = {"Orange","Bleu","Gris"};
            	int initialValue = newDe.getValeur();
                int initialColor = newDe.getCouleur();

                final int[] tempColor = {initialColor};
                final int[] tempValue = {initialValue};
                if(tempColor[0] == -1) {
                	System.out.println("Dé noir");
                	return;
                }
                status.setText(String.format("Dé sélectionné : Valeur = %d, Couleur = %s", tempValue[0],colorArray[tempColor[0]]));
            
                
                // Section pour modifier la couleur
                Label colorLabel = new Label("Modifier la couleur :");
                colorLabel.setStyle("-fx-font-size: 14px;");
                HBox colorBox = new HBox(5);
                colorBox.setAlignment(Pos.CENTER);
                
                for (int i = 0; i < 3; ++i) {
                    if (i != initialColor) {
                        Button colorButton = new Button(colorArray[i]);
                        final int currentColor = i;
                        colorButton.setOnAction(event -> {
                        	tempColor[0] = currentColor;
                            System.out.println("Couleur modifiée : " + currentColor);
                            // Logique pour modifier la couleur du dé
                        });
                        colorBox.getChildren().add(colorButton);
                    }
                }

                // Section pour modifier la valeur
                Label valueLabel = new Label("Modifier la valeur :");
                valueLabel.setStyle("-fx-font-size: 14px;");
                HBox valueBox = new HBox(5);
                valueBox.setAlignment(Pos.CENTER);
                
                for (int i = 1; i <= 6; ++i) {
                    if (i != initialValue) {
                        Button valueButton = new Button(String.valueOf(i));
                        final int currentValue = i;
                        valueButton.setOnAction(event -> {
                        	tempValue[0] = currentValue;
                            System.out.println("Valeur modifiée : " + currentValue);
                        });
                        valueBox.getChildren().add(valueButton);
                    }
                }

                Button valider = new Button("Valider");
                valider.setOnAction(event -> {
                	if(initialColor != tempColor[0]) {
                		this.gameMode.modifierCouleurDe(tempColor[0]);
                	}
                	if(initialValue != tempValue[0]) {
                		this.gameMode.modifierValeurDe(tempValue[0]);
                	}
                    System.out.println("Modifications validées.");
                });

                Button reset = new Button("Reset");
                reset.setOnAction(event -> {
                	tempColor[0] = initialColor;
                	tempValue[0]  = initialValue;
                    System.out.println("Modifications réinitialisées.");
                });

                modificationContainer.getChildren().addAll(colorLabel, colorBox, valueLabel, valueBox, valider, reset);
            }
        });
        backgroundRectangle.widthProperty().bind(modificationContainer.widthProperty());
        backgroundRectangle.heightProperty().bind(modificationContainer.heightProperty());
        stackPane.getChildren().add(modificationContainer);
        temp.getChildren().add(stackPane);

        return temp;
    }
    private void updatePlateauGroup(Bounds bounds, Group plateauGroup, ImageView imageViewPlateau, Place[] places, int nombreImages) {
        double displayedWidth = bounds.getWidth();
        double displayedHeight = bounds.getHeight();
        double rayon = Math.min(displayedWidth, displayedHeight) / 2;
        double centerX = bounds.getMinX() + displayedWidth / 2;
        double centerY = bounds.getMinY() + displayedHeight / 2;

        plateauGroup.getChildren().clear();
        plateauGroup.getChildren().add(imageViewPlateau);

        for (int i = 0; i < nombreImages; i++) {
            double angle = Math.toRadians(90) + Math.toRadians(40) * i;
            double imageX = centerX + (rayon + 25) * Math.cos(angle) - 50;
            double imageY = centerY + (rayon + 25) * Math.sin(angle) - 55;

            Place p = places[i];
            Group placeGroup = new Group();

            Button place = this.drawPlace(p, imageX, imageY);
            ImageView de = this.drawDe(p.getDe(), imageX, imageY);
            if (place != null) {
                placeGroup.getChildren().add(place);
            }
            if (de != null) {
                placeGroup.getChildren().add(de);
            }

            plateauGroup.getChildren().add(placeGroup);
        }
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
        	if(this.gameMode.getDeChoisi() == null) {
        		this.gameMode.choixDé(p);
        	}
        });

        return placeButton;
    }
    private void updateButtonBackground(Button placeButton, Place p) {
        String imageUrl;
        
        int face = p.getFaceVisible();
        
        if (face == 2) {
            imageUrl = "/images/gris.png";
        } else if (face == 0) {
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
    private ImageView drawDe(De p, double x, double y) {
        if (p == null) {
            return null;
        }

        ImageView de = new ImageView(loadDeImage(p));
        de.setFitWidth(50);
        de.setFitHeight(50);
        de.setPreserveRatio(true);

        de.setX(x + 25);
        de.setY(y + 25);

        return de;
    }
    private Image loadDeImage(De p) {
        int color = p.getCouleur();
        int valeur = p.getValeur();
        String src;

        try {
            if (color == -1) {
                src = "n" + valeur + ".png";
            } else {
                src = "t" + valeur + ".png";
            }
            return new Image(getClass().getResource("/images/" + src).toExternalForm());
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image pour le dé (couleur: " + color + ", valeur: " + valeur + ")");
            e.printStackTrace();
            return null;
        }
    }
    
    private VBox drawFeuille(ImageView imageViewFeuille) {
    	HBox quartierBox1 = this.drawQuartier(0);
        HBox quartierBox2 = this.drawQuartier(1);
        HBox quartierBox3 = this.drawQuartier(2);
        
        HBox pionBox = drawPionBox(imageViewFeuille);
        
        VBox feuilleBox = new VBox(-70,quartierBox1,quartierBox2,quartierBox3,pionBox);
        feuilleBox.setPadding(new Insets(30,0,0,0));
        return feuilleBox;
    }
    private HBox drawRessource(int type){
    	HBox ressourceBox = new HBox(20);
    	
    	Label ressourceLabel = new Label();
    	ressourceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    	String imageUrl;
    	ImageView imageViewRessource = new ImageView();
    	
    	if (type == 0) {
            imageUrl = "/images/xp.PNG";
            imageViewRessource.setFitWidth(20);
            imageViewRessource.setFitHeight(20);
        } 
    	else if (type == 1) {
            imageUrl = "/images/ects1.png";
            imageViewRessource.setFitWidth(20);
            imageViewRessource.setFitHeight(20);
        } 
    	else {
            imageUrl = "/images/connaissance.png";
            imageViewRessource.setFitWidth(15);
            imageViewRessource.setFitHeight(20);
        }
        
    	ressourceLabel.textProperty().bind(
    	        this.gameMode.getTourJoueur().ressourceProperty(type).asString("x %d")
    	);
    	
    	Image imageRessource = new Image(getClass().getResource(imageUrl).toExternalForm());
    	imageViewRessource.setImage(imageRessource);
    	ressourceBox.getChildren().addAll(imageViewRessource,ressourceLabel);
    	
    	return ressourceBox;
    }
    private HBox drawQuartier(int color) {
        HBox quartierBox = new HBox(0);
        quartierBox.setPrefHeight(50);
        
        HBox topBox = createTopBox(color, quartierBox);

        StackPane bottomBox = createBottomBox(color, quartierBox);

        VBox centerBox = new VBox(0, topBox, bottomBox);

        VBox scoreBox = createScoreBox(color, quartierBox);

        quartierBox.getChildren().addAll(centerBox,scoreBox);  
        bottomBox.prefWidthProperty().bind(quartierBox.prefWidthProperty());
        return quartierBox;
    }
    private HBox createTopBox(int color, HBox parentBox) {
        HBox topBox = new HBox(0);

        // Image statique
        Image imageStaticPart = new Image(getClass().getResource("/images/S" + color + ".png").toExternalForm());
        ImageView imageViewStaticPart = new ImageView(imageStaticPart);
        imageViewStaticPart.setFitHeight(130);
        imageViewStaticPart.setPreserveRatio(true);
        
        // Boutons de bâtiments
        GridPane batBox = drawBatimentButton(color);

        topBox.getChildren().addAll(imageViewStaticPart, batBox);
        return topBox;
    }
    private GridPane drawBatimentButton(int color) {
    	GridPane boutonPane = new GridPane();
    	boutonPane.setPadding(new Insets(0,0,2,-2));
    	boutonPane.setVgap(-0.5);
    	boutonPane.setHgap(-0.5);
    	// Affichage des entêtes de section
    	int[] index = this.gameMode.getIndexFeuille();
    	if (index != null) {
    	    for (int i = 0; i < 5; ++i) {
    	        try {
    	            Image imageIndex = new Image(getClass().getResource("/images/I" + color + ".png").toExternalForm());
    	            ImageView imageViewIndex = new ImageView(imageIndex);
    	            imageViewIndex.setFitHeight(52);
    	            imageViewIndex.setPreserveRatio(true);
    	            Label numberLabel = new Label(String.valueOf(index[i]));
    	            numberLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: bLack; -fx-font-weight: bold;");

    	            StackPane stackPane = new StackPane();
    	            stackPane.getChildren().addAll(imageViewIndex, numberLabel);

    	            StackPane.setAlignment(numberLabel, Pos.CENTER); // Centrer le texte
    	            boutonPane.add(stackPane, i, 0);
    	        } catch (NullPointerException e) {
    	            System.err.println("Image not found: /images/I" + color + ".png");
    	        }
    	    }
    	}
        
        try {
        	Image imageIndexBorder = new Image(getClass().getResource("/images/ID" + color + ".png").toExternalForm());
        	ImageView imageViewIndexBorder = new ImageView(imageIndexBorder);
        	imageViewIndexBorder.setFitHeight(52);
        	imageViewIndexBorder.setPreserveRatio(true);
        	
        	Label numberLabel = new Label(String.valueOf(index[5]));
            numberLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: bLack; -fx-font-weight: bold;");

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(imageViewIndexBorder, numberLabel);

            StackPane.setAlignment(numberLabel, Pos.CENTER); // Centrer le texte
            stackPane.prefHeightProperty().bind(boutonPane.widthProperty().divide(6));
            stackPane.prefHeightProperty().bind(boutonPane.heightProperty().divide(3));
            
            boutonPane.add(stackPane, 5, 0);
    	} catch (NullPointerException e) {
    	    System.err.println("/images/ID" + color + ".png");
    	}
        
        for (int i = 0; i < 6; ++i) {
            Button batPrestige = new Button();
            final int currentPos = i;
            batPrestige.setOnAction(event -> {
                this.gameMode.construireBatiment(color, currentPos, true);
            });
            batPrestige.setStyle("-fx-background-image: url('" + getClass().getResource("/images/P" + color + (i + 1) + ".png").toExternalForm() + "');" +
                                 "-fx-background-size: cover;");
            batPrestige.setPrefWidth(69.1);
            batPrestige.setPrefHeight(40);
            
//            StackPane stackPane = new StackPane(batPrestige);
            
//            if(this.gameMode.getLien(color, i, true) != null) {
//        		Image imageLienEtabli = new Image(getClass().getResource("/images/check.png").toExternalForm());
//        		ImageView imageViewLienEtabli = new ImageView(imageLienEtabli);
//        		imageViewLienEtabli.setFitWidth(15);
//        		imageViewLienEtabli.setPreserveRatio(true);
//        		// Afficher le lien sur l'image du bouton
//        		stackPane.getChildren().add(imageViewLienEtabli);
//        	}
            
           boutonPane.add(batPrestige, i, 1);
        }

        for (int i = 0; i < 6; ++i) {
            Button batTravail = new Button();
            final int currentPos = i;

            batTravail.setOnAction(event -> {
                this.gameMode.construireBatiment(color, currentPos, false);
            });

            batTravail.setStyle("-fx-background-image: url('" + getClass().getResource("/images/BT" + color + (i + 1) + ".png").toExternalForm() + "');" +
                                "-fx-background-size: cover;");
            batTravail.setPrefWidth(69.1);
            batTravail.setPrefHeight(40);
            
//            StackPane stackPane = new StackPane(batTravail);
//            
//            if(this.gameMode.getLien(color, i, false) != null) {
//        		Image imageLienEtabli = new Image(getClass().getResource("/images/check.png").toExternalForm());
//        		ImageView imageViewLienEtabli = new ImageView(imageLienEtabli);
//        		imageViewLienEtabli.setFitWidth(15);
//        		imageViewLienEtabli.setPreserveRatio(true);
//        		// Afficher le lien sur l'image du bouton
//        		stackPane.getChildren().add(imageViewLienEtabli);
//        	}
            
            boutonPane.add(batTravail, i, 2);
        }
        
        return boutonPane;
    }
    private StackPane createBottomBox(int color, HBox parentBox) {
        StackPane bottomBox = new StackPane();

        // Image de ressources
        Image imageRessource = new Image(getClass().getResource("/images/RQ" + color + ".png").toExternalForm());
        ImageView imageViewResource = new ImageView(imageRessource);
        imageViewResource.setFitHeight(28);
        imageViewResource.setPreserveRatio(true);
        
        // Affichage des ressources
        HBox affichageRessource = drawRessource(color);

        bottomBox.getChildren().addAll(imageViewResource, affichageRessource);
        StackPane.setMargin(imageViewResource, new Insets(-10, 0, 0, -2));
        StackPane.setMargin(affichageRessource, new Insets(5,0,0,90));
        return bottomBox;
    }
    private VBox createScoreBox(int color, HBox parentBox) {
        VBox scoreBox = new VBox(0);

        FeuilleDeJeu tempFeuille = this.gameMode.getTourJoueur().getFeuille();
        Quartier tempQuartier = tempFeuille.getQuartier(color);
        
        Image imageScorePVide  = new Image(getClass().getResource("/images/VIDE.png").toExternalForm());
    	ImageView imageViewScorePVide = new ImageView(imageScorePVide);
    	imageViewScorePVide.setFitHeight(58);
    	imageViewScorePVide.setPreserveRatio(true);
    	imageViewScorePVide.setVisible(false);
    	

    	updateScorePanes(scoreBox, tempFeuille, tempQuartier, color, imageViewScorePVide);

        tempFeuille.multOwnedProperty().addListener((obs, oldValue, newValue) -> 
            updateScorePanes(scoreBox, tempFeuille, tempQuartier, color, imageViewScorePVide)
        );

        VBox.setMargin(scoreBox.getChildren().get(2), new Insets(-5, 0, 0, -10));
        VBox.setMargin(scoreBox.getChildren().get(3), new Insets(0, 0, 0, -10));
        scoreBox.setPadding(new Insets(0, 0, 0, -2));

        return scoreBox;
    }
    private void updateScorePanes(VBox scoreBox, FeuilleDeJeu tempFeuille, Quartier tempQuartier, int color, ImageView imageViewScorePVide) {
        int scoreMultiplicateur = tempFeuille.getMultOwned();

        StackPane prestigePane = createScorePane(
            "/images/P" + color + ".png",
            String.format("%d", scoreMultiplicateur),
            tempQuartier.scorePrestigeProperty().asString("%d")
        );

        StackPane travailPane = createScorePane(
            "/images/ST" + color + ".png",
            String.format("%d", scoreMultiplicateur),
            tempQuartier.scoreTravailProperty().asString("%d")
        );

        StackPane ressourcePane = createScorePaneRessource(
            "/images/R" + color + ".png",
            tempQuartier.scoreRessourceProperty().asString("%d")
        );

        // Mettre à jour les enfants du VBox
        scoreBox.getChildren().setAll(imageViewScorePVide, prestigePane, travailPane, ressourcePane);
    }
    private StackPane createScorePaneRessource(String imagePath, StringExpression  scoreTextProperty) {
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(90);
        imageView.setPreserveRatio(true);
        
        Label scoreLabel = new Label();
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        scoreLabel.textProperty().bind(scoreTextProperty);

        StackPane scorePane = new StackPane(imageView, scoreLabel);
        
        StackPane.setAlignment(scoreLabel, Pos.CENTER);
        StackPane.setMargin(scoreLabel, new Insets(-45,0,0,-10));
        StackPane.setMargin(imageView, new Insets(-3,0,0,-45));
        return scorePane;
    }
    private StackPane createScorePane(String imagePath, String multiplierText, StringExpression  scoreTextProperty) {
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(45);
        imageView.setPreserveRatio(true);
        
        Label multiplierLabel = new Label(multiplierText);
        multiplierLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label scoreLabel = new Label();
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        scoreLabel.textProperty().bind(scoreTextProperty);

        HBox scoreDisplay = new HBox(15, multiplierLabel, scoreLabel);
        StackPane scorePane = new StackPane(imageView, scoreDisplay);
        
        StackPane.setMargin(scoreDisplay, new Insets(8,0,0,30));
        return scorePane;
    }
    private HBox drawPionBox(ImageView imageViewFeuille) {
    	Image imageB1 = new Image(getClass().getResource("/images/B1.png").toExternalForm());
        ImageView imageViewB1 = new ImageView(imageB1);
        imageViewB1.setFitHeight(80);
        imageViewB1.setPreserveRatio(true);
        
        Image imageB2 = new Image(getClass().getResource("/images/B2.png").toExternalForm());
        ImageView imageViewB2 = new ImageView(imageB2);
        imageViewB2.setFitHeight(80);
        imageViewB2.setPreserveRatio(true);
        GridPane checkHabitant = this.drawHabitant();
        
        StackPane habitantPane = new StackPane(imageViewB2,checkHabitant);
        
        for(int i = 0; i < 3;++i) {
        	this.gameMode.getTourJoueur().habitantProperty(i).addListener((obs,lastValue,newValue) ->{
        		updateHabitant(checkHabitant);
        	});
        }
        
        FeuilleDeJeu tempFeuille = this.gameMode.getTourJoueur().getFeuille();
        
        Label scorePionLabel = new Label();
        scorePionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        scorePionLabel.textProperty().bind(tempFeuille.scoreHabitantProperty().asString("%d"));
        
        Label scoreFeuilleLabel = new Label();
        scoreFeuilleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        scoreFeuilleLabel.textProperty().bind(tempFeuille.scoreFeuilleProperty().asString("%d"));
        
        Image imageB3 = new Image(getClass().getResource("/images/B3.png").toExternalForm());
        ImageView imageViewB3 = new ImageView(imageB3);
        imageViewB3.setFitHeight(30);
        imageViewB3.setPreserveRatio(true);
        
        HBox scoreDisplay = new HBox(15, scorePionLabel, scoreFeuilleLabel);
        StackPane pionPane = new StackPane(imageViewB3,scoreDisplay); 
        
        
        Label scoreTotalLabel = new Label();
        scoreTotalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        scoreTotalLabel.textProperty().bind(this.gameMode.getTourJoueur().scoreProperty().asString("%d"));
        
        Image imageB4 = new Image(getClass().getResource("/images/B4.png").toExternalForm());
        ImageView imageViewB4 = new ImageView(imageB4);
        imageViewB4.setFitHeight(50);
        imageViewB4.setPreserveRatio(true);
        
        StackPane TotalPane = new StackPane(imageViewB4,scoreTotalLabel);
        
        VBox temp = new VBox(0,pionPane,TotalPane);
        
        StackPane.setMargin(imageViewB3, new Insets(30,0,0,-11));
        StackPane.setMargin(scoreDisplay, new Insets(30,0,0,20));
        StackPane.setMargin(scoreTotalLabel, new Insets(5,0,0,3));
        
        VBox.setMargin(imageViewB4, new Insets(0,0,0,18));
        HBox bottomBox = new HBox(10,imageViewB1,habitantPane,temp);
        return bottomBox;
    }
    private GridPane drawHabitant() {
    	GridPane habitantPane = new GridPane();
    	habitantPane.setPadding(new Insets(38,0,0,8));
    	habitantPane.setVgap(9);
    	habitantPane.setHgap(6);
    	
    	// Couleurs différentes
    	for(int lignes = 0; lignes < 3; ++lignes) {
    		
	    	for(int colonne = 0; colonne < 4; ++colonne) {
		    	for(int i = 0 ; i < 5;++i) {
		    		Image image = new Image(getClass().getResource("/images/croix.png").toExternalForm());
		    		ImageView imageView = new ImageView(image);
		    		imageView.setPreserveRatio(true);
		    		imageView.setFitWidth(10);
		    		habitantPane.add(imageView,colonne*5+i,lignes);
		    		if(i==4) {
		    			if(colonne == 2)
		    				GridPane.setMargin(imageView,new Insets(0,22,0,0));
		    			else
		    				GridPane.setMargin(imageView,new Insets(0,10,0,0));
		    		}
		    		habitantPane.setVisible(false);
		    	}
	    	}
    	}
    	return habitantPane;
    }
    private GridPane updateHabitant(GridPane pane) {
    	GridPane tempPane = pane;
    	
    	for(int lignes = 0; lignes < 3; ++lignes) {
    		int amount = this.gameMode.getTourJoueur().habitantProperty(lignes).get();
    		
    		for (int i = 0; i < tempPane.getChildren().size(); i++) {
                Node node = tempPane.getChildren().get(i);

                if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == lignes) {
                    node.setVisible(i < amount);
                }
            }
    	
    	}
    	return tempPane;
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
