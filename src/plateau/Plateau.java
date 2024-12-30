package plateau;
import java.util.Arrays;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
public class Plateau {
	private BooleanProperty demiTour;
	
	private int[] indexPlace;
	
	private Place[] places;
	
	private ArrayList<Integer[]>listePrixPlace;
		
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
	
	public void updatePlaceValue() {
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
	
	public void placerDés(De [] valeurDes){
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
	public void supprimerDés() {
		for(Place p : this.places) {
			p.supprimerDé();
		}
	}
	public void setDemiTour(boolean valeur) {
		this.demiTour.set(valeur);
	}
	public Place[] getPlace() {
		return this.places;
	}
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
	public boolean getDemiTour() {
		return this.demiTour.get();
	}
	public BooleanProperty demiTourProperty() {
		return this.demiTour;
	}
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
	public String toString() {
		for(int i = 0; i < 9; i++) {
			System.out.println(this.places[i].toString());
		}
		return null;
	}
}
