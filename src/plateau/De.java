package plateau;


public class De {
	
	// valeur du dé : 1 - 6
	private int valeur;
	/*
	 * -1 : dé noir
	 * 0 : dé orange
	 * 1 : dé bleu
	 * 2 : dé gris
	*/
	private int couleur;
	
	public De(int valeur,int couleur) {
		this.valeur = 0;
		this.couleur = 0;
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
	public void setValeur(int val) {
		if(val > 0 && val <= 6) {
			this.valeur = val;
		}
	}
	public void setCouleur(int couleur) {
		if(this.couleur >= -1 && this.couleur < 3) {
			this.couleur = couleur;
		}
	}
	public String toString() {
		String val = new String("Dé ::  Couleur : " + this.couleur + "  Valeur : " + this.valeur);
		return val;
	}
}
