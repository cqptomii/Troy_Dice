package feuille;

/**
 * Classe modélisant les batiments de Prestige
 * 
 * @version 1.0
 *
 * @see Prestige
 * @author Tom FRAISSE
 */
public class Prestige extends Batiment {
	/*
	 * 1 - Protection de la section
	 * 2 - Ressource supp
	 * 3 - Point supplémentaire (Labo)
	 */
	private int bonus;
	/*	Tableau de trois entiers
	 * 1 - gain crédit
	 * 2 - gain connaissance
	 * 3 - gain experience
	*/
	private int [] recompenseBonus;
	private int colorDeMult;
	
	private boolean multiplicateur;
	private int indexMult;
	
	/** 
	 * Cette méthode permet d'instancier un batiment de travail
	 *  
	 * @param       parent : Feuille de jeu associé au batiment 
	 * @param       name : Nom du batiment 
	 * @param		nombreHabitants : nombre d'habitant du batiment
	 * @param 		colorH : Couleur des habitants
	 * @param 		bonus : index du bonus 
	 * @param 		recompense : montant des récompense de batiment
	 * @param 		colorDe : couleur du dé multiplicateur
	 * @param 		multiplicateur : état du multiplicateur (True : activé / False : desactivé)
	 * @param		indexMult : index de la section multiplié
	 * @param       colorB : couleur du batiment
	 * 
	 * @see Prestige#Prestige
	 * @author  Tom FRAISSE 
	 */
	public Prestige(FeuilleDeJeu parent,String name, int nombreHabitants,int colorH,int bonus,int [] recompense,int colorDe,boolean multiplicateur,int indexMult,int colorB) {
		super(parent,name, nombreHabitants,colorH,colorB);
		if(bonus >=1 && bonus <=3)
			this.bonus = bonus;
		this.recompenseBonus = recompense;
		this.multiplicateur = multiplicateur;
		this.indexMult = indexMult;
		this.colorDeMult = colorDe;
	}
	
	/** 
	 * Cette méthode permet de construire le batiment
	 *  
	 * @param       nbDe : nombre de dé de la couleur de la place 
	 * 
	 * @see Prestige#construire
	 * @author   Tom FRAISSE
	 */
	@Override
	public void construire(int nbDe) {
		//construire le batiment
		this.etat =1;
		
		//Appliquer l'effet spécial du batiment
		if(bonus == 0 ) {
			this.currFeuille.protegerSection(this.currSection.getIndex());
		}else if(bonus == 1) { 
			//Mettre à jour le montant des récompense bonus du batiment
			for(int i =0; i < this.recompenseBonus.length; ++i) {
				this.recompenseBonus[i] *= nbDe;
			}
			this.habitant *= nbDe;
			System.out.println("Dé multiplié : " + this.colorDeMult + " nbDe : " + nbDe + "hab : " + this.habitant);
		}else {
			//Mettre à jour le multiplicateur de batiments
			this.currFeuille.addMult();
			if(this.multiplicateur)
				this.currFeuille.getQuartier(this.indexMult).addMultPrestige();
			else
				this.currFeuille.getQuartier(this.indexMult).addMultTravail();
		
		}
	}
	
	/** 
	 * Cette méthode permet de recuperer le montant des récompenses du batiment
	 *  
	 * @return Tableau contenant le montant des récompenses
	 * 
	 * @see Prestige#getRecompenseBonus
	 * @author   Tom FRAISSE
	 */
	public int[] getRecompenseBonus() {
		return this.recompenseBonus;
	}
	
	/** 
	 * Cette méthode permet d'obtenir la couleur du dé lié au multiplicateur
	 *  
	 * @return couleur du dé
	 * 
	 * @see Prestige#getColorDe
	 * @author   Tom FRAISSE
	 */
	public int getColorDe() {
		return this.colorDeMult;
	}
	
	/** 
	 * Cette méthode permet d'obtenir une chaine de caractère qui décrite le batiment
	 *  
	 * @return Chaine de caractère 
	 * 
	 * @see Prestige#toString
	 * @author   Tom FRAISSE
	 */
	public String toString() {
		String temp = new String(super.toString() + "Bonus : " + this.bonus);
		System.out.println(temp);
		return temp;
	}
}
