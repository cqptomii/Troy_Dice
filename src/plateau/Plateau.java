package plateau;

import java.util.Arrays;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.ArrayList;

/**
 * Cette classe modélise le plateau de jeu 
 * 
 * @version 1.0
 *
 * @see Plateau
 * @author Tom FRAISSE
 */
public class Plateau {
	
	private BooleanProperty demiTour; /* Variable d'état du demi tour  */
	
	private int[] indexPlace;
	
	private Place[] places;
	
	private ArrayList<Integer[]>listePrixPlace;
	
	/** 
	 * Cette méthode permet de construire le plateau de jeu
	 *   
	 * 
	 * @see Plateau#Plateau 
	 * @author Tom FRAISSE
	 */
	public Plateau() {
		this.places = new Place[9];
		this.indexPlace = new int[9];
		this.demiTour = new SimpleBooleanProperty(false) ;
		
		this.listePrixPlace = new ArrayList<Integer[]>();
		this.initListePrix();
		
		for(int i = 0; i < 9 ;++i) {
			this.places[i] = new Place();
			this.indexPlace[i] = i;
			this.places[i].setPrix(this.listePrixPlace.get(i));
		}
	}
	
	/** 
	 * Cette méthode permet d'initialiser les prix des différentes place du plateau.
	 *
	 * @see Plateau#initListePrix 
	 * @author Tom FRAISSE
	 */
	private void  initListePrix() {
		Integer [][] tempTab = {
							{0,0,0},
							{0,0,0},
							{1,1,1},
							{0,1,0},
							{0,2,0},
							{0,0,0},
							{1,1,1},
							{0,1,0},
							{0,2,0}
					};
		for(int i = 0; i < tempTab.length; ++i) {
			this.listePrixPlace.addLast(tempTab[i]);
		}
	}
	
	/** 
	 * Cette méthode permet de mettre à jour les places du plateau de jeu
	 *   
	 * @exception IndexOutOfBoundsException  Accès invalide sur le tableau de place 
	 * @exception NullPointerException Rencontre d'une référence null
	 * 
	 * @see Plateau#updatePlaceValue 
	 * @author Tom FRAISSE
	 */
	public void updatePlaceValue() throws IndexOutOfBoundsException,NullPointerException {
		try {
			for(int i = 0; i < 9; ++i) {
				int tempIndex = (8+this.indexPlace[i])%9;
				
				this.places[i].setPrix(this.listePrixPlace.get(tempIndex));
				
				this.indexPlace[i] = tempIndex;
	
				this.places[i].supprimerDé();
			}
		}catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites dans updatePlaceValue : " + e.getMessage());
	    } catch (NullPointerException e) {
	        System.err.println("Erreur : Une référence null a été rencontrée dans updatePlaceValue : " + e.getMessage());
	    }
	}
	
	/** 
	 * Cette méthode permet de placer les 4 dé sur le plateau de jeu
	 * Les dés seront placés selon l'état du tour de jeu
	 *   
	 * @exception IndexOutOfBoundsException  Accès invalide sur le tableau de place 
	 * @exception NullPointerException Rencontre d'une référence null
	 * 
	 * @see Plateau#placerDés 
	 * @author Tom FRAISSE
	 */
	public void placerDés(De [] valeurDes) throws IndexOutOfBoundsException,NullPointerException{
		try{
			if(valeurDes.length == 4) {
				int index = -1;
				
				for(int i = 0; i < 9; ++i) {
					if(this.indexPlace[i] == 0) {
						index = i;
					}
				}
				
				if (index == -1) {
	                throw new IllegalStateException("Aucune case libre trouvée pour placer les dés.");
	            }
				
				if(this.demiTour.get()) { // seconde parties de tour
					index += 5;
				}else { // première partie de tour
					index++;
				}
				
				Arrays.sort(valeurDes, (de1,de2) -> Integer.compare(de1.getValeur(), de2.getValeur()));
				for(int i = 0; i < 4 ; ++i) {
					int targetIndex = (index + i) % this.places.length;
					this.places[targetIndex].placerDé(valeurDes[i]);
				}
			}
		} catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    } catch (NullPointerException e) {
	        System.err.println("Erreur : Une référence null a été rencontrée lors du placement des dés : " + e.getMessage());
	    }
	}
	
	/** 
	 * Cette méthode permet de supprimer les dés de toutes les places du plateau
	 *   
	 * 
	 * @see Place#Place 
	 * @author Tom FRAISSE
	 */
	public void supprimerDés() {
		for(Place p : this.places) {
			p.supprimerDé();
		}
	}
	
	/** 
	 * Cette méthode permet de construire une place qui ne contient pas de dé.
	 * La couleur des deux faces seront definis avec la bibliothèque Random
	 * 
	 * @see Plateau#setDemiTour 
	 * @author Tom FRAISSE
	 */
	public void setDemiTour(boolean valeur) {
		this.demiTour.set(valeur);
	}
	/** 
	 * Cette méthode permet d'obtenir le tableau des places du plateau
	 * 
	 * @return Tableau des places de jeu
	 *  
	 * @see Plateau#getPlace 
	 * @author Tom FRAISSE
	 */
	public Place[] getPlace() {
		return this.places;
	}
	
	/** 
	 * Cette méthode permet d'obtenir la place contenant le dé noir
	 * 
	 * @return Place contenant le dé noir
	 * 
	 * @see Plateau#getPlaceDeNoir 
	 * @author Tom FRAISSE
	 */
	public Place getPlaceDeNoir() {
		for(Place p : this.places) {
			if(p.getDe() == null ){
				continue;
			}
			if(p.getDe().getCouleur() == -1) {
				return p;
			}
		}
		return null;
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'état du tour de jeu
	 *   
	 * @return état du tour de jeu
	 * 
	 * @see Plateau#getDemiTour
	 * @author Tom FRAISSE
	 */
	public boolean getDemiTour() {
		return this.demiTour.get();
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance BooleanProperty de l'état du tour de jeu
	 * 
	 * @return Instance BooleanProperty du tour de jeu
	 * 
	 * @see Plateau#PdemiTourProperty 
	 * @author Tom FRAISSE
	 */
	public BooleanProperty demiTourProperty() {
		return this.demiTour;
	}
	
	/** 
	 * Cette méthode permet d'obtenir le nombre de dé d'une couleur présente sur la plateau de jeu
	 *   
	 * @return nombre de dé de la couleur associé
	 * 
	 * @see Plateau#getAmountDe 
	 * @author Tom FRAISSE
	 */
	public int getAmoutDe(int color) {
		int amount = 0;
		for( Place d:this.places) {
			if(d.getDe() == null) {
				continue;
			}else {
				if(d.getFaceVisible() == color) {
					amount++;
				}
			}
		}
		System.out.println("Nombre de dé : " + color + "= " + amount);
		return amount;
	}
	
	/** 
	 * Cette méthode permet d'obtenir une chaine de caractère contenant les informations des différents places du plateau
	 *   
	 * @return Chaine de caractère
	 * 
	 * @see Plateau#toString 
	 * @author Tom FRAISSE
	 */
	public String toString() {
		for(int i = 0; i < 9; i++) {
			System.out.println(this.places[i].toString());
		}
		return null;
	}
}
