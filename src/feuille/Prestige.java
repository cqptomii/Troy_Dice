package feuille;

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
	
	public Prestige(FeuilleDeJeu parent,String name, int nombreHabitants,int colorH,int bonus,int [] recompense,int colorDe,boolean multiplicateur,int indexMult,int colorB) {
		super(parent,name, nombreHabitants,colorH,colorB);
		if(bonus >=1 && bonus <=3)
			this.bonus = bonus;
		this.recompenseBonus = recompense;
		this.multiplicateur = multiplicateur;
		this.indexMult = indexMult;
		this.colorDeMult = colorDe;
	}
	
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
	public int[] getRecompenseBonus() {
		return this.recompenseBonus;
	}
	public int getColorDe() {
		return this.colorDeMult;
	}
	public String toString() {
		String temp = new String(super.toString() + "Bonus : " + this.bonus);
		System.out.println(temp);
		return temp;
	}
}
