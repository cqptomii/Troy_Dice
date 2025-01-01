package plateau;
import java.util.Random;
/**
 * Cette classe modélise une place du plateau de jeu 
 * 
 * @version 1.0
 *
 * @see Place
 * @author Tom FRAISSE
 */
public class Place {
	
	/* COULEUR DES DEUX FACES
	 * 0 : orange
	 * 1 : bleu
	 * 2 : gris
	*/
	private int faceVisible; /* couleur de la face visible */
	private int faceCachée; /* couleurde la face cachée */
	
	/*	Tableau de trois entiers
	 * 1 - coût Deniers
	 * 2 - coût connaissance
	 * 3 - coût experience
	*/
	private int[] prix;
	private De gainsDe = null;
	
	/** 
	 * Cette méthode permet de construire une place qui ne contient pas de dé.
	 * La couleur des deux faces seront definis avec la bibliothèque Random
	 *   
	 * 
	 * @see Place#Place 
	 * @author Tom FRAISSE
	 */
	public Place() {
		Random randomEngine = new Random();
		
		this.faceVisible = randomEngine.nextInt(0, 3);
		this.faceCachée = randomEngine.nextInt(0, 3);
		this.prix = new int[3];
	}
	/** 
	 * Cette méthode permet de placer un dé sur la face
	 *  
	 * @param        valeur : Valeur du dé à placer 
	 * 
	 * @see Place#placerDé 
	 * @author Tom FRAISSE
	 */
	public void placerDé(De valeur) {
		if(valeur == null)
			return;
		
		if(this.gainsDe == null) {
			this.gainsDe = valeur;
			if(this.gainsDe.getCouleur() != -1)
				this.gainsDe.setColorFromPlace(this);
		}
	}
	/** 
	 * Cette méthode permet de supprimer le dé de la place
	 *  
	 * 
	 * @see Place#supprimerDé 
	 * @author Tom FRAISSE
	 */
	public void supprimerDé() {	
		this.gainsDe = null;
	}
	/** 
	 * Cette méthode permet d'obtenir le dé placé sur la place
	 *  
	 * @return Instance du dé placé sur la place
	 * 
	 * @see Place#getDe 
	 * @author Tom FRAISSE
	 */
	public De getDe() {
		return this.gainsDe;
	}
	
	/** 
	 * Cette méthode permet de mettre à jour le prix de la place
	 *  
	 * @param        valeur : Tableau contenant le prix de la place selon la ressource 
	 * 
	 * @see Plae#setPrix 
	 * @author Tom FRAISSE
	 */
	protected void setPrix(Integer[] valeur) {
		if(valeur.length == 3) {
			for(int i=0; i < 3; ++i) {
				this.prix[i] = valeur[i];
			}
		}
	}
	/** 
	 * Cette méthode permet d'obtenir le prix de la place.
	 *  
	 * @return Tableau contenant le prix de la place
	 * 
	 * @see Place#getPrix
	 * @author Tom FRAISSE
	 */
	public int[] getPrix() {
		return this.prix;
	}
	/** 
	 * Cette méthode permet d'obtenir la couleur de la face visible de la place.
	 *  
	 * @return Couleur de la face visible
	 * 
	 * @see Place#getFaceVisible 
	 * @author Tom FRAISSE
	 */
	public int getFaceVisible() {
		return this.faceVisible;
	}
	/** 
	 * Cette méthode permet de retourner la place.
	 * Swap les valeurs des variables faceVisible et faceCachée
 	 *  
	 * 
	 * @see Place#retourner 
	 * @author Tom FRAISSE
	 */
	public void retourner() {
		int tempFace = this.faceVisible;
		this.faceVisible = this.faceCachée;
		this.faceCachée = tempFace;
	}
	/** 
	 * Cette méthode permet de savoir si il y a plusieurs choix de ressource possible
	 *  
	 * @return True : Plusieurs choix  False : Un seul choix
	 * 
	 * @see Place#isMultiplePriceChoice 
	 * @author Tom FRAISSE
	 */
	public boolean isMultiplePriceChoice() {
		return (this.prix[0] !=0 && this.prix[1] != 0 && this.prix[2] != 0); 
	}
	/** 
	 * Cette méthode permet d'obtenir une chaine de caractère contenant les propriétés de l'objet
	 *  
	 * @return Chaine de caractère
	 * 
	 * @see Place#toString 
	 * @author Tom FRAISSE
	 */
	public String toString() {
		String val;
		if(this.gainsDe != null) {
			val = new String("Face visible : " + this.getFaceVisible() +" Prix : " + this.prix[0] + "/" + this.prix[1] +"/" + this.prix[2] + " Dé : " + this.gainsDe.toString() );
		}else {
			val = new String("Face visible : " + this.getFaceVisible() +" Prix : " + this.prix[0] + "/" + this.prix[1] +"/" + this.prix[2] +  " Dé : ");
		}
		return val;
	}
}
