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
	private boolean multiplicateur;
	private int indexMult;
	public Prestige(FeuilleDeJeu parent,String name, int nombreHabitants,int colorH,int bonus,int [] recompense,boolean multiplicateur,int indexMult,int colorB) {
		super(parent,name, nombreHabitants,colorH,colorB);
		if(bonus >=1 && bonus <=3)
			this.bonus = bonus;
		this.recompenseBonus = recompense;
		this.multiplicateur = multiplicateur;
		this.indexMult = indexMult;
	}

	@Override
	public void construire() {
		//construire le batiment
		this.etat =1;
		
		//Appliquer l'effet spécial du batiment
		if(bonus == 1 ) {
			this.currFeuille.protegerSection(this.currSection.getIndex());
		}else if(bonus == 2) {
			int amount = this.currFeuille.getBatimentConstruit(true,this.colorBatiment);
			
			//Ajouter les ressources au joueur
		}else {
			//Mettre à jour le multiplicateur de batiments
			this.currFeuille.addMult();
			if(this.multiplicateur)
				this.currFeuille.getQuartier(this.indexMult).addMultPrestige();
			else
				this.currFeuille.getQuartier(this.indexMult).addMultTravail();
		
		}
		
		//verifier l'état des eventuels liens
		if(this.bonusLien != null) {
			if(this.bonusLien.lienEtablis()) {
				int[] value = this.bonusLien.getRecompense();
				
				//Mettre à jour les ressources du joueurs
				
			}
		}
	}

	public String toString() {
		String temp = new String(super.toString() + "Bonus : " + this.bonus);
		System.out.println(temp);
		return temp;
	}
}
