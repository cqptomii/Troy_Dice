package plateau;
import java.util.Arrays;
import java.util.ArrayList;
public class Plateau {
	private boolean demiTour;
	
	private int[] indexPlace;
	
	private Place[] places;
	
	private ArrayList<Integer[]>listePrixPlace;
		
	public Plateau() {
		this.places = new Place[9];
		this.indexPlace = new int[9];
		this.demiTour = false;
		
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
							{1,0,0},
							{2,0,0},
							{0,0,0},
							{1,1,1},
							{1,0,0},
							{2,0,0}
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
				int index = 0;
				for(int i = 0; i < 9; ++i) {
					if(this.indexPlace[i] == 0) {
						index = i;
					}
				}
				if(this.demiTour) { // seconde parties de tour
					index += 5;
				}else { // première partie de tour
					index++;
				}
				
				Arrays.sort(valeurDes, (de1,de2) -> Integer.compare(de1.getValeur(), de2.getValeur()));
				for(int i = 0; i < 4 ; ++i) {
					this.places[(index+i) % 8].placerDé(valeurDes[i]);
				}
			}
		} catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    } catch (NullPointerException e) {
	        System.err.println("Erreur : Une référence null a été rencontrée lors du placement des dés : " + e.getMessage());
	    }
	}
	public void setDemiTour(boolean valeur) {
		this.demiTour = valeur;
	}
	public Place[] getPlace() {
		return this.places;
	}
	public Place getPlaceDeNoir() {
		return null;
	}
	public String toString() {
		for(int i = 0; i < 9; i++) {
			System.out.println(this.places[i].toString());
		}
		return null;
	}
}
