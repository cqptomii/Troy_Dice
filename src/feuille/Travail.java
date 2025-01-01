package feuille;

/**
 * Classe modélisant les batiments de travail
 * 
 * @version 1.0
 *
 * @see Travail
 * @author Tom FRAISSE
 */
public class Travail extends Batiment{

	/** 
	 * Cette méthode permet de Construire un batiment de travail
	 *  
	 * @param       parent : Feuille de jeu associé au batiment 
	 * @param       name : Nom du batiment 
	 * @param		nombreHabitants : nombre d'habitant du batiment
	 * @param 		colorH : Couleur des habitants
	 * @param       colorB : couleur du batiment 
	 * 
	 * @see Travail#Travail
	 * @author  Tom FRAISSE 
	 */
	public Travail(FeuilleDeJeu parent,String name, int nombreHabitants,int colorH,int colorB) {
		super(parent,name, nombreHabitants,colorH,colorB);
	}
	
	/** 
	 * Cette méthode permet de construire le batiment
	 *  
	 * @param       nbDe : nombre de dé de la couleur de la place 
	 * 
	 * @see Travail#construire
	 * @author   Tom FRAISSE
	 */
	@Override
	public void construire(int nbDe) {
		//construire le batiment
		this.etat = 1;
	}
}
