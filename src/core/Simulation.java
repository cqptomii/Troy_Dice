package core;

import plateau.*;
import feuille.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;

public class Simulation {
	private static Simulation instance;
	
	private int modeJeu;
	private int nombreJoueurs;
	private int[] indexFeuilles;
	
	private ArrayList<Joueur> joueurs;
	private Crieur crieur;
	
	private Plateau plateauJeu;
	private int[] prixDeChoisi;
	private final IntegerProperty tour;
	private final ObjectProperty<De> deChoisi;
	private final ObjectProperty<Joueur> tourJoueur;
	
	public Simulation() {		
		this.tour = new SimpleIntegerProperty(1);
		this.deChoisi = new SimpleObjectProperty<>(null);
		this.tourJoueur = new SimpleObjectProperty<>(null);
		this.modeJeu = 0;
		this.nombreJoueurs = 0;
		this.indexFeuilles = null;
		this.plateauJeu = null;
		this.prixDeChoisi = null;
		this.crieur = null;
		this.joueurs = new ArrayList<Joueur>();
	}
	public static synchronized Simulation getInstance() {
		if(instance == null) {
			instance = new Simulation();
		}
		return instance;
	}
	public void setNombreJoueur(int nbJoueurs) {
		if(nbJoueurs >= 2 && nbJoueurs < 7) {
			this.nombreJoueurs = nbJoueurs;
		}
	}
	public int getNombreJoueur() {
		return this.nombreJoueurs;
	}
	public void addJoueur(String nom,int gpa) {
		if(this.joueurs.size() <this.nombreJoueurs) {
			if(nom != null && gpa >0 && gpa <= 100) {
				Joueur temp = new Joueur(nom,gpa);
				this.joueurs.add(temp);
			}
		}
	}	
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
	private void setCrieur(Joueur crieur) {
		if(crieur != null) {
			this.crieur = new Crieur(crieur);
		}
	}
	public void setModeJeu(int mode) {
		if(mode == 0 || mode == 1) {
			this.modeJeu = mode;
		}
	}
	public void setIndexFeuille(int value,int ordre) {
		if(ordre < 0) {
			this.indexFeuilles = this.crieur.choisirIndexGrille(value, ordre);
		}
		else if(value >0 && value <= 6) {
			this.indexFeuilles = this.crieur.choisirIndexGrille(value, ordre);
		}
	}
	public int[] getIndexFeuille() {
		return this.indexFeuilles;
	}
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
	public Plateau getPlateau() {
		return this.plateauJeu;
	}
	
	public void lancementDemiTour() {
		boolean state = this.plateauJeu.getDemiTour();
		if(state) {
			  this.plateauJeu.setDemiTour(false);
			  this.tour.set(this.tour.get() + 1);
		}else {
			this.plateauJeu.setDemiTour(true);
		}
		this.tourJoueur.set(this.joueurs.getFirst());
		this.plateauJeu.placerDés(this.crieur.lancerDes());
		
		if(this.tour.get() >= 3) {
			Place temp = this.plateauJeu.getPlaceDeNoir();
			
			int color = temp.getFaceVisible();
			int value = temp.getDe().getValeur();
			
			for(Joueur j: this.joueurs) {
				j.getFeuille().detruireSection(color, value);
			}
		}
	}
	
	public void changementJoueur(){
		this.deChoisi.set(null);
		int tempIndex = joueurs.indexOf(tourJoueur.get()) + 1;
		
		//retourner la place où il y a le dé noir
		if(tempIndex >= this.joueurs.size()) {
			this.plateauJeu.getPlaceDeNoir().retourner();
		}
		
		int currentPlayerIndex = ( tempIndex% joueurs.size());
		tourJoueur.set(this.joueurs.get(currentPlayerIndex));
		tourJoueur.get().updateScore();
	}
	public void choixDé(Place place) {
		if(place != null) {
			this.prixDeChoisi = place.getPrix();
			if(this.tourJoueur.get().canUse(0, this.prixDeChoisi[0])) {
				this.deChoisi.set(place.getDe());
				if(this.deChoisi.get().getCouleur() == 0) {
					this.deChoisi.set(null);
					System.out.println("Impossible de choisir le dé noir");
					return;
				}
				//mettre a jour les ressourcces du joueur
				this.tourJoueur.get().utiliserRessource(1 , this.prixDeChoisi[0]);
				System.out.println("Choix du dé : " + this.deChoisi);
			}
		}
	}
	public De getDeChoisi() {
        return this.deChoisi.get();
    }

    public ObjectProperty<De> deChoisiProperty() {
        return this.deChoisi;
    }
	
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
	
	public void gagnerRessource(int type) {
		if(this.deChoisi != null) {
			int valeur = this.deChoisi.get().getValeur(); // montant de la ressource
			int couleur = this.deChoisi.get().getCouleur(); // type de ressource
			
			//Mise à jour des ressources du joueur actuel
			this.getTourJoueur().ajouterRessource(couleur,valeur);
			
			//Changement de joueur
			this.changementJoueur();
		}
	}
	
	public boolean construireBatiment(int idQuartier, int idSection, boolean prestige) {
	    int color = this.deChoisi.get().getCouleur(); // couleur du dé
	    int value = this.deChoisi.get().getValeur(); // valeur du dé

	    if (idQuartier == color && value == idSection) {
	        int batIndex = prestige ? 0 : 1;

	        Batiment batiment = this.getTourJoueur()
	                .feuille
	                .getQuartier(idQuartier)
	                .getSection(value)
	                .getBatiment(batIndex);

	        if (batiment == null || !batiment.isConstructible()) {
	            System.err.println("Le bâtiment ne peut pas être construit.");
	            return false;
	        }

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
	        
	        this.getTourJoueur().ajouterHabitant(batiment.getColorHabitant(), batiment.getAmountHabitant());
	        
	        
	        // Vérifier et appliquer les effets des liens bonus
	        Lien temp = batiment.getBonusLien();
	        if(temp != null) {
	        	int[] lienRecompense = temp.getRecompense();
	        	if(lienRecompense != null) {
		        	for(int i = 0; i < lienRecompense.length; ++i) {
		            	this.getTourJoueur().ajouterRessource(i, lienRecompense[i]);
		            }
		        }
	        	int colorH = temp.getColorHabitant();
	        	int amount = temp.getHabitant();
	        	this.getTourJoueur().ajouterHabitant(colorH, amount);
	        }

	        // Passer au joueur suivant
	        this.changementJoueur();

	        return true; // Construction réussie
	    }

	    return false; // Construction invalide
	}
	public int getNbJoueurs() {
		return this.joueurs.size();
	}
	public int getTour() {
        return this.tour.get();
    }

    public IntegerProperty tourProperty() {
        return this.tour;
    }
    
    public Joueur getTourJoueur() {
        return this.tourJoueur.get();
    }

    public ObjectProperty<Joueur> tourJoueurProperty() {
        return this.tourJoueur;
    }
}
