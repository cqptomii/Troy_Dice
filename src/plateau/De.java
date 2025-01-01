package plateau;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Cette classe modélise un dé de jeu 
 * 
 * @version 1.0
 *
 * @see De
 * @author Tom FRAISSE
 */
public class De {
	
	private IntegerProperty valeur; /* valeur du dé : 1 - 6 */
	/*
	 * -1 : dé noir
	 * 0 : dé orange
	 * 1 : dé bleu
	 * 2 : dé gris
	*/
	private IntegerProperty couleur;
	
	/** 
	 * Cette méthode permet de construire un dé avec une couleur et une valeur précise.
	 *  
	 * @param        valeur : Valeur du dé lors de sa création 
	 * @param       couleur : Couleur du dé lors de sa création 
	 * 
	 * @see De#De 
	 * @author Tom FRAISSE
	 */
	public De(int valeur,int couleur) {
		this.valeur = new SimpleIntegerProperty(0);
		this.couleur = new SimpleIntegerProperty(0);
		this.setValeur(valeur);
		this.setCouleur(couleur);
	}
	
	/** 
	 * Cette méthode permet de mettre à jour la couleur du dé en fonction d'une Place
	 * Cela permet de modéliser le comportement des dés transparents
	 *  
	 * @param       place : place sur laquelle le dé est positionner 
	 * 
	 * @see De#setColorFromPlace
	 * @author   Tom FRAISSE 
	 */
	protected void setColorFromPlace(Place place) {
		if(place != null) {
			this.setCouleur(place.getFaceVisible());
		}
	}
	/** 
	 * Cette méthode permet d'obtenir la couleur du dé 
	 *  
	 * @return      couleur du dé
	 * 
	 * @see De#getCouleur 
	 * @author   Tom FRAISSE 
	 */
	public int getCouleur() {
		return this.couleur.get();
	}
	/** 
	 * Cette méthode permet d'obtenir la valeur du dé
	 * 
	 * @return      valeur du dé
	 * 
	 * @see De#getValeur 
	 * @author   Tom FRAISSE
	 */
	public int getValeur() {
		return this.valeur.get();		
	}
	/** 
	 * Cette méthode permet de fixer la valeur du dé
	 *  
	 * @param       val : valeur du dé modifié 
	 * 
	 * @see De#setValeur 
	 * @author   Tom FRAISSE 
	 */
	public void setValeur(int val) {
		if(val > 0 && val <= 6) {
			this.valeur.set(val);
		}
	}
	/** 
	 * Cette méthode permet de fixer la couleur du dé
	 *   
	 * @param       couleur : couleur du dé modifié 
	 * 
	 * @see De#setCouleur 
	 * @author   Tom FRAISSE 
	 */
	public void setCouleur(int couleur) {
		if(this.couleur.get() >= -1 && this.couleur.get() < 3) {
			this.couleur.set(couleur);
		}
	}
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty sur la valeur du dé
	 * Cette méthode est utilisé afin de mettre à jour l'interface graphique dynamiquement
	 *  
	 * @return      Instance de la propriété observable sur la valeur
	 * 
	 * @see De#valeurProperty 
	 * @author   Tom FRAISSE 
	 */
	public IntegerProperty valeurProperty() {
		return this.valeur;
	}
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty sur la couleur du dé
	 * Cela permeet de mettre à jour l'interface graphique dynamiquement face à la cette propriété 
	 *  
	 * @return      Instance de la propriété observable 
	 * @param       arg1 description du 1er argument 
	 * 
	 * @see De#couleurProperty 
	 * @author   Tom FRAISSE 
	 */
	public IntegerProperty couleurProperty() {
		return this.couleur;
	}
	/** 
	 * Cette méthode permet d'obtenir une chaine de caractère qui contient les différentes propriétés de l'objet 
	 * 
	 * @return      Chaine de caractère  
	 * 
	 * @see De#toString
	 * @author   Tom FRAISSE 
	 */
	public String toString() {
		String val = new String("Dé ::  Couleur : " + this.couleur + "  Valeur : " + this.valeur);
		return val;
	}
}
