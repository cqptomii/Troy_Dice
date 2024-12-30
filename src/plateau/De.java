package plateau;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class De {
	
	// valeur du dé : 1 - 6
	private IntegerProperty valeur;
	/*
	 * -1 : dé noir
	 * 0 : dé orange
	 * 1 : dé bleu
	 * 2 : dé gris
	*/
	private IntegerProperty couleur;
	
	public De(int valeur,int couleur) {
		this.valeur = new SimpleIntegerProperty(0);
		this.couleur = new SimpleIntegerProperty(0);
		this.setValeur(valeur);
		this.setCouleur(couleur);
	}
	
	protected void setColorFromPlace(Place place) {
		if(place != null) {
			this.setCouleur(place.getFaceVisible());
		}
	}
	public int getCouleur() {
		return this.couleur.get();
	}
	public int getValeur() {
		return this.valeur.get();		
	}
	public void setValeur(int val) {
		if(val > 0 && val <= 6) {
			this.valeur.set(val);
		}
	}
	public void setCouleur(int couleur) {
		if(this.couleur.get() >= -1 && this.couleur.get() < 3) {
			this.couleur.set(couleur);
		}
	}
	public IntegerProperty valeurProperty() {
		return this.valeur;
	}
	public IntegerProperty couleurProperty() {
		return this.couleur;
	}
	public String toString() {
		String val = new String("Dé ::  Couleur : " + this.couleur + "  Valeur : " + this.valeur);
		return val;
	}
}
