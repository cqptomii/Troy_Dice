package plateau;

public class De {
	
	// valeur du dé : 1 - 6
	private int valeur;
	/*
	 * 0 : dé noir
	 * 1 : dé gris
	 * 2 : dé orange
	 * 3 : dé bleu
	*/
	private int couleur;
	
	public De(int valeur,int couleur) {
		this.setValeur(valeur);
		this.setCouleur(couleur);
	}
	
	protected void setColorFromPlace(Place place) {
		if(place != null) {
			this.setCouleur(place.getFaceVisible());
		}
	}
	public int getCouleur() {
		return this.couleur;
	}
	public int getValeur() {
		return this.valeur;		
	}
	protected void setValeur(int val) {
		if(val > 0 && val <= 6) {
			this.valeur = val;
		}
	}
	protected void setCouleur(int couleur) {
		if(this.couleur > 0 && this.couleur < 4) {
			this.couleur = couleur;
		}
	}
	public String toString() {
		String val = new String("Dé ::  Couleur : " + this.couleur + "  Valeur : " + this.valeur);
		return val;
	}
	
}
