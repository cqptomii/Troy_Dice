package core;

import plateau.*;
import feuille.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.ArrayList;

/**
 * Cette classe modélise le gameMode de notre jeu de plateau
 * 
 * @version 1.0
 *
 * @see Simulation
 * @author Tom FRAISSE
 */
public class Simulation {
	private static Simulation instance;
	
	private int nombreJoueurs;
	private int[] indexFeuilles;
	
	private ArrayList<Joueur> joueurs;
	private Crieur crieur;
	
	private Plateau plateauJeu;
	private int[] prixDeChoisi;
	private final IntegerProperty tour;
	private final ObjectProperty<De> deChoisi;
	private final ObjectProperty<Joueur> tourJoueur;
	
	/** 
	 * Cette méthode permet d'instancié le gamemode.
	 * 
	 * @see Simulation#Simulation 
	 * @author Tom FRAISSE
	 */
	public Simulation() {		
		this.tour = new SimpleIntegerProperty(1);
		this.deChoisi = new SimpleObjectProperty<>(null);
		this.tourJoueur = new SimpleObjectProperty<>(null);
		this.nombreJoueurs = 0;
		this.indexFeuilles = null;
		this.plateauJeu = null;
		this.prixDeChoisi = null;
		this.crieur = null;
		this.joueurs = new ArrayList<Joueur>();
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance unique du gameMode
	 * 
	 * @return : instance de la simulation
	 * 
	 * @see Simulation#Simulation 
	 * @author Tom FRAISSE
	 */
	public static synchronized Simulation getInstance() {
		if(instance == null) {
			instance = new Simulation();
		}
		return instance;
	}
	
	/** 
	 * Cette méthode permet de modifier le nombre de joueurs.
	 * 
	 * @param		nbJoueurs : nombre de joueurs
	 * 
	 * @see Simulation#setNombreJoueur 
	 * @author Tom FRAISSE
	 */
	public void setNombreJoueur(int nbJoueurs) {
		if(nbJoueurs >= 2 && nbJoueurs < 7) {
			this.nombreJoueurs = nbJoueurs;
		}
	}
	
	/** 
	 * Cette méthode permet d'obtenir le nombre de joueurs
	 * 
	 * @return : nombre de joueur
	 * 
	 * @see Simulation#getNombreJoueur 
	 * @author Tom FRAISSE
	 */
	public int getNombreJoueur() {
		return this.nombreJoueurs;
	}
	
	/** 
	 * Cette méthode permet d'ajouter un joueur à la simulation
	 * 
	 * @param 		nom : nom du joueur
	 * @param 		gpa : montant du gpa du joueur
	 * 
	 * @see Simulation#addJoueur 
	 * @author Tom FRAISSE
	 */
	public void addJoueur(String nom,int gpa) {
		if(this.joueurs.size() <this.nombreJoueurs) {
			if(nom != null && gpa >0 && gpa <= 100) {
				Joueur temp = new Joueur(nom,gpa);
				this.joueurs.add(temp);
			}
		}
	}	
	
	/** 
	 * Cette méthode permet de choisir le crieur parmi les joueurs.
	 * 
	 * @return : le crieur choisi
	 * 
	 * @see Simulation#choisirCrieur 
	 * @author Tom FRAISSE
	 */
	public Crieur choisirCrieur() {
		if(this.joueurs.size() != 0) {

			Joueur temp = this.joueurs.getFirst();
			for(Joueur j : this.joueurs) {
				if(temp.getGpa() <= j.getGpa()) {
					temp = j;
				}
			}
			
			this.setCrieur(temp);
			return this.crieur;
		}
		return null;
	}
	
	/** 
	 * Cette méthode permet de modifier le crieur
	 * 
	 * @param 		crieur : Joueur à placer en tant que crieur
	 * 
	 * @see Simulation#setCrieur
	 * @author Tom FRAISSE
	 */
	private void setCrieur(Joueur crieur) {
		if(crieur != null) {
			this.crieur = new Crieur(crieur);
		}
	}
	
	/** 
	 * Cette méthode permet de modifier l'index des sections des différentes feuilles de jeu
	 * 
	 * @param		value : valeur du dé
	 * @param 		ordre : ordre de création de l'index
	 * 
	 * @see Simulation#setIndexFeuille 
	 * @author Tom FRAISSE
	 */
	public void setIndexFeuille(int value,int ordre) {
		if(ordre < 0) {
			this.indexFeuilles = this.crieur.choisirIndexGrille(value, ordre);
		}
		else if(value >0 && value <= 6) {
			this.indexFeuilles = this.crieur.choisirIndexGrille(value, ordre);
		}
		System.out.println("erreur" + ordre + value);
		
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'index des sections des différentes feuilles de jeu.
	 * 
	 * @param : Tableau contenant les index des sections
	 * 
	 * @see Simulation#getIndexFeuille 
	 * @author Tom FRAISSE
	 */
	public int[] getIndexFeuille() {
		return this.indexFeuilles;
	}
	
	/** 
	 * Cette méthode permet d'initialiser la partie de jeu.
	 * 
	 * @see Simulation#initPartie 
	 * @author Tom FRAISSE
	 */
	public void initPartie() {
		this.plateauJeu = new Plateau();
		
		for(Joueur j : this.joueurs) {
			FeuilleDeJeu temp = new FeuilleDeJeu(this.indexFeuilles,j);
			j.setFeuille(temp);
		}
		this.plateauJeu.setDemiTour(false);
		this.tourJoueur.set(this.joueurs.getFirst());
		this.tourJoueur.get().updateScore();
		this.plateauJeu.placerDés(this.crieur.lancerDes());
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance du plateau de jeu.
	 * 
	 * @return : Instance du plateau de jeu
	 * 
	 * @see Simulation#getPlateau 
	 * @author Tom FRAISSE
	 */
	public Plateau getPlateau() {
		return this.plateauJeu;
	}
	
	/** 
	 * Cette méthode permet de lancer un demi tour de jeu
	 * 
	 * @see Simulation#lancementDemiTour 
	 * @author Tom FRAISSE
	 */
	public void lancementDemiTour() {
		boolean state = this.plateauJeu.getDemiTour();
		// Mise à jour du tour et du demi-tour
		if(state) {
			  this.plateauJeu.setDemiTour(false);
			  this.plateauJeu.updatePlaceValue();
			  this.tour.set(this.tour.get() + 1);
		}else {
			this.plateauJeu.setDemiTour(true);
		}
		
		//Mise à jour du joueur
		
		this.tourJoueur.set(this.joueurs.getFirst());
		
		//Mise à jour du plateau
		
		this.plateauJeu.supprimerDés();
		this.plateauJeu.placerDés(this.crieur.lancerDes());
		
		//Mise à jour de la feuille de jeu en fonction du dé noir
		if(this.tour.get() >= 3) {
			Place temp = this.plateauJeu.getPlaceDeNoir();
			int color = temp.getFaceVisible();
			int value = this.getSectionPos(temp.getDe().getValeur());
			
			for(Joueur j: this.joueurs) {
				j.getFeuille().detruireSection(color, value);
			}
			
		}
	}
	
	/** 
	 * Cette méthode permet de changer de joueur
	 * 
	 * @see Simulation#changementJoueur 
	 * @author Tom FRAISSE
	 */
	public void changementJoueur(){
		this.deChoisi.set(null);
		
		int tempIndex = joueurs.indexOf(tourJoueur.get()) + 1;

		//retourner la place où il y a le dé noir
		if(tempIndex >= this.joueurs.size()) {
			this.plateauJeu.getPlaceDeNoir().retourner();
			System.out.println("On tourne la place Puis on lance un autre tour ");
			this.lancementDemiTour();
		}
		
		int currentPlayerIndex = ( tempIndex% joueurs.size());
		tourJoueur.set(this.joueurs.get(currentPlayerIndex));
		tourJoueur.get().updateScore();
		
	}
	
	/** 
	 * Cette méthode permet de choisir un dé à partir d'une place.
	 * 
	 * @param		place : Place où le dé doit être récuperé
	 * @param 		ressourceIndex : index du prix de la place
	 * 
	 * @exception NullPointerException : accès à une référence null
	 * 
	 * @see Simulation#choixDé 
	 * @author Tom FRAISSE
	 */
	public void choixDé(Place place, int ressourceIndex) throws NullPointerException{
		if(place != null) {
			
			try {
				this.prixDeChoisi = place.getPrix();
				if(this.tourJoueur.get().canUse(ressourceIndex, this.prixDeChoisi[ressourceIndex])) {
					
					this.deChoisi.set(new De(place.getDe().getValeur(),place.getDe().getCouleur()));
					if(deChoisi.get() == null) {
						return;
					}
					if(this.deChoisi.get().getCouleur() == -1) {
						this.deChoisi.set(null);
						System.out.println("Impossible de choisir le dé noir");
						return;
					}
					//mettre a jour les ressourcces du joueur
					this.tourJoueur.get().utiliserRessource(ressourceIndex , this.prixDeChoisi[ressourceIndex]);
					System.out.println("Choix du dé : " + this.deChoisi + "Prix : " + this.prixDeChoisi[ressourceIndex] );
				}
			}catch (NullPointerException e) {
	    	    System.err.println(e);
	    	}
		}
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance du dé choisi.
	 * 
	 * @return : Instance du dé choisi
	 * 
	 * @see Simulation#getDeChoisi 
	 * @author Tom FRAISSE
	 */
	public De getDeChoisi() {
        return this.deChoisi.get();
    }

	/** 
	 * Cette méthode permet d'obtenir l'instance ObjectProperty sur le dé choisi.
	 * 
	 * @return : Instance vers la propriété observable du dé choisi
	 * 
	 * @see Simulation#deChoisiProperty 
	 * @author Tom FRAISSE
	 */
    public ObjectProperty<De> deChoisiProperty() {
        return this.deChoisi;
    }
	
    /** 
	 * Cette méthode permet de modifier la couleur du dé choisi.
	 * 
	 * @return True : Modfication reussi / False : sinon
	 * @param 		couleur : Couleur du dé après modification
	 * 
	 * @see Simulation#modifierCouleurDe 
	 * @author Tom FRAISSE
	 */
    public boolean modifierCouleurDe(int couleur) {
    	if(this.deChoisi == null) {
			System.out.println("Choisir un dé ");
			return false;
		}else {
			int couleurDe = this.getDeChoisi().getCouleur();
			
			if(couleur != couleurDe) {
				if(this.getTourJoueur().canUse(2,2)) {
					
					this.getTourJoueur().utiliserRessource(2, 2);
					this.getDeChoisi().setCouleur(couleur);
				}
			}
			
			return false;
		}
    }
    
    /** 
	 * Cette méthode permet de modifier la valeur du dé choisi.
	 * 
	 * @return True : Dé modifié / False : sinon
	 * @param 		valeur : Valeur du dé après modification
	 * 
	 * @see Simulation#modifierValeurDe 
	 * @author Tom FRAISSE
	 */
	public boolean modifierValeurDe(int valeur) {
		if(this.deChoisi == null) {
			System.out.println("Choisir un dé ");
			return false;
		}else {
			int valeurDe = this.getDeChoisi().getValeur();
			
			if(valeur != valeurDe) {
				int difference = Math.abs(valeur- valeurDe);
				if(this.getTourJoueur().canUse(0, difference)) {
					
					this.getTourJoueur().utiliserRessource(0, difference);
					this.getDeChoisi().setValeur(valeur);
					return true;
				}
			}
			
			return false;
		}
	}
	
	/** 
	 * Cette méthode permet de faire gagner des ressources au joueur à partir du dé choisi.
	 * 
	 * @see Simulation#Simulation 
	 * @author Tom FRAISSE
	 */
	public void gagnerRessource() {
		if(this.deChoisi.get() != null) {
			int valeur = this.deChoisi.get().getValeur(); // montant de la ressource
			int couleur = this.deChoisi.get().getCouleur(); // type de ressource
			
			//Mise à jour des ressources du joueur actuel
			this.getTourJoueur().ajouterRessource(couleur,valeur);
			System.out.println("Gain de ressource " );
			//Changement de joueur
			this.changementJoueur();
		}
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance du Lien d'un batiment
	 * 
	 * @return : Instance du lien ciblé
	 * @param 		idQuartier : couleur du quartier ciblé
	 * @param 		idSection : index de la section ciblé
	 * @param 		prestige : type du batiment ciblé
	 * 
	 * @see Simulation#getLien 
	 * @author Tom FRAISSE
	 */
	public Lien getLien(int idQuartier, int idSection, boolean prestige) {
		int batIndex = prestige ? 0 : 1;
		return this.getTourJoueur().getFeuille().getQuartier(idQuartier).getSection(idSection).getBatiment(batIndex).getBonusLien();
	}
	
	/** 
	 * Cette méthode permet d'obtenir la position de la section en fonction de son index
	 * 
	 * @return : Position de la section dans le tableau
	 * @param 		sectionNumber : index de la section ciblé
	 * 
	 * @see Simulation#getSectionPos 
	 * @author Tom FRAISSE
	 */
	public int getSectionPos(int sectionNumber) {
		int sectionPos = 0;
		if(this.indexFeuilles != null) {
			for(int j = 0; j < this.indexFeuilles.length; ++j) {
				if(this.indexFeuilles[j] == sectionNumber) {
					sectionPos = j;
				}
			}
		}else {
			System.out.println("null");
		}
		
		return sectionPos;
	}
	
	/** 
	 * Cette méthode permet de construire un batiment d'une section précise à partir du dé choisi.
	 * 
	 * @return True : Batiment construit / False sinon
	 * @param 		idQuartier : couleur du quartier ciblé
	 * @param 		idSection : index de la section ciblé
	 * @param 		prestige : type du batiment ciblé
	 * 
	 * @see Simulation#construireBatiment 
	 * @author Tom FRAISSE
	 */
	public boolean construireBatiment(int idQuartier, int idSection, boolean prestige) {
		if(this.deChoisi.get() == null) {
			return false;
		}
	    int color = this.deChoisi.get().getCouleur(); // couleur du dé
	    int value = this.deChoisi.get().getValeur(); // valeur du dé
	    
	    //Récupération de la position de la section
	    int sectionIndex = this.getSectionPos(value);
	    if (idQuartier == color && sectionIndex == idSection) {
	        int batIndex = prestige ? 0 : 1;

	        Batiment batiment = this.getTourJoueur()
	                .feuille
	                .getQuartier(idQuartier)
	                .getSection(idSection)
	                .getBatiment(batIndex);

	        if (batiment == null || !batiment.isConstructible()) {
	            System.err.println("Le bâtiment ne peut pas être construit.");
	            return false;
	        }
	        
	        
	        System.out.println("Construction du batiment " + batiment.toString());
	        
	        // Construire le bâtiment
	        if (prestige && batiment instanceof Prestige) {
	            Prestige prestigeBat = (Prestige) batiment;
	            int amountDe = this.plateauJeu.getAmoutDe(prestigeBat.getColorDe());
	            prestigeBat.construire(amountDe);
	            int [] recompense = prestigeBat.getRecompenseBonus();
	            for(int i = 0; i < recompense.length; ++i) {
	            	this.getTourJoueur().ajouterRessource(i, recompense[i]);
	            }
	        } else {
	            batiment.construire(0);
	        }
	        
	        System.out.println("Ajout des habitants : " + batiment.getAmountHabitant() + "color : " + batiment.getColorHabitant());
	        this.getTourJoueur().ajouterHabitant(batiment.getColorHabitant(), batiment.getAmountHabitant());
	        
	        
	        // Vérifier et appliquer les effets des liens bonus
	        Lien temp = batiment.getBonusLien();
	        if(temp != null) {
	        	if(temp.lienEtabli()) {
		        	int[] lienRecompense = temp.getRecompense();
		        	if(lienRecompense != null) {
			        	for(int i = 0; i < lienRecompense.length; ++i) {
			            	this.getTourJoueur().ajouterRessource(i, lienRecompense[i]);
			            }
			        }
		        	int colorH = temp.getColorHabitant();
		        	int amount = temp.getHabitant();
		        	System.out.println("Lien :" + temp + " créé " + colorH + " " + amount); 
		        	this.getTourJoueur().ajouterHabitant(colorH, amount);
	        	}
	        }
	        
	        // Passer au joueur suivant
	        this.changementJoueur();

	        return true;
	    }

	    System.out.println("Indexes incorrects du batiment -  Dé  : c " + color + "s " + sectionIndex + " Feuille : c" + idQuartier + " s" + idSection);
	    
	    return false;
	}
	
	/** 
	 * Cette méthode permet d'obtenir le nombre de joueurs de la partie.
	 * 
	 * @return : taille de l'arrayList des joueurs
	 * 
	 * @see Simulation#getNbJoueurs 
	 * @author Tom FRAISSE
	 */
	public int getNbJoueurs() {
		return this.joueurs.size();
	}
	
	/** 
	 * Cette méthode permet d'obtenir le tour de jeu actuel de la partie.
	 * 
	 * @return : tour de jeu de la partie
	 * 
	 * @see Simulation#getTour 
	 * @author Tom FRAISSE
	 */
	public int getTour() {
        return this.tour.get();
    }

	/** 
	 * Cette méthode permet d'otenir l'instance IntegerProperty lié au tour de jeu.
	 * 
	 * @return : Instance vers la propriété observable lié au tour de jeu
	 * 
	 * @see Simulation#tourProperty 
	 * @author Tom FRAISSE
	 */
    public IntegerProperty tourProperty() {
        return this.tour;
    }
    
    /** 
	 * Cette méthode permet d'obtenir le joueur courant qui effectue son tour
	 * 
	 * @return : Joueur qui doit jouer son tour de jeu
	 * 
	 * @see Simulation#getTourJoueur 
	 * @author Tom FRAISSE
	 */
    public Joueur getTourJoueur() {
        return this.tourJoueur.get();
    }
    
    /** 
	 * Cette méthode permet d'obtenir l'ensemble des joueurs de la partie.
	 * 
	 * @return : ArrayList contenant l'ensemble des joueurs
	 * 
	 * @see Simulation#getJoueurs 
	 * @author Tom FRAISSE
	 */
    public ArrayList<Joueur> getJoueurs(){
    	return this.joueurs;
    }
    
    /** 
	 * Cette méthode permet d'otenir l'instance ObjectProperty lié au joueur qui doit jouer son tour.
	 * 
	 * @return : Instance vers la propriété observable lié au joueur qui doit jouer son tour
	 * 
	 * @see Simulation#tourJoueurProperty 
	 * @author Tom FRAISSE
	 */
    public ObjectProperty<Joueur> tourJoueurProperty() {
        return this.tourJoueur;
    }
    
    /** 
	 * Cette méthode permet d'obtenir le gagnant du jeu.
	 * 
	 * @see Simulation#getWinner 
	 * @author Tom FRAISSE
	 */
    public Joueur getWinner() {
    	Joueur winner = this.joueurs.getFirst();
    	for(Joueur w : this.joueurs) {
    		if(w.getScore() > winner.getScore()) {
    			winner = w;
    		}
    	}
    	
    	return winner;
    }
}
