package plateau;
import java.util.Random;
public class Place {
	/* COULEUR DES DEUX FACES
	 * 1 : gris
	 * 2 : orange
	 * 3 : bleu
	*/
	private int faceVisible;
	private int faceCachée;
	
	/*	Tableau de trois entiers
	 * 1 - coût Deniers
	 * 2 - coût connaissance
	 * 3 - coût experience
	*/
	private int[] prix;
	private De gainsDe = null;
	
	public Place() {
		Random randomEngine = new Random();
		
		this.faceVisible = randomEngine.nextInt(1, 4);
		this.faceCachée = randomEngine.nextInt(1, 4);
		this.prix = new int[3];
	}
	public void placerDé(De valeur) {
		if(valeur == null)
			return;
		
		if(this.gainsDe == null) {
			this.gainsDe = valeur;
			if(this.gainsDe.getCouleur() != 0)
				this.gainsDe.setColorFromPlace(this);
		}
	}
	
	public void supprimerDé() {	
		this.gainsDe = null;
	}
	
	public De getDe() {
		return this.gainsDe;
	}
	
	
	protected void setPrix(Integer[] valeur) {
		if(valeur.length == 3) {
			for(int i=0; i < 3; ++i) {
				this.prix[i] = valeur[i];
			}
		}
	}
	public int[] getPrix() {
		return this.prix;
	}	
	public int getFaceVisible() {
		return this.faceVisible;
	}
	public void retourner() {
		int tempFace = this.faceVisible;
		this.faceVisible = this.faceCachée;
		this.faceCachée = tempFace;
	}
	
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
